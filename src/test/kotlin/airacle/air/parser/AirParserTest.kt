package airacle.air.parser

import airacle.air.interpreter.*
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
        val values = assertDoesNotThrow {
            parser.parse(tokens)
        }
        println(values)

        assertEquals(6, values.size)
        var i = 0
        assertEquals(
            TupleValue(
                arrayOf(
                    StringValue("comment"),
                    StringValue("this is a syntax example")
                )
            ), values[i]
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
            values[i]
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
            values[i]
        )
        i += 1
        assertEquals(
            MapValue(
                mutableMapOf(
                    Pair(StringValue("a"), StringValue("b")),
                    Pair(StringValue("c"), StringValue("d")),
                    Pair(StringValue("e"), StringValue("f")),
                    Pair(StringValue("g"), StringValue("h")),
                    Pair(IntegerValue(-1), FloatValue(-2.0)),
                    Pair(FloatValue(1.0), IntegerValue(2)),
                    Pair(UnitValue, TrueValue)
                )
            ),
            values[i]
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
                                    TupleValue(
                                        arrayOf(
                                            StringValue("."),
                                            StringValue("a")
                                        )
                                    ),
                                    TupleValue(
                                        arrayOf(
                                            StringValue("."),
                                            StringValue("b")
                                        )
                                    ),
                                )
                            ),
                            TupleValue(
                                arrayOf(
                                    StringValue("."),
                                    StringValue("c")
                                )
                            )
                        )
                    ),
                    TupleValue(
                        arrayOf(
                            StringValue("."),
                            StringValue("d")
                        )
                    )
                )
            ),
            values[i]
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
            values[i]
        )
    }

    @Test
    fun toStringTest() {
        val values = assertDoesNotThrow {
            parser.parse(tokens)
        }

        val builder = StringBuilder()
        for (v in values) {
            builder.append(v).append(" ")
        }

        val lexer = AirLexer(AirLexerConfig)
        val newTokens = lexer.lex(builder.toString())

        val newValues = assertDoesNotThrow {
            parser.parse(newTokens)
        }

        assertEquals(values, newValues)
    }
}