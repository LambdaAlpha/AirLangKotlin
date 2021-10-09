package airacle.air.cli

import picocli.CommandLine
import java.util.concurrent.Callable

@CommandLine.Command(
    name = "air",
    description = ["a command line interface of the air language"],
    mixinStandardHelpOptions = true,
    versionProvider = MainCli::class,
    subcommands = [

    ]
)
class MainCli : Callable<Int>, CommandLine.IVersionProvider {
    override fun call(): Int {
        return 0
    }

    override fun getVersion(): Array<String> {
        return arrayOf("0.0.0")
    }
}
