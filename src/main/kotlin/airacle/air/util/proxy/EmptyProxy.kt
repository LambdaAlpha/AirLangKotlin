package airacle.air.util.proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

object EmptyProxy : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any?>?): Any? {
        method ?: return null
        val retCls: Class<*> = method.returnType
        if (!retCls.isPrimitive) {
            return null
        }
        return when (retCls) {
            Boolean::class.javaPrimitiveType, Boolean::class.javaObjectType -> false
            Char::class.javaPrimitiveType, Char::class.javaObjectType -> ' '
            Byte::class.javaPrimitiveType, Byte::class.javaObjectType -> 0.toByte()
            Short::class.javaPrimitiveType, Short::class.javaObjectType -> 0.toShort()
            Int::class.javaPrimitiveType, Int::class.javaObjectType -> 0
            Long::class.javaPrimitiveType, Long::class.javaObjectType -> 0L
            Float::class.javaPrimitiveType, Long::class.javaObjectType -> 0f
            Double::class.javaPrimitiveType, Long::class.javaObjectType -> 0.0
            Void::class.javaPrimitiveType, Void::class.javaObjectType -> null
            else -> null
        }
    }
}