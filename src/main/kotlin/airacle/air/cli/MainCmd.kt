package airacle.air.cli

import airacle.air.util.log.Logger
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Level

object MainCmd {
    fun main(args: Array<String>) {
        init()
        Logger.i("Hello Air!")
        AirRepl().start()
    }

    private fun init() {
        initLogger()
    }

    private fun initLogger() {
        Logger.setLevel(Level.ALL)
        val timeFormatter = SimpleDateFormat("yyyy-MM-dd")
        Logger.init("./Logs/${timeFormatter.format(Date())}.log")
    }
}
