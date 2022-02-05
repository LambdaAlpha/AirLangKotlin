package airacle.air.interpreter

interface IDecimals {
    fun precision(a: AirValue): AirValue

    fun random(c: AirValue): AirValue
    fun seed(seed: AirValue, c: AirValue): AirValue
    fun round(a: AirValue, c: AirValue): AirValue
    fun add(a: AirValue, b: AirValue, c: AirValue): AirValue
    fun subtract(a: AirValue, b: AirValue, c: AirValue): AirValue
    fun multiply(a: AirValue, b: AirValue, c: AirValue): AirValue
    fun divide(a: AirValue, b: AirValue, c: AirValue): AirValue
    fun divideToInt(a: AirValue, b: AirValue, c: AirValue): AirValue
    fun remainder(a: AirValue, b: AirValue, c: AirValue): AirValue
    fun divRem(a: AirValue, b: AirValue, c: AirValue): AirValue
    fun power(a: AirValue, b: AirValue, c: AirValue): AirValue
    fun exp(a: AirValue, c: AirValue): AirValue
    fun log(a: AirValue, b: AirValue, c: AirValue): AirValue
    fun ln(a: AirValue, c: AirValue): AirValue
    fun shiftLeft(a: AirValue, n: AirValue, c: AirValue): AirValue
    fun shiftRight(a: AirValue, n: AirValue, c: AirValue): AirValue
    fun sin(a: AirValue, c: AirValue): AirValue
    fun cos(a: AirValue, c: AirValue): AirValue
    fun tan(a: AirValue, c: AirValue): AirValue
    fun asin(a: AirValue, c: AirValue): AirValue
    fun acos(a: AirValue, c: AirValue): AirValue
    fun atan(a: AirValue, c: AirValue): AirValue
    fun atan2(a: AirValue, b: AirValue, c: AirValue): AirValue
    fun sinh(a: AirValue, c: AirValue): AirValue
    fun cosh(a: AirValue, c: AirValue): AirValue
    fun tanh(a: AirValue, c: AirValue): AirValue
    fun asinh(a: AirValue, c: AirValue): AirValue
    fun acosh(a: AirValue, c: AirValue): AirValue
    fun atanh(a: AirValue, c: AirValue): AirValue

    fun random32(): AirValue
    fun seed32(seed: AirValue): AirValue
    fun round32(a: AirValue): AirValue
    fun add32(a: AirValue, b: AirValue): AirValue
    fun subtract32(a: AirValue, b: AirValue): AirValue
    fun multiply32(a: AirValue, b: AirValue): AirValue
    fun divide32(a: AirValue, b: AirValue): AirValue
    fun divideToInt32(a: AirValue, b: AirValue): AirValue
    fun remainder32(a: AirValue, b: AirValue): AirValue
    fun divRem32(a: AirValue, b: AirValue): AirValue
    fun power32(a: AirValue, b: AirValue): AirValue
    fun exp32(a: AirValue): AirValue
    fun log32(a: AirValue, b: AirValue): AirValue
    fun ln32(a: AirValue): AirValue
    fun shiftLeft32(a: AirValue, n: AirValue): AirValue
    fun shiftRight32(a: AirValue, n: AirValue): AirValue
    fun sin32(a: AirValue): AirValue
    fun cos32(a: AirValue): AirValue
    fun tan32(a: AirValue): AirValue
    fun asin32(a: AirValue): AirValue
    fun acos32(a: AirValue): AirValue
    fun atan32(a: AirValue): AirValue
    fun atan232(a: AirValue, b: AirValue): AirValue
    fun sinh32(a: AirValue): AirValue
    fun cosh32(a: AirValue): AirValue
    fun tanh32(a: AirValue): AirValue
    fun asinh32(a: AirValue): AirValue
    fun acosh32(a: AirValue): AirValue
    fun atanh32(a: AirValue): AirValue

    fun random64(): AirValue
    fun seed64(seed: AirValue): AirValue
    fun round64(a: AirValue): AirValue
    fun add64(a: AirValue, b: AirValue): AirValue
    fun subtract64(a: AirValue, b: AirValue): AirValue
    fun multiply64(a: AirValue, b: AirValue): AirValue
    fun divide64(a: AirValue, b: AirValue): AirValue
    fun divideToInt64(a: AirValue, b: AirValue): AirValue
    fun remainder64(a: AirValue, b: AirValue): AirValue
    fun divRem64(a: AirValue, b: AirValue): AirValue
    fun power64(a: AirValue, b: AirValue): AirValue
    fun exp64(a: AirValue): AirValue
    fun log64(a: AirValue, b: AirValue): AirValue
    fun ln64(a: AirValue): AirValue
    fun shiftLeft64(a: AirValue, n: AirValue): AirValue
    fun shiftRight64(a: AirValue, n: AirValue): AirValue
    fun sin64(a: AirValue): AirValue
    fun cos64(a: AirValue): AirValue
    fun tan64(a: AirValue): AirValue
    fun asin64(a: AirValue): AirValue
    fun acos64(a: AirValue): AirValue
    fun atan64(a: AirValue): AirValue
    fun atan264(a: AirValue, b: AirValue): AirValue
    fun sinh64(a: AirValue): AirValue
    fun cosh64(a: AirValue): AirValue
    fun tanh64(a: AirValue): AirValue
    fun asinh64(a: AirValue): AirValue
    fun acosh64(a: AirValue): AirValue
    fun atanh64(a: AirValue): AirValue

    fun random128(): AirValue
    fun seed128(seed: AirValue): AirValue
    fun round128(a: AirValue): AirValue
    fun add128(a: AirValue, b: AirValue): AirValue
    fun subtract128(a: AirValue, b: AirValue): AirValue
    fun multiply128(a: AirValue, b: AirValue): AirValue
    fun divide128(a: AirValue, b: AirValue): AirValue
    fun divideToInt128(a: AirValue, b: AirValue): AirValue
    fun remainder128(a: AirValue, b: AirValue): AirValue
    fun divRem128(a: AirValue, b: AirValue): AirValue
    fun power128(a: AirValue, b: AirValue): AirValue
    fun exp128(a: AirValue): AirValue
    fun log128(a: AirValue, b: AirValue): AirValue
    fun ln128(a: AirValue): AirValue
    fun shiftLeft128(a: AirValue, n: AirValue): AirValue
    fun shiftRight128(a: AirValue, n: AirValue): AirValue
    fun sin128(a: AirValue): AirValue
    fun cos128(a: AirValue): AirValue
    fun tan128(a: AirValue): AirValue
    fun asin128(a: AirValue): AirValue
    fun acos128(a: AirValue): AirValue
    fun atan128(a: AirValue): AirValue
    fun atan2128(a: AirValue, b: AirValue): AirValue
    fun sinh128(a: AirValue): AirValue
    fun cosh128(a: AirValue): AirValue
    fun tanh128(a: AirValue): AirValue
    fun asinh128(a: AirValue): AirValue
    fun acosh128(a: AirValue): AirValue
    fun atanh128(a: AirValue): AirValue
}