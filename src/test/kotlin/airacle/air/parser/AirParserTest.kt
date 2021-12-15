package airacle.air.parser

import airacle.air.interpreter.ListValue
import airacle.air.interpreter.MapValue
import airacle.air.interpreter.StringValue
import airacle.air.interpreter.TupleValue
import airacle.air.lexer.AirLexer
import airacle.air.lexer.AirLexerConfig
import airacle.air.lexer.AirToken
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.FileReader

internal class AirParserTest {
    private val parser = AirParser(AirParserConfig)
    private lateinit var tokens: List<AirToken>

    @BeforeEach
    fun prepare() {
        val sampleFile = javaClass.getResource("/air_src/SyntaxExamples.air")!!.file
        val reader = FileReader(sampleFile)
        val content = reader.readText()
        reader.close()
        val lexer = AirLexer(AirLexerConfig)
        tokens = lexer.lex(content)
    }

    @Test
    fun parseTest() {
        val syntaxList = assertDoesNotThrow {
            parser.parse(tokens)
        }
        assertEquals(5, syntaxList.size)
        assertEquals(
            TupleValue(
                arrayOf(
                    StringValue("comment"),
                    StringValue("this is a syntax example")
                )
            ), syntaxList[0]
        )
        assertEquals(
            TupleValue(
                arrayOf(
                    StringValue("="),
                    StringValue("max"),
                    TupleValue(
                        arrayOf(
                            StringValue("function"),
                            ListValue(
                                mutableListOf(
                                    StringValue("a"),
                                    StringValue(("b"))
                                )
                            ),
                            ListValue(
                                mutableListOf(
                                    TupleValue(
                                        arrayOf(
                                            StringValue("if"),
                                            TupleValue(
                                                arrayOf(
                                                    StringValue("<="),
                                                    StringValue("a"),
                                                    StringValue("b")
                                                )
                                            ),
                                            ListValue(
                                                mutableListOf(
                                                    TupleValue(
                                                        arrayOf(
                                                            StringValue("return"),
                                                            StringValue("b")
                                                        )
                                                    )
                                                )
                                            ),
                                            ListValue(
                                                mutableListOf(
                                                    TupleValue(
                                                        arrayOf(
                                                            StringValue("return"),
                                                            StringValue("a")
                                                        )
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            ),
            syntaxList[1]
        )
        assertEquals(
            MapValue(
                mutableMapOf(
                    Pair(StringValue("a"), StringValue("b")),
                    Pair(StringValue("c"), StringValue("d")),
                    Pair(StringValue("e"), StringValue("f")),
                    Pair(StringValue("g"), StringValue("h"))
                )
            ),
            syntaxList[2]
        )
        assertEquals(
            TupleValue(
                arrayOf(
                    StringValue("while"),
                    StringValue("true"),
                    ListValue(mutableListOf())
                )
            ),
            syntaxList[3]
        )
        assertEquals(
            TupleValue(
                arrayOf(
                    StringValue("for"),
                    StringValue("i"),
                    StringValue("min"),
                    StringValue("max"),
                    ListValue(mutableListOf())
                )
            ),
            syntaxList[4]
        )
    }
}