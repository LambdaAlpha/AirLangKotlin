package airacle.air.lexer

import airacle.air.api.IAirLexer
import java.util.regex.Matcher
import java.util.regex.Pattern

interface IAirLexerConfig {
    // return a regex lexer, or null if char is illegal
    fun dispatch(char: Char): IAirRegexLexer?

    // return true if the token should be kept
    fun filter(airToken: AirToken): Boolean
}

interface IAirRegexLexer {
    fun pattern(): Pattern
    fun parse(match: Matcher): AirToken
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
    val version: AirLexerVersion
) : IAirLexer<AirToken> {
    private val config: IAirLexerConfig

    init {
        when (version) {
            AirLexerVersion.V0 -> config = AirLexerConfig
        }
    }

    override fun lex(source: String): List<AirToken> {
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
            val matchResult = pattern.matcher(source)
                .useAnchoringBounds(false)
                .useTransparentBounds(true)
                .region(start, source.length)
            if (!matchResult.lookingAt()) {
                throw AirLexerError(pattern.pattern(), source, start)
            } else {
                val token = lexer.parse(matchResult)
                if (config.filter(token)) {
                    tokens.add(token)
                }
                start = matchResult.end()
            }
        }
    }
}
