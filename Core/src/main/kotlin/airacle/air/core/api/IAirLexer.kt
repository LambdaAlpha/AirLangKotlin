package airacle.air.core.api

interface IAirLexer<T> {
    fun lex(source: String): List<T>
}