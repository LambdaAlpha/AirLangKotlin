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
        private val positiveConstants: Array<IntValue> = Array(17) { i: Int ->
            IntValue(BigInteger.valueOf(i.toLong()))
        }
        private val negativeConstants: Array<IntValue> = Array(17) { i: Int ->
            IntValue(BigInteger.valueOf(-i.toLong()))
        }
        val ZERO: IntValue = IntValue(BigInteger.ZERO)
        val ONE: IntValue = valueOf(BigInteger.ONE)
        val TWO: IntValue = valueOf(BigInteger.TWO)
        val NEG_ONE: IntValue = valueOf(BigInteger.valueOf(-1L))

        fun valueOf(value: BigInteger): IntValue {
            return when {
                value == BigInteger.ZERO -> ZERO
                value > BigInteger.ZERO && value <= BigInteger.valueOf(16L) -> positiveConstants[value.toInt()]
                value < BigInteger.ZERO && value >= BigInteger.valueOf(-16L) -> negativeConstants[-value.toInt()]
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

class DecimalValue private constructor(val value: BigDecimal) : PrimitiveValue {
    companion object {
        val ZERO: DecimalValue = DecimalValue(BigDecimal.ZERO)
        val ONE: DecimalValue = DecimalValue(BigDecimal.ONE)
        val TWO: DecimalValue = DecimalValue("2".toBigDecimal())

        fun valueOf(value: BigDecimal): DecimalValue {
            return when (value) {
                BigDecimal.ZERO -> ZERO
                BigDecimal.ONE -> ONE
                "2".toBigDecimal() -> TWO
                else -> DecimalValue(value)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is DecimalValue && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return DecimalToken.toString(value)
    }
}

class StringValue private constructor(val value: String) : PrimitiveValue {
    companion object {
        val EMPTY = StringValue("")
        private val CONSTANTS: Array<StringValue> = Array(128) {
            StringValue(it.toChar().toString())
        }

        fun valueOf(value: String): StringValue {
            return if (value.isEmpty()) {
                EMPTY
            } else if (value.length == 1 && value[0].code < 128) {
                CONSTANTS[value[0].code]
            } else {
                StringValue(value)
            }
        }
    }

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

class TupleValue private constructor(val value: Array<AirValue>) : AirValue {
    companion object {
        fun valueOf(vararg values: AirValue): TupleValue {
            return TupleValue(arrayOf(*values))
        }

        fun fromArray(values: Array<AirValue>): TupleValue {
            return TupleValue(values)
        }
    }

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

class ListValue private constructor(val value: MutableList<AirValue>) : AirValue {
    companion object {
        fun valueOf(value: MutableList<AirValue>): ListValue {
            return ListValue(value)
        }

        fun valueOf(vararg values: AirValue): ListValue {
            return ListValue(mutableListOf(*values))
        }
    }

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

class MapValue private constructor(val value: MutableMap<AirValue, AirValue>) : AirValue {
    companion object {
        fun valueOf(value: MutableMap<AirValue, AirValue>): MapValue {
            return MapValue(value)
        }

        fun valueOf(vararg values: Pair<AirValue, AirValue>): MapValue {
            return MapValue(mutableMapOf(*values))
        }
    }

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
