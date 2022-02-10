package airacle.air.interpreter

import ch.obermuhlner.math.big.BigDecimalMath
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

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
            when {
                b.value.signum() < 0 -> UnitValue
                b == IntValue.ZERO -> IntValue.ONE
                a == IntValue.ZERO || a == IntValue.ONE || b == IntValue.ONE -> a
                a == IntValue.TWO -> IntValue.valueOf(BigInteger.ONE.shl(b.value.intValueExact()))
                a == IntValue.NEG_ONE -> if (b.value.testBit(0)) {
                    IntValue.NEG_ONE
                } else {
                    IntValue.ONE
                }
                a == IntValue.NEG_TWO -> if (b.value.testBit(0)) {
                    IntValue.valueOf(BigInteger.ONE.shl(b.value.intValueExact()).negate())
                } else {
                    IntValue.valueOf(BigInteger.ONE.shl(b.value.intValueExact()))
                }
                else -> try {
                    IntValue.valueOf(a.value.pow(b.value.intValueExact()))
                } catch (t: Throwable) {
                    UnitValue
                }
            }
        } else {
            UnitValue
        }
    }

    override fun root(a: AirValue, b: AirValue): AirValue {
        return if (a is IntValue && b is IntValue) {
            when {
                b.value.signum() <= 0 -> UnitValue
                a == IntValue.ZERO || a == IntValue.ONE -> a
                b == IntValue.ONE -> a
                a.value.bitLength().toBigInteger() < b.value -> IntValue.ONE
                else -> {
                    try {
                        val floor = BigDecimalMath.root(
                            a.value.toBigDecimal(),
                            b.value.toBigDecimal(),
                            MathContext(11, RoundingMode.FLOOR)
                        ).toInt()
                        val ceiling = (floor + 1).toBigInteger()
                        if (ceiling.pow(b.value.intValueExact()) <= a.value) {
                            IntValue.valueOf(ceiling)
                        } else {
                            IntValue.valueOf(floor.toBigInteger())
                        }
                    } catch (t: Throwable) {
                        UnitValue
                    }
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
                    when (b) {
                        IntValue.TWO -> IntValue.valueOf((a.value.bitLength() - 1).toBigInteger())
                        IntValue.ONE -> UnitValue
                        else -> {
                            // b >= 3, so the result will be an int32 < 2^31 ~ 2e10
                            // 1 < logA < 2^31
                            val logA = BigDecimalMath.log(a.value.toBigDecimal(), MathContext(11))
                            // 1 < logB < 2^31
                            val logB = BigDecimalMath.log(b.value.toBigDecimal(), MathContext(11))
                            val floor = logA.divide(logB, 0, RoundingMode.FLOOR).toInt()
                            val ceiling = floor + 1
                            if (b.value.pow(ceiling) <= a.value) {
                                IntValue.valueOf(ceiling.toBigInteger())
                            } else {
                                IntValue.valueOf(floor.toBigInteger())
                            }
                        }
                    }
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
                IntValue.valueOf(a.value.abs().mod(b.value.abs()))
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
}