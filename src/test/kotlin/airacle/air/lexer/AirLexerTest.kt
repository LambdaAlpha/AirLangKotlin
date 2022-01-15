package airacle.air.lexer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.FileReader

internal class AirLexerTest {
    private val lexer = AirLexer(AirLexerConfig)
    private lateinit var content: String

    @BeforeEach
    fun prepare() {
        val exampleFile = javaClass.getResource("/airacle/air/lexer/Tokens.air")!!.file
        val reader = FileReader(exampleFile)
        content = reader.readText()
        reader.close()
    }

    @Test
    fun lexTest() {
        val tokens = assertDoesNotThrow {
            lexer.lex(content)
        }

        assertEquals(TOKENS, tokens)
    }
}