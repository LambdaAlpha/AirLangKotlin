package airacle.air.cli

import picocli.CommandLine
import java.util.concurrent.Callable

@CommandLine.Command(
    name = "air",
    description = ["a command line interface of the air language"],
    subcommands = [
        AirSubCmdQuit::class
    ],
    mixinStandardHelpOptions = true,
    versionProvider = AirCmd::class
)
class AirCmd : Callable<Int>, CommandLine.IVersionProvider {
    override fun call(): Int {
        return 0
    }

    override fun getVersion(): Array<String> {
        return MainCmd.version
    }
}

@CommandLine.Command(
    name = "quit",
    aliases = ["exit"],
    description = ["quit the read-eval-print loop"]
)
class AirSubCmdQuit : Callable<Int> {
    override fun call(): Int {
        return MaidSubCmdAirRepl.CODE_QUIT
    }
}