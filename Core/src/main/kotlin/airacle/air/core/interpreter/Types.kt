package airacle.air.core.interpreter

object Types : ITypes {
    override fun typeOf(value: AirValue): AirValue {
        return when (value) {
            is UnitValue -> ITypes.UNIT
            is BoolValue -> ITypes.BOOL
            is IntValue -> ITypes.INT
            is DecimalValue -> ITypes.DECIMAL
            is StringValue -> ITypes.STRING
            is TupleValue -> ITypes.TUPLE
            is ListValue -> ITypes.LIST
            is MapValue -> ITypes.MAP
        }

    }

    override fun isType(a: AirValue, type: AirValue): AirValue {
        return when {
            a is UnitValue && type == ITypes.UNIT -> BoolValue.TRUE
            a is BoolValue && type == ITypes.BOOL -> BoolValue.TRUE
            a is IntValue && type == ITypes.INT -> BoolValue.TRUE
            a is DecimalValue && type == ITypes.DECIMAL -> BoolValue.TRUE
            a is StringValue && type == ITypes.STRING -> BoolValue.TRUE
            a is TupleValue && type == ITypes.TUPLE -> BoolValue.TRUE
            a is ListValue && type == ITypes.LIST -> BoolValue.TRUE
            a is MapValue && type == ITypes.MAP -> BoolValue.TRUE
            else -> BoolValue.FALSE
        }
    }
}