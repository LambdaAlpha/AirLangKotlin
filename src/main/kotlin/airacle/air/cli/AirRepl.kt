package airacle.air.cli

import airacle.air.api.Air
import airacle.air.api.AirVersion
import airacle.air.interpreter.StringValue
import airacle.air.interpreter.TupleValue
import airacle.air.util.log.Logger

class AirRepl {
    private val air: Air = Air(AirVersion.V0)

    fun call(): Int {
        println("Air ${air.version.versionName}(${air.version.versionCode})")

        while (true) {
            print("> ")

            try {
                val s = readLine() ?: break
                val tokens = air.lexer.lex(s)
                val value = air.parser.parse(tokens)
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
                val ret = air.interpreter.interpret(value)
                println(ret)
            } catch (t: Throwable) {
                t.printStackTrace()
                Logger.i("throw ${t.javaClass} in repl", throwable = t)
            }
        }
        return 0
    }
}
