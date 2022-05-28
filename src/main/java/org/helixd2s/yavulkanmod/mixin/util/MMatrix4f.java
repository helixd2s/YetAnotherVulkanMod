package org.helixd2s.yavulkanmod.mixin.util;

import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Matrix4f.class)
public abstract class MMatrix4f {

    /**
     * @author
     */
    /*@Overwrite
    public static Matrix4f viewboxMatrix(double fovy, float aspect, float zNear, float zFar) {
        float f = (float)(1.0D / Math.tan(fovy * (double)((float)Math.PI / 180F) / 2.0D));
        Matrix4f matrix4f = new Matrix4f();

        try(MemoryStack stack = MemoryStack.stackPush()) {
            org.joml.Matrix4f mat4f = new org.joml.Matrix4f();
            FloatBuffer fb = stack.mallocFloat(16);

            mat4f.m00(f / aspect);
            mat4f.m11(f);
            mat4f.m22((zFar) / (zNear - zFar));
            mat4f.m32(-1.0F);
            mat4f.m23(zFar * zNear / (zNear - zFar));
            mat4f.m33(0.0F);
            mat4f.get(fb);
            matrix4f.readRowMajor(fb);
        }

        return matrix4f;
    }*/

    /**
     * @author
     */
    /*@Overwrite
    public static Matrix4f projectionMatrix(float left, float right, float bottom, float top, float nearPlane, float farPlane) {
        Matrix4f matrix4f = new Matrix4f();

        try(MemoryStack stack = MemoryStack.stackPush()) {
            org.joml.Matrix4f mat4f = new org.joml.Matrix4f();
            FloatBuffer fb = stack.mallocFloat(16);

            float f = right - left;
            float f1 = bottom - top;
            float f2 = farPlane - nearPlane;
            mat4f.m00(2.0F / f);
            mat4f.m11(2.0F / f1);
            mat4f.m22(-1.0F / f2);
            mat4f.m03(-(right + left) / f);
            mat4f.m13(-(bottom + top) / f1);
            mat4f.m23(-(nearPlane) / f2);
            mat4f.m33(1.0F);

            mat4f.get(fb);
            matrix4f.readRowMajor(fb);
        }
        return matrix4f;
    }*/
}
