package airacle.air.util.proxy

import airacle.air.util.log.ILogger
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class LogProxy(
    private val log: ILogger,
    private val handler: InvocationHandler,
    private val enableStaticLog: Boolean = true,
    private val staticLogLevel: Int = 5,
    private val enableDynamicLog: Boolean = true,
    private val dynamicLogLevel: Int = 3
) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any?>?): Any? {
        method ?: return EmptyProxy.invoke(proxy, method, args)

        val tag: String = method.declaringClass?.simpleName ?: "UnknownClass"
        if (enableStaticLog) {
            log.log(staticLogLevel, "[$tag] method=$method")
        }

        if (enableStaticLog) {
            val argsCls = args?.joinToString {
                it?.javaClass?.simpleName ?: "Null"
            } ?: "Null"
            log.log(staticLogLevel, "[$tag] start ${method.name} cls=$argsCls")
        }
        if (enableDynamicLog) {
            log.log(dynamicLogLevel, "[$tag] start ${method.name} args=$args")
        }

        val ret = handler.invoke(proxy, method, args)

        if (enableStaticLog) {
            val retCls = ret?.javaClass?.simpleName ?: "Null"
            log.log(staticLogLevel, "[$tag] end ${method.name} cls=$retCls")
        }
        if (enableDynamicLog) {
            log.log(dynamicLogLevel, "[$tag] end ${method.name} ret=$ret")
        }

        return ret
    }
}