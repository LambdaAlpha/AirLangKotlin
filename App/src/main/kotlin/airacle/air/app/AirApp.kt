package airacle.air.app

import airacle.air.app.repl.AirReplApp

object AirApp {
    @JvmStatic
    fun main(args: Array<String>) {
        AirReplApp().start()
    }
}
