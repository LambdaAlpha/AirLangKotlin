package airacle.air.lexer

internal val TOKENS = listOf(
    UnitToken,

    FalseToken,
    TrueToken,

    IntegerToken(0),
    IntegerToken(0),
    IntegerToken(0),
    IntegerToken(0),
    IntegerToken(1),
    IntegerToken(1234),
    IntegerToken(5678),
    IntegerToken(-90),
    IntegerToken(0xabcdef),
    IntegerToken(0x012345),
    IntegerToken(0xa0b1c2d3),
    IntegerToken(0xabcdef),
    IntegerToken(-0x012345),
    IntegerToken(0b1101),
    IntegerToken(0b0011),
    IntegerToken(-0b1111),
    IntegerToken(123),
    IntegerToken(1223334444),
    IntegerToken(1234),
    IntegerToken(122333),
    IntegerToken(-122333),
    IntegerToken(0xabbccc),
    IntegerToken(0xabbccc),
    IntegerToken(-0xabbccc),
    IntegerToken(0b011000),
    IntegerToken(0b011000),
    IntegerToken(-0b011000),

    FloatToken(0.0),
    FloatToken(0.0),
    FloatToken(-0.0),
    FloatToken(1.234),
    FloatToken(1.234e0),
    FloatToken(1.234e10),
    FloatToken(1.234e-10),
    FloatToken(1.234e10),
    FloatToken(-1.234e-10),
    FloatToken(122333.455666e-2),

    AlphaStringToken("a"),
    AlphaStringToken("Abc"),
    AlphaStringToken("A_BB__CCC"),

    BackQuoteToken,
    TildeToken,
    ExclamationToken,
    AtToken,
    NumToken,
    DollarToken,
    PercentToken,
    HatToken,
    AmpersandToken,
    AsteriskToken,
    LCircleToken,
    RCircleToken,
    MinusToken,
    UnderscoreToken,
    EqualToken,
    PlusToken,
    LSquareToken,
    RSquareToken,
    LCurlyToken,
    RCurlyToken,
    SemicolonToken,
    ColonToken,
    SingleQuoteToken,
    CommaToken,
    PeriodToken,
    LAngleToken,
    RAngleToken,
    QuestionMarkToken,

    FullStringToken("Hello world!"),
    FullStringToken("`~!@#\$%^&*()-_=+[]{};:'\",.<>?\\|/"),
    FullStringToken("  \n\r\t\uD83D\uDF01\uD83D\uDF01"),
    FullStringToken("\\s\\n\\r\\t\\uD83D\\uDF01"),
    FullStringToken("multiple lines"),
)