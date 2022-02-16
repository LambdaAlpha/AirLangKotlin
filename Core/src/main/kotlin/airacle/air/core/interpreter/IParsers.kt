package airacle.air.core.interpreter

interface IParsers {
    fun decodeFromString(v: AirValue): AirValue

    fun encodeToString(v: AirValue): AirValue

    fun prettyPrint(v: AirValue): AirValue
}