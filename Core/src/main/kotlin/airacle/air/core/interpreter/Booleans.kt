package airacle.air.core.interpreter

object Booleans : IBooleans {
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