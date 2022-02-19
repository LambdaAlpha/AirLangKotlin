package airacle.air.core.interpreter

interface IInts {
    fun toDecimal(a: AirValue): AirValue

    fun add(a: AirValue, b: AirValue): AirValue
    fun subtract(a: AirValue, b: AirValue): AirValue
    fun multiply(a: AirValue, b: AirValue): AirValue
    fun divide(a: AirValue, b: AirValue): AirValue
    fun remainder(a: AirValue, b: AirValue): AirValue
    fun divRem(a: AirValue, b: AirValue): AirValue
    fun power(a: AirValue, b: AirValue): AirValue
    fun root(a: AirValue, b: AirValue): AirValue
    fun log(a: AirValue, b: AirValue): AirValue
    fun mod(a: AirValue, b: AirValue): AirValue

    fun bitNot(a: AirValue): AirValue
    fun bitAnd(a: AirValue, b: AirValue): AirValue
    fun bitOr(a: AirValue, b: AirValue): AirValue
    fun bitXor(a: AirValue, b: AirValue): AirValue
    fun bitAndNot(a: AirValue, b: AirValue): AirValue
    fun testBit(a: AirValue, index: AirValue): AirValue
    fun setBit(a: AirValue, index: AirValue): AirValue
    fun clearBit(a: AirValue, index: AirValue): AirValue
    fun flipBit(a: AirValue, index: AirValue): AirValue
    fun bitCount(a: AirValue): AirValue
    fun bitLength(a: AirValue): AirValue
    fun lowestSetBit(a: AirValue): AirValue
    fun shiftLeft(a: AirValue, n: AirValue): AirValue
    fun shiftRight(a: AirValue, n: AirValue): AirValue

    fun toLowerBinaryString(a: AirValue): AirValue
    fun toUpperBinaryString(a: AirValue): AirValue
    fun toLowerHexString(a: AirValue): AirValue
    fun toUpperHexString(a: AirValue): AirValue
}