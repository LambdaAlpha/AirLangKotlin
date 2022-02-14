package airacle.air.core.interpreter

interface IBooleans {
    fun not(b: AirValue): AirValue
    fun and(a: AirValue, b: AirValue): AirValue
    fun or(a: AirValue, b: AirValue): AirValue
    fun xor(a: AirValue, b: AirValue): AirValue
}