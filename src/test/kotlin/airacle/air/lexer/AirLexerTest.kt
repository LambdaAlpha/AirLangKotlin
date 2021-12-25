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
        val exampleFile = javaClass.getResource("/air_src/TokenExamples.air")!!.file
        val reader = FileReader(exampleFile)
        content = reader.readText()
        reader.close()
    }

    @Test
    fun lexTest() {
        val tokens = assertDoesNotThrow {
            lexer.lex(content)
        }

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

        assertEquals(AlphaStringToken("AlphaString_0"), tokens[i])
        i += 1

        val symbolStrings = arrayOf("<=", ">=", "==", "!=", "++", "--", "&&")
        for (j in symbolStrings.indices) {
            assertEquals(symbolStrings[j], (tokens[i + j] as SymbolStringToken).value)
        }
        i += symbolStrings.size

        val symbols = "`~!@#$%&*()-_=+[]{};:',.<>?"
        for (j in symbols.indices) {
            assertEquals(symbols[j].toString(), (tokens[i + j] as SymbolStringToken).value)
        }
        i += symbols.length

        assertEquals(FullStringToken("Hello world! \n\r\t\uD83D\uDF01 \uD83D\uDF01"), tokens[i])
        i += 1
    }
}