package airacle.air.core.parser

import airacle.air.core.interpreter.AirValue
import airacle.air.core.interpreter.StringValue

object AirParserConfig : IAirParserConfig {
    // bool
    const val ANY2BOOL = "a2b"
    const val BOOL2INT = "b2i"
    const val NOT = "not"
    const val AND = "and"
    const val OR = "or"
    const val XOR = "xor"

    // comparator
    const val LE = "le"
    const val LT = "lt"
    const val GE = "ge"
    const val GT = "gt"
    const val EQ = "eq"
    const val NE = "ne"
    const val VEQ = "veq"
    const val VNE = "vne"

    // number
    const val UNARY_PLUS = "plus"
    const val UNARY_MINUS = "minus"
    const val ABS = "abs"
    const val SIGN = "sign"
    const val MIN = "min"
    const val MAX = "max"

    // int
    const val INT2DECIMAL = "i2d"
    const val ADD = "add"
    const val SUBTRACT = "sub"
    const val MULTIPLY = "mul"
    const val DIV_INT = "dii"
    const val REMAINDER = "rem"
    const val DIV_REM = "dir"
    const val POWER = "pow"
    const val ROOT = "root"
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
    const val BINARY_LOWER = "bin"
    const val BINARY_UPPER = "Bin"
    const val HEX_LOWER = "hex"
    const val HEX_UPPER = "Hex"

    // decimal
    const val DECIMAL2INT = "d2i"
    const val PRECISION = "precision"

    const val ROUND0 = "round0"
    const val ADD0 = "add0"
    const val SUBTRACT0 = "sub0"
    const val MULTIPLY0 = "mul0"
    const val DIVIDE0 = "div0"
    const val DIVIDE_TO_INT0 = "dii0"
    const val REMAINDER0 = "rem0"
    const val DIVIDE_AND_REMAINDER0 = "dir0"
    const val POWER0 = "pow0"
    const val EXP0 = "exp0"
    const val LOG0 = "log0"
    const val LN0 = "ln0"
    const val SHIFT_LEFT0 = "shl0"
    const val SHIFT_RIGHT0 = "shr0"
    const val SIN0 = "sin0"
    const val COS0 = "cos0"
    const val TAN0 = "tan0"
    const val ASIN0 = "asin0"
    const val ACOS0 = "acos0"
    const val ATAN0 = "atan0"
    const val ATAN20 = "atan20"
    const val SINH0 = "sinh0"
    const val COSH0 = "cosh0"
    const val TANH0 = "tanh0"
    const val ASINH0 = "asinh0"
    const val ACOSH0 = "acosh0"
    const val ATANH0 = "atanh0"

    const val ROUND32 = "round32"
    const val ADD32 = "add32"
    const val SUBTRACT32 = "sub32"
    const val MULTIPLY32 = "mul32"
    const val DIVIDE32 = "div32"
    const val DIVIDE_TO_INT32 = "dii32"
    const val REMAINDER32 = "rem32"
    const val DIVIDE_AND_REMAINDER32 = "dir32"
    const val POWER32 = "pow32"
    const val EXP32 = "exp32"
    const val LOG32 = "log32"
    const val LN32 = "ln32"
    const val SHIFT_LEFT32 = "shl32"
    const val SHIFT_RIGHT32 = "shr32"
    const val SIN32 = "sin32"
    const val COS32 = "cos32"
    const val TAN32 = "tan32"
    const val ASIN32 = "asin32"
    const val ACOS32 = "acos32"
    const val ATAN32 = "atan32"
    const val ATAN232 = "atan232"
    const val SINH32 = "sinh32"
    const val COSH32 = "cosh32"
    const val TANH32 = "tanh32"
    const val ASINH32 = "asinh32"
    const val ACOSH32 = "acosh32"
    const val ATANH32 = "atanh32"

    const val ROUND64 = "round64"
    const val ADD64 = "add64"
    const val SUBTRACT64 = "sub64"
    const val MULTIPLY64 = "mul64"
    const val DIVIDE64 = "div64"
    const val DIVIDE_TO_INT64 = "dii64"
    const val REMAINDER64 = "rem64"
    const val DIVIDE_AND_REMAINDER64 = "dir64"
    const val POWER64 = "pow64"
    const val EXP64 = "exp64"
    const val LOG64 = "log64"
    const val LN64 = "ln64"
    const val SHIFT_LEFT64 = "shl64"
    const val SHIFT_RIGHT64 = "shr64"
    const val SIN64 = "sin64"
    const val COS64 = "cos64"
    const val TAN64 = "tan64"
    const val ASIN64 = "asin64"
    const val ACOS64 = "acos64"
    const val ATAN64 = "atan64"
    const val ATAN264 = "atan264"
    const val SINH64 = "sinh64"
    const val COSH64 = "cosh64"
    const val TANH64 = "tanh64"
    const val ASINH64 = "asinh64"
    const val ACOSH64 = "acosh64"
    const val ATANH64 = "atanh64"

    const val ROUND128 = "round128"
    const val ADD128 = "add128"
    const val SUBTRACT128 = "sub128"
    const val MULTIPLY128 = "mul128"
    const val DIVIDE128 = "div128"
    const val DIVIDE_TO_INT128 = "dii128"
    const val REMAINDER128 = "rem128"
    const val DIVIDE_AND_REMAINDER128 = "dir128"
    const val POWER128 = "pow128"
    const val EXP128 = "exp128"
    const val LOG128 = "log128"
    const val LN128 = "ln128"
    const val SHIFT_LEFT128 = "shl128"
    const val SHIFT_RIGHT128 = "shr128"
    const val SIN128 = "sin128"
    const val COS128 = "cos128"
    const val TAN128 = "tan128"
    const val ASIN128 = "asin128"
    const val ACOS128 = "acos128"
    const val ATAN128 = "atan128"
    const val ATAN2128 = "atan2128"
    const val SINH128 = "sinh128"
    const val COSH128 = "cosh128"
    const val TANH128 = "tanh128"
    const val ASINH128 = "asinh128"
    const val ACOSH128 = "acosh128"
    const val ATANH128 = "atanh128"

    // tuple
    const val TUPLE2LIST = "t2l"

    // list
    const val LIST2TUPLE = "l2t"

    // map
    const val TUPLE_LIST2MAP = "tl2m"
    const val LIST_TUPLE2MAP = "lt2m"
    const val MAP2TUPLE_LIST = "m2tl"
    const val MAP2LIST_TUPLE = "m2lt"

    // parser
    const val ENCODE_TO_STRING = "toString"
    const val DECODE_FROM_STRING = "fromString"
    const val PRETTY_PRINT = "pretty"

    // type
    const val TYPE_OF = "typeOf"
    const val IS_TYPE = "is"

    // interpret
    const val VALUE = "value"
    const val VALUE_SYMBOL = "'"
    const val QUOTE = "quote"
    const val QUOTE_SYMBOL = "`"
    const val EVAL = "eval"
    const val EVAL_SYMBOL = "~"

    // control
    const val NOP = "nop"
    const val IF = "if"
    const val FOR = "for"
    const val WHILE = "while"
    const val FUNCTION = "function"
    const val RETURN = "return"
    const val CALL = "call"

    // context
    const val NORM_PATH = "normPath"
    const val FOCUS = "focus"
    const val FOCUSED = "focused"
    const val EXIST = "has"
    const val READ = "read"
    const val READ_SYMBOL = "."
    const val WRITE = "write"
    const val WRITE_SYMBOL = "="
    const val MOVE = "mov"
    const val COPY = "copy"
    const val INSERT = "ins"
    const val DELETE = "del"

    override fun paramLength(value: AirValue): Int {
        if (value !is StringValue) {
            return -1
        }
        return when (value.value) {
            // bool
            ANY2BOOL, BOOL2INT -> 1
            NOT -> 1
            AND, OR, XOR -> 2

            // comparator
            LE, LT, GE, GT, EQ, NE, VEQ, VNE -> 2

            // number
            UNARY_PLUS, UNARY_MINUS, ABS, SIGN -> 1
            MIN, MAX -> 2

            // int
            INT2DECIMAL -> 1
            ADD, SUBTRACT, MULTIPLY, DIV_INT, REMAINDER, DIV_REM,
            POWER, ROOT, LOG, MODULO -> 2

            BIT_NOT -> 1
            BIT_AND, BIT_OR, BIT_XOR, BIT_AND_NOT, SHIFT_LEFT, SHIFT_RIGHT,
            TEST_BIT, SET_BIT, CLEAR_BIT, FLIP_BIT -> 2
            BIT_COUNT, BIT_LENGTH, LOWEST_SET_BIT -> 1
            BINARY_LOWER, BINARY_UPPER, HEX_LOWER, HEX_UPPER -> 1

            // decimal
            DECIMAL2INT -> 1
            PRECISION -> 1

            ROUND0 -> 2
            ADD0, SUBTRACT0, MULTIPLY0, DIVIDE0, DIVIDE_TO_INT0, REMAINDER0, DIVIDE_AND_REMAINDER0,
            POWER0, LOG0, SHIFT_LEFT0, SHIFT_RIGHT0 -> 3
            EXP0, LN0 -> 2
            SIN0, COS0, TAN0, ASIN0, ACOS0, ATAN0, SINH0, COSH0, TANH0, ASINH0, ACOSH0, ATANH0 -> 2
            ATAN20 -> 3

            ROUND32 -> 1
            ADD32, SUBTRACT32, MULTIPLY32, DIVIDE32, DIVIDE_TO_INT32, REMAINDER32, DIVIDE_AND_REMAINDER32,
            POWER32, LOG32, SHIFT_LEFT32, SHIFT_RIGHT32 -> 2
            EXP32, LN32 -> 1
            SIN32, COS32, TAN32, ASIN32, ACOS32, ATAN32, SINH32, COSH32, TANH32, ASINH32, ACOSH32, ATANH32 -> 1
            ATAN232 -> 2

            ROUND64 -> 1
            ADD64, SUBTRACT64, MULTIPLY64, DIVIDE64, DIVIDE_TO_INT64, REMAINDER64, DIVIDE_AND_REMAINDER64,
            POWER64, LOG64, SHIFT_LEFT64, SHIFT_RIGHT64 -> 2
            EXP64, LN64 -> 1
            SIN64, COS64, TAN64, ASIN64, ACOS64, ATAN64, SINH64, COSH64, TANH64, ASINH64, ACOSH64, ATANH64 -> 1
            ATAN264 -> 2

            ROUND128 -> 1
            ADD128, SUBTRACT128, MULTIPLY128, DIVIDE128, DIVIDE_TO_INT128, REMAINDER128, DIVIDE_AND_REMAINDER128,
            POWER128, LOG128, SHIFT_LEFT128, SHIFT_RIGHT128 -> 2
            EXP128, LN128 -> 1
            SIN128, COS128, TAN128, ASIN128, ACOS128, ATAN128, SINH128, COSH128, TANH128, ASINH128, ACOSH128, ATANH128 -> 1
            ATAN2128 -> 2

            // tuple
            TUPLE2LIST -> 1

            // list
            LIST2TUPLE -> 1

            // map
            TUPLE_LIST2MAP,
            LIST_TUPLE2MAP,
            MAP2TUPLE_LIST,
            MAP2LIST_TUPLE -> 1

            // type
            TYPE_OF -> 1
            IS_TYPE -> 2

            // parse
            ENCODE_TO_STRING, PRETTY_PRINT, DECODE_FROM_STRING -> 1

            // eval
            VALUE, VALUE_SYMBOL, QUOTE, QUOTE_SYMBOL, EVAL, EVAL_SYMBOL -> 1

            // control
            NOP -> 0
            IF -> 3
            FOR -> 4
            WHILE -> 2
            FUNCTION -> 2
            RETURN -> 1
            CALL -> 2

            // context
            FOCUS -> 1
            FOCUSED -> 0
            NORM_PATH -> 1
            EXIST -> 1
            READ, READ_SYMBOL -> 1
            WRITE, WRITE_SYMBOL -> 2
            MOVE -> 2
            COPY -> 2
            INSERT -> 2
            DELETE -> 1

            else -> -1
        }
    }
}