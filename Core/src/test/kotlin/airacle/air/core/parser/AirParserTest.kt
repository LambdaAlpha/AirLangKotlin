package airacle.air.core.parser

import airacle.air.core.api.IAirLexer
import airacle.air.core.lexer.AirLexer
import airacle.air.core.lexer.AirLexerVersion
import airacle.air.core.lexer.AirToken
import airacle.air.core.util.ParserReader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AirParserTest {
    private val lexer: IAirLexer<AirToken> = AirLexer(AirLexerVersion.V0)
    private val parser = AirParser(AirParserVersion.V0, AirParserConfig)
    private val parserReader = ParserReader(lexer, parser)

    @Test
    fun parseTest() {
        val values = parserReader.readAndParse("/airacle/air/core/parser/Values.air")
        assertEquals(VALUES, values)
    }

    @Test
    fun toStringTest() {
        val newValues = parserReader.parse(VALUES.toString())
        assertEquals(VALUES, newValues)
    }
}