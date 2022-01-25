package airacle.air.interpreter

import airacle.air.lexer.*
import java.math.BigDecimal
import java.math.BigInteger

sealed interface AirValue

sealed interface PrimitiveValue : AirValue

object UnitValue : PrimitiveValue {
    override fun equals(other: Any?): Boolean {
        return other is UnitValue
    }

    override fun hashCode(): Int {
        return Unit.hashCode()
    }

    override fun toString(): String {
        return UnitToken.toString()
    }
}

class BoolValue private constructor(val value: Boolean) : PrimitiveValue {
    companion object {
        val TRUE: BoolValue = BoolValue(true)
        val FALSE: BoolValue = BoolValue(false)

        fun valueOf(value: Boolean): BoolValue {
            return if (value) TRUE else FALSE
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is BoolValue && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return BoolToken.toString(value)
    }
}

class IntValue private constructor(val value: BigInteger) : PrimitiveValue {
    companion object {
        val ZERO: IntValue = IntValue(BigInteger.ZERO)
        val ONE: IntValue = IntValue(BigInteger.ONE)
        val TWO: IntValue = IntValue(BigInteger.TWO)

        fun valueOf(value: BigInteger): IntValue {
            return when (value) {
                BigInteger.ZERO -> ZERO
                BigInteger.ONE -> ONE
                BigInteger.TWO -> TWO
                else -> IntValue(value)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is IntValue && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return IntToken.toString(value)
    }
}

class RealValue private constructor(val value: BigDecimal) : PrimitiveValue {
    companion object {
        val ZERO: RealValue = RealValue(BigDecimal.ZERO)
        val ONE: RealValue = RealValue(BigDecimal.ONE)
        val TWO: RealValue = RealValue("2".toBigDecimal())

        fun valueOf(value: BigDecimal): RealValue {
            return when (value) {
                BigDecimal.ZERO -> ZERO
                BigDecimal.ONE -> ONE
                "2".toBigDecimal() -> TWO
                else -> RealValue(value)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is RealValue && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return RealToken.toString(value)
    }
}

class StringValue(val value: String) : PrimitiveValue {
    override fun equals(other: Any?): Boolean {
        return other is StringValue && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return FullStringToken.toString(value)
    }
}

class TupleValue(val value: Array<AirValue>) : AirValue {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        return other is TupleValue && value.contentEquals(other.value)
    }

    override fun hashCode(): Int {
        return value.contentHashCode()
    }

    override fun toString(): String {
        return value.joinToString(prefix = "<", postfix = ">", separator = " ")
    }
}

class ListValue(val value: MutableList<AirValue>) : AirValue {
    override fun equals(other: Any?): Boolean {
        return other is ListValue && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value.joinToString(prefix = "[", postfix = "]", separator = " ")
    }
}

class MapValue(val value: MutableMap<AirValue, AirValue>) : AirValue {
    override fun equals(other: Any?): Boolean {
        return other is MapValue && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value.entries.joinToString(prefix = "{", postfix = "}", separator = " ", transform = {
            "${it.key}:${it.value}"
        })
    }
}
