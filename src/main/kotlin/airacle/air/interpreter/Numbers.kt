package airacle.air.interpreter

object Numbers : INumbers {
    override fun unaryPlus(a: AirValue): AirValue {
        return when (a) {
            is IntValue -> a
            is DecimalValue -> a
            else -> UnitValue
        }
    }

    override fun unaryMinus(a: AirValue): AirValue {
        return when (a) {
            is IntValue -> IntValue.valueOf(-a.value)
            is DecimalValue -> DecimalValue.valueOf(-a.value)
            else -> UnitValue
        }
    }

    override fun abs(a: AirValue): AirValue {
        return when (a) {
            is IntValue -> if (a.value.signum() >= 0) a else IntValue.valueOf(-a.value)
            is DecimalValue -> if (a.value.signum() >= 0) a else DecimalValue.valueOf(-a.value)
            else -> UnitValue
        }
    }

    override fun sign(a: AirValue): AirValue {
        return when (a) {
            is IntValue -> IntValue.valueOf(a.value.signum().toBigInteger())
            is DecimalValue -> IntValue.valueOf(a.value.signum().toBigInteger())
            else -> UnitValue
        }
    }

    override fun min(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            if (a.value < b.value) a else b
        } else if (a is DecimalValue && b is DecimalValue) {
            if (a.value < b.value) a else b
        } else {
            UnitValue
        }
    }

    override fun max(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            if (a.value < b.value) b else a
        } else if (a is DecimalValue && b is DecimalValue) {
            if (a.value < b.value) b else a
        } else {
            UnitValue
        }
    }
}