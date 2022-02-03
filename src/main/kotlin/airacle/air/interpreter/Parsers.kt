package airacle.air.interpreter

import airacle.air.api.IAirLexer
import airacle.air.api.IAirParser
import airacle.air.lexer.AirToken

class Parsers(
    private val lexer: IAirLexer<AirToken>,
    private val parser: IAirParser<AirToken, AirValue>
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