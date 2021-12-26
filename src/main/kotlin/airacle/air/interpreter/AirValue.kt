package airacle.air.interpreter

sealed interface AirValue

sealed interface PrimitiveValue : AirValue

object UnitValue : PrimitiveValue {
    override fun toString(): String {
        return "|"
    }
}

sealed class BoolValue(val value: Boolean) : PrimitiveValue

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

data class IntegerValue(val value: Long) : PrimitiveValue {
    override fun toString(): String {
        return value.toString()
    }
}

data class FloatValue(val value: Double) : PrimitiveValue {
    override fun toString(): String {
        return value.toString()
    }
}

data class StringValue(val value: String) : PrimitiveValue {
    override fun toString(): String {
        return "\"$value\""
    }
}

data class TupleValue(val value: Array<AirValue>) : AirValue {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TupleValue

        if (!value.contentEquals(other.value)) return false

        return true
    }

    override fun hashCode(): Int {
        return value.contentHashCode()
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("< ")
        for (v in value) {
            builder.append(v).append(" ")
        }
        builder.append(">")
        return builder.toString()
    }
}

data class ListValue(val value: MutableList<AirValue>) : AirValue {
    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("[ ")
        for (v in value) {
            builder.append(v).append(" ")
        }
        builder.append("]")
        return builder.toString()
    }
}

data class MapValue(val value: MutableMap<AirValue, AirValue>) : AirValue {
    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("{ ")
        for (v in value.entries) {
            builder.append(v.key).append(" ")
            builder.append(v.value).append(" ")
        }
        builder.append("}")
        return builder.toString()
    }
}
