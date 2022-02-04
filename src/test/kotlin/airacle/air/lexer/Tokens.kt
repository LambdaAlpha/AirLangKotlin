package airacle.air.lexer

internal val TOKENS = listOf(
    UnitToken,

    BoolToken.FALSE,
    BoolToken.TRUE,

    IntToken.ZERO,
    IntToken.ZERO,
    IntToken.ZERO,
    IntToken.ZERO,
    IntToken.ONE,
    IntToken.valueOf(1234.toBigInteger()),
    IntToken.valueOf(5678.toBigInteger()),
    IntToken.valueOf((-90).toBigInteger()),
    IntToken.valueOf(0xabcdef.toBigInteger()),
    IntToken.valueOf(0x012345.toBigInteger()),
    IntToken.valueOf(0xa0b1c2d3.toBigInteger()),
    IntToken.valueOf(0xabcdef.toBigInteger()),
    IntToken.valueOf((-0x012345).toBigInteger()),
    IntToken.valueOf(0b1101.toBigInteger()),
    IntToken.valueOf(0b0011.toBigInteger()),
    IntToken.valueOf((-0b1111).toBigInteger()),

    IntToken.valueOf("11111111222222223333333344444444555555556666666677777777888888889999999900000000".toBigInteger()),
    IntToken.valueOf("+11111111222222223333333344444444555555556666666677777777888888889999999900000000".toBigInteger()),
    IntToken.valueOf("-11111111222222223333333344444444555555556666666677777777888888889999999900000000".toBigInteger()),
    IntToken.valueOf(
        "11111111222222223333333344444444555555556666666677777777888888889999999900000000aaaaaaaabbbbbbbbccccccccddddddddeeeeeeeeffffffff".toBigInteger(
            16
        )
    ),
    IntToken.valueOf(
        "+11111111222222223333333344444444555555556666666677777777888888889999999900000000aaaaaaaabbbbbbbbccccccccddddddddeeeeeeeeffffffff".toBigInteger(
            16
        )
    ),
    IntToken.valueOf(
        "-11111111222222223333333344444444555555556666666677777777888888889999999900000000aaaaaaaabbbbbbbbccccccccddddddddeeeeeeeeffffffff".toBigInteger(
            16
        )
    ),

    IntToken.valueOf(123.toBigInteger()),
    IntToken.valueOf(1223334444.toBigInteger()),
    IntToken.valueOf(1234.toBigInteger()),
    IntToken.valueOf(122333.toBigInteger()),
    IntToken.valueOf((-122333).toBigInteger()),
    IntToken.valueOf(0xabbccc.toBigInteger()),
    IntToken.valueOf(0xabbccc.toBigInteger()),
    IntToken.valueOf((-0xabbccc).toBigInteger()),
    IntToken.valueOf(0b011000.toBigInteger()),
    IntToken.valueOf(0b011000.toBigInteger()),
    IntToken.valueOf((-0b011000).toBigInteger()),

    DecimalToken.valueOf("0.0".toBigDecimal()),
    DecimalToken.valueOf("+0.0".toBigDecimal()),
    DecimalToken.valueOf("-0.0".toBigDecimal()),
    DecimalToken.valueOf("1.234".toBigDecimal()),
    DecimalToken.valueOf("1.234e0".toBigDecimal()),
    DecimalToken.valueOf("1.234e10".toBigDecimal()),
    DecimalToken.valueOf("1.234e-10".toBigDecimal()),
    DecimalToken.valueOf("1.234e10".toBigDecimal()),
    DecimalToken.valueOf("-1.234e-10".toBigDecimal()),
    DecimalToken.valueOf("1.234567890987654321e100".toBigDecimal()),
    DecimalToken.valueOf("1.223334444555556666667777777888888889999999990e1234567890".toBigDecimal()),
    DecimalToken.valueOf("122333.455666e-2".toBigDecimal()),

    AlphaStringToken("a"),
    AlphaStringToken("Abc"),
    AlphaStringToken("A_BB__CCC"),
    AlphaStringToken("A1B2C3"),

    SymbolStringToken.BackQuote,
    SymbolStringToken.Tilde,
    SymbolStringToken.Exclamation,
    SymbolStringToken.At,
    SymbolStringToken.Sharp,
    SymbolStringToken.Dollar,
    SymbolStringToken.Percent,
    SymbolStringToken.Hat,
    SymbolStringToken.Ampersand,
    SymbolStringToken.Star,
    SymbolStringToken.LCircle,
    SymbolStringToken.RCircle,
    SymbolStringToken.Underscore,
    SymbolStringToken.Equal,
    SymbolStringToken.LSquare,
    SymbolStringToken.RSquare,
    SymbolStringToken.LCurly,
    SymbolStringToken.RCurly,
    SymbolStringToken.Semicolon,
    SymbolStringToken.Colon,
    SymbolStringToken.SingleQuote,
    SymbolStringToken.Comma,
    SymbolStringToken.Period,
    SymbolStringToken.LAngle,
    SymbolStringToken.RAngle,
    SymbolStringToken.QuestionMark,

    FullStringToken(""),
    FullStringToken("Hello world!"),
    FullStringToken("`~!@#\$%^&*()-_=+[]{};:'\",.<>?\\|/"),
    FullStringToken(" \n\r\t\uD83D\uDF01\uD83D\uDF01"),
    FullStringToken("\\s\\n\\r\\t\\uD83D\\uDF01"),
    FullStringToken("\\ \\\n\\\r\\\t\\\uD83D\\\uDF01"),
    FullStringToken("multiple lines"),
    FullStringToken("leading\nspaces"),
)