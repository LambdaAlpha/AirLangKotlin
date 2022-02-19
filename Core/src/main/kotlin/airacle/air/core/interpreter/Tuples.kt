package airacle.air.core.interpreter

object Tuples : ITuples {
    override fun toList(a: AirValue): AirValue {
        return if (a is TupleValue) {
            ListValue.valueOf(a.value.toMutableList())
        } else {
            UnitValue
        }
    }
}