package airacle.air.core.api

import airacle.air.core.interpreter.AirInterpreter
import airacle.air.core.interpreter.AirInterpreterConfig
import airacle.air.core.interpreter.AirInterpreterVersion
import airacle.air.core.interpreter.AirValue
import airacle.air.core.lexer.AirLexer
import airacle.air.core.lexer.AirLexerConfig
import airacle.air.core.lexer.AirLexerVersion
import airacle.air.core.lexer.AirToken
import airacle.air.core.parser.AirParser
import airacle.air.core.parser.AirParserConfig
import airacle.air.core.parser.AirParserVersion

class Air(val version: AirVersion) {
    val lexer: IAirLexer<AirToken>
    val parser: IAirParser<AirToken, AirValue>
    val interpreter: IAirInterpreter<AirValue>

    init {
        when (version) {
            AirVersion.V0 -> {
                lexer = AirLexer(AirLexerVersion.V0, AirLexerConfig)
                parser = AirParser(AirParserVersion.V0, AirParserConfig)
                interpreter = AirInterpreter(
                    AirInterpreterVersion.V0,
                    AirInterpreterConfig(lexer, parser)
                )
            }
        }
    }
}