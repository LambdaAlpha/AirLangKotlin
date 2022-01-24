package airacle.air.cli

import airacle.air.interpreter.AirValue
import airacle.air.interpreter.StringValue
import airacle.air.interpreter.TupleValue
import airacle.air.lexer.AirLexer
import airacle.air.lexer.AirLexerConfig
import airacle.air.parser.AirParser
import airacle.air.util.log.Logger

class AirRepl {
    private val lexer = AirLexer(AirLexerConfig)
    private val parser = AirParser(AirCliParserConfig)
    private val interpreter = AirCliInterpreter()

    fun call(): Int {
        val metaInfoCmd = TupleValue(arrayOf(StringValue(AirCliInterpreter.CMD_META_INFO)))
        println(interpreter.interpret(metaInfoCmd))

        while (true) {
            print("> ")

            try {
                val s = readLine() ?: break
                if (s == "exit" || s == "quit") {
                    Logger.i("exit repl")
                    break
                }
                val tokens = lexer.lex(s)
                val value = parser.parse(tokens)
                val ret = interpreter.interpret(value)
                println(ret)
            } catch (t: Throwable) {
                println("throw $t")
                Logger.i("throw $t in repl")
                break
            }
        }
        return 0
    }

    private fun println(value: AirValue) {
        println(if (value is StringValue) value.value else value)
    }
}
