package airacle.air.interpreter

import airacle.air.api.IAirInterpreter
import airacle.air.api.IAirLexer
import airacle.air.api.IAirParser
import airacle.air.lexer.AirToken
import airacle.air.parser.AirParserConfig

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
    private val evaluations: IEvaluations
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
                evaluations = Evaluations
                types = Types
                parsers = Parsers(lexer, parser)
            }
        }
    }

    override fun interpret(value: AirValue): AirValue {
        if (value !is TupleValue) {
            return value
        }
        if (value.value.isNotEmpty()) {
            val tuple = value.value
            val keyword = tuple[0]
            if (keyword is StringValue) {
                when (keyword.value) {
                    // bool
                    AirParserConfig.NOT -> return booleans.not(interpret(tuple[1]))
                    AirParserConfig.AND -> return booleans.and(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.OR -> return booleans.or(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.XOR -> return booleans.xor(interpret(tuple[1]), interpret(tuple[2]))

                    // comparator
                    AirParserConfig.LE -> return comparators.le(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.LT -> return comparators.lt(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.GE -> return comparators.ge(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.GT -> return comparators.gt(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.EQ -> return comparators.eq(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.NE -> return comparators.ne(interpret(tuple[1]), interpret(tuple[2]))

                    // number
                    AirParserConfig.UNARY_PLUS -> return numbers.unaryPlus(interpret(tuple[1]))
                    AirParserConfig.UNARY_MINUS -> return numbers.unaryMinus(interpret(tuple[1]))
                    AirParserConfig.ABS -> return numbers.abs(interpret(tuple[1]))
                    AirParserConfig.SIGN -> return numbers.sign(interpret(tuple[1]))
                    AirParserConfig.MIN -> return numbers.min(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.MAX -> return numbers.max(interpret(tuple[1]), interpret(tuple[2]))

                    // int
                    AirParserConfig.ADD -> return ints.add(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.SUBTRACT -> return ints.subtract(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.MULTIPLY -> return ints.multiply(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.DIV_INT -> return ints.divide(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.REMAINDER -> return ints.remainder(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.DIV_REM -> return ints.divRem(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.POWER -> return ints.power(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.LOG -> return ints.log(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.MODULO -> return ints.mod(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.BIT_NOT -> return ints.bitNot(interpret(tuple[1]))
                    AirParserConfig.BIT_AND -> return ints.bitAnd(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.BIT_OR -> return ints.bitOr(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.BIT_XOR -> return ints.bitXor(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.BIT_AND_NOT -> return ints.bitAndNot(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.TEST_BIT -> return ints.testBit(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.SET_BIT -> return ints.setBit(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.CLEAR_BIT -> return ints.clearBit(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.FLIP_BIT -> return ints.flipBit(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.BIT_COUNT -> return ints.bitCount(interpret(tuple[1]))
                    AirParserConfig.BIT_LENGTH -> return ints.bitLength(interpret(tuple[1]))
                    AirParserConfig.LOWEST_SET_BIT -> return ints.lowestSetBit(interpret(tuple[1]))
                    AirParserConfig.SHIFT_LEFT -> return ints.shiftLeft(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.SHIFT_RIGHT -> return ints.shiftRight(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.RANDOM -> return ints.random(interpret(tuple[1]))
                    AirParserConfig.SEED -> return ints.randomWithSeed(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )

                    // decimal
                    AirParserConfig.PRECISION -> return decimals.precision(interpret(tuple[1]))

                    AirParserConfig.RAND0 -> return decimals.random(interpret(tuple[1]))
                    AirParserConfig.SEED0 -> return decimals.seed(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )
                    AirParserConfig.ROUND0 -> return decimals.round(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )
                    AirParserConfig.ADD0 -> return decimals.add(
                        interpret(tuple[1]),
                        interpret(tuple[2]),
                        interpret(tuple[3])
                    )
                    AirParserConfig.SUBTRACT0 -> return decimals.subtract(
                        interpret(tuple[1]),
                        interpret(tuple[2]),
                        interpret(tuple[3])
                    )
                    AirParserConfig.MULTIPLY0 -> return decimals.multiply(
                        interpret(tuple[1]),
                        interpret(tuple[2]),
                        interpret(tuple[3])
                    )
                    AirParserConfig.DIVIDE0 -> return decimals.divide(
                        interpret(tuple[1]),
                        interpret(tuple[2]),
                        interpret(tuple[3])
                    )
                    AirParserConfig.DIVIDE_TO_INT0 -> return decimals.divideToInt(
                        interpret(tuple[1]),
                        interpret(tuple[2]),
                        interpret(tuple[3])
                    )
                    AirParserConfig.REMAINDER0 -> return decimals.remainder(
                        interpret(tuple[1]),
                        interpret(tuple[2]),
                        interpret(tuple[3])
                    )
                    AirParserConfig.DIVIDE_AND_REMAINDER0 -> return decimals.divRem(
                        interpret(tuple[1]),
                        interpret(tuple[2]),
                        interpret(tuple[3])
                    )
                    AirParserConfig.POWER0 -> return decimals.power(
                        interpret(tuple[1]),
                        interpret(tuple[2]),
                        interpret(tuple[3])
                    )
                    AirParserConfig.EXP0 -> return decimals.exp(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.LOG0 -> return decimals.log(
                        interpret(tuple[1]),
                        interpret(tuple[2]),
                        interpret(tuple[3])
                    )
                    AirParserConfig.LN0 -> return decimals.ln(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.SHIFT_LEFT0 -> return decimals.shiftLeft(
                        interpret(tuple[1]),
                        interpret(tuple[2]),
                        interpret(tuple[3])
                    )
                    AirParserConfig.SHIFT_RIGHT0 -> return decimals.shiftRight(
                        interpret(tuple[1]),
                        interpret(tuple[2]),
                        interpret(tuple[3])
                    )
                    AirParserConfig.SIN0 -> return decimals.sin(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.COS0 -> return decimals.cos(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.TAN0 -> return decimals.tan(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.ASIN0 -> return decimals.asin(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.ACOS0 -> return decimals.acos(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.ATAN0 -> return decimals.atan(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.ATAN20 -> return decimals.atan2(
                        interpret(tuple[1]),
                        interpret(tuple[2]),
                        interpret(tuple[3])
                    )
                    AirParserConfig.SINH0 -> return decimals.sinh(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.COSH0 -> return decimals.cosh(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.TANH0 -> return decimals.tanh(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.ASINH0 -> return decimals.asinh(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.ACOSH0 -> return decimals.acosh(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.ATANH0 -> return decimals.atanh(interpret(tuple[1]), interpret(tuple[2]))

                    AirParserConfig.RAND32 -> return decimals.random32()
                    AirParserConfig.SEED32 -> return decimals.seed32(interpret(tuple[1]))
                    AirParserConfig.ROUND32 -> return decimals.round32(interpret(tuple[1]))
                    AirParserConfig.ADD32 -> return decimals.add32(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.SUBTRACT32 -> return decimals.subtract32(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.MULTIPLY32 -> return decimals.multiply32(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.DIVIDE32 -> return decimals.divide32(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.DIVIDE_TO_INT32 -> return decimals.divideToInt32(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )
                    AirParserConfig.REMAINDER32 -> return decimals.remainder32(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.DIVIDE_AND_REMAINDER32 -> return decimals.divRem32(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )
                    AirParserConfig.POWER32 -> return decimals.power32(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.EXP32 -> return decimals.exp32(interpret(tuple[1]))
                    AirParserConfig.LOG32 -> return decimals.log32(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.LN32 -> return decimals.ln32(interpret(tuple[1]))
                    AirParserConfig.SHIFT_LEFT32 -> return decimals.shiftLeft32(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )
                    AirParserConfig.SHIFT_RIGHT32 -> return decimals.shiftRight32(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )
                    AirParserConfig.SIN32 -> return decimals.sin32(interpret(tuple[1]))
                    AirParserConfig.COS32 -> return decimals.cos32(interpret(tuple[1]))
                    AirParserConfig.TAN32 -> return decimals.tan32(interpret(tuple[1]))
                    AirParserConfig.ASIN32 -> return decimals.asin32(interpret(tuple[1]))
                    AirParserConfig.ACOS32 -> return decimals.acos32(interpret(tuple[1]))
                    AirParserConfig.ATAN32 -> return decimals.atan32(interpret(tuple[1]))
                    AirParserConfig.ATAN232 -> return decimals.atan232(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.SINH32 -> return decimals.sinh32(interpret(tuple[1]))
                    AirParserConfig.COSH32 -> return decimals.cosh32(interpret(tuple[1]))
                    AirParserConfig.TANH32 -> return decimals.tanh32(interpret(tuple[1]))
                    AirParserConfig.ASINH32 -> return decimals.asinh32(interpret(tuple[1]))
                    AirParserConfig.ACOSH32 -> return decimals.acosh32(interpret(tuple[1]))
                    AirParserConfig.ATANH32 -> return decimals.atanh32(interpret(tuple[1]))

                    AirParserConfig.RAND64 -> return decimals.random64()
                    AirParserConfig.SEED64 -> return decimals.seed64(interpret(tuple[1]))
                    AirParserConfig.ROUND64 -> return decimals.round64(interpret(tuple[1]))
                    AirParserConfig.ADD64 -> return decimals.add64(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.SUBTRACT64 -> return decimals.subtract64(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.MULTIPLY64 -> return decimals.multiply64(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.DIVIDE64 -> return decimals.divide64(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.DIVIDE_TO_INT64 -> return decimals.divideToInt64(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )
                    AirParserConfig.REMAINDER64 -> return decimals.remainder64(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.DIVIDE_AND_REMAINDER64 -> return decimals.divRem64(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )
                    AirParserConfig.POWER64 -> return decimals.power64(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.EXP64 -> return decimals.exp64(interpret(tuple[1]))
                    AirParserConfig.LOG64 -> return decimals.log64(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.LN64 -> return decimals.ln64(interpret(tuple[1]))
                    AirParserConfig.SHIFT_LEFT64 -> return decimals.shiftLeft64(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )
                    AirParserConfig.SHIFT_RIGHT64 -> return decimals.shiftRight64(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )
                    AirParserConfig.SIN64 -> return decimals.sin64(interpret(tuple[1]))
                    AirParserConfig.COS64 -> return decimals.cos64(interpret(tuple[1]))
                    AirParserConfig.TAN64 -> return decimals.tan64(interpret(tuple[1]))
                    AirParserConfig.ASIN64 -> return decimals.asin64(interpret(tuple[1]))
                    AirParserConfig.ACOS64 -> return decimals.acos64(interpret(tuple[1]))
                    AirParserConfig.ATAN64 -> return decimals.atan64(interpret(tuple[1]))
                    AirParserConfig.ATAN264 -> return decimals.atan264(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.SINH64 -> return decimals.sinh64(interpret(tuple[1]))
                    AirParserConfig.COSH64 -> return decimals.cosh64(interpret(tuple[1]))
                    AirParserConfig.TANH64 -> return decimals.tanh64(interpret(tuple[1]))
                    AirParserConfig.ASINH64 -> return decimals.asinh64(interpret(tuple[1]))
                    AirParserConfig.ACOSH64 -> return decimals.acosh64(interpret(tuple[1]))
                    AirParserConfig.ATANH64 -> return decimals.atanh64(interpret(tuple[1]))

                    AirParserConfig.RAND128 -> return decimals.random128()
                    AirParserConfig.SEED128 -> return decimals.seed128(interpret(tuple[1]))
                    AirParserConfig.ROUND128 -> return decimals.round128(interpret(tuple[1]))
                    AirParserConfig.ADD128 -> return decimals.add128(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.SUBTRACT128 -> return decimals.subtract128(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.MULTIPLY128 -> return decimals.multiply128(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.DIVIDE128 -> return decimals.divide128(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.DIVIDE_TO_INT128 -> return decimals.divideToInt128(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )
                    AirParserConfig.REMAINDER128 -> return decimals.remainder128(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )
                    AirParserConfig.DIVIDE_AND_REMAINDER128 -> return decimals.divRem128(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )
                    AirParserConfig.POWER128 -> return decimals.power128(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.EXP128 -> return decimals.exp128(interpret(tuple[1]))
                    AirParserConfig.LOG128 -> return decimals.log128(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.LN128 -> return decimals.ln128(interpret(tuple[1]))
                    AirParserConfig.SHIFT_LEFT128 -> return decimals.shiftLeft128(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )
                    AirParserConfig.SHIFT_RIGHT128 -> return decimals.shiftRight128(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )
                    AirParserConfig.SIN128 -> return decimals.sin128(interpret(tuple[1]))
                    AirParserConfig.COS128 -> return decimals.cos128(interpret(tuple[1]))
                    AirParserConfig.TAN128 -> return decimals.tan128(interpret(tuple[1]))
                    AirParserConfig.ASIN128 -> return decimals.asin128(interpret(tuple[1]))
                    AirParserConfig.ACOS128 -> return decimals.acos128(interpret(tuple[1]))
                    AirParserConfig.ATAN128 -> return decimals.atan128(interpret(tuple[1]))
                    AirParserConfig.ATAN2128 -> return decimals.atan2128(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.SINH128 -> return decimals.sinh128(interpret(tuple[1]))
                    AirParserConfig.COSH128 -> return decimals.cosh128(interpret(tuple[1]))
                    AirParserConfig.TANH128 -> return decimals.tanh128(interpret(tuple[1]))
                    AirParserConfig.ASINH128 -> return decimals.asinh128(interpret(tuple[1]))
                    AirParserConfig.ACOSH128 -> return decimals.acosh128(interpret(tuple[1]))
                    AirParserConfig.ATANH128 -> return decimals.atanh128(interpret(tuple[1]))

                    // type
                    AirParserConfig.TYPE_OF -> return types.typeOf(interpret(tuple[1]))
                    AirParserConfig.TYPE_CAST -> return types.toType(interpret(tuple[1]), interpret(tuple[2]))

                    // parse
                    AirParserConfig.ENCODE_TO_STRING -> return parsers.encodeToString(interpret(tuple[1]))
                    AirParserConfig.DECODE_FROM_STRING -> return parsers.decodeFromString(interpret(tuple[1]))
                }
            }
        }
        return value
    }
}