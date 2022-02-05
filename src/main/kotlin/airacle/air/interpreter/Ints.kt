package airacle.air.interpreter

import ch.obermuhlner.math.big.BigDecimalMath
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode
import java.util.*

object Ints : IInts {
    override fun add(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            IntValue.valueOf(a.value + b.value)
        } else {
            UnitValue
        }
    }

    override fun subtract(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            IntValue.valueOf(a.value - b.value)
        } else {
            UnitValue
        }
    }

    override fun multiply(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            IntValue.valueOf(a.value * b.value)
        } else {
            UnitValue
        }
    }

    override fun divide(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            if (b == IntValue.ZERO) {
                UnitValue
            } else {
                IntValue.valueOf(a.value / b.value)
            }
        } else {
            UnitValue
        }
    }

    override fun remainder(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            if (b == IntValue.ZERO) {
                UnitValue
            } else {
                IntValue.valueOf(a.value % b.value)
            }
        } else {
            UnitValue
        }
    }

    override fun divRem(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            if (b == IntValue.ZERO) {
                UnitValue
            } else {
                val result = a.value.divideAndRemainder(b.value)
                TupleValue.valueOf(IntValue.valueOf(result[0]), IntValue.valueOf(result[1]))
            }
        } else {
            UnitValue
        }
    }

    override fun power(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            if (b.value.signum() < 0) {
                UnitValue
            } else if (b == IntValue.ZERO) {
                IntValue.ONE
            } else if (a == IntValue.ZERO || a == IntValue.ONE || b == IntValue.ONE) {
                a
            } else if (a == IntValue.NEG_ONE) {
                if (b.value.testBit(0)) {
                    IntValue.NEG_ONE
                } else {
                    IntValue.ONE
                }
            } else {
                try {
                    IntValue.valueOf(a.value.pow(b.value.intValueExact()))
                } catch (t: Throwable) {
                    UnitValue
                }
            }
        } else {
            UnitValue
        }
    }

    override fun log(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue && a.value.signum() > 0 && b.value.signum() > 0) {
            if (a.value < b.value) {
                IntValue.ZERO
            } else {
                try {
                    // TODO: 2022/2/5 maybe not accurate
                    val logA = BigDecimalMath.log(a.value.toBigDecimal(), MathContext.DECIMAL64)
                    val logB = BigDecimalMath.log(b.value.toBigDecimal(), MathContext.DECIMAL64)
                    val ret = logA.divide(logB, 0, RoundingMode.HALF_UP)
                    IntValue.valueOf(ret.toBigInteger())
                } catch (t: Throwable) {
                    UnitValue
                }
            }
        } else {
            UnitValue
        }
    }

    override fun mod(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            if (b == IntValue.ZERO) {
                UnitValue
            } else {
                IntValue.valueOf(a.value.mod(b.value))
            }
        } else {
            UnitValue
        }
    }

    override fun bitNot(a: AirValue): AirValue {
        return if (a is IntValue) {
            IntValue.valueOf(a.value.not())
        } else {
            UnitValue
        }
    }

    override fun bitAnd(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            IntValue.valueOf(a.value and b.value)
        } else {
            UnitValue
        }
    }

    override fun bitOr(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            IntValue.valueOf(a.value or b.value)
        } else {
            UnitValue
        }
    }

    override fun bitXor(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            IntValue.valueOf(a.value xor b.value)
        } else {
            UnitValue
        }
    }

    override fun bitAndNot(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            IntValue.valueOf(a.value.andNot(b.value))
        } else {
            UnitValue
        }
    }

    override fun testBit(a: AirValue, index: AirValue): AirValue {
        return if (a is IntValue && index is IntValue) {
            try {
                BoolValue.valueOf(a.value.testBit(index.value.intValueExact()))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun setBit(a: AirValue, index: AirValue): AirValue {
        return if (a is IntValue && index is IntValue) {
            try {
                IntValue.valueOf(a.value.setBit(index.value.intValueExact()))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun clearBit(a: AirValue, index: AirValue): AirValue {
        return if (a is IntValue && index is IntValue) {
            try {
                IntValue.valueOf(a.value.clearBit(index.value.intValueExact()))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun flipBit(a: AirValue, index: AirValue): AirValue {
        return if (a is IntValue && index is IntValue) {
            try {
                IntValue.valueOf(a.value.flipBit(index.value.intValueExact()))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun bitCount(a: AirValue): AirValue {
        return if (a is IntValue) {
            IntValue.valueOf(a.value.bitCount().toBigInteger())
        } else {
            UnitValue
        }
    }

    override fun bitLength(a: AirValue): AirValue {
        return if (a is IntValue) {
            IntValue.valueOf(a.value.bitLength().toBigInteger())
        } else {
            UnitValue
        }
    }

    override fun lowestSetBit(a: AirValue): AirValue {
        return if (a is IntValue) {
            IntValue.valueOf(a.value.lowestSetBit.toBigInteger())
        } else {
            UnitValue
        }
    }

    override fun shiftLeft(a: AirValue, n: AirValue): AirValue {
        return if (a is IntValue && n is IntValue) {
            try {
                IntValue.valueOf(a.value shl n.value.intValueExact())
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun shiftRight(a: AirValue, n: AirValue): AirValue {
        return if (a is IntValue && n is IntValue) {
            try {
                IntValue.valueOf(a.value shr n.value.intValueExact())
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun random(n: AirValue): AirValue {
        return if (n is IntValue) {
            try {
                IntValue.valueOf(BigInteger(n.value.intValueExact(), Random()))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun randomWithSeed(n: AirValue, seed: AirValue): AirValue {
        return if (n is IntValue && seed is IntValue) {
            try {
                IntValue.valueOf(BigInteger(n.value.intValueExact(), Random(seed.value.longValueExact())))
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }
}