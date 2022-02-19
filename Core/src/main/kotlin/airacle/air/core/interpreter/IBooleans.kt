package airacle.air.core.interpreter

interface IBooleans {
    fun toBool(a: AirValue): AirValue
    fun toInt(b: AirValue): AirValue
    fun not(b: AirValue): AirValue
    fun and(a: AirValue, b: AirValue): AirValue
    fun or(a: AirValue, b: AirValue): AirValue
    fun xor(a: AirValue, b: AirValue): AirValue
}