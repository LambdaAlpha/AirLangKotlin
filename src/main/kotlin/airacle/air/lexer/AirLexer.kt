package airacle.air.lexer

interface IAirLexerConfig {
    // return a regex lexer, or null if char is illegal
    fun dispatch(char: Char): IAirRegexLexer?

    // return true if the token should be kept
    fun filter(airToken: AirToken): Boolean
}

interface IAirRegexLexer {
    fun pattern(): Regex
    fun parse(match: MatchResult): AirToken
}

class AirLexerError(
    pattern: String,
    source: String,
    index: Int
) : Error(
    "pattern $pattern matching failed at $index: " +
            source.substring(index, (index + 100).coerceAtMost(source.length))
)

class AirLexer(
    val config: IAirLexerConfig
) {
    fun lex(source: String): List<AirToken> {
        val tokens = mutableListOf<AirToken>()
        lex(source, tokens)
        return tokens
    }

    private fun lex(source: String, tokens: MutableList<AirToken>) {
        if (source.isEmpty()) {
            return
        }

        var start = 0
        while (start < source.length) {
            val lexer = config.dispatch(source[start]) ?: throw AirLexerError("", source, start)
            val pattern = lexer.pattern()
            val matchResult = pattern.find(source, start)
            if (matchResult == null) {
                throw AirLexerError(pattern.pattern, source, start)
            } else {
                val token = lexer.parse(matchResult)
                if (config.filter(token)) {
                    tokens.add(token)
                }
                start = matchResult.range.last + 1
            }
        }
    }
}

class AirLexerConfig : IAirLexerConfig {
    override fun dispatch(char: Char): IAirRegexLexer? {
        if (isAsciiWhiteSpace(char)) {
            return DelimiterLexer
        }
        if (isAsciiAlpha(char) || char == NameLexer.KEY) {
            return NameLexer
        }
        if (isAsciiDigit(char)) {
            return NumberLexer()
        }

        return when (char) {
            UnitLexer.KEY -> UnitLexer
            BoolLexer.KEY_TRUE, BoolLexer.KEY_FALSE -> BoolLexer
            StringLexer.KEY -> StringLexer()
            CommentLexer.KEY -> CommentLexer()
            else -> if (isAsciiSymbol(char)) {
                SymbolLexer
            } else {
                null
            }
        }
    }

    override fun filter(airToken: AirToken): Boolean {
        return airToken !is DelimiterToken &&
                airToken !is CommentToken
    }

    private fun isAsciiAlpha(char: Char): Boolean {
        return when (char) {
            'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z' -> true
            else -> false
        }
    }

    private fun isAsciiDigit(char: Char): Boolean {
        return when (char) {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> true
            else -> false
        }
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

    private fun isAscii(char: Char): Boolean {
        return isAsciiAlpha(char) || isAsciiDigit(char) || isAsciiSymbol(char) || isAsciiWhiteSpace(char)
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

object NameLexer : IAirRegexLexer {
    const val KEY = '`'
    private val pattern = Regex("`\\p{Graph}*|\\p{Alpha}\\w*")

    override fun pattern(): Regex {
        return pattern
    }

    override fun parse(match: MatchResult): AirToken {
        return NameToken(match.value)
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

object SymbolLexer : IAirRegexLexer {
    private val pattern = Regex("\\p{Punct}")

    override fun pattern(): Regex {
        return pattern
    }

    override fun parse(match: MatchResult): AirToken {
        return when (match.value[0]) {
            '`' -> BackQuoteToken
            '~' -> TildeToken
            '!' -> ExclamationToken
            '@' -> AtToken
            '#' -> NumToken
            '$' -> DollarToken
            '%' -> PercentToken
            '^' -> HatToken
            '&' -> AmpersandToken
            '*' -> AsteriskToken
            '(' -> LPToken
            ')' -> RPToken
            '-' -> MinusToken
            '_' -> UnderscoreToken
            '=' -> EqualToken
            '+' -> PlusToken
            '[' -> LSToken
            '{' -> LCToken
            ']' -> RSToken
            '}' -> RCToken
            '\\' -> LSlashToken
            '|' -> MSlashToken
            ';' -> SemicolonToken
            ':' -> ColonToken
            '\'' -> SingleQuoteToken
            '"' -> DoubleQuoteToken
            ',' -> CommaToken
            '<' -> LTToken
            '.' -> PeriodToken
            '>' -> GTToken
            '/' -> RSlashToken
            '?' -> QuestionMarkToken
            else -> QuestionMarkToken
        }
    }
}

class CommentLexer : IAirRegexLexer {
    companion object {
        const val KEY = '^'

        // begin and end with ^, \ to escape any character
        private val pattern = Regex("\\^((?:[^^\\\\]|\\\\.)*+)\\^")
    }

    override fun pattern(): Regex {
        return pattern
    }

    override fun parse(match: MatchResult): AirToken {
        return CommentToken(match.groups[1]!!.value)
    }
}

class StringLexer : IAirRegexLexer {
    companion object {
        const val KEY = '"'
        private val pattern = Regex("\"((?:[^\"\\\\]|\\\\[rnt\\\\'\"]|\\\\u[a-fA-F0-9]{4})*+)\"")
        private val delimiterPattern = Regex("[\n\r\t]+")
        private val escapePattern = Regex("\\\\([rnt\\\\'\"])")
        private val unicodePattern = Regex("\\\\u([a-fA-F0-9]{4})")
    }

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
        return StringToken(s)
    }
}

class NumberLexer : IAirRegexLexer {
    companion object {
        // binary, decimal, hexadecimal integers and decimal float numbers
        // prefix with 0 if not start with digit
        // match binary or hexadecimal first because otherwise the prefix 0 will always match decimal integer pattern
        private val pattern = Regex(
            "0[-+]?(?:[xX][a-fA-F0-9]+[a-fA-F0-9_]*|[bB][01]+[01_]*)" +
                    "|(?:0[-+])?\\d+[_\\d]*(?:\\.\\d+[_\\d]*(?:[eE][-+]?\\d+)?)?"
        )
    }

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
