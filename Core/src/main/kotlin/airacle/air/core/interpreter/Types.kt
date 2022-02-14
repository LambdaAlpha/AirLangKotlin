package airacle.air.core.interpreter

import java.math.BigDecimal
import java.math.BigInteger

object Types : ITypes {
    override fun typeOf(value: AirValue): AirValue {
        return when (value) {
            is UnitValue -> ITypes.UNIT
            is BoolValue -> ITypes.BOOL
            is IntValue -> ITypes.INT
            is DecimalValue -> ITypes.DECIMAL
            is StringValue -> ITypes.STRING
            is TupleValue -> ITypes.TUPLE
            is ListValue -> ITypes.LIST
            is MapValue -> ITypes.MAP
        }

    }

    override fun toType(value: AirValue, type: AirValue): AirValue {
        if (value is StringValue || type !is StringValue) {
            return UnitValue
        }

        return if (value is PrimitiveValue) {
            primitiveToPrimitive(value, type)
        } else {
            containerToContainer(value, type)
        }
    }

    private fun primitiveToPrimitive(v: PrimitiveValue, type: StringValue): AirValue {
        return when (type) {
            ITypes.UNIT -> UnitValue
            ITypes.BOOL -> {
                val b = when (v) {
                    is UnitValue -> false
                    is BoolValue -> v.value
                    is IntValue -> v != IntValue.ZERO
                    is DecimalValue -> v != DecimalValue.ZERO
                    else -> null
                }
                if (b == null) UnitValue else BoolValue.valueOf(b)
            }
            ITypes.INT -> {
                val i = when (v) {
                    is UnitValue -> BigInteger.ZERO
                    is BoolValue -> if (v.value) BigInteger.ONE else BigInteger.ZERO
                    is IntValue -> v.value
                    is DecimalValue -> v.value.toBigInteger()
                    else -> null
                }
                if (i == null) UnitValue else IntValue.valueOf(i)
            }
            ITypes.DECIMAL -> {
                val f = when (v) {
                    is UnitValue -> BigDecimal.ZERO
                    is BoolValue -> if (v.value) BigDecimal.ONE else BigDecimal.ZERO
                    is IntValue -> v.value.toBigDecimal()
                    is DecimalValue -> v.value
                    else -> null
                }
                if (f == null) UnitValue else DecimalValue.valueOf(f)
            }
            else -> UnitValue
        }
    }

    private fun containerToContainer(v: AirValue, type: StringValue): AirValue {
        return when (type) {
            ITypes.TUPLE -> when (v) {
                is TupleValue -> v
                is ListValue -> TupleValue.fromArray(v.value.toTypedArray())
                is MapValue -> TupleValue.valueOf(
                    ListValue.valueOf(v.value.keys.toMutableList()),
                    ListValue.valueOf(v.value.values.toMutableList())
                )
                else -> UnitValue
            }
            ITypes.LIST -> when (v) {
                is TupleValue -> ListValue.valueOf(v.value.toMutableList())
                is ListValue -> v
                is MapValue -> ListValue.valueOf(v.value.entries.map {
                    TupleValue.valueOf(it.key, it.value)
                }.toMutableList())
                else -> UnitValue
            }
            ITypes.MAP -> when (v) {
                is TupleValue -> if (v.value.size == 2) {
                    val v1 = v.value[0]
                    val v2 = v.value[1]
                    if (v1 is ListValue && v2 is ListValue && v1.value.size == v2.value.size) {
                        val map = mutableMapOf<AirValue, AirValue>()
                        for (i in v1.value.indices) {
                            map[v1.value[i]] = v2.value[i]
                        }
                        MapValue.valueOf(map)
                    } else {
                        UnitValue
                    }
                } else {
                    UnitValue
                }
                is ListValue -> {
                    val map = mutableMapOf<AirValue, AirValue>()
                    var valid = true
                    for (i in v.value) {
                        if (i is TupleValue && i.value.size == 2) {
                            map[i.value[0]] = i.value[1]
                        } else {
                            valid = false
                            break
                        }
                    }
                    if (valid) MapValue.valueOf(map) else UnitValue
                }
                is MapValue -> v
                else -> UnitValue
            }
            else -> UnitValue
        }
    }

}