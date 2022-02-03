package airacle.air.interpreter

interface INumbers {
    fun negate(v: AirValue): AirValue
    fun add(a: AirValue, b: AirValue): AirValue
    fun subtract(a: AirValue, b: AirValue): AirValue
    fun multiply(a: AirValue, b: AirValue): AirValue
    fun divide(a: AirValue, b: AirValue): AirValue
    fun remainder(a: AirValue, b: AirValue): AirValue
    fun divideAndRemainder(a: AirValue, b: AirValue): AirValue
}