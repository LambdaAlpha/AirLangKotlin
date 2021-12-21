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
        val exampleFile = javaClass.getResource("/air_src/SyntaxExamples.air")!!.file
        val reader = FileReader(exampleFile)
        val content = reader.readText()
        reader.close()
        val lexer = AirLexer(AirLexerConfig)
        tokens = lexer.lex(content)
    }

    @Test
    fun parseTest() {
        val nodes = assertDoesNotThrow {
            parser.parse(tokens)
        }
        println(nodes)

        assertEquals(6, nodes.size)
        var i = 0
        assertEquals(
            TupleValue(
                arrayOf(
                    StringValue("comment"),
                    StringValue("this is a syntax example")
                )
            ), nodes[i]
        )
        i += 1
        assertEquals(
            TupleValue(
                arrayOf(
                    StringValue("assign"),
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
                                                    StringValue("le"),
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
            nodes[i]
        )
        i += 1
        assertEquals(
            TupleValue(
                arrayOf(
                    StringValue("="),
                    StringValue("max"),
                    TupleValue(
                        arrayOf(
                            StringValue("^"),
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
                                            StringValue("?"),
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
                                                            StringValue("~"),
                                                            StringValue("b")
                                                        )
                                                    )
                                                )
                                            ),
                                            ListValue(
                                                mutableListOf(
                                                    TupleValue(
                                                        arrayOf(
                                                            StringValue("~"),
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
            nodes[i]
        )
        i += 1
        assertEquals(
            MapValue(
                mutableMapOf(
                    Pair(StringValue("a"), StringValue("b")),
                    Pair(StringValue("c"), StringValue("d")),
                    Pair(StringValue("e"), StringValue("f")),
                    Pair(StringValue("g"), StringValue("h"))
                )
            ),
            nodes[i]
        )
        i += 1
        assertEquals(
            TupleValue(
                arrayOf(
                    StringValue("-"),
                    TupleValue(
                        arrayOf(
                            StringValue("+"),
                            TupleValue(
                                arrayOf(
                                    StringValue("+"),
                                    StringValue("a"),
                                    StringValue("b"),
                                )
                            ),
                            StringValue("c")
                        )
                    ),
                    StringValue("d")
                )
            ),
            nodes[i]
        )
        i += 1
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
            nodes[i]
        )
    }
}