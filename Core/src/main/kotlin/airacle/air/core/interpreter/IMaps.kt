package airacle.air.core.interpreter

interface IMaps {
    fun toTupleList(a: AirValue): AirValue
    fun fromTupleList(a: AirValue): AirValue
    fun toListTuple(a: AirValue): AirValue
    fun fromListTuple(a: AirValue): AirValue
}