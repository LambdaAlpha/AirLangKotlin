package airacle.air.interpreter

interface IComparators {
    fun lt(a: AirValue, b: AirValue): AirValue
    fun le(a: AirValue, b: AirValue): AirValue
    fun gt(a: AirValue, b: AirValue): AirValue
    fun ge(a: AirValue, b: AirValue): AirValue

    // ordinal equal
    fun eq(a: AirValue, b: AirValue): AirValue
    fun ne(a: AirValue, b: AirValue): AirValue

    // value equal, i.e., the same information
    fun veq(a: AirValue, b: AirValue): AirValue
    fun vne(a: AirValue, b: AirValue): AirValue
}