package org.helixd2s.yavulkanmod.mixin.util

import net.minecraft.util.math.Matrix3f
import org.helixd2s.yavulkanmod.ducks.utils.IEMatrix3f
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow

//mojang does not provide a method to load numbers into matrix
@Mixin(Matrix3f::class)
abstract class MMatrix3f : IEMatrix3f {
    @Shadow var a00 = 1f
    @Shadow var a01 = 0f
    @Shadow var a02 = 0f
    @Shadow var a10 = 0f
    @Shadow var a11 = 1f
    @Shadow var a12 = 0f
    @Shadow var a20 = 0f
    @Shadow var a21 = 0f
    @Shadow var a22 = 1f

    // TODO: Dynamic Array Size (12x or 16x)
    override fun loadFromArray(arr: FloatArray?) {
        a00 = arr?.get(0) ?: 1.0F
        a01 = arr?.get(1) ?: 0.0F
        a02 = arr?.get(2) ?: 0.0F
        a10 = arr?.get(3) ?: 0.0F
        a11 = arr?.get(4) ?: 1.0f
        a12 = arr?.get(5) ?: 0.0F
        a20 = arr?.get(6) ?: 0.0F
        a21 = arr?.get(7) ?: 0.0F
        a22 = arr?.get(8) ?: 1.0F
    }

    override fun toArray(): FloatArray {
        return floatArrayOf(
            a00, a01, a02,
            a10, a11, a12,
            a20, a21, a22
        )
    }
}
