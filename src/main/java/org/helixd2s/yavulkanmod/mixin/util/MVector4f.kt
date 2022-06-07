package org.helixd2s.yavulkanmod.mixin.util

import net.minecraft.util.math.Matrix4f
import net.minecraft.util.math.Vector4f
import org.helixd2s.yavulkanmod.ReflectionUtil
import org.helixd2s.yavulkanmod.ducks.utils.IEVector4f
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow

@Mixin(Vector4f::class)
abstract class MVector4f : IEVector4f {
    @Shadow var x = 0f
    @Shadow var y = 0f
    @Shadow var z = 0f
    @Shadow var w = 0f

    @Override
    @Throws(NoSuchFieldException::class, IllegalAccessException::class)
    override fun transform(matrix: Matrix4f) {
        val f = x
        val g = y
        val h = z
        val i = w
        x = ReflectionUtil.getFieldFValue(matrix, "a00") * f + ReflectionUtil.getFieldFValue(matrix, "a01") * g + ReflectionUtil.getFieldFValue(matrix, "a02") * h + ReflectionUtil.getFieldFValue(matrix, "a03") * i
        y = ReflectionUtil.getFieldFValue(matrix, "a10") * f + ReflectionUtil.getFieldFValue(matrix, "a11") * g + ReflectionUtil.getFieldFValue(matrix, "a12") * h + ReflectionUtil.getFieldFValue(matrix, "a13") * i
        z = ReflectionUtil.getFieldFValue(matrix, "a20") * f + ReflectionUtil.getFieldFValue(matrix, "a21") * g + ReflectionUtil.getFieldFValue(matrix, "a22") * h + ReflectionUtil.getFieldFValue(matrix, "a23") * i
        w = ReflectionUtil.getFieldFValue(matrix, "a30") * f + ReflectionUtil.getFieldFValue(matrix, "a31") * g + ReflectionUtil.getFieldFValue(matrix, "a32") * h + ReflectionUtil.getFieldFValue(matrix, "a33") * i
    }

    override fun loadFromArray(arr: FloatArray?) {
        x = arr?.get(0) ?: 0.0F
        y = arr?.get(1) ?: 0.0F
        z = arr?.get(2) ?: 0.0F
        w = arr?.get(3) ?: 1.0F
    }

    override fun toArray(): FloatArray {
        return floatArrayOf(x, y, z, w)
    }
}