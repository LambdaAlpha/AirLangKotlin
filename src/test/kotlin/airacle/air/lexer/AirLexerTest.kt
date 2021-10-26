package airacle.air.lexer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.FileReader

internal class AirLexerTest {
    private val lexer = AirLexer(AirLexerConfig())

    @Test
    fun lexString() {
        val sampleFile = javaClass.getResource("/air_src/TokenSamples.air")!!.file
        val reader = FileReader(sampleFile)
        val content = reader.readText()
        reader.close()

        assertDoesNotThrow {
            val tokens = lexer.lex(content)
            assertEquals(NameToken("simpleName0"), tokens[0])
            assertEquals(NameToken("`complex+name!"), tokens[1])
            assertEquals(UnitToken, tokens[2])
            assertEquals(FalseToken, tokens[3])
            assertEquals(TrueToken, tokens[4])
            assertEquals(IntegerToken(1), tokens[5])
            assertEquals(IntegerToken(-1), tokens[6])
            assertEquals(IntegerToken(-255), tokens[7])
            assertEquals(IntegerToken(13), tokens[8])
            assertEquals(IntegerToken(1234), tokens[9])
            assertEquals(FloatToken(0.0), tokens[10])
            assertEquals(FloatToken(1.222333e-10), tokens[11])
            assertEquals(FloatToken(-11.1), tokens[12])
            assertEquals(StringToken("Hello world!"), tokens[13])
            assertEquals(StringToken(" \n\r\t"), tokens[14])
            assertEquals(StringToken("\uD83D"), tokens[15])
            assertEquals(StringToken("string can cross multiple lines,like this"), tokens[16])
            val symbols = "~!@#$%&*()-_=+[]{};:',.<>?"
            for (i in symbols.indices) {
                assertEquals(symbols[i], (tokens[17 + i] as SymbolToken).key)
            }
        }
    }
}