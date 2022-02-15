package airacle.air.more.repl

import airacle.air.core.api.IAirLexer
import airacle.air.core.api.IAirParser
import airacle.air.core.interpreter.*
import airacle.air.core.lexer.AirToken

private typealias C = AirReplParserConfig

class AirReplInterpreterConfig(
    lexer: IAirLexer<AirToken>,
    parser: IAirParser<AirToken, AirValue>
) : IAirInterpreterConfig {
    private val config: IAirInterpreterConfig = AirInterpreterConfig(lexer, parser)

    override fun primitiveInterpret(value: AirValue): AirValue {
        if (value !is TupleValue || value.value.isEmpty()) {
            return value
        }
        val keyword = value.value[0]
        if (keyword is StringValue) {
            return interpretString(keyword.value, value)
        }
        return value
    }

    private fun interpretString(
        keyword: String,
        value: TupleValue
    ): AirValue {
        return when (keyword) {
            C.EXIT -> TupleValue.valueOf(EXIT)
            C.QUIT -> TupleValue.valueOf(QUIT)
            else -> TupleValue.valueOf(OUTPUT, config.primitiveInterpret(value))
        }
    }

    companion object {
        val OUTPUT = StringValue.valueOf("output")
        val EXIT = StringValue.valueOf(C.EXIT)
        val QUIT = StringValue.valueOf(C.QUIT)
    }
}