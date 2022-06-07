package org.helixd2s.yavulkanmod.mixin.util

import net.minecraft.util.math.Matrix4f
import org.helixd2s.yavulkanmod.ducks.utils.IEMatrix4f
import org.lwjgl.system.MemoryStack
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Overwrite
import org.spongepowered.asm.mixin.Shadow
import java.nio.FloatBuffer

@Mixin(Matrix4f::class)
class MMatrix4f : IEMatrix4f {
    @Shadow
    var a00 = 0f
    @Shadow var a01 = 1f
    @Shadow var a02 = 0f
    @Shadow var a03 = 0f
    @Shadow var a10 = 0f
    @Shadow var a11 = 1f
    @Shadow var a12 = 0f
    @Shadow var a13 = 0f
    @Shadow var a20 = 0f
    @Shadow var a21 = 0f
    @Shadow var a22 = 1f
    @Shadow var a23 = 0f
    @Shadow var a30 = 0f
    @Shadow var a31 = 0f
    @Shadow var a32 = 0f
    @Shadow var a33 = 1f

    // TODO: Dynamic Array Size (12x or 16x)
    override fun loadFromArray(arr: FloatArray?) {
        a00 = arr?.get(0) ?: 1.0F
        a01 = arr?.get(1) ?: 0.0F
        a02 = arr?.get(2) ?: 0.0F
        a03 = arr?.get(3) ?: 0.0F
        a10 = arr?.get(4) ?: 0.0F
        a11 = arr?.get(5) ?: 1.0f
        a12 = arr?.get(6) ?: 0.0F
        a13 = arr?.get(7) ?: 0.0F
        a20 = arr?.get(8) ?: 0.0F
        a21 = arr?.get(9) ?: 0.0F
        a22 = arr?.get(10) ?: 1.0F
        a23 = arr?.get(11) ?: 0.0F
        a30 = arr?.get(12) ?: 0.0F
        a31 = arr?.get(13) ?: 0.0F
        a32 = arr?.get(14) ?: 0.0F
        a33 = arr?.get(15) ?: 1.0F
    }

    override fun toArray(): FloatArray {
        return floatArrayOf(
                a00, a01, a02, a03,
                a10, a11, a12, a13,
                a20, a21, a22, a23,
                a30, a31, a32, a33
        )
    }

    @Overwrite
    fun viewboxMatrix(fovy: Double, aspect: Float, zNear: Float, zFar: Float): Matrix4f? {
        val f = (1.0 / Math.tan(fovy * (Math.PI.toFloat() / 180f).toDouble() / 2.0)).toFloat()
        val matrix4f = Matrix4f()
        MemoryStack.stackPush().use { stack ->
            val mat4f = org.joml.Matrix4f()
            val fb: FloatBuffer = stack.mallocFloat(16)
            mat4f.m00(f / aspect)
            mat4f.m11(f)
            mat4f.m22(zFar / (zNear - zFar))
            mat4f.m32(-1.0f)
            mat4f.m23(zFar * zNear / (zNear - zFar))
            mat4f.m33(0.0f)
            mat4f.get(fb)
            matrix4f.readRowMajor(fb)
        }
        return matrix4f
    }

    @Overwrite
    fun projectionMatrix(left: Float, right: Float, bottom: Float, top: Float, nearPlane: Float, farPlane: Float): Matrix4f? {
        val matrix4f = Matrix4f()
        MemoryStack.stackPush().use { stack ->
            val mat4f = org.joml.Matrix4f()
            val fb: FloatBuffer = stack.mallocFloat(16)
            val f = right - left
            val f1 = bottom - top
            val f2 = farPlane - nearPlane
            mat4f.m00(2.0f / f)
            mat4f.m11(2.0f / f1)
            mat4f.m22(-1.0f / f2)
            mat4f.m03(-(right + left) / f)
            mat4f.m13(-(bottom + top) / f1)
            mat4f.m23(-nearPlane / f2)
            mat4f.m33(1.0f)
            mat4f.get(fb)
            matrix4f.readRowMajor(fb)
        }
        return matrix4f
    }

}