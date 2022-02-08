package airacle.air.parser

import airacle.air.api.IAirLexer
import airacle.air.lexer.AirLexer
import airacle.air.lexer.AirLexerVersion
import airacle.air.lexer.AirToken
import airacle.air.util.ParserReader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AirParserTest {
    private val lexer: IAirLexer<AirToken> = AirLexer(AirLexerVersion.V0)
    private val parser = AirParser(AirParserVersion.V0)
    private val parserReader = ParserReader(lexer, parser)

    @Test
    fun parseTest() {
        val values = parserReader.readAndParse("/airacle/air/parser/Values.air")
        assertEquals(VALUES, values)
    }

    @Test
    fun toStringTest() {
        val newValues = parserReader.parse(VALUES.toString())
        assertEquals(VALUES, newValues)
    }
}