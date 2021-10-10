package airacle.air.cli

import airacle.air.api.Air
import airacle.air.api.IAir
import airacle.air.util.log.Logger
import edu.rice.cs.util.ArgumentTokenizer
import picocli.CommandLine
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Callable
import java.util.logging.Level

@CommandLine.Command(
    name = "air",
    description = ["a command line interface of the air language"],
    mixinStandardHelpOptions = true,
    versionProvider = MainCmd::class,
    // TODO: 10/10/21 optionally include some other subcommands of air command
    subcommands = [
        MaidSubCmdAirRepl::class
    ]
)
object MainCmd : Callable<Int>, CommandLine.IVersionProvider, CommandLine.IExecutionStrategy {
    @CommandLine.Spec
    lateinit var commandSpec: CommandLine.Model.CommandSpec

    val air: IAir = Air()
    val airCmd: AirCmd = AirCmd()

    override fun call(): Int {
        Logger.i("Hello Air!")
        return MaidSubCmdAirRepl().call()
    }

    override fun getVersion(): Array<String> {
        return arrayOf("air ${air.metaInfo().versionName()}(${air.metaInfo().versionCode()})")
    }

    private fun init() {
        initLogger()
    }

    private fun initLogger() {
        Logger.setLevel(Level.ALL)
        val timeFormatter = SimpleDateFormat("yyyy-MM-dd")
        Logger.init("./Logs/${timeFormatter.format(Date())}.log")
    }

    override fun execute(parseResult: CommandLine.ParseResult?): Int {
        init()
        return CommandLine.RunLast().execute(parseResult)
    }
}

@CommandLine.Command(
    name = "repl",
    description = ["read-eval-print loop of the air language"],
    mixinStandardHelpOptions = true,
)
class MaidSubCmdAirRepl : Callable<Int> {
    override fun call(): Int {
        val cmdLine = CommandLine(MainCmd.airCmd)
        cmdLine.printVersionHelp(System.out)

        while (true) {
            print("> ")

            try {
                val s = readLine() ?: break
                val args = ArgumentTokenizer.tokenize(s)
                val ret = cmdLine.execute(*args.toTypedArray())
                if (ret == CODE_QUIT) {
                    break
                }
            } catch (t: Throwable) {
                println("throw $t")
                break
            }
        }
        return 0
    }

    companion object {
        const val CODE_QUIT: Int = 11
    }
}
