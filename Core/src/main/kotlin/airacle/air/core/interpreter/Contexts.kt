package airacle.air.core.interpreter

class Contexts : IContexts {
    companion object {
        val ROOT = BoolValue.TRUE
        val PARENT = BoolValue.FALSE
        val FOCUS = StringValue.valueOf("_focus")
    }

    private var root: AirValue = MapValue.valueOf(
        FOCUS to ListValue.valueOf(),
    )

    private fun shallowCopy(a: AirValue): AirValue {
        return when (a) {
            is TupleValue -> TupleValue.valueOf(*a.value)
            is ListValue -> ListValue.valueOf(ArrayList(a.value))
            is MapValue -> MapValue.valueOf(HashMap(a.value))
            else -> a
        }
    }

    private fun deepCopy(a: AirValue): AirValue {
        return when (a) {
            is TupleValue -> tupleCopy(a)
            is ListValue -> listCopy(a)
            is MapValue -> mapCopy(a)
            else -> a
        }
    }

    private fun tupleCopy(a: TupleValue): TupleValue {
        return TupleValue.fromArray(Array(a.value.size) {
            deepCopy(a.value[it])
        })
    }

    private fun listCopy(a: ListValue): ListValue {
        val list = ArrayList<AirValue>(a.value.size)
        for (v in a.value) {
            list.add(deepCopy(v))
        }
        return ListValue.valueOf(list)
    }

    private fun mapCopy(a: MapValue): MapValue {
        val map = HashMap<AirValue, AirValue>(a.value.size)
        for (entry in a.value) {
            map[deepCopy(entry.key)] = deepCopy(entry.value)
        }
        return MapValue.valueOf(map)
    }

    private fun focused0(): AirValue {
        return valueRead(root, FOCUS)
    }

    override fun focused(): AirValue {
        return deepCopy(focused0())
    }

    override fun focus(path: AirValue): AirValue {
        val normalized = normalizePath(path)
        if (normalized !is ListValue) {
            return BoolValue.FALSE
        }
        val suc = valueWrite(root, FOCUS, normalized)
        return BoolValue.valueOf(suc)
    }

    private fun normalizePath0(path: AirValue): AirValue {
        val concat = ArrayList<AirValue>()

        val transformer: (AirValue) -> Iterable<AirValue> = {
            if (it !is ListValue) {
                listOf(it)
            } else {
                it.value
            }
        }

        val focused = focused0()
        if (focused is ListValue) {
            focused.value.flatMapTo(concat, transformer)
        } else {
            concat.add(focused)
        }

        if (path is ListValue) {
            path.value.flatMapTo(concat, transformer)
        } else {
            concat.add(path)
        }

        val trim = ArrayList<AirValue>(concat.size)
        for (i in concat) {
            if (i == ROOT) {
                trim.clear()
            } else if (i == PARENT) {
                if (trim.isEmpty()) {
                    return UnitValue
                } else {
                    trim.removeLast()
                }
            } else if (i != UnitValue) {
                trim.add(i)
            }
        }
        return ListValue.valueOf(trim)
    }

    override fun normalizePath(path: AirValue): AirValue {
        return deepCopy(normalizePath0(path))
    }

    private fun valueExist(parent: AirValue, key: AirValue): BoolValue {
        return when (parent) {
            is TupleValue -> tupleExist(parent, key)
            is ListValue -> listExist(parent, key)
            is MapValue -> mapExist(parent, key)
            else -> BoolValue.FALSE
        }
    }

    private fun tupleExist(tuple: TupleValue, key: AirValue): BoolValue {
        return if (key is IntValue) {
            try {
                val i = key.value.intValueExact()
                BoolValue.valueOf(i in tuple.value.indices)
            } catch (t: Throwable) {
                BoolValue.FALSE
            }
        } else {
            BoolValue.FALSE
        }
    }

    private fun listExist(list: ListValue, key: AirValue): BoolValue {
        return if (key is IntValue) {
            try {
                val i = key.value.intValueExact()
                BoolValue.valueOf(i in list.value.indices)
            } catch (t: Throwable) {
                BoolValue.FALSE
            }
        } else {
            BoolValue.FALSE
        }
    }

    private fun mapExist(map: MapValue, key: AirValue): BoolValue {
        return BoolValue.valueOf(map.value.contains(key))
    }

    override fun exist(path: AirValue): AirValue {
        val normalized = normalizePath0(path)
        if (normalized !is ListValue) {
            return BoolValue.FALSE
        }

        if (normalized.value.isEmpty()) {
            return BoolValue.TRUE
        }
        val parent = readParent(normalized)
        return valueExist(parent, normalized.value.last())
    }

    private fun readParent(path: AirValue): AirValue {
        return if (path is ListValue && path.value.isNotEmpty()) {
            var ret: AirValue = root
            for (i in 0 until path.value.size - 1) {
                ret = valueRead(ret, path.value[i])
            }
            ret
        } else {
            UnitValue
        }
    }

    private fun read0(path: AirValue): AirValue {
        return if (path is ListValue) {
            var ret: AirValue = root
            for (i in path.value) {
                ret = valueRead(ret, i)
            }
            ret
        } else {
            UnitValue
        }
    }

    private fun valueRead(v: AirValue, key: AirValue): AirValue {
        return when (v) {
            is TupleValue -> tupleRead(v, key)
            is ListValue -> listRead(v, key)
            is MapValue -> mapRead(v, key)
            else -> UnitValue
        }
    }

    private fun tupleRead(tuple: TupleValue, key: AirValue): AirValue {
        return if (key is IntValue) {
            try {
                val i = key.value.intValueExact()
                if (i in tuple.value.indices) {
                    tuple.value[i]
                } else {
                    UnitValue
                }
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    private fun listRead(list: ListValue, key: AirValue): AirValue {
        return if (key is IntValue) {
            try {
                val i = key.value.intValueExact()
                if (i in list.value.indices) {
                    list.value[i]
                } else {
                    UnitValue
                }
            } catch (t: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    private fun mapRead(map: MapValue, key: AirValue): AirValue {
        return map.value[key] ?: UnitValue
    }

    override fun read(path: AirValue): AirValue {
        val normalized = normalizePath(path)
        if (normalized !is ListValue) {
            return UnitValue
        }

        if (normalized.value.isEmpty()) {
            return deepCopy(root)
        }
        val v = read0(normalized)
        return deepCopy(v)
    }

    private fun tupleWrite(tuple: TupleValue, key: AirValue, value: AirValue): Boolean {
        return if (key is IntValue) {
            try {
                val i = key.value.intValueExact()
                if (i in tuple.value.indices) {
                    tuple.value[i] = value
                    true
                } else {
                    false
                }
            } catch (t: Throwable) {
                false
            }
        } else {
            false
        }
    }

    private fun listWrite(list: ListValue, key: AirValue, value: AirValue): Boolean {
        return if (key is IntValue) {
            try {
                val i = key.value.intValueExact()
                if (i in list.value.indices) {
                    list.value[i] = value
                    true
                } else {
                    false
                }
            } catch (t: Throwable) {
                false
            }
        } else {
            false
        }
    }

    private fun mapWrite(map: MapValue, key: AirValue, value: AirValue): Boolean {
        map.value[key] = value
        return true
    }

    private fun valueWrite(parent: AirValue, key: AirValue, value: AirValue): Boolean {
        return when (parent) {
            is TupleValue -> tupleWrite(parent, key, value)
            is ListValue -> listWrite(parent, key, value)
            is MapValue -> mapWrite(parent, key, value)
            else -> false
        }
    }

    override fun write(path: AirValue, value: AirValue): AirValue {
        val normalized = normalizePath(path)
        if (normalized !is ListValue) {
            return BoolValue.FALSE
        }

        if (normalized.value.isEmpty()) {
            root = deepCopy(value)
            return BoolValue.TRUE
        }

        val parent = readParent(normalized)
        val k = deepCopy(normalized.value.last())
        val v = deepCopy(value)
        val suc = valueWrite(parent, k, v)
        return BoolValue.valueOf(suc)
    }

    private fun listDelete(list: ListValue, key: AirValue): AirValue? {
        return if (key is IntValue) {
            try {
                val i = key.value.intValueExact()
                if (i in list.value.indices) {
                    list.value.removeAt(i)
                } else {
                    null
                }
            } catch (t: Throwable) {
                null
            }
        } else {
            null
        }
    }

    private fun mapDelete(map: MapValue, key: AirValue): AirValue? {
        return map.value.remove(key)
    }

    private fun valueDelete(parent: AirValue, key: AirValue): AirValue? {
        return when (parent) {
            is ListValue -> listDelete(parent, key)
            is MapValue -> mapDelete(parent, key)
            else -> null
        }
    }

    override fun delete(path: AirValue): AirValue {
        val normalized = normalizePath(path)
        if (normalized !is ListValue) {
            return BoolValue.FALSE
        }
        if (normalized.value.isEmpty()) {
            return BoolValue.FALSE
        }
        val parent = readParent(normalized)
        val suc = valueDelete(parent, normalized.value.last())
        return BoolValue.valueOf(suc != null)
    }

    override fun move(oldPath: AirValue, newPath: AirValue): AirValue {
        val normOld = normalizePath(oldPath)
        val normNew = normalizePath(newPath)

        if (normOld !is ListValue || normNew !is ListValue) {
            return BoolValue.FALSE
        }

        if (normOld.value.isEmpty()) {
            return BoolValue.FALSE
        }
        if (normNew.value.isEmpty()) {
            root = read0(normOld)
            return BoolValue.TRUE
        }

        // delete old value before reading new parent to avoid cyclic reference
        val parentOld = readParent(normOld)
        val v = valueDelete(parentOld, normOld.value.last()) ?: return BoolValue.FALSE
        val parentNew = readParent(normNew)
        val suc = valueWrite(parentNew, normNew.value.last(), v)
        return BoolValue.valueOf(suc)
    }

    private fun listInsert(list: ListValue, key: AirValue, value: AirValue): Boolean {
        return if (key is IntValue) {
            try {
                val i = key.value.intValueExact()
                if (i in (-list.value.size - 1)..list.value.size) {
                    val index = if (i >= 0) i else list.value.size + 1 + i
                    list.value.add(index, value)
                    true
                } else {
                    false
                }
            } catch (t: Throwable) {
                false
            }
        } else {
            false
        }
    }

    private fun mapInsert(map: MapValue, key: AirValue, value: AirValue): Boolean {
        map.value[key] = value
        return true
    }

    private fun valueInsert(v: AirValue, key: AirValue, value: AirValue): Boolean {
        return when (v) {
            is ListValue -> listInsert(v, key, value)
            is MapValue -> mapInsert(v, key, value)
            else -> false
        }
    }

    override fun insert(path: AirValue, value: AirValue): AirValue {
        val normalized = normalizePath(path)
        if (normalized !is ListValue) {
            return BoolValue.FALSE
        }

        if (normalized.value.isEmpty()) {
            root = deepCopy(value)
            return BoolValue.TRUE
        }

        val parent = readParent(normalized)
        val k = deepCopy(normalized.value.last())
        val v = deepCopy(value)
        val suc = valueInsert(parent, k, v)
        return BoolValue.valueOf(suc)
    }
}