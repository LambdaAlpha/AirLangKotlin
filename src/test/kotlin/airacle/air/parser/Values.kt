package airacle.air.parser

import airacle.air.interpreter.*

val VALUES = ListValue(
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
                FalseValue,
                FalseValue,
                TrueValue,
            )
        ),
        TupleValue(
            arrayOf(
                IntegerValue(111),
                StringValue("a"),
                StringValue("b"),
                StringValue("c"),
            )
        ),
        TupleValue(
            arrayOf(
                FloatValue(1.234),
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
                IntegerValue(1)
            )
        ),
        TupleValue(
            arrayOf(
                StringValue("@"),
                FloatValue(1.2)
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
                        IntegerValue(1),
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
                        Pair(IntegerValue(1), StringValue("a")),
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
                FalseValue
            )
        ),
        TupleValue(
            arrayOf(
                StringValue("-"),
                TrueValue
            )
        ),
        TupleValue(
            arrayOf(
                StringValue(":"),
                TupleValue(
                    arrayOf(
                        StringValue(":"),
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
                                Pair(
                                    ListValue(
                                        mutableListOf(
                                            TupleValue(
                                                arrayOf(
                                                    MapValue(mutableMapOf())
                                                )
                                            )
                                        )
                                    ),
                                    TupleValue(
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
                        Pair(ListValue(mutableListOf()), TupleValue(arrayOf())),
                        Pair(TupleValue(arrayOf()), MapValue(mutableMapOf())),
                        Pair(MapValue(mutableMapOf()), ListValue(mutableListOf())),
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
        )
    )
)