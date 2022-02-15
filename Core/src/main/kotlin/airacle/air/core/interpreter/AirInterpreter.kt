package airacle.air.core.interpreter

import airacle.air.core.api.IAirInterpreter
import airacle.air.core.parser.AirParserConfig
import airacle.air.core.parser.IAirParserConfig

interface IAirInterpreterConfig {
    fun primitiveInterpret(value: AirValue): AirValue
}

class AirInterpreter(
    val version: AirInterpreterVersion,
    private val parserConfig: IAirParserConfig,
    private val interpreterConfig: IAirInterpreterConfig
) : IAirInterpreter<AirValue> {

    init {
        when (version) {
            AirInterpreterVersion.V0 -> {

            }
        }
    }

    override fun interpret(value: AirValue): AirValue {
        if (value !is TupleValue) {
            return value
        }

        val tuple = value.value
        if (tuple.isEmpty()) {
            return value
        }

        val keyword = interpret(tuple[0])

        if (parserConfig.paramLength(keyword) + 1 != tuple.size) {
            return UnitValue
        }

        if (keyword is StringValue) {
            when (keyword.value) {
                // eval
                AirParserConfig.VALUE, AirParserConfig.VALUE_SYMBOL -> return tuple[1]
                AirParserConfig.QUOTE, AirParserConfig.QUOTE_SYMBOL -> return quote(tuple[1])
                AirParserConfig.EVAL, AirParserConfig.EVAL_SYMBOL -> return interpret(
                    interpret(tuple[1])
                )
            }
        }

        val evalTuple = TupleValue.fromArray(Array(tuple.size) {
            if (it == 0) {
                keyword
            } else {
                interpret(tuple[it])
            }
        })

        return interpreterConfig.primitiveInterpret(evalTuple)
    }

    private fun quote(v: AirValue): AirValue {
        if (v is ListValue) {
            val list = ArrayList<AirValue>(v.value.size)
            for (i in v.value) {
                list.add(quote(i))
            }
            return ListValue.valueOf(list)
        }

        if (v is MapValue) {
            val map =
                HashMap<AirValue, AirValue>(v.value.size)
            for (entry in v.value) {
                map[quote(entry.key)] = quote(entry.value)
            }
            return MapValue.valueOf(map)
        }

        if (v !is TupleValue) {
            return v
        }

        val t = v.value

        if (t.isEmpty()) {
            return v
        }

        val keyword = t[0]
        if (keyword is StringValue) {
            when (keyword.value) {
                AirParserConfig.VALUE, AirParserConfig.VALUE_SYMBOL -> return t[1]
                AirParserConfig.QUOTE, AirParserConfig.QUOTE_SYMBOL -> return quote(
                    quote(
                        t[1]
                    )
                )
                AirParserConfig.EVAL, AirParserConfig.EVAL_SYMBOL -> return interpret(t[1])
            }
        }
        return TupleValue.fromArray(Array(t.size) {
            quote(t[it])
        })
    }
}