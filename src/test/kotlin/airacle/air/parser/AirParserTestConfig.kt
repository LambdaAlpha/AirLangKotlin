package airacle.air.parser

import airacle.air.interpreter.*

internal object AirParserTestConfig : IAirParserConfig {
    override fun paramLength(value: AirValue): Int {
        return when (value) {
            is StringValue -> {
                when (value.value) {
                    "zero" -> 0
                    "one" -> 1
                    "two" -> 2
                    "three" -> 3
                    "four" -> 4
                    "five" -> 5
                    "six" -> 6
                    "seven" -> 7
                    "eight" -> 8
                    "nine" -> 9
                    "ten" -> 10
                    "`", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+" -> 1
                    "[", "{", "]", "}", "\\", "|", ";", ":", "\'", "\"", ",", "<", ".", ">", "/", "?" -> 2
                    else -> -1
                }
            }
            is UnitValue -> 1
            is BoolValue -> 2
            is IntValue -> 3
            is DecimalValue -> 4
            else -> -1
        }
    }
}