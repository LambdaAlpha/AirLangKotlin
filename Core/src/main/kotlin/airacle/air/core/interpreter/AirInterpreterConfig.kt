package airacle.air.core.interpreter

import airacle.air.core.api.IAirLexer
import airacle.air.core.api.IAirParser
import airacle.air.core.lexer.AirToken
import airacle.air.core.parser.AirParserConfig

private typealias C = AirParserConfig

class AirInterpreterConfig(
    lexer: IAirLexer<AirToken>,
    parser: IAirParser<AirToken, AirValue>
) : IAirInterpreterConfig {
    private val units: IUnits
    private val booleans: IBooleans
    private val ints: IInts
    private val decimals: IDecimals
    private val strings: IStrings
    private val tuples: ITuples
    private val lists: ILists
    private val maps: IMaps
    private val numbers: INumbers
    private val containers: IContainers
    private val comparators: IComparators
    private val complexity: IComplexity
    private val computability: IComputability
    private val contexts: IContexts
    private val controls: IControls
    private val types: ITypes
    private val parsers: IParsers

    init {
        units = Units
        booleans = Booleans
        ints = Ints
        decimals = Decimals
        strings = Strings
        tuples = Tuples
        lists = Lists
        maps = Maps
        numbers = Numbers
        containers = Containers
        comparators = Comparators
        complexity = Complexity
        computability = Computability
        contexts = Contexts
        controls = Controls
        types = Types
        parsers = Parsers(lexer, parser)
    }

    override fun primitiveInterpret(value: AirValue): AirValue {
        if (value !is TupleValue || value.value.isEmpty()) {
            return value
        }
        val keyword = value.value[0]
        if (keyword is StringValue) {
            return interpretString(keyword.value, value)
        }
        return value
    }

    private fun interpretString(
        keyword: String,
        value: TupleValue
    ): AirValue {
        val t = value.value
        return when (keyword) {
            // bool
            C.NOT -> booleans.not(t[1])
            C.AND -> booleans.and(t[1], t[2])
            C.OR -> booleans.or(t[1], t[2])
            C.XOR -> booleans.xor(t[1], t[2])

            // comparator
            C.LE -> comparators.le(t[1], t[2])
            C.LT -> comparators.lt(t[1], t[2])
            C.GE -> comparators.ge(t[1], t[2])
            C.GT -> comparators.gt(t[1], t[2])
            C.EQ -> comparators.eq(t[1], t[2])
            C.NE -> comparators.ne(t[1], t[2])
            C.VEQ -> comparators.veq(t[1], t[2])
            C.VNE -> comparators.vne(t[1], t[2])

            // number
            C.UNARY_PLUS -> numbers.unaryPlus(t[1])
            C.UNARY_MINUS -> numbers.unaryMinus(t[1])
            C.ABS -> numbers.abs(t[1])
            C.SIGN -> numbers.sign(t[1])
            C.MIN -> numbers.min(t[1], t[2])
            C.MAX -> numbers.max(t[1], t[2])

            // int
            C.ADD -> ints.add(t[1], t[2])
            C.SUBTRACT -> ints.subtract(t[1], t[2])
            C.MULTIPLY -> ints.multiply(t[1], t[2])
            C.DIV_INT -> ints.divide(t[1], t[2])
            C.REMAINDER -> ints.remainder(t[1], t[2])
            C.DIV_REM -> ints.divRem(t[1], t[2])
            C.POWER -> ints.power(t[1], t[2])
            C.ROOT -> ints.root(t[1], t[2])
            C.LOG -> ints.log(t[1], t[2])
            C.MODULO -> ints.mod(t[1], t[2])
            C.BIT_NOT -> ints.bitNot(t[1])
            C.BIT_AND -> ints.bitAnd(t[1], t[2])
            C.BIT_OR -> ints.bitOr(t[1], t[2])
            C.BIT_XOR -> ints.bitXor(t[1], t[2])
            C.BIT_AND_NOT -> ints.bitAndNot(t[1], t[2])
            C.TEST_BIT -> ints.testBit(t[1], t[2])
            C.SET_BIT -> ints.setBit(t[1], t[2])
            C.CLEAR_BIT -> ints.clearBit(t[1], t[2])
            C.FLIP_BIT -> ints.flipBit(t[1], t[2])
            C.BIT_COUNT -> ints.bitCount(t[1])
            C.BIT_LENGTH -> ints.bitLength(t[1])
            C.LOWEST_SET_BIT -> ints.lowestSetBit(t[1])
            C.SHIFT_LEFT -> ints.shiftLeft(t[1], t[2])
            C.SHIFT_RIGHT -> ints.shiftRight(t[1], t[2])
            C.BINARY_LOWER -> ints.toLowerBinaryString(t[1])
            C.BINARY_UPPER -> ints.toUpperBinaryString(t[1])
            C.HEX_LOWER -> ints.toLowerHexString(t[1])
            C.HEX_UPPER -> ints.toUpperHexString(t[1])

            // decimal
            C.PRECISION -> decimals.precision(t[1])

            C.ROUND0 -> decimals.round(t[1], t[2])
            C.ADD0 -> decimals.add(t[1], t[2], t[3])
            C.SUBTRACT0 -> decimals.subtract(t[1], t[2], t[3])
            C.MULTIPLY0 -> decimals.multiply(t[1], t[2], t[3])
            C.DIVIDE0 -> decimals.divide(t[1], t[2], t[3])
            C.DIVIDE_TO_INT0 -> decimals.divideToInt(t[1], t[2], t[3])
            C.REMAINDER0 -> decimals.remainder(t[1], t[2], t[3])
            C.DIVIDE_AND_REMAINDER0 -> decimals.divRem(t[1], t[2], t[3])
            C.POWER0 -> decimals.power(t[1], t[2], t[3])
            C.EXP0 -> decimals.exp(t[1], t[2])
            C.LOG0 -> decimals.log(t[1], t[2], t[3])
            C.LN0 -> decimals.ln(t[1], t[2])
            C.SHIFT_LEFT0 -> decimals.shiftLeft(t[1], t[2], t[3])
            C.SHIFT_RIGHT0 -> decimals.shiftRight(t[1], t[2], t[3])
            C.SIN0 -> decimals.sin(t[1], t[2])
            C.COS0 -> decimals.cos(t[1], t[2])
            C.TAN0 -> decimals.tan(t[1], t[2])
            C.ASIN0 -> decimals.asin(t[1], t[2])
            C.ACOS0 -> decimals.acos(t[1], t[2])
            C.ATAN0 -> decimals.atan(t[1], t[2])
            C.ATAN20 -> decimals.atan2(t[1], t[2], t[3])
            C.SINH0 -> decimals.sinh(t[1], t[2])
            C.COSH0 -> decimals.cosh(t[1], t[2])
            C.TANH0 -> decimals.tanh(t[1], t[2])
            C.ASINH0 -> decimals.asinh(t[1], t[2])
            C.ACOSH0 -> decimals.acosh(t[1], t[2])
            C.ATANH0 -> decimals.atanh(t[1], t[2])

            C.ROUND32 -> decimals.round32(t[1])
            C.ADD32 -> decimals.add32(t[1], t[2])
            C.SUBTRACT32 -> decimals.subtract32(t[1], t[2])
            C.MULTIPLY32 -> decimals.multiply32(t[1], t[2])
            C.DIVIDE32 -> decimals.divide32(t[1], t[2])
            C.DIVIDE_TO_INT32 -> decimals.divideToInt32(t[1], t[2])
            C.REMAINDER32 -> decimals.remainder32(t[1], t[2])
            C.DIVIDE_AND_REMAINDER32 -> decimals.divRem32(t[1], t[2])
            C.POWER32 -> decimals.power32(t[1], t[2])
            C.EXP32 -> decimals.exp32(t[1])
            C.LOG32 -> decimals.log32(t[1], t[2])
            C.LN32 -> decimals.ln32(t[1])
            C.SHIFT_LEFT32 -> decimals.shiftLeft32(t[1], t[2])
            C.SHIFT_RIGHT32 -> decimals.shiftRight32(t[1], t[2])
            C.SIN32 -> decimals.sin32(t[1])
            C.COS32 -> decimals.cos32(t[1])
            C.TAN32 -> decimals.tan32(t[1])
            C.ASIN32 -> decimals.asin32(t[1])
            C.ACOS32 -> decimals.acos32(t[1])
            C.ATAN32 -> decimals.atan32(t[1])
            C.ATAN232 -> decimals.atan232(t[1], t[2])
            C.SINH32 -> decimals.sinh32(t[1])
            C.COSH32 -> decimals.cosh32(t[1])
            C.TANH32 -> decimals.tanh32(t[1])
            C.ASINH32 -> decimals.asinh32(t[1])
            C.ACOSH32 -> decimals.acosh32(t[1])
            C.ATANH32 -> decimals.atanh32(t[1])

            C.ROUND64 -> decimals.round64(t[1])
            C.ADD64 -> decimals.add64(t[1], t[2])
            C.SUBTRACT64 -> decimals.subtract64(t[1], t[2])
            C.MULTIPLY64 -> decimals.multiply64(t[1], t[2])
            C.DIVIDE64 -> decimals.divide64(t[1], t[2])
            C.DIVIDE_TO_INT64 -> decimals.divideToInt64(t[1], t[2])
            C.REMAINDER64 -> decimals.remainder64(t[1], t[2])
            C.DIVIDE_AND_REMAINDER64 -> decimals.divRem64(t[1], t[2])
            C.POWER64 -> decimals.power64(t[1], t[2])
            C.EXP64 -> decimals.exp64(t[1])
            C.LOG64 -> decimals.log64(t[1], t[2])
            C.LN64 -> decimals.ln64(t[1])
            C.SHIFT_LEFT64 -> decimals.shiftLeft64(t[1], t[2])
            C.SHIFT_RIGHT64 -> decimals.shiftRight64(t[1], t[2])
            C.SIN64 -> decimals.sin64(t[1])
            C.COS64 -> decimals.cos64(t[1])
            C.TAN64 -> decimals.tan64(t[1])
            C.ASIN64 -> decimals.asin64(t[1])
            C.ACOS64 -> decimals.acos64(t[1])
            C.ATAN64 -> decimals.atan64(t[1])
            C.ATAN264 -> decimals.atan264(t[1], t[2])
            C.SINH64 -> decimals.sinh64(t[1])
            C.COSH64 -> decimals.cosh64(t[1])
            C.TANH64 -> decimals.tanh64(t[1])
            C.ASINH64 -> decimals.asinh64(t[1])
            C.ACOSH64 -> decimals.acosh64(t[1])
            C.ATANH64 -> decimals.atanh64(t[1])

            C.ROUND128 -> decimals.round128(t[1])
            C.ADD128 -> decimals.add128(t[1], t[2])
            C.SUBTRACT128 -> decimals.subtract128(t[1], t[2])
            C.MULTIPLY128 -> decimals.multiply128(t[1], t[2])
            C.DIVIDE128 -> decimals.divide128(t[1], t[2])
            C.DIVIDE_TO_INT128 -> decimals.divideToInt128(t[1], t[2])
            C.REMAINDER128 -> decimals.remainder128(t[1], t[2])
            C.DIVIDE_AND_REMAINDER128 -> decimals.divRem128(t[1], t[2])
            C.POWER128 -> decimals.power128(t[1], t[2])
            C.EXP128 -> decimals.exp128(t[1])
            C.LOG128 -> decimals.log128(t[1], t[2])
            C.LN128 -> decimals.ln128(t[1])
            C.SHIFT_LEFT128 -> decimals.shiftLeft128(t[1], t[2])
            C.SHIFT_RIGHT128 -> decimals.shiftRight128(t[1], t[2])
            C.SIN128 -> decimals.sin128(t[1])
            C.COS128 -> decimals.cos128(t[1])
            C.TAN128 -> decimals.tan128(t[1])
            C.ASIN128 -> decimals.asin128(t[1])
            C.ACOS128 -> decimals.acos128(t[1])
            C.ATAN128 -> decimals.atan128(t[1])
            C.ATAN2128 -> decimals.atan2128(t[1], t[2])
            C.SINH128 -> decimals.sinh128(t[1])
            C.COSH128 -> decimals.cosh128(t[1])
            C.TANH128 -> decimals.tanh128(t[1])
            C.ASINH128 -> decimals.asinh128(t[1])
            C.ACOSH128 -> decimals.acosh128(t[1])
            C.ATANH128 -> decimals.atanh128(t[1])

            // type
            C.TYPE_OF -> types.typeOf(t[1])
            C.TYPE_CAST -> types.toType(t[1], t[2])

            // parse
            C.ENCODE_TO_STRING -> parsers.encodeToString(t[1])
            C.DECODE_FROM_STRING -> parsers.decodeFromString(t[1])
            else -> value
        }
    }
}