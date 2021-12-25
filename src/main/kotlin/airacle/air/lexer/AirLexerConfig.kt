package airacle.air.lexer

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
        if (isAsciiDigit(char)) {
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
    private val pattern = Regex("[ \r\n\t]+")

    override fun pattern(): Regex {
        return pattern
    }

    override fun parse(match: MatchResult): AirToken {
        return DelimiterToken
    }
}

object UnitLexer : IAirRegexLexer {
    const val KEY = '|'
    private val pattern = Regex("\\|")

    override fun pattern(): Regex {
        return pattern
    }

    override fun parse(match: MatchResult): AirToken {
        return UnitToken
    }
}

object BoolLexer : IAirRegexLexer {
    const val KEY_TRUE = '/'
    const val KEY_FALSE = '\\'
    private val pattern = Regex("[\\\\/]")

    override fun pattern(): Regex {
        return pattern
    }

    override fun parse(match: MatchResult): AirToken {
        return if (match.value == "/") TrueToken else FalseToken
    }
}

object SymbolStringLexer : IAirRegexLexer {
    private val pattern = Regex("\\p{Punct}+")

    override fun pattern(): Regex {
        return pattern
    }

    override fun parse(match: MatchResult): AirToken {
        return when (match.value) {
            "`" -> BackQuoteToken
            "~" -> TildeToken
            "!" -> ExclamationToken
            "@" -> AtToken
            "#" -> NumToken
            "$" -> DollarToken
            "%" -> PercentToken
            "^" -> HatToken
            "&" -> AmpersandToken
            "*" -> AsteriskToken
            "(" -> LCircleToken
            ")" -> RCircleToken
            "-" -> MinusToken
            "_" -> UnderscoreToken
            "=" -> EqualToken
            "+" -> PlusToken
            "[" -> LSquareToken
            "{" -> LCurlyToken
            "]" -> RSquareToken
            "}" -> RCurlyToken
            "\\" -> LSlashToken
            "|" -> MSlashToken
            ";" -> SemicolonToken
            ":" -> ColonToken
            "\'" -> SingleQuoteToken
            "\"" -> DoubleQuoteToken
            "," -> CommaToken
            "<" -> LAngleToken
            "." -> PeriodToken
            ">" -> RAngleToken
            "/" -> RSlashToken
            "?" -> QuestionMarkToken
            else -> FullSymbolStringToken(match.value)
        }
    }
}

object AlphaStringLexer : IAirRegexLexer {
    private val pattern = Regex("\\p{Alpha}\\w*")

    override fun pattern(): Regex {
        return pattern
    }

    override fun parse(match: MatchResult): AirToken {
        return AlphaStringToken(match.value)
    }
}

object FullStringLexer : IAirRegexLexer {
    const val KEY = '"'
    private val pattern = Regex("\"((?:[^\"\\\\]|\\\\[rnt\\\\'\"]|\\\\u[a-fA-F0-9]{4})*+)\"")
    private val delimiterPattern = Regex("[\n\r\t]+")
    private val escapePattern = Regex("\\\\([rnt\\\\'\"])")
    private val unicodePattern = Regex("\\\\u([a-fA-F0-9]{4})")

    override fun pattern(): Regex {
        return pattern
    }

    override fun parse(match: MatchResult): AirToken {
        var s = match.groups[1]!!.value
        s = s.replace(delimiterPattern, "")
        s = s.replace(escapePattern) {
            when (val c = it.groups[1]!!.value) {
                "t" -> "\t"
                "n" -> "\n"
                "r" -> "\r"
                else -> c
            }
        }
        s = s.replace(unicodePattern) {
            it.groups[1]!!.value.toInt(16).toChar().toString()
        }
        return FullStringToken(s)
    }
}

object NumberLexer : IAirRegexLexer {
    // binary, decimal, hexadecimal integers and decimal float numbers
    // prefix with 0 if not start with digit
    // match binary or hexadecimal first because otherwise the prefix 0 will always match decimal integer pattern
    private val pattern = Regex(
        "0[-+]?(?:[xX][a-fA-F0-9]+[a-fA-F0-9_]*|[bB][01]+[01_]*)" +
                "|(?:0[-+])?\\d+[_\\d]*(?:\\.\\d+[_\\d]*(?:[eE][-+]?\\d+)?)?"
    )


    override fun pattern(): Regex {
        return pattern
    }

    override fun parse(match: MatchResult): AirToken {
        var s = match.value
        // remove delimiters
        s = s.replace("_", "")
        val negative = s.length > 2 && s[1] == '-'
        // remove sign
        if (s.length > 2 && (s[1] == '-' || s[1] == '+')) {
            s = s.substring(2)
        }
        return if (s.contains('x', true)) {
            val value = s.substring(1).toLong(16)
            IntegerToken(if (negative) -value else value)
        } else if (s.contains('b', true)) {
            val value = s.substring(1).toLong(2)
            IntegerToken(if (negative) -value else value)
        } else if (s.contains('.')) {
            val value = s.toDouble()
            FloatToken(if (negative) -value else value)
        } else {
            val value = s.toLong()
            IntegerToken(if (negative) -value else value)
        }
    }
}
