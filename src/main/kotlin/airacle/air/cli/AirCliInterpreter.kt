package airacle.air.cli

import airacle.air.api.Air
import airacle.air.interpreter.AirValue
import airacle.air.interpreter.StringValue
import airacle.air.interpreter.TupleValue
import airacle.air.parser.AirParserConfig
import airacle.air.parser.IAirParserConfig

class AirCliInterpreter {
    private val air = Air()

    fun interpret(value: AirValue): AirValue {
        if (value !is TupleValue) {
            return value
        }

        if (value.value.isEmpty()) {
            return StringValue("empty command")
        }

        val keyword = value.value[0]
        if (keyword is StringValue) {
            when (keyword.value) {
                CMD_META_INFO -> {
                    val metaInfo = air.metaInfo()
                    return StringValue("air v${metaInfo.versionName}(${metaInfo.versionCode})")
                }
                CMD_VERSION_CODE -> return StringValue(air.metaInfo().versionCode.toString())
                CMD_VERSION_NAME -> return StringValue(air.metaInfo().versionName)
            }
        }
        return StringValue("unknown command")
    }

    companion object {
        const val CMD_META_INFO = "meta_info"
        const val CMD_VERSION_CODE = "version_code"
        const val CMD_VERSION_NAME = "version_name"
    }
}

object AirCliParserConfig : IAirParserConfig {
    override fun paramLength(value: AirValue): Int {
        if (value is StringValue) {
            when (value.value) {
                AirCliInterpreter.CMD_VERSION_CODE -> return 0
            }
        }

        return AirParserConfig.paramLength(value)
    }
}