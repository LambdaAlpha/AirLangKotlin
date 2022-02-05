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

    // int
    const val UNARY_PLUS = "plus"
    const val UNARY_MINUS = "minus"
    const val ABS = "abs"
    const val SIGN = "sign"
    const val MIN = "min"
    const val MAX = "max"
    const val ADD = "add"
    const val SUBTRACT = "sub"
    const val MULTIPLY = "mul"
    const val DIVIDE_TO_INTEGRAL = "dii"
    const val REMAINDER = "rem"
    const val DIVIDE_AND_REMAINDER = "dir"
    const val POWER = "pow"
    const val LOG = "log"
    const val MODULO = "mod"
    const val BIT_NOT = "bitNot"
    const val BIT_AND = "bitAnd"
    const val BIT_OR = "bitOr"
    const val BIT_XOR = "bitXor"
    const val BIT_AND_NOT = "bitAndNot"
    const val TEST_BIT = "testBit"
    const val SET_BIT = "setBit"
    const val CLEAR_BIT = "clearBit"
    const val FLIP_BIT = "flipBit"
    const val BIT_COUNT = "bitCount"
    const val BIT_LENGTH = "bitLength"
    const val LOWEST_SET_BIT = "lowestSetBit"
    const val SHIFT_LEFT = "shl"
    const val SHIFT_RIGHT = "shr"
    const val RANDOM = "rand"
    const val RANDOM_SEED = "seed"

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

                UNARY_PLUS, UNARY_MINUS, ABS, SIGN -> 1
                MIN, MAX,
                ADD, SUBTRACT, MULTIPLY, DIVIDE_TO_INTEGRAL, REMAINDER, DIVIDE_AND_REMAINDER,
                POWER, LOG, MODULO -> 2

                BIT_NOT -> 1
                BIT_AND, BIT_OR, BIT_XOR, BIT_AND_NOT, SHIFT_LEFT, SHIFT_RIGHT,
                TEST_BIT, SET_BIT, CLEAR_BIT, FLIP_BIT -> 2
                BIT_COUNT, BIT_LENGTH, LOWEST_SET_BIT -> 1
                RANDOM -> 1
                RANDOM_SEED -> 2

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