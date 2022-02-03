package airacle.air.interpreter

object Numbers : INumbers {
    override fun negate(v: AirValue): AirValue {
        return when (v) {
            is IntValue -> IntValue.valueOf(-v.value)
            is DecimalValue -> DecimalValue.valueOf(-v.value)
            else -> UnitValue
        }
    }

    override fun add(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            IntValue.valueOf(a.value + b.value)
        } else if (a is DecimalValue && b is DecimalValue) {
            DecimalValue.valueOf(a.value + b.value)
        } else {
            UnitValue
        }
    }

    override fun subtract(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            IntValue.valueOf(a.value - b.value)
        } else if (a is DecimalValue && b is DecimalValue) {
            DecimalValue.valueOf(a.value - b.value)
        } else {
            UnitValue
        }
    }

    override fun multiply(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            IntValue.valueOf(a.value * b.value)
        } else if (a is DecimalValue && b is DecimalValue) {
            DecimalValue.valueOf(a.value * b.value)
        } else {
            UnitValue
        }
    }

    override fun divide(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            if (b == IntValue.ZERO) {
                UnitValue
            } else {
                IntValue.valueOf(a.value / b.value)
            }
        } else if (a is DecimalValue && b is DecimalValue) {
            if (b == DecimalValue.ZERO) {
                UnitValue
            } else {
                DecimalValue.valueOf(a.value / b.value)
            }
        } else {
            UnitValue
        }
    }

    override fun remainder(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            if (b == IntValue.ZERO) {
                UnitValue
            } else {
                IntValue.valueOf(a.value % b.value)
            }
        } else if (a is DecimalValue && b is DecimalValue) {
            if (b == DecimalValue.ZERO) {
                UnitValue
            } else {
                DecimalValue.valueOf(a.value % b.value)
            }
        } else {
            UnitValue
        }
    }

    override fun divideAndRemainder(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            if (b == IntValue.ZERO) {
                UnitValue
            } else {
                val result = a.value.divideAndRemainder(b.value)
                TupleValue(arrayOf(IntValue.valueOf(result[0]), IntValue.valueOf(result[1])))
            }
        } else if (a is DecimalValue && b is DecimalValue) {
            if (b == DecimalValue.ZERO) {
                UnitValue
            } else {
                val result = a.value.divideAndRemainder(b.value)
                TupleValue(arrayOf(DecimalValue.valueOf(result[0]), DecimalValue.valueOf(result[1])))
            }
        } else {
            UnitValue
        }
    }
}