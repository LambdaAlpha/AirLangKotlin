import airacle.air.cli.MainCmd
import picocli.CommandLine
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val cmdLine = CommandLine(MainCmd)
    cmdLine.executionStrategy = CommandLine.IExecutionStrategy { parseResult ->
        MainCmd.execute(parseResult)
    }
    val ret = cmdLine.execute(*args)
    exitProcess(ret)
}