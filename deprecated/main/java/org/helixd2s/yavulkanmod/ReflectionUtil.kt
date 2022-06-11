package org.helixd2s.yavulkanmod

import net.minecraft.client.render.RenderLayer

import java.lang.reflect.Field
import java.lang.reflect.Modifier

object ReflectionUtil {
    @Throws(NoSuchFieldException::class)
    fun getField(clazz: Class<*>, fieldName: String?): Field {
        return try {
            clazz.getDeclaredField(fieldName)
        } catch (e: NoSuchFieldException) {
            val superClass = clazz.superclass
            superClass?.let { getField(it, fieldName) } ?: throw e
        }
    }

    fun makeAccessible(field: Field) {
        if (!Modifier.isPublic(field.modifiers) || !Modifier.isPublic(field.declaringClass.modifiers)) {
            field.isAccessible = true
        }
    }

    @Throws(NoSuchFieldException::class, IllegalAccessException::class)
    fun getFieldFValue(obj: Any, name: String?): Float {
        val field = obj?.javaClass?.let { getField(it, name) }
        if (field != null) { makeAccessible(field) }
        if (field != null) { return field.getFloat(obj) }
        return 0.0F
    }

    @Throws(NoSuchFieldException::class, IllegalAccessException::class)
    fun <T> getFieldValue(obj: Any, name: String?): T? {
        val field = obj?.javaClass?.let { getField(it, name) }
        if (field != null) { makeAccessible(field) }
        if (field != null) { return field.get(obj) as T }
        return null
    }
}