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

    fun parse(tokens: List<AirToken>): ListValue {
        val nodes = tokens.map { TokenNode(it) }
        val result = mutableListOf<AirValue>()
        var start = 0
        while (start < nodes.size) {
            val pair = parseOne(nodes, start)
            val node = pair.first
            if (node is ValueNode<*>) {
                result.add(node.value)
            } else {
                throw AirParserError("non-value: $node")
            }
            start = pair.second
        }
        return ListValue(result)
    }

    private fun parseOne(nodes: List<AirSyntaxNode>, start: Int, infixMode: Boolean = false): Pair<AirSyntaxNode, Int> {
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
            is AlphaStringToken -> Pair(ValueNode(StringValue(key.token.value)), next)
            is FullStringToken -> Pair(ValueNode(StringValue(key.token.value)), next)

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
            is SingleSymbolStringToken -> parseSymbol(key.token, nodes, next, infixMode = infixMode)

            else -> throw AirParserError("unknown token: $key")
        }
    }

    // infix expression with left association
    private fun parseCircle(nodes: List<AirSyntaxNode>, start: Int): Pair<AirSyntaxNode, Int> {
        var pair = parseOne(nodes, start, infixMode = true)
        var node = pair.first
        var firstValue: AirValue? = null
        var secondValue: AirValue? = null
        while (!(node is TokenNode<*> && node.token is RCircleToken)) {
            val value: AirValue = if (node is ValueNode<*>) {
                node.value
            } else {
                throw AirParserError("unexpected node when parsing circle: $node")
            }
            if (firstValue == null) {
                firstValue = value
            } else if (secondValue == null) {
                secondValue = value
            } else {
                firstValue = TupleValue(arrayOf(secondValue, firstValue, value))
                secondValue = null
            }
            pair = parseOne(nodes, pair.second, infixMode = true)
            node = pair.first
        }
        if (firstValue == null || secondValue != null) {
            throw AirParserError("unexpected end when parsing circle")
        }
        return Pair(ValueNode(firstValue), pair.second)
    }

    // list, optional comma
    private fun parseSquare(nodes: List<AirSyntaxNode>, start: Int): Pair<AirSyntaxNode, Int> {
        val list = mutableListOf<AirValue>()
        var pair = parseOne(nodes, start)
        var node = pair.first
        var allowComma = false
        while (!(node is TokenNode<*> && node.token is RSquareToken)) {
            if (node is ValueNode<*>) {
                allowComma = true
                list.add(node.value)
            } else {
                if (allowComma && node is TokenNode<*> && node.token is CommaToken) {
                    allowComma = false
                } else {
                    throw AirParserError("non-value when parsing list: $node")
                }
            }
            if (pair.second >= nodes.size) {
                throw AirParserError("unexpected ending when parsing list")
            }
            pair = parseOne(nodes, pair.second)
            node = pair.first
        }
        return Pair(ValueNode(ListValue(list)), pair.second)
    }

    // map, optional comma
    private fun parseCurly(nodes: List<AirSyntaxNode>, start: Int): Pair<AirSyntaxNode, Int> {
        val map = mutableMapOf<AirValue, AirValue>()
        var pair = parseOne(nodes, start)
        var node = pair.first
        // 1 key 2 value 3 comma
        var status = 1
        var key: AirValue? = null
        while (!(node is TokenNode<*> && node.token is RCurlyToken)) {

            when (status) {
                1 -> {
                    if (node !is ValueNode<*>) {
                        throw AirParserError("non-value for key when parsing map: $node")
                    }
                    key = node.value
                    status = 2
                }
                2 -> {
                    if (node !is ValueNode<*>) {
                        throw AirParserError("non-value for value when parsing map: $node")
                    }
                    val value = node.value
                    map[key!!] = value
                    status = 3
                }
                3 -> {
                    if (node is ValueNode<*>) {
                        key = node.value
                        status = 2
                    } else if (node is TokenNode<*>) {
                        if (node.token !is CommaToken) {
                            throw AirParserError("unexpected token when parsing map: ${node.token}")
                        }
                        status = 1
                    }
                }
            }

            if (pair.second >= nodes.size) {
                throw AirParserError("unexpected ending when parsing map")
            }
            pair = parseOne(nodes, pair.second)
            node = pair.first
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
        var node = pair.first
        var allowComma = false
        while (!(node is TokenNode<*> && node.token is RAngleToken)) {
            if (node is ValueNode<*>) {
                allowComma = true
                list.add(node.value)
            } else {
                if (allowComma && node is TokenNode<*> && node.token is CommaToken) {
                    allowComma = false
                } else {
                    throw AirParserError("non-value when parsing tuple: $node")
                }
            }
            if (pair.second >= nodes.size) {
                throw AirParserError("unexpected ending when parsing surrounded tuple")
            }
            pair = parseOne(nodes, pair.second)
            node = pair.first
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
            val node = pair.first
            if (node is ValueNode<*>) {
                list.add(node.value)
                startVar = pair.second
            } else {
                throw AirParserError("non-value when parsing fixed-length tuple: $node")
            }
        }
        val tupleValue = TupleValue(list.toTypedArray())
        val resultNode = ValueNode(tupleValue)
        return Pair(resultNode, startVar)
    }

    private fun parseKeyword(nodes: List<AirSyntaxNode>, start: Int): Pair<AirSyntaxNode, Int> {
        val pair = parseOne(nodes, start)
        val node = pair.first
        if (node !is ValueNode<*>) {
            throw AirParserError("non-value when parsing keyword: $node")
        }
        val length = config.tupleLength(node.value)
        if (length < 0) {
            throw AirParserError("tuple length < 0 when parsing keyword: ${node.value}")
        }
        return parseFixedLengthTuple(node, length, nodes, pair.second)
    }

    private fun parseSymbol(
        token: SingleSymbolStringToken,
        nodes: List<AirSyntaxNode>,
        start: Int,
        infixMode: Boolean = false
    ): Pair<AirSyntaxNode, Int> {
        val value = StringValue(token.value)
        val length = config.tupleLength(value)
        if (length < 0) {
            throw AirParserError("tuple length < 0 when parsing alias: ${token.value}")
        }
        if (infixMode && length == 2) {
            return Pair(ValueNode(value), start)
        }
        return parseFixedLengthTuple(ValueNode(value), length, nodes, start)
    }
}