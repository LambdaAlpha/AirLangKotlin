package airacle.air.app

import airacle.air.app.repl.AirRepl

object AirApp {
    @JvmStatic
    fun main(args: Array<String>) {
        AirRepl().start()
    }
}
