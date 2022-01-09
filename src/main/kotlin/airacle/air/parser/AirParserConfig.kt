package airacle.air.parser

import airacle.air.interpreter.AirValue
import airacle.air.interpreter.StringValue

object AirParserConfig : IAirParserConfig {
    override fun tupleLength(value: AirValue): Int {
        if (value is StringValue) {
            return 1 + when (value.value) {
                "if", "?" -> 3
                "for", "%" -> 4
                "while", "@" -> 2
                "comment", "#" -> 1
                "function", "^" -> 2
                "return", "~" -> 1
                "apply", "$" -> 2
                "read", "." -> 1
                "assign", "=" -> 2
                "le", "lt", "ge", "gt", "eq", "ne" -> 2
                "add", "plus", "subtract", "minus", "+", "-" -> 2
                else -> -1
            }
        }
        return -1
    }
}