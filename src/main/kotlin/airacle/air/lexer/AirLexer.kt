package airacle.air.lexer

import java.io.InputStreamReader

class AirLexer {
    // never fail
    fun lex(source: String, ignoreDelimiter: Boolean = true): List<AirToken> {
        return lexMultiple(source, ignoreDelimiter)
    }

    // never fail
    fun lex(sourceReader: InputStreamReader, ignoreDelimiter: Boolean = true): List<AirToken> {
        return lexMultiple(sourceReader, ignoreDelimiter)
    }
}

private fun lexMultiple(source: String, ignoreDelimiter: Boolean): List<AirToken> {
    val tokens = mutableListOf<AirToken>()
    if (source.isEmpty()) {
        return tokens
    }

    var start = 0
    while (start < source.length) {
        val pair = lexSingle(source, start)
        if (pair.first !is DelimiterToken || !ignoreDelimiter) {
            tokens.add(pair.first)
        }
        start = pair.second
    }
    return tokens
}

private fun lexSingle(source: String, start: Int): Pair<AirToken, Int> {
    val lexer = getSingleLexer(source[start])
    lexer.first(source[start])
    var local = 1
    var global = start + local
    while (global < source.length) {
        if (lexer.follow(source[global], local)) {
            local += 1
            global += 1
        } else {
            break
        }
    }
    return Pair(lexer.lex(source.substring(start, global)), global)
}

private fun lexMultiple(sourceReader: InputStreamReader, ignoreDelimiter: Boolean): List<AirToken> {
    val tokens = mutableListOf<AirToken>()

    val ret = sourceReader.read()
    if (ret == -1) {
        return tokens
    }

    var key: Char? = ret.toChar()
    while (key != null) {
        val pair = lexSingle(key, sourceReader)
        if (pair.first !is DelimiterToken || !ignoreDelimiter) {
            tokens.add(pair.first)
        }
        key = pair.second
    }
    return tokens
}

private fun lexSingle(key: Char, sourceReader: InputStreamReader): Pair<AirToken, Char?> {
    val lexer = getSingleLexer(key)
    lexer.first(key)
    val builder = StringBuilder().append(key)

    var ret = sourceReader.read()
    var index = 1
    var next: Char? = null
    while (ret != -1) {
        val char = ret.toChar()
        if (lexer.follow(char, index)) {
            ret = sourceReader.read()
            index += 1
        } else {
            next = char
            break
        }
    }
    val token = lexer.lex(builder.toString())
    return Pair(token, next)
}

private fun isAsciiLetter(char: Char): Boolean {
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
    return isAsciiLetter(char) || isAsciiDigit(char) || isAsciiSymbol(char) || isAsciiWhiteSpace(char)
}

private fun getSingleLexer(char: Char): IPrimitiveLexer {
    if (isAsciiLetter(char)) {
        return SimpleNameLexer
    }
    if (isAsciiDigit(char)) {
        return NumberLexer()
    }
    if (isAsciiWhiteSpace(char)) {
        return DelimiterLexer
    }
    return when (char) {
        MetaLexer.KEY -> MetaLexer
        StringLexer.KEY -> StringLexer()
        CommentLexer.KEY -> CommentLexer()
        ComplexNameLexer.KEY -> ComplexNameLexer
        else -> if (isAsciiSymbol(char)) {
            SymbolLexer
        } else {
            UnknownLexer
        }
    }
}

interface IPrimitiveLexer {
    fun first(char: Char)

    fun follow(char: Char, index: Int): Boolean

    fun lex(primitive: String): AirToken
}

object UnknownLexer : IPrimitiveLexer {
    override fun first(char: Char) {

    }

    override fun follow(char: Char, index: Int): Boolean {
        return !isAscii(char)
    }

    override fun lex(primitive: String): AirToken {
        return UnknownToken(primitive)
    }
}

object DelimiterLexer : IPrimitiveLexer {
    override fun first(char: Char) {

    }

    override fun follow(char: Char, index: Int): Boolean {
        return isAsciiWhiteSpace(char)
    }

    override fun lex(primitive: String): AirToken {
        return DelimiterToken
    }
}

object SimpleNameLexer : IPrimitiveLexer {
    override fun first(char: Char) {

    }

    override fun follow(char: Char, index: Int): Boolean {
        return isAsciiLetter(char) || isAsciiDigit(char) || char == '_'
    }

    override fun lex(primitive: String): AirToken {
        return NameToken(primitive)
    }
}

object ComplexNameLexer : IPrimitiveLexer {
    const val KEY = '`'

    override fun first(char: Char) {

    }

    override fun follow(char: Char, index: Int): Boolean {
        return isAsciiLetter(char) || isAsciiDigit(char) || isAsciiSymbol(char)
    }

    override fun lex(primitive: String): AirToken {
        return NameToken(primitive)
    }
}

object MetaLexer : IPrimitiveLexer {
    const val KEY = '\''

    override fun first(char: Char) {

    }

    override fun follow(char: Char, index: Int): Boolean {
        return isAsciiLetter(char) || isAsciiDigit(char) || isAsciiSymbol(char)
    }

    override fun lex(primitive: String): AirToken {
        return when (val id = primitive.substring(1)) {
            "t", "true" -> TrueToken
            "f", "false" -> FalseToken
            // TODO: 10/13/21 recognize more keyword
            else -> InvalidToken(MetaToken::class, id)
        }
    }
}

object SymbolLexer : IPrimitiveLexer {
    override fun first(char: Char) {

    }

    override fun follow(char: Char, index: Int): Boolean {
        return false
    }

    override fun lex(primitive: String): AirToken {
        return when (primitive[0]) {
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

class CommentLexer : IPrimitiveLexer {
    companion object {
        const val KEY = '^'
    }

    private var end = false
    override fun first(char: Char) {

    }

    override fun follow(char: Char, index: Int): Boolean {
        if (end) {
            return false
        }
        if (char == '^') {
            end = true
        }
        return true
    }

    override fun lex(primitive: String): AirToken {
        if (!end) {
            return InvalidToken(CommentToken::class, primitive)
        }
        return CommentToken(primitive.substring(1, primitive.length - 1))
    }
}

class NumberLexer : IPrimitiveLexer {
    private var meetDot = false

    override fun first(char: Char) {

    }

    // TODO: 10/14/21 may support 0b111, 0xfff, 1.111e-3, 0xff.11p-2
    override fun follow(char: Char, index: Int): Boolean {
        if (char == '.') {
            return if (meetDot) {
                false
            } else {
                meetDot = true
                true
            }
        }
        return isAsciiDigit(char)
    }

    override fun lex(primitive: String): AirToken {
        return if (meetDot) {
            FloatToken(primitive.toDouble())
        } else {
            IntegerToken(primitive.toLong())
        }
    }
}

class StringLexer : IPrimitiveLexer {
    companion object {
        const val KEY = '"'
    }

    private var end = false
    private var lastEscape = false
    private val stringBuilder = StringBuilder()

    override fun first(char: Char) {

    }

    // TODO: 10/14/21 may support \uxxxx format
    override fun follow(char: Char, index: Int): Boolean {
        if (end) {
            return false
        }

        if (lastEscape) {
            when (char) {
                't' -> stringBuilder.append('\t')
                'n' -> stringBuilder.append('\n')
                'r' -> stringBuilder.append('\r')
                'b' -> stringBuilder.append('\b')
                else -> stringBuilder.append(char)
            }
            lastEscape = false
            return true
        }

        if (char == '"') {
            end = true
            return true
        }

        if (char == '\\') {
            lastEscape = true
        } else if (char == ' ' || !isAsciiWhiteSpace(char)) {
            stringBuilder.append(char)
        }
        return true
    }

    override fun lex(primitive: String): AirToken {
        if (!end) {
            return InvalidToken(StringToken::class, primitive)
        }
        return StringToken(stringBuilder.toString())
    }
}
