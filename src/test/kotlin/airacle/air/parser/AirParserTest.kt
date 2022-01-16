package airacle.air.parser

import airacle.air.lexer.AirLexer
import airacle.air.lexer.AirLexerConfig
import airacle.air.lexer.AirToken
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.FileReader

internal class AirParserTest {
    private val parser = AirParser(AirParserTestConfig)
    private lateinit var tokens: List<AirToken>

    @BeforeEach
    fun prepare() {
        val exampleFile = javaClass.getResource("/airacle/air/parser/Values.air")!!.file
        val reader = FileReader(exampleFile)
        val content = reader.readText()
        reader.close()
        val lexer = AirLexer(AirLexerConfig)
        tokens = lexer.lex(content)
    }

    @Test
    fun parseTest() {
        val values = parser.parse(tokens)
        assertEquals(VALUES, values)
    }

    @Test
    fun toStringTest() {
        val lexer = AirLexer(AirLexerConfig)
        val newTokens = lexer.lex(VALUES.toString())

        val newValues = parser.parse(newTokens)
        assertEquals(VALUES, newValues)
    }
}