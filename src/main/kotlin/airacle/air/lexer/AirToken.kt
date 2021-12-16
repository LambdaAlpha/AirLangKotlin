package airacle.air.lexer

sealed interface AirToken

object DelimiterToken : AirToken

object UnitToken : AirToken

sealed class BoolToken(val value: Boolean) : AirToken

object TrueToken : BoolToken(true)

object FalseToken : BoolToken(false)

data class IntegerToken(val value: Long) : AirToken

data class FloatToken(val value: Double) : AirToken

data class StringToken(val value: String) : AirToken

sealed class SymbolToken(val value: Char) : AirToken

object LCircleToken : SymbolToken('(')

object RCircleToken : SymbolToken(')')

object LSquareToken : SymbolToken('[')

object RSquareToken : SymbolToken(']')

object LCurlyToken : SymbolToken('{')

object RCurlyToken : SymbolToken('}')

object LSlashToken : SymbolToken('\\')

object MSlashToken : SymbolToken('|')

object RSlashToken : SymbolToken('/')

object LAngleToken : SymbolToken('<')

object EqualToken : SymbolToken('=')

object RAngleToken : SymbolToken('>')

object SemicolonToken : SymbolToken(';')

object ColonToken : SymbolToken(':')

object CommaToken : SymbolToken(',')

object PeriodToken : SymbolToken('.')

object SingleQuoteToken : SymbolToken('\'')

object DoubleQuoteToken : SymbolToken('"')

object BackQuoteToken : SymbolToken('`')

object HatToken : SymbolToken('^')

object MinusToken : SymbolToken('-')

object PlusToken : SymbolToken('+')

object ExclamationToken : SymbolToken('!')

object QuestionMarkToken : SymbolToken('?')

object TildeToken : SymbolToken('~')

object AtToken : SymbolToken('@')

object NumToken : SymbolToken('#')

object DollarToken : SymbolToken('$')

object PercentToken : SymbolToken('%')

object AmpersandToken : SymbolToken('&')

object AsteriskToken : SymbolToken('*')

object UnderscoreToken : SymbolToken('_')

