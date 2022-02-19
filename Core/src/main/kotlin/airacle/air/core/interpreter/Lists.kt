package airacle.air.core.interpreter

object Lists : ILists {
    override fun toTuple(a: AirValue): AirValue {
        return if (a is ListValue) {
            TupleValue.fromArray(a.value.toTypedArray())
        } else {
            UnitValue
        }
    }
}