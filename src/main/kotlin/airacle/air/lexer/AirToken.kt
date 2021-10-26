package airacle.air.lexer

// TODO: 10/13/21 impl to string
sealed interface AirToken

object DelimiterToken : AirToken

data class CommentToken(val comment: String) : AirToken

data class NameToken(val name: String) : AirToken

object UnitToken : AirToken

sealed class BoolToken(val bool: Boolean) : AirToken

object TrueToken : BoolToken(true)

object FalseToken : BoolToken(false)

data class IntegerToken(val number: Long) : AirToken

data class FloatToken(val number: Double) : AirToken

data class StringToken(val string: String) : AirToken

sealed interface MetaToken : AirToken

sealed class SymbolToken(val key: Char) : MetaToken

object LPToken : SymbolToken('(')

object RPToken : SymbolToken(')')

object LSToken : SymbolToken('[')

object RSToken : SymbolToken(']')

object LCToken : SymbolToken('{')

object RCToken : SymbolToken('}')

object LSlashToken : SymbolToken('\\')

object MSlashToken : SymbolToken('|')

object RSlashToken : SymbolToken('/')

object LTToken : SymbolToken('<')

object EqualToken : SymbolToken('=')

object GTToken : SymbolToken('>')

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

