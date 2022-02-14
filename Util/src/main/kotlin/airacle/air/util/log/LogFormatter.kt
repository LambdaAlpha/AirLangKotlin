package airacle.air.util.log

import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Formatter
import java.util.logging.LogRecord

class LogFormatter : Formatter() {
    private val timeFormatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private val date: Date = Date()

    override fun format(record: LogRecord): String {
        val sb = StringBuilder()

        date.time = record.millis
        sb.append(timeFormatter.format(date)).append("|")

        sb.append(record.threadID).append("|")

        sb.append(record.level.name).append("|")

        if (record.sourceClassName != null) {
            sb.append(record.sourceClassName)
            if (record.sourceMethodName != null) {
                sb.append(".").append(record.sourceMethodName)
            }
            sb.append("|")
        }

        sb.append(formatMessage(record)).append("|")

        if (record.parameters != null && record.parameters.isNotEmpty()) {
            sb.append(record.parameters.joinToString(", ")).append("|")
        }

        if (record.thrown != null) {
            sb.append("throw\n")
            val stringWriter = StringWriter()
            val printWriter = PrintWriter(stringWriter)
            record.thrown.printStackTrace(printWriter)
            printWriter.close()
            sb.append(stringWriter.toString())
        }

        sb.append("\n")
        return sb.toString()
    }
}