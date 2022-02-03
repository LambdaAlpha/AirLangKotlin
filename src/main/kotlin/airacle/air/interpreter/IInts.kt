package airacle.air.interpreter

interface IInts {
    fun bitAnd(a: AirValue, b: AirValue): AirValue
    fun bitOr(a: AirValue, b: AirValue): AirValue
    fun bitXor(a: AirValue, b: AirValue): AirValue
    fun shiftLeft(a: AirValue, b: AirValue): AirValue
    fun shiftRight(a: AirValue, b: AirValue): AirValue
}