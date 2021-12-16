package airacle.air.parser

import airacle.air.interpreter.*
import airacle.air.lexer.*

class AirParserError(msg: String) : Error(msg)

interface IAirParserConfig {
    /**
     * valueNode: key value
     * return the length of tuple determined by key value
     * return negative int if key value is not valid
     */
    fun tupleLength(value: AirValue): Int
}

class AirParser(private val config: IAirParserConfig) {

    fun parse(tokens: List<AirToken>): List<AirValue> {
        val nodes = tokens.map { TokenNode(it) }
        val result = mutableListOf<AirValue>()
        var start = 0
        while (start < nodes.size) {
            val pair = parseOne(nodes, start)
            if (pair.first is ValueNode<*>) {
                result.add((pair.first as ValueNode<*>).value)
            } else {
                throw AirParserError("non-value: ${pair.first}")
            }
            start = pair.second
        }
        return result
    }

    private fun parseOne(nodes: List<AirSyntaxNode>, start: Int): Pair<AirSyntaxNode, Int> {
        val key = nodes[start]
        val next = start + 1
        if (key !is TokenNode<*>) {
            return Pair(key, next)
        }
        return when (key.token) {
            // basic values
            is UnitToken -> Pair(ValueNode(UnitValue), next)
            is TrueToken -> Pair(ValueNode(TrueValue), next)
            is FalseToken -> Pair(ValueNode(FalseValue), next)
            is IntegerToken -> Pair(ValueNode(IntegerValue(key.token.value)), next)
            is FloatToken -> Pair(ValueNode(FloatValue(key.token.value)), next)
            is StringToken -> Pair(ValueNode(StringValue(key.token.value)), next)

            // compound values
            is LCircleToken -> parseCircle(nodes, next)
            is LSquareToken -> parseSquare(nodes, next)
            is LCurlyToken -> parseCurly(nodes, next)
            is LAngleToken -> parseAngle(nodes, next)
            is RCircleToken,
            is RSquareToken,
            is RCurlyToken,
            is RAngleToken,
            is CommaToken -> Pair(key, next)

            // keyword tuples
            is BackQuoteToken -> parseKeyword(nodes, next)

            // symbols
            is SymbolToken -> parseSymbol(key.token, nodes, next)

            else -> throw AirParserError("unknown token: $key")
        }
    }

    // grouping
    private fun parseCircle(nodes: List<AirSyntaxNode>, start: Int): Pair<AirSyntaxNode, Int> {
        val pair = parseOne(nodes, start)
        if (pair.second >= nodes.size) {
            throw AirParserError("unexpected ending when parsing circle")
        }
        val nextPair = parseOne(nodes, pair.second)
        if (nextPair.first is TokenNode<*>) {
            val node = nextPair.first as TokenNode<*>
            if (node.token is RCircleToken) {
                return Pair(pair.first, nextPair.second)
            }
        }
        throw AirParserError("unexpected node when parsing circle: ${nextPair.first}")
    }

    // list, optional comma
    private fun parseSquare(nodes: List<AirSyntaxNode>, start: Int): Pair<AirSyntaxNode, Int> {
        val list = mutableListOf<AirValue>()
        var pair = parseOne(nodes, start)
        var allowComma = false
        while (!(pair.first is TokenNode<*> && (pair.first as TokenNode<*>).token is RSquareToken)) {
            if (pair.first is ValueNode<*>) {
                allowComma = true
                list.add((pair.first as ValueNode<*>).value)
            } else {
                if (allowComma && pair.first is TokenNode<*> && (pair.first as TokenNode<*>).token is CommaToken) {
                    allowComma = false
                } else {
                    throw AirParserError("non-value when parsing list: ${pair.first}")
                }
            }
            if (pair.second >= nodes.size) {
                throw AirParserError("unexpected ending when parsing list")
            }
            pair = parseOne(nodes, pair.second)
        }
        return Pair(ValueNode(ListValue(list)), pair.second)
    }

    // map, optional comma
    private fun parseCurly(nodes: List<AirSyntaxNode>, start: Int): Pair<AirSyntaxNode, Int> {
        val map = mutableMapOf<AirValue, AirValue>()
        var pair = parseOne(nodes, start)
        // 1 key 2 value 3 comma
        var status = 1
        var key: AirValue? = null
        while (!(pair.first is TokenNode<*> && (pair.first as TokenNode<*>).token is RCurlyToken)) {

            when (status) {
                1 -> {
                    if (pair.first !is ValueNode<*>) {
                        throw AirParserError("non-value for key when parsing map: ${pair.first}")
                    }
                    key = (pair.first as ValueNode<*>).value
                    status = 2
                }
                2 -> {
                    if (pair.first !is ValueNode<*>) {
                        throw AirParserError("non-value for value when parsing map: ${pair.first}")
                    }
                    val value = (pair.first as ValueNode<*>).value
                    map[key!!] = value
                    status = 3
                }
                3 -> {
                    if (pair.first is ValueNode<*>) {
                        key = (pair.first as ValueNode<*>).value
                        status = 2
                    } else if (pair.first is TokenNode<*>) {
                        if ((pair.first as TokenNode<*>).token !is CommaToken) {
                            throw AirParserError("unexpected token when parsing map: ${pair.first}")
                        }
                        status = 1
                    }
                }
            }

            if (pair.second >= nodes.size) {
                throw AirParserError("unexpected ending when parsing map")
            }
            pair = parseOne(nodes, pair.second)
        }
        if (status != 1 && status != 3) {
            throw AirParserError("unexpected ending when parsing map, status = $status")
        }
        return Pair(ValueNode(MapValue(map)), pair.second)
    }

    // tuple, optional comma
    private fun parseAngle(nodes: List<AirSyntaxNode>, start: Int): Pair<AirSyntaxNode, Int> {
        val list = mutableListOf<AirValue>()
        var pair = parseOne(nodes, start)
        var allowComma = false
        while (!(pair.first is TokenNode<*> && (pair.first as TokenNode<*>).token is RAngleToken)) {
            if (pair.first is ValueNode<*>) {
                allowComma = true
                list.add((pair.first as ValueNode<*>).value)
            } else {
                if (allowComma && pair.first is TokenNode<*> && (pair.first as TokenNode<*>).token is CommaToken) {
                    allowComma = false
                } else {
                    throw AirParserError("non-value when parsing tuple: ${pair.first}")
                }
            }
            if (pair.second >= nodes.size) {
                throw AirParserError("unexpected ending when parsing surrounded tuple")
            }
            pair = parseOne(nodes, pair.second)
        }
        val tupleValue = TupleValue(list.toTypedArray())
        val resultNode = ValueNode(tupleValue)
        return Pair(resultNode, pair.second)
    }

    private fun parseFixedLengthTuple(
        first: ValueNode<*>,
        number: Int,
        nodes: List<AirSyntaxNode>,
        start: Int
    ): Pair<AirSyntaxNode, Int> {
        val list = mutableListOf<AirValue>()
        list.add(first.value)
        var startVar = start
        for (i in 1..number) {
            if (startVar >= nodes.size) {
                throw AirParserError("unexpected ending when parsing fixed-length tuple")
            }
            val pair = parseOne(nodes, startVar)
            if (pair.first is ValueNode<*>) {
                list.add((pair.first as ValueNode<*>).value)
                startVar = pair.second
            } else {
                throw AirParserError("non-value when parsing fixed-length tuple")
            }
        }
        val tupleValue = TupleValue(list.toTypedArray())
        val resultNode = ValueNode(tupleValue)
        return Pair(resultNode, startVar)
    }

    private fun parseKeyword(nodes: List<AirSyntaxNode>, start: Int): Pair<AirSyntaxNode, Int> {
        val pair = parseOne(nodes, start)
        if (pair.first !is ValueNode<*>) {
            throw AirParserError("non-value when parsing keyword")
        }
        val length = config.tupleLength((pair.first as ValueNode<*>).value)
        if (length < 0) {
            throw AirParserError("tuple length < 0 when parsing keyword")
        }
        return parseFixedLengthTuple(pair.first as ValueNode<*>, length, nodes, pair.second)
    }

    private fun parseSymbol(token: SymbolToken, nodes: List<AirSyntaxNode>, start: Int): Pair<AirSyntaxNode, Int> {
        val value = StringValue("${token.value}")
        val length = config.tupleLength(value)
        if (length < 0) {
            throw AirParserError("tuple length < 0 when parsing alias")
        }
        return parseFixedLengthTuple(ValueNode(value), length, nodes, start)
    }
}

object AirParserConfig : IAirParserConfig {
    override fun tupleLength(value: AirValue): Int {
        if (value is StringValue) {
            return when (value.value) {
                "if", "?" -> 3
                "for", "%" -> 4
                "while", "@" -> 2
                "comment", "#" -> 1
                "function", "^" -> 2
                "return", "~" -> 1
                "apply", "$" -> 2
                "assign", "=" -> 2
                "le", "lt", "ge", "gt", "eq", "ne",
                "<=", "<", ">=", ">", "==", "!=" -> 2
                else -> -1
            }
        }
        return -1
    }
}