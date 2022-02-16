package airacle.air.more.repl

import airacle.air.core.interpreter.*
import airacle.air.core.lexer.AirLexer
import airacle.air.core.parser.AirParser

private typealias C = AirReplParserConfig

class AirReplInterpreterConfig(
    val lexer: AirLexer,
    val parser: AirParser
) : IAirInterpreterConfig {
    private val config: IAirInterpreterConfig = AirInterpreterConfig(lexer, parser)

    override fun isValue(v: AirValue): Boolean {
        return config.isValue(v)
    }

    override fun isQuote(v: AirValue): Boolean {
        return config.isQuote(v)
    }

    override fun isEval(v: AirValue): Boolean {
        return config.isEval(v)
    }

    override fun primitiveInterpret(value: AirValue): AirValue {
        if (value !is TupleValue || value.value.isEmpty()) {
            return value
        }
        val keyword = value.value[0]
        if (parser.config.paramLength(keyword) + 1 != value.value.size) {
            return UnitValue
        }
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
            else -> config.primitiveInterpret(value)
        }
    }

    companion object {
        val EXIT = StringValue.valueOf(C.EXIT)
        val QUIT = StringValue.valueOf(C.QUIT)
    }
}