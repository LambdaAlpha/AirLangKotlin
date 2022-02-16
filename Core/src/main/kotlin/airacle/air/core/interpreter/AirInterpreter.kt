package airacle.air.core.interpreter

import airacle.air.core.api.IAirInterpreter

interface IAirInterpreterConfig {
    fun isValue(v: AirValue): Boolean
    fun isQuote(v: AirValue): Boolean
    fun isEval(v: AirValue): Boolean
    fun primitiveInterpret(value: AirValue): AirValue
}

class AirInterpreter(
    val version: AirInterpreterVersion,
    val config: IAirInterpreterConfig
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

        if (config.isValue(keyword)) {
            return if (tuple.size == 2) {
                tuple[1]
            } else {
                UnitValue
            }
        }

        if (config.isQuote(keyword)) {
            return if (tuple.size == 2) {
                quote(tuple[1])
            } else {
                UnitValue
            }
        }

        if (config.isEval(keyword)) {
            return if (tuple.size == 2) {
                interpret(interpret(tuple[1]))
            } else {
                UnitValue
            }
        }

        val evalTuple = TupleValue.fromArray(Array(tuple.size) {
            if (it == 0) {
                keyword
            } else {
                interpret(tuple[it])
            }
        })

        return config.primitiveInterpret(evalTuple)
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

        if (config.isValue(keyword)) {
            return if (t.size == 2) {
                t[1]
            } else {
                UnitValue
            }
        }

        if (config.isQuote(keyword)) {
            return if (t.size == 2) {
                quote(quote(t[1]))
            } else {
                UnitValue
            }
        }

        if (config.isEval(keyword)) {
            return if (t.size == 2) {
                interpret(t[1])
            } else {
                UnitValue
            }
        }

        return TupleValue.fromArray(Array(t.size) {
            quote(t[it])
        })
    }
}