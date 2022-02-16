package airacle.air.app.repl

import airacle.air.core.interpreter.AirValue
import airacle.air.core.interpreter.StringValue
import airacle.air.core.interpreter.TupleValue
import airacle.air.core.lexer.AirLexerError
import airacle.air.core.parser.AirParserConfig
import airacle.air.core.parser.AirParserError
import airacle.air.more.repl.AirRepl
import airacle.air.more.repl.AirReplInterpreterConfig
import airacle.air.more.repl.AirReplVersion
import airacle.air.util.log.Logger

private typealias C = AirReplInterpreterConfig

class AirReplApp {
    private val air: AirRepl = AirRepl(AirReplVersion.V0)

    fun start(): Int {
        println("Air ${air.version.versionName}(${air.version.versionCode})")

        loop@ while (true) {
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

            val ret = try {
                air.interpreter.interpret(value)
            } catch (t: Throwable) {
                println("something went wrong...")
                Logger.i("throw ${t.javaClass} in repl when interpreting", throwable = t)
                continue
            }

            if (ret !is TupleValue || ret.value.isEmpty()) {
                prettyPrint(ret)
                continue
            }
            val t = ret.value
            val keyword = t[0]
            if (keyword !is StringValue) {
                prettyPrint(ret)
                continue
            }
            when (keyword) {
                C.EXIT, C.QUIT -> break@loop
                else -> prettyPrint(ret)
            }
        }
        return 0
    }

    private fun prettyPrint(value: AirValue) {
        val prettyString = air.interpreter.interpret(
            TupleValue.valueOf(
                StringValue.valueOf(AirParserConfig.PRETTY_PRINT),
                TupleValue.valueOf(
                    StringValue.valueOf(AirParserConfig.VALUE_SYMBOL),
                    value
                )
            )
        )
        if (prettyString is StringValue) {
            println(prettyString.value)
        } else {
            println(value)
        }
    }
}
