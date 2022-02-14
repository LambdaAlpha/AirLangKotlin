package airacle.air.core.lexer

import java.math.BigInteger
import java.util.regex.Matcher
import java.util.regex.Pattern

object AirLexerConfig : IAirLexerConfig {
    private val lexers: Array<IAirRegexLexer?> = Array(128) {
        init(it.toChar())
    }

    private fun init(char: Char): IAirRegexLexer? {
        if (isAsciiWhiteSpace(char)) {
            return DelimiterLexer
        }
        if (isAsciiAlpha(char)) {
            return AlphaStringLexer
        }
        if (isAsciiDigit(char) || char == '+' || char == '-') {
            return NumberLexer
        }

        return when (char) {
            UnitLexer.KEY -> UnitLexer
            BoolLexer.KEY_TRUE, BoolLexer.KEY_FALSE -> BoolLexer
            FullStringLexer.KEY -> FullStringLexer
            else -> if (isAsciiSymbol(char)) {
                SymbolStringLexer
            } else {
                null
            }
        }
    }

    override fun dispatch(char: Char): IAirRegexLexer? {
        if (char.code >= 128) {
            return null
        }
        return lexers[char.code]
    }

    override fun filter(airToken: AirToken): Boolean {
        return airToken !is DelimiterToken
    }

    private fun isAsciiAlpha(char: Char): Boolean {
        return (char.code >= 'A'.code && char.code <= 'Z'.code)
                || (char.code >= 'a'.code && char.code <= 'z'.code)
    }

    private fun isAsciiDigit(char: Char): Boolean {
        return char.code >= '0'.code && char.code <= '9'.code
    }

    private fun isAsciiSymbol(char: Char): Boolean {
        return when (char) {
            '`', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+',
            '[', '{', ']', '}', '\\', '|', ';', ':', '\'', '"', ',', '<', '.', '>', '/', '?' -> true
            else -> false
        }
    }

    private fun isAsciiWhiteSpace(char: Char): Boolean {
        return when (char) {
            ' ', '\t', '\n', '\r' -> true
            else -> false
        }
    }
}

object DelimiterLexer : IAirRegexLexer {
    private val pattern = Pattern.compile("[ \r\n\t]+")

    override fun pattern(): Pattern {
        return pattern
    }

    override fun parse(match: Matcher): AirToken {
        return DelimiterToken
    }
}

object UnitLexer : IAirRegexLexer {
    const val KEY = '|'
    private val pattern = Pattern.compile("\\|")

    override fun pattern(): Pattern {
        return pattern
    }

    override fun parse(match: Matcher): AirToken {
        return UnitToken
    }
}

object BoolLexer : IAirRegexLexer {
    const val KEY_TRUE = '/'
    const val KEY_FALSE = '\\'
    private val pattern = Pattern.compile("[\\\\/]")

    override fun pattern(): Pattern {
        return pattern
    }

    override fun parse(match: Matcher): AirToken {
        return BoolToken.valueOf(match.group() == "/")
    }
}

object SymbolStringLexer : IAirRegexLexer {
    private val pattern = Pattern.compile("\\p{Punct}")

    override fun pattern(): Pattern {
        return pattern
    }

    override fun parse(match: Matcher): AirToken {
        return when (match.group()) {
            "`" -> SymbolStringToken.BackQuote
            "~" -> SymbolStringToken.Tilde
            "!" -> SymbolStringToken.Exclamation
            "@" -> SymbolStringToken.At
            "#" -> SymbolStringToken.Sharp
            "$" -> SymbolStringToken.Dollar
            "%" -> SymbolStringToken.Percent
            "^" -> SymbolStringToken.Hat
            "&" -> SymbolStringToken.Ampersand
            "*" -> SymbolStringToken.Star
            "(" -> SymbolStringToken.LCircle
            ")" -> SymbolStringToken.RCircle
            "-" -> SymbolStringToken.Minus
            "_" -> SymbolStringToken.Underscore
            "=" -> SymbolStringToken.Equal
            "+" -> SymbolStringToken.Plus
            "[" -> SymbolStringToken.LSquare
            "{" -> SymbolStringToken.LCurly
            "]" -> SymbolStringToken.RSquare
            "}" -> SymbolStringToken.RCurly
            "\\" -> SymbolStringToken.LSlash
            "|" -> SymbolStringToken.MSlash
            ";" -> SymbolStringToken.Semicolon
            ":" -> SymbolStringToken.Colon
            "\'" -> SymbolStringToken.SingleQuote
            "\"" -> SymbolStringToken.DoubleQuote
            "," -> SymbolStringToken.Comma
            "<" -> SymbolStringToken.LAngle
            "." -> SymbolStringToken.Period
            ">" -> SymbolStringToken.RAngle
            "/" -> SymbolStringToken.RSlash
            "?" -> SymbolStringToken.QuestionMark
            // never
            else -> FullStringToken(match.group())
        }
    }
}

object AlphaStringLexer : IAirRegexLexer {
    private val pattern = Pattern.compile("\\p{Alpha}\\w*")

    override fun pattern(): Pattern {
        return pattern
    }

    override fun parse(match: Matcher): AirToken {
        return AlphaStringToken(match.group())
    }
}

object FullStringLexer : IAirRegexLexer {
    const val KEY = '"'
    private val pattern = Pattern.compile("\"(?:[^\"\\\\]|\\\\[srnt\\\\'\"]|\\\\u[a-fA-F0-9]{4})*+\"")
    private val delimiterPattern = Regex(" *[\n\r\t]+ *")

    override fun pattern(): Pattern {
        return pattern
    }

    override fun parse(match: Matcher): AirToken {
        var s = match.group(0)!!
        s = s.replace(delimiterPattern, "")
        val builder = StringBuilder()

        var escape = false
        var i = 1
        val last = s.length - 1
        while (i < last) {
            val c = s[i]
            if (escape) {
                val escaped = when (c) {
                    't' -> '\t'
                    'n' -> '\n'
                    'r' -> '\r'
                    's' -> ' '
                    'u', 'U' -> {
                        val code = s.substring(i + 1, i + 5).toInt(16).toChar()
                        i += 4
                        code
                    }
                    else -> c
                }
                builder.append(escaped)
                escape = false
            } else {
                if (c == '\\') {
                    escape = true
                } else {
                    builder.append(c)
                }
            }
            i += 1
        }
        return FullStringToken(builder.toString())
    }
}

object NumberLexer : IAirRegexLexer {
    // binary, decimal, hexadecimal integers and decimal float numbers
    // match float numbers first
    private val pattern = Pattern.compile(
        "[-+]?(?:" +
                "\\d+[\\d_]*\\.[\\d_]*(?:[eE][-+]?\\d+)?" +
                "|" +
                "0[xX][a-fA-F0-9]+[a-fA-F0-9_]*" +
                "|" +
                "0[bB][01]+[01_]*" +
                "|" +
                "[0-9]+[0-9_]*" +
                ")"
    )


    override fun pattern(): Pattern {
        return pattern
    }

    override fun parse(match: Matcher): AirToken {
        var s = match.group()
        // remove delimiters
        s = s.replace("_", "")

        var i = 0

        val hasSign = s.length > 1 && (s[0] == '+' || s[0] == '-')
        val negative = hasSign && s[i] == '-'
        if (hasSign) {
            i += 1
        }

        val hasRadix = s.length > i + 1 && "xXbB".contains(s[i + 1])
        var bitMode = false
        var radix = 10
        if (hasRadix) {
            i += 1
            when (s[i]) {
                'x' -> {
                    radix = 16
                    bitMode = false
                }
                'X' -> {
                    radix = 16
                    bitMode = true
                }
                'b' -> {
                    radix = 2
                    bitMode = false
                }
                'B' -> {
                    radix = 2
                    bitMode = true
                }
            }
        }

        if (hasRadix) {
            i += 1
        }

        s = s.substring(i)
        val isReal = s.contains(".")
        if (isReal) {
            s = (if (negative) "-" else "+") + s
            val value = s.toBigDecimal()
            return DecimalToken.valueOf(value)
        }

        val value = s.toBigInteger(radix)
        return if (negative) {
            if (bitMode) {
                when (radix) {
                    16 -> IntToken.valueOf(value - BigInteger.ONE.shl(s.length * 4))
                    2 -> IntToken.valueOf(value - BigInteger.ONE.shl(s.length))
                    else -> IntToken.valueOf(value.negate())
                }
            } else {
                IntToken.valueOf(value.negate())
            }
        } else {
            IntToken.valueOf(value)
        }
    }
}
