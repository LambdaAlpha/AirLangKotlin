package airacle.air.util.proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method


class SingleProxy<T>(private val t: T) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any?>?): Any? {
        method ?: return EmptyProxy.invoke(proxy, method, args)
        return if (args == null) {
            method.invoke(t)
        } else {
            method.invoke(t, *args)
        }
    }
}