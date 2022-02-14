package airacle.air.util.proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.util.concurrent.CopyOnWriteArrayList

class DispatchProxy<T> : InvocationHandler {
    private val subscribers: CopyOnWriteArrayList<T> = CopyOnWriteArrayList()

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any?>?): Any? {
        method ?: return EmptyProxy.invoke(proxy, method, args)

        var ret: Any? = null
        for (t: T in subscribers) {
            try {
                ret = if (args == null) {
                    method.invoke(t)
                } else {
                    method.invoke(t, *args)
                }
            } catch (e: Throwable) {

            }
        }
        return ret ?: EmptyProxy.invoke(proxy, method, args)
    }

    fun add(t: T) {
        if (!subscribers.contains(t)) {
            subscribers.add(t)
        }
    }

    fun remove(t: T) {
        subscribers.remove(t)
    }

    fun clear() {
        subscribers.clear()
    }
}