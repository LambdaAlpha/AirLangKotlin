package airacle.air.core.interpreter

object Maps : IMaps {
    override fun toTupleList(a: AirValue): AirValue {
        return if (a is MapValue) {
            TupleValue.valueOf(
                ListValue.valueOf(a.value.keys.toMutableList()),
                ListValue.valueOf(a.value.values.toMutableList())
            )
        } else {
            UnitValue
        }
    }

    override fun fromTupleList(a: AirValue): AirValue {
        return if (a is TupleValue) {
            if (a.value.size == 2) {
                val v1 = a.value[0]
                val v2 = a.value[1]
                if (v1 is ListValue && v2 is ListValue && v1.value.size == v2.value.size) {
                    val map = HashMap<AirValue, AirValue>(v1.value.size)
                    for (i in v1.value.indices) {
                        map[v1.value[i]] = v2.value[i]
                    }
                    MapValue.valueOf(map)
                } else {
                    UnitValue
                }
            } else {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun toListTuple(a: AirValue): AirValue {
        return if (a is MapValue) {
            val list = ArrayList<AirValue>(a.value.size)
            for (entry in a.value) {
                list.add(TupleValue.valueOf(entry.key, entry.value))
            }
            ListValue.valueOf(list)
        } else {
            UnitValue
        }
    }

    override fun fromListTuple(a: AirValue): AirValue {
        return if (a is ListValue) {
            val map = HashMap<AirValue, AirValue>(a.value.size)
            var valid = true
            for (i in a.value) {
                if (i is TupleValue && i.value.size == 2) {
                    map[i.value[0]] = i.value[1]
                } else {
                    valid = false
                    break
                }
            }
            if (valid) MapValue.valueOf(map) else UnitValue
        } else {
            UnitValue
        }
    }
}