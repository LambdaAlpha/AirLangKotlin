package airacle.air.lexer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.FileReader

internal class AirLexerTest {
    private val lexer = AirLexer(AirLexerConfig)

    private fun readTokens(): String {
        val exampleFile = javaClass.getResource("/airacle/air/lexer/Tokens.air")!!.file
        val reader = FileReader(exampleFile)
        val content = reader.readText()
        reader.close()
        return content
    }

    @Test
    fun lexTest() {
        val content = readTokens()
        val tokens = lexer.lex(content)
        assertEquals(TOKENS, tokens)
    }

    @Test
    fun toStringTest() {
        val str = TOKENS.joinToString(separator = " ")
        assertEquals(TOKENS, lexer.lex(str))
    }
}