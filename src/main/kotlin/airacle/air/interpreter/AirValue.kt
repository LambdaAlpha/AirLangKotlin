package airacle.air.interpreter

sealed interface AirValue

sealed interface PrimitiveValue : AirValue

object UnitValue : PrimitiveValue

sealed class BoolValue(val value: Boolean) : PrimitiveValue

object TrueValue : BoolValue(true)

object FalseValue : BoolValue(false)

data class IntegerValue(val value: Long) : PrimitiveValue

data class FloatValue(val value: Double) : PrimitiveValue

data class StringValue(val value: String) : PrimitiveValue

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
}

data class ListValue(val value: MutableList<AirValue>) : AirValue

data class MapValue(val value: MutableMap<AirValue, AirValue>) : AirValue
