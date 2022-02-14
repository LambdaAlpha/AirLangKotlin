package airacle.air.core.interpreter

interface INumbers {
    fun unaryPlus(a: AirValue): AirValue
    fun unaryMinus(a: AirValue): AirValue
    fun abs(a: AirValue): AirValue
    fun sign(a: AirValue): AirValue
    fun min(a: AirValue, b: AirValue): AirValue
    fun max(a: AirValue, b: AirValue): AirValue
}