package airacle.air.util.log

interface ILogger {
    // level range [0, 7]
    fun log(level: Int, msg: String, any: Any? = null)
}