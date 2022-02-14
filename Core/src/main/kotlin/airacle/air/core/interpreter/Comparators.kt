package airacle.air.core.interpreter

object Comparators : IComparators {
    override fun lt(a: AirValue, b: AirValue): AirValue {
        return if (a is UnitValue && b is UnitValue) {
            BoolValue.FALSE
        } else if (a is BoolValue && b is BoolValue) {
            BoolValue.valueOf(a.value < b.value)
        } else if (a is IntValue && b is IntValue) {
            BoolValue.valueOf(a.value < b.value)
        } else if (a is DecimalValue && b is DecimalValue) {
            BoolValue.valueOf(a.value < b.value)
        } else if (a is StringValue && b is StringValue) {
            BoolValue.valueOf(a.value < b.value)
        } else {
            UnitValue
        }
    }

    override fun le(a: AirValue, b: AirValue): AirValue {
        return if (a is UnitValue && b is UnitValue) {
            BoolValue.TRUE
        } else if (a is BoolValue && b is BoolValue) {
            BoolValue.valueOf(a.value <= b.value)
        } else if (a is IntValue && b is IntValue) {
            BoolValue.valueOf(a.value <= b.value)
        } else if (a is DecimalValue && b is DecimalValue) {
            BoolValue.valueOf(a.value <= b.value)
        } else if (a is StringValue && b is StringValue) {
            BoolValue.valueOf(a.value <= b.value)
        } else {
            UnitValue
        }
    }

    override fun gt(a: AirValue, b: AirValue): AirValue {
        return if (a is UnitValue && b is UnitValue) {
            BoolValue.FALSE
        } else if (a is BoolValue && b is BoolValue) {
            BoolValue.valueOf(a.value > b.value)
        } else if (a is IntValue && b is IntValue) {
            BoolValue.valueOf(a.value > b.value)
        } else if (a is DecimalValue && b is DecimalValue) {
            BoolValue.valueOf(a.value > b.value)
        } else if (a is StringValue && b is StringValue) {
            BoolValue.valueOf(a.value > b.value)
        } else {
            UnitValue
        }
    }

    override fun ge(a: AirValue, b: AirValue): AirValue {
        return if (a is UnitValue && b is UnitValue) {
            BoolValue.TRUE
        } else if (a is BoolValue && b is BoolValue) {
            BoolValue.valueOf(a.value >= b.value)
        } else if (a is IntValue && b is IntValue) {
            BoolValue.valueOf(a.value >= b.value)
        } else if (a is DecimalValue && b is DecimalValue) {
            BoolValue.valueOf(a.value >= b.value)
        } else if (a is StringValue && b is StringValue) {
            BoolValue.valueOf(a.value >= b.value)
        } else {
            UnitValue
        }
    }

    override fun eq(a: AirValue, b: AirValue): AirValue {
        return if (a is UnitValue && b is UnitValue) {
            BoolValue.TRUE
        } else if (a is BoolValue && b is BoolValue) {
            BoolValue.valueOf(a.value == b.value)
        } else if (a is IntValue && b is IntValue) {
            BoolValue.valueOf(a.value.compareTo(b.value) == 0)
        } else if (a is DecimalValue && b is DecimalValue) {
            BoolValue.valueOf(a.value.compareTo(b.value) == 0)
        } else if (a is StringValue && b is StringValue) {
            BoolValue.valueOf(a.value.compareTo(b.value) == 0)
        } else {
            UnitValue
        }
    }

    override fun ne(a: AirValue, b: AirValue): AirValue {
        return if (a is UnitValue && b is UnitValue) {
            BoolValue.FALSE
        } else if (a is BoolValue && b is BoolValue) {
            BoolValue.valueOf(a.value != b.value)
        } else if (a is IntValue && b is IntValue) {
            BoolValue.valueOf(a.value.compareTo(b.value) != 0)
        } else if (a is DecimalValue && b is DecimalValue) {
            BoolValue.valueOf(a.value.compareTo(b.value) != 0)
        } else if (a is StringValue && b is StringValue) {
            BoolValue.valueOf(a.value.compareTo(b.value) != 0)
        } else {
            UnitValue
        }
    }

    override fun veq(a: AirValue, b: AirValue): AirValue {
        return BoolValue.valueOf(a == b)
    }

    override fun vne(a: AirValue, b: AirValue): AirValue {
        return BoolValue.valueOf(a != b)
    }
}