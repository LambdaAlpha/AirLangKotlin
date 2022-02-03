package airacle.air.api

interface IAirLexer<T> {
    fun lex(source: String): List<T>
}