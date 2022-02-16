package airacle.air.core.interpreter

import airacle.air.core.lexer.AirLexer
import airacle.air.core.lexer.AlphaStringLexer
import airacle.air.core.lexer.SymbolStringLexer
import airacle.air.core.parser.AirParser

class Parsers(
    private val lexer: AirLexer,
    private val parser: AirParser
) : IParsers {
    override fun decodeFromString(v: AirValue): AirValue {
        return if (v is StringValue) {
            try {
                val tokens = lexer.lex(v.value)
                return parser.parse(tokens)
            } catch (e: Throwable) {
                UnitValue
            }
        } else {
            UnitValue
        }
    }

    override fun encodeToString(v: AirValue): AirValue {
        return StringValue.valueOf(v.toString())
    }

    private fun prettyPrintString(builder: StringBuilder, str: StringValue) {
        if (AlphaStringLexer.pattern().matcher(str.value).matches()) {
            builder.append(str.value)
            return
        }

        builder.append('"')
        for (c in str.value) {
            when (c) {
                '\\' -> builder.append("\\\\")
                '\n' -> builder.append("\\n")
                '\r' -> builder.append("\\r")
                '\t' -> builder.append("\\t")
                '"' -> builder.append("\\\"")
                else -> builder.append(c)
            }
        }
        builder.append('"')
    }

    private fun prettyPrintTuple(builder: StringBuilder, tuple: TupleValue, indent: Int) {
        val array = tuple.value
        if (array.isEmpty()) {
            builder.append("<>")
            return
        }

        val keyword = array[0]
        if (parser.config.paramLength(keyword) + 1 == array.size) {
            if (keyword is StringValue && SymbolStringLexer.pattern().matcher(keyword.value).matches()) {
                builder.append(keyword.value)
                if (array.size > 1) {
                    if (array.size > 2) {
                        builder.append(' ')
                    }
                    prettyPrintValue(builder, array[1], indent)
                    for (i in 2 until array.size) {
                        builder.append(' ')
                        prettyPrintValue(builder, array[i], indent)
                    }
                }
            } else {
                builder.append(';')
                prettyPrintValue(builder, keyword, indent)
                for (i in 1 until array.size) {
                    builder.append(' ')
                    prettyPrintValue(builder, array[i], indent)
                }
            }
        } else {
            builder.append("<")
            prettyPrintValue(builder, keyword, indent)
            for (i in 1 until array.size) {
                builder.append(' ')
                prettyPrintValue(builder, array[i], indent)
            }
            builder.append('>')
        }
    }

    private fun prettyPrintList(builder: StringBuilder, list: ListValue, indent: Int) {
        val v = list.value
        if (v.isEmpty()) {
            builder.append("[]")
            return
        }

        builder.append("[\n")
        val deeper = indent + 1
        for (i in 0 until v.size) {
            repeat(deeper) {
                builder.append("  ")
            }
            prettyPrintValue(builder, v[i], deeper)
            builder.append('\n')
        }
        repeat(indent) {
            builder.append("  ")
        }
        builder.append(']')
    }

    private fun prettyPrintMap(builder: StringBuilder, map: MapValue, indent: Int) {
        if (map.value.isEmpty()) {
            builder.append("{}")
            return
        }

        builder.append("{\n")
        val deeper = indent + 1
        for (entry in map.value.entries) {
            repeat(deeper) {
                builder.append("  ")
            }
            prettyPrintValue(builder, entry.key, deeper)
            builder.append(": ")
            prettyPrintValue(builder, entry.value, deeper)
            builder.append(",\n")
        }
        repeat(indent) {
            builder.append("  ")
        }
        builder.append('}')
    }

    private fun prettyPrintValue(builder: StringBuilder, v: AirValue, indent: Int) {
        when (v) {
            is UnitValue,
            is BoolValue,
            is IntValue,
            is DecimalValue -> builder.append(v)
            is StringValue -> prettyPrintString(builder, v)
            is TupleValue -> prettyPrintTuple(builder, v, indent)
            is ListValue -> prettyPrintList(builder, v, indent)
            is MapValue -> prettyPrintMap(builder, v, indent)
        }
    }

    override fun prettyPrint(v: AirValue): AirValue {
        val builder = StringBuilder()
        prettyPrintValue(builder, v, 0)
        return StringValue.valueOf(builder.toString())
    }
}