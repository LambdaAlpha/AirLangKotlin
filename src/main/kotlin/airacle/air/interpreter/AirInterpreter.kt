package airacle.air.interpreter

import airacle.air.api.IAirInterpreter
import airacle.air.api.IAirLexer
import airacle.air.api.IAirParser
import airacle.air.lexer.AirToken

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
        // TODO: 2022/2/3 impl
        return value
    }
}