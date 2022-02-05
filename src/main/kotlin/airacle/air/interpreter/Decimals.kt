package airacle.air.interpreter

import ch.obermuhlner.math.big.BigDecimalMath
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode
import java.util.*
import kotlin.math.ceil

object Decimals : IDecimals {
    private const val LOG_2_10 = 3.3219280948873626
    const val UP = "up"
    const val DOWN = "down"
    const val CEILING = "ceiling"
    const val FLOOR = "floor"
    const val HALF_UP = "halfUp"
    const val HALF_DOWN = "halfDown"
    const val HALF_EVEN = "halfEven"
    const val UNNECESSARY = "unnecessary"

    override fun precision(a: AirValue): AirValue {
        return if (a is DecimalValue) {
            IntValue.valueOf(a.value.precision().toBigInteger())
        } else {
            UnitValue
        }
    }

    private fun getMathContext(c: AirValue): MathContext? {
        if (c is TupleValue && c.value.size == 2) {
            val first = c.value[0]
            val second = c.value[1]
            if (first is IntValue && second is StringValue) {
                try {
                    val precision = first.value.intValueExact()
                    val roundingMode: RoundingMode? = when (second.value) {
                        UP -> RoundingMode.UP
                        DOWN -> RoundingMode.DOWN
                        CEILING -> RoundingMode.CEILING
                        FLOOR -> RoundingMode.FLOOR
                        HALF_UP -> RoundingMode.HALF_UP
                        HALF_DOWN -> RoundingMode.HALF_DOWN
                        HALF_EVEN -> RoundingMode.HALF_EVEN
                        UNNECESSARY -> RoundingMode.UNNECESSARY
                        else -> null
                    }
                    if (roundingMode != null) {
                        return MathContext(precision, roundingMode)
                    }
                } catch (t: Throwable) {

                }
            }
        }
        return null
    }

    private fun randomDecimal(mc: MathContext?, random: Random): AirValue {
        return if (mc != null) {
            try {
                val bitLength = ceil(mc.precision * LOG_2_10).toInt()
                val i = BigDecimal(BigInteger(bitLength, random))
                val total = BigDecimal(BigInteger.ONE.shiftLeft(bitLength))
                val percent = i.divide(total, mc)
                DecimalValue.valueOf(percent)
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    private fun random(mc: MathContext?): AirValue {
        return randomDecimal(mc, Random())
    }

    override fun random(c: AirValue): AirValue {
        return random(getMathContext(c))
    }

    private fun seed(seed: AirValue, mc: MathContext?): AirValue {
        return if (seed is IntValue) {
            try {
                randomDecimal(mc, Random(seed.value.longValueExact()))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun seed(seed: AirValue, c: AirValue): AirValue {
        return seed(seed, getMathContext(c))
    }

    private fun round(a: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && mc != null) {
            DecimalValue.valueOf(a.value.plus(mc))
        } else {
            UnitValue
        }
    }

    override fun round(a: AirValue, c: AirValue): AirValue {
        return round(a, getMathContext(c))
    }

    private fun add(a: AirValue, b: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && b is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(a.value.add(b.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun add(a: AirValue, b: AirValue, c: AirValue): AirValue {
        return add(a, b, getMathContext(c))
    }

    private fun subtract(a: AirValue, b: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && b is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(a.value.subtract(b.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun subtract(a: AirValue, b: AirValue, c: AirValue): AirValue {
        return subtract(a, b, getMathContext(c))
    }

    private fun multiply(a: AirValue, b: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && b is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(a.value.multiply(b.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun multiply(a: AirValue, b: AirValue, c: AirValue): AirValue {
        return multiply(a, b, getMathContext(c))
    }

    private fun divide(a: AirValue, b: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && b is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(a.value.divide(b.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun divide(a: AirValue, b: AirValue, c: AirValue): AirValue {
        return divide(a, b, getMathContext(c))
    }

    private fun divideToInt(a: AirValue, b: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && b is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(a.value.divideToIntegralValue(b.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun divideToInt(a: AirValue, b: AirValue, c: AirValue): AirValue {
        return divideToInt(a, b, getMathContext(c))
    }

    private fun remainder(a: AirValue, b: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && b is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(a.value.remainder(b.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun remainder(a: AirValue, b: AirValue, c: AirValue): AirValue {
        return remainder(a, b, getMathContext(c))
    }

    private fun divRem(a: AirValue, b: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && b is DecimalValue && mc != null) {
            try {
                val ret = a.value.divideAndRemainder(b.value, mc)
                TupleValue(
                    arrayOf(
                        DecimalValue.valueOf(ret[0]),
                        DecimalValue.valueOf(ret[1])
                    )
                )
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun divRem(a: AirValue, b: AirValue, c: AirValue): AirValue {
        return divRem(a, b, getMathContext(c))
    }

    private fun power(a: AirValue, b: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && b is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(BigDecimalMath.pow(a.value, b.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun power(a: AirValue, b: AirValue, c: AirValue): AirValue {
        return power(a, b, getMathContext(c))
    }

    private fun exp(a: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(BigDecimalMath.exp(a.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun exp(a: AirValue, c: AirValue): AirValue {
        return exp(a, getMathContext(c))
    }

    private fun log(a: AirValue, b: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && b is DecimalValue && mc != null) {
            try {
                val doubleMc = MathContext(mc.precision * 2, mc.roundingMode)
                val logA = BigDecimalMath.log(a.value, doubleMc)
                val logB = BigDecimalMath.log(b.value, doubleMc)
                DecimalValue.valueOf(logA.divide(logB, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun log(a: AirValue, b: AirValue, c: AirValue): AirValue {
        return log(a, b, getMathContext(c))
    }

    private fun ln(a: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(BigDecimalMath.log(a.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun ln(a: AirValue, c: AirValue): AirValue {
        return ln(a, getMathContext(c))
    }

    private fun shiftLeft(a: AirValue, n: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && n is IntValue && mc != null) {
            try {
                val i = n.value.intValueExact()
                DecimalValue.valueOf(a.value.scaleByPowerOfTen(i).plus(mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun shiftLeft(a: AirValue, n: AirValue, c: AirValue): AirValue {
        return shiftLeft(a, n, getMathContext(c))
    }

    private fun shiftRight(a: AirValue, n: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && n is IntValue && mc != null) {
            try {
                val i = n.value.intValueExact()
                DecimalValue.valueOf(a.value.scaleByPowerOfTen(-i).plus(mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun shiftRight(a: AirValue, n: AirValue, c: AirValue): AirValue {
        return shiftRight(a, n, getMathContext(c))
    }

    private fun sin(a: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(BigDecimalMath.sin(a.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun sin(a: AirValue, c: AirValue): AirValue {
        return sin(a, getMathContext(c))
    }

    private fun cos(a: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(BigDecimalMath.cos(a.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun cos(a: AirValue, c: AirValue): AirValue {
        return cos(a, getMathContext(c))
    }

    private fun tan(a: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(BigDecimalMath.tan(a.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun tan(a: AirValue, c: AirValue): AirValue {
        return tan(a, getMathContext(c))
    }

    private fun asin(a: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(BigDecimalMath.asin(a.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun asin(a: AirValue, c: AirValue): AirValue {
        return asin(a, getMathContext(c))
    }

    private fun acos(a: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(BigDecimalMath.acos(a.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun acos(a: AirValue, c: AirValue): AirValue {
        return acos(a, getMathContext(c))
    }

    private fun atan(a: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(BigDecimalMath.atan(a.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun atan(a: AirValue, c: AirValue): AirValue {
        return atan(a, getMathContext(c))
    }

    private fun atan2(a: AirValue, b: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && b is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(BigDecimalMath.atan2(a.value, b.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun atan2(a: AirValue, b: AirValue, c: AirValue): AirValue {
        return atan2(a, b, getMathContext(c))
    }

    private fun sinh(a: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(BigDecimalMath.sinh(a.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun sinh(a: AirValue, c: AirValue): AirValue {
        return sinh(a, getMathContext(c))
    }

    private fun cosh(a: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(BigDecimalMath.cosh(a.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun cosh(a: AirValue, c: AirValue): AirValue {
        return cosh(a, getMathContext(c))
    }

    private fun tanh(a: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(BigDecimalMath.tanh(a.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun tanh(a: AirValue, c: AirValue): AirValue {
        return tanh(a, getMathContext(c))
    }

    private fun asinh(a: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(BigDecimalMath.asinh(a.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun asinh(a: AirValue, c: AirValue): AirValue {
        return asinh(a, getMathContext(c))
    }

    private fun acosh(a: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(BigDecimalMath.acosh(a.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun acosh(a: AirValue, c: AirValue): AirValue {
        return acosh(a, getMathContext(c))
    }

    private fun atanh(a: AirValue, mc: MathContext?): AirValue {
        return if (a is DecimalValue && mc != null) {
            try {
                DecimalValue.valueOf(BigDecimalMath.atanh(a.value, mc))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun atanh(a: AirValue, c: AirValue): AirValue {
        return atanh(a, getMathContext(c))
    }

    override fun random32(): AirValue {
        return random(MathContext.DECIMAL32)
    }

    override fun seed32(seed: AirValue): AirValue {
        return seed(seed, MathContext.DECIMAL32)
    }

    override fun round32(a: AirValue): AirValue {
        return round(a, MathContext.DECIMAL32)
    }

    override fun add32(a: AirValue, b: AirValue): AirValue {
        return add(a, b, MathContext.DECIMAL32)
    }

    override fun subtract32(a: AirValue, b: AirValue): AirValue {
        return subtract(a, b, MathContext.DECIMAL32)
    }

    override fun multiply32(a: AirValue, b: AirValue): AirValue {
        return multiply(a, b, MathContext.DECIMAL32)
    }

    override fun divide32(a: AirValue, b: AirValue): AirValue {
        return divide(a, b, MathContext.DECIMAL32)
    }

    override fun divideToInt32(a: AirValue, b: AirValue): AirValue {
        return divideToInt(a, b, MathContext.DECIMAL32)
    }

    override fun remainder32(a: AirValue, b: AirValue): AirValue {
        return remainder(a, b, MathContext.DECIMAL32)
    }

    override fun divRem32(a: AirValue, b: AirValue): AirValue {
        return divRem(a, b, MathContext.DECIMAL32)
    }

    override fun power32(a: AirValue, b: AirValue): AirValue {
        return power(a, b, MathContext.DECIMAL32)
    }

    override fun exp32(a: AirValue): AirValue {
        return exp(a, MathContext.DECIMAL32)
    }

    override fun log32(a: AirValue, b: AirValue): AirValue {
        return log(a, b, MathContext.DECIMAL32)
    }

    override fun ln32(a: AirValue): AirValue {
        return ln(a, MathContext.DECIMAL32)
    }

    override fun shiftLeft32(a: AirValue, n: AirValue): AirValue {
        return shiftLeft(a, n, MathContext.DECIMAL32)
    }

    override fun shiftRight32(a: AirValue, n: AirValue): AirValue {
        return shiftRight(a, n, MathContext.DECIMAL32)
    }

    override fun sin32(a: AirValue): AirValue {
        return sin(a, MathContext.DECIMAL32)
    }

    override fun cos32(a: AirValue): AirValue {
        return cos(a, MathContext.DECIMAL32)
    }

    override fun tan32(a: AirValue): AirValue {
        return tan(a, MathContext.DECIMAL32)
    }

    override fun asin32(a: AirValue): AirValue {
        return asin(a, MathContext.DECIMAL32)
    }

    override fun acos32(a: AirValue): AirValue {
        return acos(a, MathContext.DECIMAL32)
    }

    override fun atan32(a: AirValue): AirValue {
        return atan(a, MathContext.DECIMAL32)
    }

    override fun atan232(a: AirValue, b: AirValue): AirValue {
        return atan2(a, b, MathContext.DECIMAL32)
    }

    override fun sinh32(a: AirValue): AirValue {
        return sinh(a, MathContext.DECIMAL32)
    }

    override fun cosh32(a: AirValue): AirValue {
        return cosh(a, MathContext.DECIMAL32)
    }

    override fun tanh32(a: AirValue): AirValue {
        return tanh(a, MathContext.DECIMAL32)
    }

    override fun asinh32(a: AirValue): AirValue {
        return asinh(a, MathContext.DECIMAL32)
    }

    override fun acosh32(a: AirValue): AirValue {
        return acosh(a, MathContext.DECIMAL32)
    }

    override fun atanh32(a: AirValue): AirValue {
        return atanh(a, MathContext.DECIMAL32)
    }

    override fun random64(): AirValue {
        return random(MathContext.DECIMAL64)
    }

    override fun seed64(seed: AirValue): AirValue {
        return seed(seed, MathContext.DECIMAL64)
    }

    override fun round64(a: AirValue): AirValue {
        return round(a, MathContext.DECIMAL64)
    }

    override fun add64(a: AirValue, b: AirValue): AirValue {
        return add(a, b, MathContext.DECIMAL64)
    }

    override fun subtract64(a: AirValue, b: AirValue): AirValue {
        return subtract(a, b, MathContext.DECIMAL64)
    }

    override fun multiply64(a: AirValue, b: AirValue): AirValue {
        return multiply(a, b, MathContext.DECIMAL64)
    }

    override fun divide64(a: AirValue, b: AirValue): AirValue {
        return divide(a, b, MathContext.DECIMAL64)
    }

    override fun divideToInt64(a: AirValue, b: AirValue): AirValue {
        return divideToInt(a, b, MathContext.DECIMAL64)
    }

    override fun remainder64(a: AirValue, b: AirValue): AirValue {
        return remainder(a, b, MathContext.DECIMAL64)
    }

    override fun divRem64(a: AirValue, b: AirValue): AirValue {
        return divRem(a, b, MathContext.DECIMAL64)
    }

    override fun power64(a: AirValue, b: AirValue): AirValue {
        return power(a, b, MathContext.DECIMAL64)
    }

    override fun exp64(a: AirValue): AirValue {
        return exp(a, MathContext.DECIMAL64)
    }

    override fun log64(a: AirValue, b: AirValue): AirValue {
        return log(a, b, MathContext.DECIMAL64)
    }

    override fun ln64(a: AirValue): AirValue {
        return ln(a, MathContext.DECIMAL64)
    }

    override fun shiftLeft64(a: AirValue, n: AirValue): AirValue {
        return shiftLeft(a, n, MathContext.DECIMAL64)
    }

    override fun shiftRight64(a: AirValue, n: AirValue): AirValue {
        return shiftRight(a, n, MathContext.DECIMAL64)
    }

    override fun sin64(a: AirValue): AirValue {
        return sin(a, MathContext.DECIMAL64)
    }

    override fun cos64(a: AirValue): AirValue {
        return cos(a, MathContext.DECIMAL64)
    }

    override fun tan64(a: AirValue): AirValue {
        return tan(a, MathContext.DECIMAL64)
    }

    override fun asin64(a: AirValue): AirValue {
        return asin(a, MathContext.DECIMAL64)
    }

    override fun acos64(a: AirValue): AirValue {
        return acos(a, MathContext.DECIMAL64)
    }

    override fun atan64(a: AirValue): AirValue {
        return atan(a, MathContext.DECIMAL64)
    }

    override fun atan264(a: AirValue, b: AirValue): AirValue {
        return atan2(a, b, MathContext.DECIMAL64)
    }

    override fun sinh64(a: AirValue): AirValue {
        return sinh(a, MathContext.DECIMAL64)
    }

    override fun cosh64(a: AirValue): AirValue {
        return cosh(a, MathContext.DECIMAL64)
    }

    override fun tanh64(a: AirValue): AirValue {
        return tanh(a, MathContext.DECIMAL64)
    }

    override fun asinh64(a: AirValue): AirValue {
        return asinh(a, MathContext.DECIMAL64)
    }

    override fun acosh64(a: AirValue): AirValue {
        return acosh(a, MathContext.DECIMAL64)
    }

    override fun atanh64(a: AirValue): AirValue {
        return atanh(a, MathContext.DECIMAL64)
    }

    override fun random128(): AirValue {
        return random(MathContext.DECIMAL128)
    }

    override fun seed128(seed: AirValue): AirValue {
        return seed(seed, MathContext.DECIMAL128)
    }

    override fun round128(a: AirValue): AirValue {
        return round(a, MathContext.DECIMAL128)
    }

    override fun add128(a: AirValue, b: AirValue): AirValue {
        return add(a, b, MathContext.DECIMAL128)
    }

    override fun subtract128(a: AirValue, b: AirValue): AirValue {
        return subtract(a, b, MathContext.DECIMAL128)
    }

    override fun multiply128(a: AirValue, b: AirValue): AirValue {
        return multiply(a, b, MathContext.DECIMAL128)
    }

    override fun divide128(a: AirValue, b: AirValue): AirValue {
        return divide(a, b, MathContext.DECIMAL128)
    }

    override fun divideToInt128(a: AirValue, b: AirValue): AirValue {
        return divideToInt(a, b, MathContext.DECIMAL128)
    }

    override fun remainder128(a: AirValue, b: AirValue): AirValue {
        return remainder(a, b, MathContext.DECIMAL128)
    }

    override fun divRem128(a: AirValue, b: AirValue): AirValue {
        return divRem(a, b, MathContext.DECIMAL128)
    }

    override fun power128(a: AirValue, b: AirValue): AirValue {
        return power(a, b, MathContext.DECIMAL128)
    }

    override fun exp128(a: AirValue): AirValue {
        return exp(a, MathContext.DECIMAL128)
    }

    override fun log128(a: AirValue, b: AirValue): AirValue {
        return log(a, b, MathContext.DECIMAL128)
    }

    override fun ln128(a: AirValue): AirValue {
        return ln(a, MathContext.DECIMAL128)
    }

    override fun shiftLeft128(a: AirValue, n: AirValue): AirValue {
        return shiftLeft(a, n, MathContext.DECIMAL128)
    }

    override fun shiftRight128(a: AirValue, n: AirValue): AirValue {
        return shiftRight(a, n, MathContext.DECIMAL128)
    }

    override fun sin128(a: AirValue): AirValue {
        return sin(a, MathContext.DECIMAL128)
    }

    override fun cos128(a: AirValue): AirValue {
        return cos(a, MathContext.DECIMAL128)
    }

    override fun tan128(a: AirValue): AirValue {
        return tan(a, MathContext.DECIMAL128)
    }

    override fun asin128(a: AirValue): AirValue {
        return asin(a, MathContext.DECIMAL128)
    }

    override fun acos128(a: AirValue): AirValue {
        return acos(a, MathContext.DECIMAL128)
    }

    override fun atan128(a: AirValue): AirValue {
        return atan(a, MathContext.DECIMAL128)
    }

    override fun atan2128(a: AirValue, b: AirValue): AirValue {
        return atan2(a, b, MathContext.DECIMAL128)
    }

    override fun sinh128(a: AirValue): AirValue {
        return sinh(a, MathContext.DECIMAL128)
    }

    override fun cosh128(a: AirValue): AirValue {
        return cosh(a, MathContext.DECIMAL128)
    }

    override fun tanh128(a: AirValue): AirValue {
        return tanh(a, MathContext.DECIMAL128)
    }

    override fun asinh128(a: AirValue): AirValue {
        return asinh(a, MathContext.DECIMAL128)
    }

    override fun acosh128(a: AirValue): AirValue {
        return acosh(a, MathContext.DECIMAL128)
    }

    override fun atanh128(a: AirValue): AirValue {
        return atanh(a, MathContext.DECIMAL128)
    }

}