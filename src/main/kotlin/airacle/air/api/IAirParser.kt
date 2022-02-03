package airacle.air.api

interface IAirParser<T, V> {
    fun parse(tokens: List<T>): V
}