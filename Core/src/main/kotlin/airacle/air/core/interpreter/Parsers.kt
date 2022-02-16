package airacle.air.core.interpreter

import airacle.air.core.lexer.AirLexer
import airacle.air.core.parser.AirParser

class Parsers(
    private val lexer: AirLexer,
    private val parser: AirParser
) : IParsers {
    override fun decodeFromString(v: AirValue): AirValue {
        return if (v is StringValue) {
            try {
                val tokens = lexer.lex(v.value)
                return parser.parse(tokens)
            } catch (e: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun encodeToString(v: AirValue): AirValue {
        return StringValue.valueOf(v.toString())
    }
}