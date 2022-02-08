package airacle.air.util

import airacle.air.api.IAirInterpreter
import airacle.air.api.IAirLexer
import airacle.air.api.IAirParser
import airacle.air.interpreter.AirValue
import airacle.air.lexer.AirToken
import java.io.FileReader

open class SourceCodeReader {
    fun readString(path: String): String {
        val file = javaClass.getResource(path)!!.file
        val reader = FileReader(file)
        val content = reader.readText()
        reader.close()
        return content
    }
}

open class LexerReader(private val lexer: IAirLexer<AirToken>) : SourceCodeReader() {
    fun readAndLex(path: String): List<AirToken> {
        return lexer.lex(readString(path))
    }

    fun lex(source: String): List<AirToken> {
        return lexer.lex(source)
    }
}

open class ParserReader(
    lexer: IAirLexer<AirToken>,
    private val parser: IAirParser<AirToken, AirValue>
) : LexerReader(lexer) {
    fun readAndParse(path: String): AirValue {
        return parser.parse(readAndLex(path))
    }

    fun parse(source: String): AirValue {
        return parser.parse(lex(source))
    }
}

class InterpreterReader(
    lexer: IAirLexer<AirToken>,
    parser: IAirParser<AirToken, AirValue>,
    private val interpreter: IAirInterpreter<AirValue>
) : ParserReader(lexer, parser) {
    fun readAndInterpret(path: String): AirValue {
        return interpreter.interpret(readAndParse(path))
    }

    fun interpret(source: String): AirValue {
        return interpreter.interpret(parse(source))
    }
}