package airacle.air.core.interpreter

interface ITypes {
    companion object {
        val UNIT = StringValue.valueOf("unit")
        val BOOL = StringValue.valueOf("bool")
        val INT = StringValue.valueOf("int")
        val DECIMAL = StringValue.valueOf("decimal")
        val STRING = StringValue.valueOf("string")
        val TUPLE = StringValue.valueOf("tuple")
        val LIST = StringValue.valueOf("list")
        val MAP = StringValue.valueOf("map")
    }

    fun typeOf(value: AirValue): AirValue

    fun isType(a: AirValue, type: AirValue): AirValue
}