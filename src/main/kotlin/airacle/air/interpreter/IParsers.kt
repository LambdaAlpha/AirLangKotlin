package airacle.air.interpreter

interface IParsers {
    fun decodeFromString(v: AirValue): AirValue

    fun encodeToString(v: AirValue): AirValue
}