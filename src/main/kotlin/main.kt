import airacle.air.cli.MainCli
import picocli.CommandLine
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    exitProcess(CommandLine(MainCli()).execute(*args))
}