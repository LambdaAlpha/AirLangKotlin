package airacle.air.interpreter

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
        return "|"
    }
}

sealed class BoolValue(val value: Boolean) : PrimitiveValue {
    override fun equals(other: Any?): Boolean {
        return other is BoolValue && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

object TrueValue : BoolValue(true) {
    override fun toString(): String {
        return "/"
    }
}

object FalseValue : BoolValue(false) {
    override fun toString(): String {
        return "\\"
    }
}

class IntegerValue(val value: Long) : PrimitiveValue {
    override fun equals(other: Any?): Boolean {
        return other is IntegerValue && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return if (value >= 0) {
            value.toString()
        } else {
            "0$value"
        }
    }
}

class FloatValue(val value: Double) : PrimitiveValue {
    override fun equals(other: Any?): Boolean {
        return other is FloatValue && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return if (value >= 0) {
            value.toString()
        } else {
            "0$value"
        }
    }
}

class StringValue(val value: String) : PrimitiveValue {
    override fun equals(other: Any?): Boolean {
        return other is StringValue && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String = "\"$value\""
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
