package airacle.air.more.repl

import airacle.air.core.api.IAirInterpreter
import airacle.air.core.api.IAirLexer
import airacle.air.core.api.IAirParser
import airacle.air.core.interpreter.AirInterpreter
import airacle.air.core.interpreter.AirInterpreterVersion
import airacle.air.core.interpreter.AirValue
import airacle.air.core.lexer.AirLexer
import airacle.air.core.lexer.AirLexerVersion
import airacle.air.core.lexer.AirToken
import airacle.air.core.parser.AirParser
import airacle.air.core.parser.AirParserVersion

class AirRepl(val version: AirReplVersion) {
    val lexer: IAirLexer<AirToken>
    val parser: IAirParser<AirToken, AirValue>
    val interpreter: IAirInterpreter<AirValue>

    init {
        when (version) {
            AirReplVersion.V0 -> {
                lexer = AirLexer(AirLexerVersion.V0)
                parser = AirParser(AirParserVersion.V0, AirReplParserConfig)
                interpreter = AirInterpreter(
                    AirInterpreterVersion.V0,
                    AirReplParserConfig,
                    AirReplInterpreterConfig(lexer, parser)
                )
            }
        }
    }
}