package airacle.air.api

interface IAirInterpreter<V> {
    fun interpret(value: V): V
}