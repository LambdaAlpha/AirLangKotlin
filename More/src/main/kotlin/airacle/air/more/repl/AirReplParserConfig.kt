package airacle.air.more.repl

import airacle.air.core.interpreter.AirValue
import airacle.air.core.interpreter.StringValue
import airacle.air.core.parser.AirParserConfig
import airacle.air.core.parser.IAirParserConfig

object AirReplParserConfig : IAirParserConfig {
    const val EXIT = "exit"
    const val QUIT = "quit"

    override fun paramLength(value: AirValue): Int {
        if (value is StringValue) {
            when (value.value) {
                EXIT, QUIT -> return 0
            }
        }
        return AirParserConfig.paramLength(value)
    }
}