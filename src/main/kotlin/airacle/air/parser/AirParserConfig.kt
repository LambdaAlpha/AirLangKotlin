package airacle.air.parser

import airacle.air.interpreter.AirValue
import airacle.air.interpreter.StringValue

object AirParserConfig : IAirParserConfig {
    const val EXIT = "exit"
    const val QUIT = "quit"

    const val NOT = "not"
    const val AND = "and"
    const val OR = "or"
    const val XOR = "xor"

    const val LE = "le"
    const val LT = "lt"
    const val GE = "ge"
    const val GT = "gt"
    const val EQ = "eq"
    const val NE = "ne"

    const val ADD = "add"
    const val SUBTRACT = "sub"
    const val MULTIPLY = "mul"
    const val DIVIDE = "div"
    const val REMAINDER = "rem"
    const val DIVIDE_AND_REMAINDER = "divAndRem"

    const val BIT_AND = "bitAnd"
    const val BIT_OR = "bitOr"
    const val BIT_XOR = "bitXor"
    const val SHIFT_LEFT = "shl"
    const val SHIFT_RIGHT = "shr"

    const val ENCODE_TO_STRING = "toString"
    const val DECODE_FROM_STRING = "fromString"

    const val TYPE_OF = "typeOf"
    const val TYPE_CAST = "toType"

    const val NOP = "nop"
    const val IF = "if"
    const val FOR = "for"
    const val WHILE = "while"
    const val FUNCTION = "function"
    const val RETURN = "return"
    const val CALL = "call"

    const val READ = "read"
    const val WRITE = "write"

    override fun paramLength(value: AirValue): Int {
        if (value is StringValue) {
            return when (value.value) {
                EXIT, QUIT -> 0

                NOT -> 1
                AND, OR, XOR -> 2

                LE, LT, GE, GT, EQ, NE -> 2

                ADD, SUBTRACT, MULTIPLY, DIVIDE, REMAINDER, DIVIDE_AND_REMAINDER -> 2

                BIT_AND, BIT_OR, BIT_XOR, SHIFT_LEFT, SHIFT_RIGHT -> 2

                TYPE_OF -> 1
                TYPE_CAST -> 2

                ENCODE_TO_STRING, DECODE_FROM_STRING -> 1

                NOP -> 0
                IF -> 3
                FOR -> 4
                WHILE -> 2
                FUNCTION -> 2
                RETURN -> 1
                CALL -> 2

                READ -> 1
                WRITE -> 2
                else -> -1
            }
        }
        return -1
    }
}