package airacle.air.core.lexer

import airacle.air.core.util.LexerReader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AirLexerTest {
    private val lexer = AirLexer(AirLexerVersion.V0)
    private val lexerReader = LexerReader(lexer)

    @Test
    fun lexTest() {
        val tokens = lexerReader.readAndLex("/airacle/air/core/lexer/Tokens.air")
        assertEquals(TOKENS, tokens)
    }

    @Test
    fun toStringTest() {
        val str = TOKENS.joinToString(separator = " ")
        assertEquals(TOKENS, lexer.lex(str))
    }
}