package airacle.air.interpreter

import airacle.air.api.IAirInterpreter
import airacle.air.api.IAirLexer
import airacle.air.api.IAirParser
import airacle.air.lexer.AirToken
import airacle.air.parser.AirParserConfig

private typealias C = AirParserConfig

class AirInterpreter(
    val version: AirInterpreterVersion,
    private val lexer: IAirLexer<AirToken>,
    private val parser: IAirParser<AirToken, AirValue>
) : IAirInterpreter<AirValue> {
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
        when (version) {
            AirInterpreterVersion.V0 -> {
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
        }
    }

    override fun interpret(value: AirValue): AirValue {
        return i(value)
    }

    // just for a shorter name
    private fun i(value: AirValue): AirValue {
        if (value !is TupleValue) {
            return value
        }

        val tuple = value.value
        if (tuple.isEmpty()) {
            return value
        }

        val keyword = tuple[0]
        if (C.paramLength(keyword) + 1 != tuple.size) {
            return UnitValue
        }

        if (keyword is StringValue) {
            return interpretString(keyword.value, value)
        }

        return value
    }

    private fun interpretString(keyword: String, value: TupleValue): AirValue {
        val t = value.value
        return when (keyword) {
            // eval
            C.VALUE, C.VALUE_SYMBOL -> t[1]
            C.QUOTE, C.QUOTE_SYMBOL -> quote(t[1])
            C.EVAL, C.EVAL_SYMBOL -> i(i(t[1]))

            // bool
            C.NOT -> booleans.not(i(t[1]))
            C.AND -> booleans.and(i(t[1]), i(t[2]))
            C.OR -> booleans.or(i(t[1]), i(t[2]))
            C.XOR -> booleans.xor(i(t[1]), i(t[2]))

            // comparator
            C.LE -> comparators.le(i(t[1]), i(t[2]))
            C.LT -> comparators.lt(i(t[1]), i(t[2]))
            C.GE -> comparators.ge(i(t[1]), i(t[2]))
            C.GT -> comparators.gt(i(t[1]), i(t[2]))
            C.EQ -> comparators.eq(i(t[1]), i(t[2]))
            C.NE -> comparators.ne(i(t[1]), i(t[2]))

            // number
            C.UNARY_PLUS -> numbers.unaryPlus(i(t[1]))
            C.UNARY_MINUS -> numbers.unaryMinus(i(t[1]))
            C.ABS -> numbers.abs(i(t[1]))
            C.SIGN -> numbers.sign(i(t[1]))
            C.MIN -> numbers.min(i(t[1]), i(t[2]))
            C.MAX -> numbers.max(i(t[1]), i(t[2]))

            // int
            C.ADD -> ints.add(i(t[1]), i(t[2]))
            C.SUBTRACT -> ints.subtract(i(t[1]), i(t[2]))
            C.MULTIPLY -> ints.multiply(i(t[1]), i(t[2]))
            C.DIV_INT -> ints.divide(i(t[1]), i(t[2]))
            C.REMAINDER -> ints.remainder(i(t[1]), i(t[2]))
            C.DIV_REM -> ints.divRem(i(t[1]), i(t[2]))
            C.POWER -> ints.power(i(t[1]), i(t[2]))
            C.LOG -> ints.log(i(t[1]), i(t[2]))
            C.MODULO -> ints.mod(i(t[1]), i(t[2]))
            C.BIT_NOT -> ints.bitNot(i(t[1]))
            C.BIT_AND -> ints.bitAnd(i(t[1]), i(t[2]))
            C.BIT_OR -> ints.bitOr(i(t[1]), i(t[2]))
            C.BIT_XOR -> ints.bitXor(i(t[1]), i(t[2]))
            C.BIT_AND_NOT -> ints.bitAndNot(i(t[1]), i(t[2]))
            C.TEST_BIT -> ints.testBit(i(t[1]), i(t[2]))
            C.SET_BIT -> ints.setBit(i(t[1]), i(t[2]))
            C.CLEAR_BIT -> ints.clearBit(i(t[1]), i(t[2]))
            C.FLIP_BIT -> ints.flipBit(i(t[1]), i(t[2]))
            C.BIT_COUNT -> ints.bitCount(i(t[1]))
            C.BIT_LENGTH -> ints.bitLength(i(t[1]))
            C.LOWEST_SET_BIT -> ints.lowestSetBit(i(t[1]))
            C.SHIFT_LEFT -> ints.shiftLeft(i(t[1]), i(t[2]))
            C.SHIFT_RIGHT -> ints.shiftRight(i(t[1]), i(t[2]))
            C.RANDOM -> ints.random(i(t[1]))
            C.SEED -> ints.randomWithSeed(i(t[1]), i(t[2]))

            // decimal
            C.PRECISION -> decimals.precision(i(t[1]))

            C.RAND0 -> decimals.random(i(t[1]))
            C.SEED0 -> decimals.seed(i(t[1]), i(t[2]))
            C.ROUND0 -> decimals.round(i(t[1]), i(t[2]))
            C.ADD0 -> decimals.add(i(t[1]), i(t[2]), i(t[3]))
            C.SUBTRACT0 -> decimals.subtract(i(t[1]), i(t[2]), i(t[3]))
            C.MULTIPLY0 -> decimals.multiply(i(t[1]), i(t[2]), i(t[3]))
            C.DIVIDE0 -> decimals.divide(i(t[1]), i(t[2]), i(t[3]))
            C.DIVIDE_TO_INT0 -> decimals.divideToInt(i(t[1]), i(t[2]), i(t[3]))
            C.REMAINDER0 -> decimals.remainder(i(t[1]), i(t[2]), i(t[3]))
            C.DIVIDE_AND_REMAINDER0 -> decimals.divRem(i(t[1]), i(t[2]), i(t[3]))
            C.POWER0 -> decimals.power(i(t[1]), i(t[2]), i(t[3]))
            C.EXP0 -> decimals.exp(i(t[1]), i(t[2]))
            C.LOG0 -> decimals.log(i(t[1]), i(t[2]), i(t[3]))
            C.LN0 -> decimals.ln(i(t[1]), i(t[2]))
            C.SHIFT_LEFT0 -> decimals.shiftLeft(i(t[1]), i(t[2]), i(t[3]))
            C.SHIFT_RIGHT0 -> decimals.shiftRight(i(t[1]), i(t[2]), i(t[3]))
            C.SIN0 -> decimals.sin(i(t[1]), i(t[2]))
            C.COS0 -> decimals.cos(i(t[1]), i(t[2]))
            C.TAN0 -> decimals.tan(i(t[1]), i(t[2]))
            C.ASIN0 -> decimals.asin(i(t[1]), i(t[2]))
            C.ACOS0 -> decimals.acos(i(t[1]), i(t[2]))
            C.ATAN0 -> decimals.atan(i(t[1]), i(t[2]))
            C.ATAN20 -> decimals.atan2(i(t[1]), i(t[2]), i(t[3]))
            C.SINH0 -> decimals.sinh(i(t[1]), i(t[2]))
            C.COSH0 -> decimals.cosh(i(t[1]), i(t[2]))
            C.TANH0 -> decimals.tanh(i(t[1]), i(t[2]))
            C.ASINH0 -> decimals.asinh(i(t[1]), i(t[2]))
            C.ACOSH0 -> decimals.acosh(i(t[1]), i(t[2]))
            C.ATANH0 -> decimals.atanh(i(t[1]), i(t[2]))

            C.RAND32 -> decimals.random32()
            C.SEED32 -> decimals.seed32(i(t[1]))
            C.ROUND32 -> decimals.round32(i(t[1]))
            C.ADD32 -> decimals.add32(i(t[1]), i(t[2]))
            C.SUBTRACT32 -> decimals.subtract32(i(t[1]), i(t[2]))
            C.MULTIPLY32 -> decimals.multiply32(i(t[1]), i(t[2]))
            C.DIVIDE32 -> decimals.divide32(i(t[1]), i(t[2]))
            C.DIVIDE_TO_INT32 -> decimals.divideToInt32(i(t[1]), i(t[2]))
            C.REMAINDER32 -> decimals.remainder32(i(t[1]), i(t[2]))
            C.DIVIDE_AND_REMAINDER32 -> decimals.divRem32(i(t[1]), i(t[2]))
            C.POWER32 -> decimals.power32(i(t[1]), i(t[2]))
            C.EXP32 -> decimals.exp32(i(t[1]))
            C.LOG32 -> decimals.log32(i(t[1]), i(t[2]))
            C.LN32 -> decimals.ln32(i(t[1]))
            C.SHIFT_LEFT32 -> decimals.shiftLeft32(i(t[1]), i(t[2]))
            C.SHIFT_RIGHT32 -> decimals.shiftRight32(i(t[1]), i(t[2]))
            C.SIN32 -> decimals.sin32(i(t[1]))
            C.COS32 -> decimals.cos32(i(t[1]))
            C.TAN32 -> decimals.tan32(i(t[1]))
            C.ASIN32 -> decimals.asin32(i(t[1]))
            C.ACOS32 -> decimals.acos32(i(t[1]))
            C.ATAN32 -> decimals.atan32(i(t[1]))
            C.ATAN232 -> decimals.atan232(i(t[1]), i(t[2]))
            C.SINH32 -> decimals.sinh32(i(t[1]))
            C.COSH32 -> decimals.cosh32(i(t[1]))
            C.TANH32 -> decimals.tanh32(i(t[1]))
            C.ASINH32 -> decimals.asinh32(i(t[1]))
            C.ACOSH32 -> decimals.acosh32(i(t[1]))
            C.ATANH32 -> decimals.atanh32(i(t[1]))

            C.RAND64 -> decimals.random64()
            C.SEED64 -> decimals.seed64(i(t[1]))
            C.ROUND64 -> decimals.round64(i(t[1]))
            C.ADD64 -> decimals.add64(i(t[1]), i(t[2]))
            C.SUBTRACT64 -> decimals.subtract64(i(t[1]), i(t[2]))
            C.MULTIPLY64 -> decimals.multiply64(i(t[1]), i(t[2]))
            C.DIVIDE64 -> decimals.divide64(i(t[1]), i(t[2]))
            C.DIVIDE_TO_INT64 -> decimals.divideToInt64(i(t[1]), i(t[2]))
            C.REMAINDER64 -> decimals.remainder64(i(t[1]), i(t[2]))
            C.DIVIDE_AND_REMAINDER64 -> decimals.divRem64(i(t[1]), i(t[2]))
            C.POWER64 -> decimals.power64(i(t[1]), i(t[2]))
            C.EXP64 -> decimals.exp64(i(t[1]))
            C.LOG64 -> decimals.log64(i(t[1]), i(t[2]))
            C.LN64 -> decimals.ln64(i(t[1]))
            C.SHIFT_LEFT64 -> decimals.shiftLeft64(i(t[1]), i(t[2]))
            C.SHIFT_RIGHT64 -> decimals.shiftRight64(i(t[1]), i(t[2]))
            C.SIN64 -> decimals.sin64(i(t[1]))
            C.COS64 -> decimals.cos64(i(t[1]))
            C.TAN64 -> decimals.tan64(i(t[1]))
            C.ASIN64 -> decimals.asin64(i(t[1]))
            C.ACOS64 -> decimals.acos64(i(t[1]))
            C.ATAN64 -> decimals.atan64(i(t[1]))
            C.ATAN264 -> decimals.atan264(i(t[1]), i(t[2]))
            C.SINH64 -> decimals.sinh64(i(t[1]))
            C.COSH64 -> decimals.cosh64(i(t[1]))
            C.TANH64 -> decimals.tanh64(i(t[1]))
            C.ASINH64 -> decimals.asinh64(i(t[1]))
            C.ACOSH64 -> decimals.acosh64(i(t[1]))
            C.ATANH64 -> decimals.atanh64(i(t[1]))

            C.RAND128 -> decimals.random128()
            C.SEED128 -> decimals.seed128(i(t[1]))
            C.ROUND128 -> decimals.round128(i(t[1]))
            C.ADD128 -> decimals.add128(i(t[1]), i(t[2]))
            C.SUBTRACT128 -> decimals.subtract128(i(t[1]), i(t[2]))
            C.MULTIPLY128 -> decimals.multiply128(i(t[1]), i(t[2]))
            C.DIVIDE128 -> decimals.divide128(i(t[1]), i(t[2]))
            C.DIVIDE_TO_INT128 -> decimals.divideToInt128(i(t[1]), i(t[2]))
            C.REMAINDER128 -> decimals.remainder128(i(t[1]), i(t[2]))
            C.DIVIDE_AND_REMAINDER128 -> decimals.divRem128(i(t[1]), i(t[2]))
            C.POWER128 -> decimals.power128(i(t[1]), i(t[2]))
            C.EXP128 -> decimals.exp128(i(t[1]))
            C.LOG128 -> decimals.log128(i(t[1]), i(t[2]))
            C.LN128 -> decimals.ln128(i(t[1]))
            C.SHIFT_LEFT128 -> decimals.shiftLeft128(i(t[1]), i(t[2]))
            C.SHIFT_RIGHT128 -> decimals.shiftRight128(i(t[1]), i(t[2]))
            C.SIN128 -> decimals.sin128(i(t[1]))
            C.COS128 -> decimals.cos128(i(t[1]))
            C.TAN128 -> decimals.tan128(i(t[1]))
            C.ASIN128 -> decimals.asin128(i(t[1]))
            C.ACOS128 -> decimals.acos128(i(t[1]))
            C.ATAN128 -> decimals.atan128(i(t[1]))
            C.ATAN2128 -> decimals.atan2128(i(t[1]), i(t[2]))
            C.SINH128 -> decimals.sinh128(i(t[1]))
            C.COSH128 -> decimals.cosh128(i(t[1]))
            C.TANH128 -> decimals.tanh128(i(t[1]))
            C.ASINH128 -> decimals.asinh128(i(t[1]))
            C.ACOSH128 -> decimals.acosh128(i(t[1]))
            C.ATANH128 -> decimals.atanh128(i(t[1]))

            // type
            C.TYPE_OF -> types.typeOf(i(t[1]))
            C.TYPE_CAST -> types.toType(i(t[1]), i(t[2]))

            // parse
            C.ENCODE_TO_STRING -> parsers.encodeToString(i(t[1]))
            C.DECODE_FROM_STRING -> parsers.decodeFromString(i(t[1]))
            else -> value
        }
    }

    private fun quote(v: AirValue): AirValue {
        if (v is ListValue) {
            val list = ArrayList<AirValue>(v.value.size)
            for (i in v.value) {
                list.add(quote(i))
            }
            return ListValue.valueOf(list)
        }

        if (v is MapValue) {
            val map = HashMap<AirValue, AirValue>(v.value.size)
            for (entry in map) {
                map[quote(entry.key)] = quote(entry.value)
            }
            return MapValue.valueOf(map)
        }

        if (v !is TupleValue) {
            return v
        }

        val t = v.value

        if (t.isEmpty()) {
            return v
        }

        val keyword = t[0]
        if (keyword is StringValue) {
            when (keyword.value) {
                C.VALUE, C.VALUE_SYMBOL -> return t[1]
                C.QUOTE, C.QUOTE_SYMBOL -> return quote(quote(t[1]))
                C.EVAL, C.EVAL_SYMBOL -> return i(t[1])
            }
        }
        return TupleValue.fromArray(Array(t.size) {
            quote(t[it])
        })
    }
}