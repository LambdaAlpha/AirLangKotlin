package airacle.air.lexer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.FileReader

internal class AirLexerTest {
    private val lexer = AirLexer(AirLexerConfig)

    @Test
    fun lexString() {
        val sampleFile = javaClass.getResource("/air_src/TokenSamples.air")!!.file
        val reader = FileReader(sampleFile)
        val content = reader.readText()
        reader.close()

        assertDoesNotThrow {
            val tokens = lexer.lex(content)
            var i = 0
            assertEquals(UnitToken, tokens[i])

            i += 1
            assertEquals(FalseToken, tokens[i])
            i += 1
            assertEquals(TrueToken, tokens[i])

            i += 1
            assertEquals(IntegerToken(1), tokens[i])
            i += 1
            assertEquals(IntegerToken(-1), tokens[i])
            i += 1
            assertEquals(IntegerToken(-255), tokens[i])
            i += 1
            assertEquals(IntegerToken(13), tokens[i])
            i += 1
            assertEquals(IntegerToken(1234), tokens[i])

            i += 1
            assertEquals(FloatToken(0.0), tokens[i])
            i += 1
            assertEquals(FloatToken(1.222333e-10), tokens[i])
            i += 1
            assertEquals(FloatToken(-11.1), tokens[i])

            i += 1
            assertEquals(StringToken("AlphaString_0"), tokens[i])
            i += 1
            assertEquals(StringToken("Graph+String!"), tokens[i])
            i += 1
            assertEquals(StringToken("Hello world! \n\r\t\uD83D\uDF01 \uD83D\uDF01"), tokens[i])

            i += 1
            val symbols = "`~!@#$%&*()-_=+[]{};:,.<>?"
            for (j in symbols.indices) {
                assertEquals(symbols[j], (tokens[i + j] as SymbolToken).key)
            }
        }
    }
}