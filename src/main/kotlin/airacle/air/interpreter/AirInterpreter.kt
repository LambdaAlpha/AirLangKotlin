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
                    AirParserConfig.NOT -> return booleans.not(interpret(tuple[1]))
                    AirParserConfig.AND -> return booleans.and(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.OR -> return booleans.or(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.XOR -> return booleans.xor(interpret(tuple[1]), interpret(tuple[2]))

                    AirParserConfig.LE -> return comparators.le(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.LT -> return comparators.lt(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.GE -> return comparators.ge(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.GT -> return comparators.gt(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.EQ -> return comparators.eq(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.NE -> return comparators.ne(interpret(tuple[1]), interpret(tuple[2]))

                    AirParserConfig.ADD -> return numbers.add(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.SUBTRACT -> return numbers.subtract(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.MULTIPLY -> return numbers.multiply(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.DIVIDE -> return numbers.divide(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.REMAINDER -> return numbers.remainder(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.DIVIDE_AND_REMAINDER -> return numbers.divideAndRemainder(
                        interpret(tuple[1]),
                        interpret(tuple[2])
                    )

                    AirParserConfig.BIT_AND -> return ints.bitAnd(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.BIT_OR -> return ints.bitOr(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.BIT_XOR -> return ints.bitXor(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.SHIFT_LEFT -> return ints.shiftLeft(interpret(tuple[1]), interpret(tuple[2]))
                    AirParserConfig.SHIFT_RIGHT -> return ints.shiftRight(interpret(tuple[1]), interpret(tuple[2]))

                    AirParserConfig.TYPE_OF -> return types.typeOf(interpret(tuple[1]))
                    AirParserConfig.TYPE_CAST -> return types.toType(interpret(tuple[1]), interpret(tuple[2]))

                    AirParserConfig.ENCODE_TO_STRING -> return parsers.encodeToString(interpret(tuple[1]))
                    AirParserConfig.DECODE_FROM_STRING -> return parsers.decodeFromString(interpret(tuple[1]))
                }
            }
        }
        return value
    }
}