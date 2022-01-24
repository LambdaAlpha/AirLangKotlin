package airacle.air.parser

import airacle.air.interpreter.*

internal val VALUES = ListValue(
    mutableListOf(
        TupleValue(
            arrayOf(
                StringValue("zero"),
            )
        ),
        TupleValue(
            arrayOf(
                StringValue("zero"),
            )
        ),
        TupleValue(
            arrayOf(
                StringValue("one"),
                StringValue("one"),
            )
        ),
        TupleValue(
            arrayOf(
                StringValue("two"),
                TupleValue(
                    arrayOf(
                        StringValue("zero"),
                    )
                ),
                TupleValue(
                    arrayOf(
                        StringValue("one"),
                        StringValue("one"),
                    )
                ),
            )
        ),

        TupleValue(
            arrayOf(
                UnitValue,
                UnitValue,
            )
        ),
        TupleValue(
            arrayOf(
                BoolValue.FALSE,
                BoolValue.FALSE,
                BoolValue.TRUE,
            )
        ),
        TupleValue(
            arrayOf(
                IntValue.valueOf(111.toBigInteger()),
                StringValue("a"),
                StringValue("b"),
                StringValue("c"),
            )
        ),
        TupleValue(
            arrayOf(
                RealValue.valueOf("1.234".toBigDecimal()),
                StringValue("a"),
                StringValue("b"),
                StringValue("c"),
                StringValue("d"),
            )
        ),

        TupleValue(
            arrayOf(
                StringValue("`"),
                StringValue("`"),
            )
        ),
        TupleValue(
            arrayOf(
                StringValue("~"),
                IntValue.ONE,
            )
        ),
        TupleValue(
            arrayOf(
                StringValue("@"),
                RealValue.valueOf("1.2".toBigDecimal())
            )
        ),
        TupleValue(
            arrayOf(
                StringValue("$"),
                TupleValue(
                    arrayOf(
                        StringValue("a"),
                        StringValue("b"),
                    )
                ),
            )
        ),
        TupleValue(
            arrayOf(
                StringValue("%"),
                ListValue(
                    mutableListOf(
                        IntValue.ONE,
                        StringValue("a"),
                    )
                )
            )
        ),
        TupleValue(
            arrayOf(
                StringValue("^"),
                MapValue(
                    mutableMapOf(
                        IntValue.ONE to StringValue("a"),
                    )
                )
            )
        ),
        TupleValue(
            arrayOf(
                StringValue("&"),
                UnitValue
            )
        ),
        TupleValue(
            arrayOf(
                StringValue("*"),
                BoolValue.FALSE,
            )
        ),
        TupleValue(
            arrayOf(
                StringValue("-"),
                BoolValue.TRUE,
            )
        ),
        TupleValue(
            arrayOf(
                StringValue("?"),
                TupleValue(
                    arrayOf(
                        StringValue("?"),
                        StringValue("a"),
                        StringValue("b"),
                    )
                ),
                StringValue("c")
            )
        ),
        TupleValue(
            arrayOf(
                StringValue("."),
                StringValue("a"),
                TupleValue(
                    arrayOf(
                        StringValue("."),
                        StringValue("b"),
                        StringValue("c"),
                    )
                ),
            )
        ),

        TupleValue(
            arrayOf(
                ListValue(
                    mutableListOf(
                        MapValue(
                            mutableMapOf(
                                ListValue(
                                    mutableListOf(
                                        TupleValue(
                                            arrayOf(
                                                MapValue(mutableMapOf())
                                            )
                                        )
                                    )
                                ) to TupleValue(
                                    arrayOf(
                                        ListValue(
                                            mutableListOf(
                                                MapValue(mutableMapOf())
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
        ListValue(
            mutableListOf(
                ListValue(
                    mutableListOf(
                        TupleValue(arrayOf()),
                        MapValue(mutableMapOf()),
                        ListValue(mutableListOf()),
                    )
                ),
                MapValue(
                    mutableMapOf(
                        ListValue(mutableListOf()) to TupleValue(arrayOf()),
                        TupleValue(arrayOf()) to MapValue(mutableMapOf()),
                        MapValue(mutableMapOf()) to ListValue(mutableListOf()),
                    )
                ),
                TupleValue(
                    arrayOf(
                        TupleValue(arrayOf()),
                        ListValue(mutableListOf()),
                        MapValue(mutableMapOf()),
                    )
                )
            )
        ),

        TupleValue(
            arrayOf(
                StringValue("a"),
                StringValue("b"),
                StringValue("c"),
            )
        ),
        ListValue(
            mutableListOf(
                StringValue("a"),
                StringValue("b"),
                StringValue("c"),
            )
        ),
        MapValue(
            mutableMapOf(
                StringValue("a") to StringValue("b"),
                StringValue("c") to StringValue("d"),
                StringValue("e") to StringValue("f"),
                StringValue("g") to StringValue("h"),
            )
        )
    )
)