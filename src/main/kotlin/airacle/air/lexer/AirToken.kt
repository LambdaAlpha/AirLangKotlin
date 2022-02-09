package airacle.air.lexer

import java.math.BigDecimal
import java.math.BigInteger

sealed interface AirToken

object DelimiterToken : AirToken

object UnitToken : AirToken {
    override fun equals(other: Any?): Boolean {
        return other is UnitToken
    }

    override fun hashCode(): Int {
        return Unit.hashCode()
    }

    override fun toString(): String {
        return "|"
    }
}

class BoolToken private constructor(val value: Boolean) : AirToken {
    companion object {
        val FALSE: BoolToken = BoolToken(false)
        val TRUE: BoolToken = BoolToken(true)

        fun valueOf(value: Boolean): BoolToken {
            return if (value) TRUE else FALSE
        }

        fun toString(value: Boolean): String {
            return if (value) "/" else "\\"
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is BoolToken && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return Companion.toString(value)
    }
}

class IntToken private constructor(val value: BigInteger) : AirToken {
    companion object {
        val ZERO: IntToken = IntToken(BigInteger.ZERO)
        val ONE: IntToken = IntToken(BigInteger.ONE)
        val TWO: IntToken = IntToken(BigInteger.TWO)

        fun valueOf(value: BigInteger): IntToken {
            return when (value) {
                BigInteger.ZERO -> ZERO
                BigInteger.ONE -> ONE
                BigInteger.TWO -> TWO
                else -> IntToken(value)
            }
        }

        fun toString(value: BigInteger): String {
            return value.toString()
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is IntToken && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return Companion.toString(value)
    }
}

class DecimalToken private constructor(val value: BigDecimal) : AirToken {
    companion object {
        val ZERO: DecimalToken = DecimalToken(BigDecimal.ZERO)
        val ONE: DecimalToken = DecimalToken(BigDecimal.ONE)
        val TWO: DecimalToken = DecimalToken("2".toBigDecimal())
        val HALF: DecimalToken = DecimalToken("0.5".toBigDecimal())

        fun valueOf(value: BigDecimal): DecimalToken {
            return when (value) {
                BigDecimal.ZERO -> ZERO
                BigDecimal.ONE -> ONE
                "2".toBigDecimal() -> TWO
                "0.5".toBigDecimal() -> HALF
                else -> DecimalToken(value)
            }
        }

        fun toString(value: BigDecimal): String {
            val s = value.toString()
            if (s.contains(".")) {
                return s
            }
            val i = s.indexOf('E')
            return if (i >= 0) {
                s.substring(0, i) + "." + s.substring(i)
            } else {
                "$s."
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is DecimalToken && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return Companion.toString(value)
    }
}

sealed class StringToken(val value: String) : AirToken {
    override fun equals(other: Any?): Boolean {
        return other is StringToken && other.value == value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

class FullStringToken(value: String) : StringToken(value) {
    companion object {
        fun toString(value: String): String {
            val builder = StringBuilder(value.length)
            builder.append('"')
            for (c in value) {
                when (c) {
                    '\\' -> builder.append("\\\\")
                    '\n' -> builder.append("\\n")
                    '\r' -> builder.append("\\r")
                    '\t' -> builder.append("\\t")
                    ' ' -> builder.append("\\s")
                    '"' -> builder.append("\\\"")
                    else -> builder.append(c)
                }
            }
            builder.append('"')
            return builder.toString()
        }
    }

    override fun toString(): String {
        return Companion.toString(value)
    }
}

class AlphaStringToken(value: String) : StringToken(value) {
    companion object {
        fun toString(value: String): String {
            return value
        }
    }

    override fun toString(): String {
        return Companion.toString(value)
    }
}

class SymbolStringToken private constructor(value: String) : StringToken(value) {
    companion object {
        val LCircle = SymbolStringToken("(")
        val RCircle = SymbolStringToken(")")
        val LSquare = SymbolStringToken("[")
        val RSquare = SymbolStringToken("]")
        val LCurly = SymbolStringToken("{")
        val RCurly = SymbolStringToken("}")
        val LSlash = SymbolStringToken("\\")
        val MSlash = SymbolStringToken("|")
        val RSlash = SymbolStringToken("/")
        val LAngle = SymbolStringToken("<")
        val Equal = SymbolStringToken("=")
        val RAngle = SymbolStringToken(">")
        val Semicolon = SymbolStringToken(";")
        val Colon = SymbolStringToken(":")
        val Comma = SymbolStringToken(",")
        val Period = SymbolStringToken(".")
        val SingleQuote = SymbolStringToken("\'")
        val DoubleQuote = SymbolStringToken("\"")
        val BackQuote = SymbolStringToken("`")
        val Hat = SymbolStringToken("^")
        val Minus = SymbolStringToken("-")
        val Plus = SymbolStringToken("+")
        val Exclamation = SymbolStringToken("!")
        val QuestionMark = SymbolStringToken("?")
        val Tilde = SymbolStringToken("~")
        val At = SymbolStringToken("@")
        val Sharp = SymbolStringToken("#")
        val Dollar = SymbolStringToken("$")
        val Percent = SymbolStringToken("%")
        val Ampersand = SymbolStringToken("&")
        val Star = SymbolStringToken("*")
        val Underscore = SymbolStringToken("_")

        fun toString(value: String): String {
            return value
        }
    }

    override fun toString(): String {
        return Companion.toString(value)
    }
}
