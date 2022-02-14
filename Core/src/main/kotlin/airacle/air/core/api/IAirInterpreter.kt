package airacle.air.core.api

interface IAirInterpreter<V> {
    fun interpret(value: V): V
}