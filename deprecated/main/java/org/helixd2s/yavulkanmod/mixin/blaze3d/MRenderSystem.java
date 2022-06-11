package org.helixd2s.yavulkanmod.mixin.blaze3d;

import com.mojang.blaze3d.systems.RenderSystem;
import org.helixd2s.yavulkanmod.GlContext;
import org.helixd2s.yavulkanmod.alter.Alter;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;
import org.helixd2s.yavulkanmod.alter.objects.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;

@Mixin(RenderSystem.class)
public abstract class MRenderSystem {
    @Inject(method = "initRenderer(IZ)V", at=@At("TAIL"))
    private static void mInitRenderer(int debugVerbosity, boolean debugSync, CallbackInfo ci) {
        GlContext.initialize();
    }

    @Inject(method="deleteTexture(I)V", at=@At("HEAD"))
    private static void mDeleteTexture(int texture, CallbackInfo ci) {
        GlContext.ResourceImage resource = GlContext.resourceImageMap.get(texture);
        ResourceObj resourceObj = null;
        if (resource != null) {
            resourceObj = resource.obj;
            // TODO: deallocation of Vulkan Objects

            GlContext.resourceImageMap.remove(texture);
        }
    };

    @Inject(method="glDeleteBuffers(I)V", at=@At("HEAD"))
    private static void mDeleteBuffer(int buffer, CallbackInfo ci) {
        GlContext.ResourceBuffer resource = GlContext.resourceBufferMap.get(buffer);
        ResourceObj resourceObj = null;
        if (resource != null) {
            resourceObj = resource.obj;
            // TODO: deallocation of Vulkan Objects

            GlContext.resourceBufferMap.remove(buffer);
        }
    };
}
