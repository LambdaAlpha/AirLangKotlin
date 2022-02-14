package airacle.air.util.proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy


/**
 * proxy interfaces only
 */
object Proxy {
    fun <T> proxy(cls: Class<T>, handler: InvocationHandler): T {
        return Proxy.newProxyInstance(
            cls.classLoader, arrayOf<Class<*>>(cls), handler
        ) as T
    }
}