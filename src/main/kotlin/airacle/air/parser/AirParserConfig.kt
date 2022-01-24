package airacle.air.parser

import airacle.air.interpreter.AirValue
import airacle.air.interpreter.StringValue

object AirParserConfig : IAirParserConfig {
    override fun paramLength(value: AirValue): Int {
        if (value is StringValue) {
            return when (value.value) {
                "if" -> 3
                "for" -> 4
                "while" -> 2
                "function" -> 2
                "return" -> 1
                "call" -> 2
                "read", "." -> 1
                "assign", "=" -> 2
                "le", "lt", "ge", "gt", "eq", "ne" -> 2
                else -> -1
            }
        }
        return -1
    }
}