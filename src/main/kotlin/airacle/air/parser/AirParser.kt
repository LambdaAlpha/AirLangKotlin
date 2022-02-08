package airacle.air.parser

import airacle.air.api.IAirParser
import airacle.air.interpreter.*
import airacle.air.lexer.*

class AirParserError(msg: String) : Error(msg)

interface IAirParserConfig {
    /**
     * value: key value
     * return the length of param determined by the key value
     * return negative int if the key value is not valid
     */
    fun paramLength(value: AirValue): Int
}

class AirParser(
    val version: AirParserVersion,
    private val config: IAirParserConfig
) : IAirParser<AirToken, AirValue> {

    override fun parse(tokens: List<AirToken>): AirValue {
        val nodes = tokens.map { TokenNode(it) }
        val node = parseOneSkipComment(nodes, 0, false).first
        if (node is ValueNode<*>) {
            return node.value
        } else {
            throw AirParserError("non-value: $node")
        }
    }

    private fun parseOne(nodes: List<AirSyntaxNode>, start: Int, infixMode: Boolean = false): Pair<AirSyntaxNode, Int> {
        if (start >= nodes.size) {
            throw AirParserError("unexpected end when parsing")
        }
        val key = nodes[start]
        val next = start + 1
        if (key !is TokenNode<*>) {
            return Pair(key, next)
        }
        return when (val token = key.token) {
            // basic values
            is UnitToken -> Pair(ValueNode(UnitValue), next)
            is BoolToken -> Pair(ValueNode(BoolValue.valueOf(token.value)), next)
            is IntToken -> Pair(ValueNode(IntValue.valueOf(token.value)), next)
            is DecimalToken -> Pair(ValueNode(DecimalValue.valueOf(token.value)), next)
            is AlphaStringToken -> Pair(ValueNode(StringValue.valueOf(token.value)), next)
            is FullStringToken -> Pair(ValueNode(StringValue.valueOf(token.value)), next)

            // compound values
            SymbolStringToken.LCircle -> parseCircle(nodes, next)
            SymbolStringToken.LSquare -> parseSquare(nodes, next)
            SymbolStringToken.LCurly -> parseCurly(nodes, next)
            SymbolStringToken.LAngle -> parseAngle(nodes, next)
            SymbolStringToken.RCircle,
            SymbolStringToken.RSquare,
            SymbolStringToken.RCurly,
            SymbolStringToken.RAngle,
            SymbolStringToken.Colon,
            SymbolStringToken.Comma -> Pair(key, next)

            // keyword tuples
            SymbolStringToken.Semicolon -> parseKeyword(nodes, next)

            // comment
            SymbolStringToken.Sharp -> parseComment(nodes, next, infixMode)

            // symbols
            is SymbolStringToken -> parseSymbol(token, nodes, next, infixMode = infixMode)

            else -> throw AirParserError("unknown token: $key")
        }
    }

    private fun parseOneSkipComment(
        nodes: List<AirSyntaxNode>,
        start: Int,
        infixMode: Boolean = false
    ): Pair<AirSyntaxNode, Int> {
        var next = start
        do {
            val pair = parseOne(nodes, next, infixMode)
            next = pair.second
            if (pair.first !is CommentNode) {
                return pair
            }
        } while (next < nodes.size)
        throw AirParserError("unexpected end when parsing")
    }

    private fun parseComment(nodes: List<AirSyntaxNode>, start: Int, infixMode: Boolean): Pair<AirSyntaxNode, Int> {
        // drop one
        val pair = parseOne(nodes, start, infixMode)
        return Pair(CommentNode, pair.second)
    }

    // infix expression with left association
    private fun parseCircle(nodes: List<AirSyntaxNode>, start: Int): Pair<AirSyntaxNode, Int> {
        var pair = parseOneSkipComment(nodes, start, infixMode = true)
        var node = pair.first
        var firstValue: AirValue? = null
        var secondValue: AirValue? = null
        while (!(node is TokenNode<*> && node.token == SymbolStringToken.RCircle)) {
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
                firstValue = TupleValue.valueOf(secondValue, firstValue, value)
                secondValue = null
            }
            pair = parseOneSkipComment(nodes, pair.second, infixMode = true)
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
        var pair = parseOneSkipComment(nodes, start)
        var node = pair.first
        var allowComma = false
        while (!(node is TokenNode<*> && node.token == SymbolStringToken.RSquare)) {
            if (node is ValueNode<*>) {
                allowComma = true
                list.add(node.value)
            } else {
                if (allowComma && node is TokenNode<*> && node.token == SymbolStringToken.Comma) {
                    allowComma = false
                } else {
                    throw AirParserError("non-value when parsing list: $node")
                }
            }
            pair = parseOneSkipComment(nodes, pair.second)
            node = pair.first
        }
        return Pair(ValueNode(ListValue.valueOf(list)), pair.second)
    }

    // map, optional colon and comma
    private fun parseCurly(nodes: List<AirSyntaxNode>, start: Int): Pair<AirSyntaxNode, Int> {
        val map = mutableMapOf<AirValue, AirValue>()
        var pair = parseOneSkipComment(nodes, start)
        var node = pair.first
        // 1 key 2 colon 3 value 4 comma
        var status = 1
        var key: AirValue? = null
        while (!(node is TokenNode<*> && node.token == SymbolStringToken.RCurly)) {
            when (status) {
                1 -> {
                    if (node !is ValueNode<*>) {
                        throw AirParserError("non-value for key when parsing map: $node")
                    }
                    key = node.value
                    status = 2
                }
                2 -> {
                    if (node is ValueNode<*>) {
                        val value = node.value
                        map[key!!] = value
                        status = 4
                    } else if (node is TokenNode<*>) {
                        if (node.token != SymbolStringToken.Colon) {
                            throw AirParserError("unexpected token when parsing map: ${node.token}")
                        }
                        status = 3
                    }
                }
                3 -> {
                    if (node is ValueNode<*>) {
                        val value = node.value
                        map[key!!] = value
                        status = 4
                    } else {
                        throw AirParserError("non-value for value when parsing map: $node")
                    }
                }
                4 -> {
                    if (node is ValueNode<*>) {
                        key = node.value
                        status = 2
                    } else if (node is TokenNode<*>) {
                        if (node.token != SymbolStringToken.Comma) {
                            throw AirParserError("unexpected token when parsing map: ${node.token}")
                        }
                        status = 1
                    }
                }
            }

            pair = parseOneSkipComment(nodes, pair.second)
            node = pair.first
        }
        if (status != 1 && status != 4) {
            throw AirParserError("unexpected ending when parsing map, status = $status")
        }
        return Pair(ValueNode(MapValue.valueOf(map)), pair.second)
    }

    // tuple, optional comma
    private fun parseAngle(nodes: List<AirSyntaxNode>, start: Int): Pair<AirSyntaxNode, Int> {
        val list = mutableListOf<AirValue>()
        var pair = parseOneSkipComment(nodes, start)
        var node = pair.first
        var allowComma = false
        while (!(node is TokenNode<*> && node.token == SymbolStringToken.RAngle)) {
            if (node is ValueNode<*>) {
                allowComma = true
                list.add(node.value)
            } else {
                if (allowComma && node is TokenNode<*> && node.token == SymbolStringToken.Comma) {
                    allowComma = false
                } else {
                    throw AirParserError("non-value when parsing tuple: $node")
                }
            }
            pair = parseOneSkipComment(nodes, pair.second)
            node = pair.first
        }
        val tupleValue = TupleValue.fromArray(list.toTypedArray())
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
            val pair = parseOneSkipComment(nodes, startVar)
            val node = pair.first
            if (node is ValueNode<*>) {
                list.add(node.value)
                startVar = pair.second
            } else {
                throw AirParserError("non-value when parsing fixed-length tuple: $node")
            }
        }
        val tupleValue = TupleValue.fromArray(list.toTypedArray())
        val resultNode = ValueNode(tupleValue)
        return Pair(resultNode, startVar)
    }

    private fun parseKeyword(nodes: List<AirSyntaxNode>, start: Int): Pair<AirSyntaxNode, Int> {
        val pair = parseOneSkipComment(nodes, start)
        val node = pair.first
        if (node !is ValueNode<*>) {
            throw AirParserError("non-value when parsing keyword: $node")
        }
        val length = config.paramLength(node.value)
        if (length < 0) {
            throw AirParserError("param length < 0 when parsing keyword: ${node.value}")
        }
        return parseFixedLengthTuple(node, length, nodes, pair.second)
    }

    private fun parseSymbol(
        token: SymbolStringToken,
        nodes: List<AirSyntaxNode>,
        start: Int,
        infixMode: Boolean
    ): Pair<AirSyntaxNode, Int> {
        val value = StringValue.valueOf(token.value)
        val length = config.paramLength(value)
        if (length < 0) {
            throw AirParserError("param length < 0 when parsing alias: ${token.value}")
        }
        if (infixMode && length == 2) {
            return Pair(ValueNode(value), start)
        }
        return parseFixedLengthTuple(ValueNode(value), length, nodes, start)
    }
}