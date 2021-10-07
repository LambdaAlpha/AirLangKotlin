package airacle.air.util.proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class CatchProxy(private val handler: InvocationHandler) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any?>?): Any? {
        return try {
            handler.invoke(proxy, method, args);
        } catch (e: Throwable) {
            EmptyProxy.invoke(proxy, method, args)
        }
    }
}