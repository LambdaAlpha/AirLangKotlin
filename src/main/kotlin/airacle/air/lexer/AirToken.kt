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
    }

    override fun equals(other: Any?): Boolean {
        return other is BoolToken && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return if (value) "/" else "\\"
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
    }

    override fun equals(other: Any?): Boolean {
        return other is IntToken && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return if (value >= BigInteger.ZERO) {
            value.toString()
        } else {
            "0$value"
        }
    }
}

class RealToken private constructor(val value: BigDecimal) : AirToken {
    companion object {
        val ZERO: RealToken = RealToken(BigDecimal.ZERO)
        val ONE: RealToken = RealToken(BigDecimal.ONE)
        val TWO: RealToken = RealToken("2".toBigDecimal())

        fun valueOf(value: BigDecimal): RealToken {
            return when (value) {
                BigDecimal.ZERO -> ZERO
                BigDecimal.ONE -> ONE
                "2".toBigDecimal() -> TWO
                else -> RealToken(value)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is RealToken && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return if (value < BigDecimal.ZERO) {
            "0$value"
        } else {
            value.toString()
        }
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
    override fun toString(): String {
        return "\"" +
                // replace \ first
                value.replace("\\", "\\\\")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t")
                    .replace(" ", "\\s")
                    .replace("\"", "\\\"") +
                "\""
    }
}

class AlphaStringToken(value: String) : StringToken(value) {
    override fun toString(): String {
        return value
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
    }

    override fun toString(): String {
        return value
    }
}
