package airacle.air.api

import airacle.air.interpreter.AirInterpreter
import airacle.air.interpreter.AirInterpreterVersion
import airacle.air.interpreter.AirValue
import airacle.air.lexer.AirLexer
import airacle.air.lexer.AirLexerConfig
import airacle.air.lexer.AirLexerVersion
import airacle.air.lexer.AirToken
import airacle.air.parser.AirParser
import airacle.air.parser.AirParserConfig
import airacle.air.parser.AirParserVersion

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
                    lexer,
                    parser
                )
            }
        }
    }
}