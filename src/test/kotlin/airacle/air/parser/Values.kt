package airacle.air.parser

import airacle.air.interpreter.*

val VALUES = ListValue(
    mutableListOf(
        TupleValue(
            arrayOf(
                StringValue("comment"),
                StringValue("this is a syntax example")
            )
        ),
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
                                                StringValue("le"),
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
        TupleValue(
            arrayOf(
                StringValue("for"),
                StringValue("i"),
                StringValue("min"),
                StringValue("max"),
                ListValue(mutableListOf())
            )
        ),
    )
)