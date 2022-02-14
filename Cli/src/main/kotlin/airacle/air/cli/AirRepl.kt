package airacle.air.cli

import airacle.air.core.api.Air
import airacle.air.core.api.AirVersion
import airacle.air.core.interpreter.StringValue
import airacle.air.core.interpreter.TupleValue
import airacle.air.core.lexer.AirLexerError
import airacle.air.core.parser.AirParserError
import airacle.air.util.log.Logger

class AirRepl {
    private val air: Air = Air(AirVersion.V0)

    fun start(): Int {
        println("Air ${air.version.versionName}(${air.version.versionCode})")

        while (true) {
            print("> ")
            val s = readLine() ?: break

            val tokens = try {
                air.lexer.lex(s)
            } catch (t: AirLexerError) {
                println("syntax error: ${t.message}")
                continue
            } catch (t: Throwable) {
                println("something went wrong...")
                Logger.i("throw ${t.javaClass} in repl when lexing", throwable = t)
                continue
            }

            val value = try {
                air.parser.parse(tokens)
            } catch (t: AirParserError) {
                println("syntax error: ${t.message}")
                continue
            } catch (t: Throwable) {
                println("something went wrong...")
                Logger.i("throw ${t.javaClass} in repl when parsing", throwable = t)
                continue
            }

            if (value is TupleValue && value.value.isNotEmpty()) {
                val first = value.value[0]
                if (first is StringValue) {
                    when (first.value) {
                        "exit", "quit" -> {
                            Logger.i("exit repl")
                            break
                        }
                    }
                }
            }

            val ret = try {
                air.interpreter.interpret(value)
            } catch (t: Throwable) {
                println("something went wrong...")
                Logger.i("throw ${t.javaClass} in repl when interpreting", throwable = t)
                continue
            }
            println(ret)
        }
        return 0
    }
}
