package airacle.air.core.api

interface IAirParser<T, V> {
    fun parse(tokens: List<T>): V
}