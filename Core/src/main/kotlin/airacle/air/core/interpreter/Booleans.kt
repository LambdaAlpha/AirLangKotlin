package airacle.air.core.interpreter

object Booleans : IBooleans {
    override fun toBool(a: AirValue): AirValue {
        return when (a) {
            is UnitValue -> BoolValue.FALSE
            is BoolValue -> a
            is IntValue -> BoolValue.valueOf(a != IntValue.ZERO)
            is DecimalValue -> BoolValue.valueOf(a != DecimalValue.ZERO)
            is StringValue -> BoolValue.valueOf(a != StringValue.EMPTY)
            is TupleValue -> BoolValue.valueOf(a.value.isNotEmpty())
            is ListValue -> BoolValue.valueOf(a.value.isNotEmpty())
            is MapValue -> BoolValue.valueOf(a.value.isNotEmpty())
        }
    }

    override fun toInt(b: AirValue): AirValue {
        return if (b is BoolValue) {
            if (b.value) {
                IntValue.ONE
            } else {
                IntValue.ZERO
            }
        } else {
            UnitValue
        }
    }

    override fun not(b: AirValue): AirValue {
        return if (b is BoolValue) {
            BoolValue.valueOf(!b.value)
        } else {
            UnitValue
        }
    }

    override fun and(a: AirValue, b: AirValue): AirValue {
        return if (a is BoolValue && b is BoolValue) {
            BoolValue.valueOf(a.value && b.value)
        } else {
            UnitValue
        }
    }

    override fun or(a: AirValue, b: AirValue): AirValue {
        return if (a is BoolValue && b is BoolValue) {
            BoolValue.valueOf(a.value || b.value)
        } else {
            UnitValue
        }
    }

    override fun xor(a: AirValue, b: AirValue): AirValue {
        return if (a is BoolValue && b is BoolValue) {
            BoolValue.valueOf(a.value xor b.value)
        } else {
            UnitValue
        }
    }
}