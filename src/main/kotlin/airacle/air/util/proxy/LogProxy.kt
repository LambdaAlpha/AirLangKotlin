package airacle.air.util.proxy

import airacle.air.util.log.Logger
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.util.logging.Level

class LogProxy(
    private val handler: InvocationHandler,
    private val enableStaticLog: Boolean = true,
    private val staticLogLevel: Level = Level.INFO,
    private val enableDynamicLog: Boolean = true,
    private val dynamicLogLevel: Level = Level.CONFIG
) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any?>?): Any? {
        method ?: return EmptyProxy.invoke(proxy, method, args)

        val tag: String = method.declaringClass?.simpleName ?: "UnknownClass"
        if (enableStaticLog) {
            Logger.log(staticLogLevel, "[$tag] method=$method", null)
        }

        if (enableStaticLog) {
            val argsCls = args?.joinToString {
                it?.javaClass?.simpleName ?: "Null"
            } ?: "Null"
            Logger.log(staticLogLevel, "[$tag] start ${method.name} cls=$argsCls", null)
        }
        if (enableDynamicLog) {
            val argsValue = args?.joinToString(", ") ?: "null"
            Logger.log(dynamicLogLevel, "[$tag] start ${method.name} args=$argsValue", null)
        }

        val ret = handler.invoke(proxy, method, args)

        if (enableStaticLog) {
            val retCls = ret?.javaClass?.simpleName ?: "Null"
            Logger.log(staticLogLevel, "[$tag] end ${method.name} cls=$retCls", null)
        }
        if (enableDynamicLog) {
            Logger.log(dynamicLogLevel, "[$tag] end ${method.name} ret=$ret", null)
        }

        return ret
    }
}