package airacle.air.lexer

sealed interface AirToken

object DelimiterToken : AirToken

object UnitToken : AirToken

sealed class BoolToken(val value: Boolean) : AirToken

object TrueToken : BoolToken(true)

object FalseToken : BoolToken(false)

data class IntegerToken(val value: Long) : AirToken

data class FloatToken(val value: Double) : AirToken

sealed class StringToken(val value: String) : AirToken {
    override fun equals(other: Any?): Boolean {
        return other is StringToken && other.value == value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

class FullStringToken(value: String) : StringToken(value)

class AlphaStringToken(value: String) : StringToken(value)

sealed class SymbolStringToken(value: String) : StringToken(value)

class FullSymbolStringToken(value: String) : SymbolStringToken(value)

sealed class SingleSymbolStringToken(value: String) : SymbolStringToken(value)

object LCircleToken : SingleSymbolStringToken("(")

object RCircleToken : SymbolStringToken(")")

object LSquareToken : SymbolStringToken("[")

object RSquareToken : SymbolStringToken("]")

object LCurlyToken : SymbolStringToken("{")

object RCurlyToken : SymbolStringToken("}")

object LSlashToken : SymbolStringToken("\\")

object MSlashToken : SymbolStringToken("|")

object RSlashToken : SymbolStringToken("/")

object LAngleToken : SymbolStringToken("<")

object EqualToken : SymbolStringToken("=")

object RAngleToken : SymbolStringToken(">")

object SemicolonToken : SymbolStringToken(";")

object ColonToken : SymbolStringToken(":")

object CommaToken : SymbolStringToken(",")

object PeriodToken : SymbolStringToken(".")

object SingleQuoteToken : SymbolStringToken("\'")

object DoubleQuoteToken : SymbolStringToken("\"")

object BackQuoteToken : SymbolStringToken("`")

object HatToken : SymbolStringToken("^")

object MinusToken : SymbolStringToken("-")

object PlusToken : SymbolStringToken("+")

object ExclamationToken : SymbolStringToken("!")

object QuestionMarkToken : SymbolStringToken("?")

object TildeToken : SymbolStringToken("~")

object AtToken : SymbolStringToken("@")

object NumToken : SymbolStringToken("#")

object DollarToken : SymbolStringToken("$")

object PercentToken : SymbolStringToken("%")

object AmpersandToken : SymbolStringToken("&")

object AsteriskToken : SymbolStringToken("*")

object UnderscoreToken : SymbolStringToken("_")
