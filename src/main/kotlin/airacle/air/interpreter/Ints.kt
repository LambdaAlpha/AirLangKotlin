package airacle.air.interpreter

object Ints : IInts {
    override fun bitAnd(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            IntValue.valueOf(a.value and b.value)
        } else {
            UnitValue
        }
    }

    override fun bitOr(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            IntValue.valueOf(a.value or b.value)
        } else {
            UnitValue
        }
    }

    override fun bitXor(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            IntValue.valueOf(a.value xor b.value)
        } else {
            UnitValue
        }
    }

    override fun shiftLeft(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            IntValue.valueOf(a.value shl b.value.toInt())
        } else {
            UnitValue
        }
    }

    override fun shiftRight(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            IntValue.valueOf(a.value shr b.value.toInt())
        } else {
            UnitValue
        }
    }
}