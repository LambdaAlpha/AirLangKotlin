package airacle.air.lexer

sealed interface AirToken

object DelimiterToken : AirToken

object UnitToken : AirToken {
    override fun toString(): String {
        return "|"
    }
}

sealed class BoolToken(val value: Boolean) : AirToken

object TrueToken : BoolToken(true) {
    override fun toString(): String {
        return "/"
    }
}

object FalseToken : BoolToken(false) {
    override fun toString(): String {
        return "\\"
    }
}

class IntegerToken(val value: Long) : AirToken {
    override fun equals(other: Any?): Boolean {
        return other is IntegerToken && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value.toString()
    }
}

data class FloatToken(val value: Double) : AirToken {
    override fun equals(other: Any?): Boolean {
        return other is FloatToken && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value.toString()
    }
}

sealed class StringToken(val value: String) : AirToken {
    override fun equals(other: Any?): Boolean {
        return other is StringToken && other.value == value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "\"$value\""
    }
}

class FullStringToken(value: String) : StringToken(value)

class AlphaStringToken(value: String) : StringToken(value)

sealed class SingleSymbolStringToken(value: String) : StringToken(value)

object LCircleToken : SingleSymbolStringToken("(")

object RCircleToken : SingleSymbolStringToken(")")

object LSquareToken : SingleSymbolStringToken("[")

object RSquareToken : SingleSymbolStringToken("]")

object LCurlyToken : SingleSymbolStringToken("{")

object RCurlyToken : SingleSymbolStringToken("}")

object LSlashToken : SingleSymbolStringToken("\\")

object MSlashToken : SingleSymbolStringToken("|")

object RSlashToken : SingleSymbolStringToken("/")

object LAngleToken : SingleSymbolStringToken("<")

object EqualToken : SingleSymbolStringToken("=")

object RAngleToken : SingleSymbolStringToken(">")

object SemicolonToken : SingleSymbolStringToken(";")

object ColonToken : SingleSymbolStringToken(":")

object CommaToken : SingleSymbolStringToken(",")

object PeriodToken : SingleSymbolStringToken(".")

object SingleQuoteToken : SingleSymbolStringToken("\'")

object DoubleQuoteToken : SingleSymbolStringToken("\"")

object BackQuoteToken : SingleSymbolStringToken("`")

object HatToken : SingleSymbolStringToken("^")

object MinusToken : SingleSymbolStringToken("-")

object PlusToken : SingleSymbolStringToken("+")

object ExclamationToken : SingleSymbolStringToken("!")

object QuestionMarkToken : SingleSymbolStringToken("?")

object TildeToken : SingleSymbolStringToken("~")

object AtToken : SingleSymbolStringToken("@")

object NumToken : SingleSymbolStringToken("#")

object DollarToken : SingleSymbolStringToken("$")

object PercentToken : SingleSymbolStringToken("%")

object AmpersandToken : SingleSymbolStringToken("&")

object AsteriskToken : SingleSymbolStringToken("*")

object UnderscoreToken : SingleSymbolStringToken("_")
