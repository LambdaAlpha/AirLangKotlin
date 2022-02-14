package airacle.air.util.log

import java.io.File
import java.util.logging.ConsoleHandler
import java.util.logging.FileHandler
import java.util.logging.Level
import java.util.logging.Logger

object Logger {
    val logger: Logger = Logger.getLogger("air")

    fun init(filePath: String) {
        logger.useParentHandlers = false

        val formatter = LogFormatter()

        val consoleHandler = ConsoleHandler()
        consoleHandler.formatter = formatter
        logger.addHandler(consoleHandler)

        val file = File(filePath)
        file.parentFile?.mkdirs()
        val fileHandler = FileHandler(filePath, true)
        fileHandler.formatter = formatter
        logger.addHandler(fileHandler)
    }

    fun setLevel(level: Level) {
        logger.level = level
    }

    // inline for sourceClassName and sourceMethodName
    inline fun log(level: Level, msg: String, args: Array<Any?>? = null, throwable: Throwable? = null) {
        if (args != null) {
            logger.log(level, msg, args)
        } else {
            logger.log(level, msg, throwable)
        }
    }

    inline fun vvv(msg: String, args: Array<Any?>? = null, throwable: Throwable? = null) {
        log(Level.FINEST, msg, args, throwable)
    }

    inline fun vv(msg: String, args: Array<Any?>? = null, throwable: Throwable? = null) {
        log(Level.FINER, msg, args, throwable)
    }

    inline fun v(msg: String, args: Array<Any?>? = null, throwable: Throwable? = null) {
        log(Level.FINE, msg, args, throwable)
    }

    inline fun d(msg: String, args: Array<Any?>? = null, throwable: Throwable? = null) {
        log(Level.CONFIG, msg, args, throwable)
    }

    inline fun i(msg: String, args: Array<Any?>? = null, throwable: Throwable? = null) {
        log(Level.INFO, msg, args, throwable)
    }

    inline fun w(msg: String, args: Array<Any?>? = null, throwable: Throwable? = null) {
        log(Level.WARNING, msg, args, throwable)
    }

    inline fun e(msg: String, args: Array<Any?>? = null, throwable: Throwable? = null) {
        log(Level.SEVERE, msg, args, throwable)
    }
}