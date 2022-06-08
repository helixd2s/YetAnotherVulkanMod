package org.helixd2s.yavulkanmod.mixin.blaze3d;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.math.Matrix4f;
import org.helixd2s.yavulkanmod.Context;
import org.helixd2s.yavulkanmod.ResourceBuffer;
import org.helixd2s.yavulkanmod.ResourceImage;
import org.helixd2s.yavulkanmod.alter.Alter;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;
import org.helixd2s.yavulkanmod.alter.objects.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.helixd2s.yavulkanmod.GlOverride.glCreateBuffer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;

@Mixin(RenderSystem.class)
public abstract class MRenderSystem {
    @Inject(method = "initRenderer(IZ)V", at=@At("TAIL"))
    private static void mInitRenderer(int debugVerbosity, boolean debugSync, CallbackInfo ci) {
        Context.contextObj = Alter.initialize(new CreateInfo.ContextCreateInfo());
        Context.instanceObj = InstanceObj.make(new CreateInfo.InstanceCreateInfo());
        Context.deviceObj = DeviceObj.make(Context.instanceObj.getHandle(), new CreateInfo.DeviceCreateInfo());
        Context.memoryAllocatorObj = MemoryAllocatorVma.make(Context.deviceObj.getHandle(), new CreateInfo.MemoryAllocatorCreateInfo());
        Context.pipelineLayoutObj = PipelineLayoutObj.make(Context.deviceObj.getHandle(), new CreateInfo.PipelineLayoutCreateInfo());
        Context.uploaderObj = UploaderObj.make(Context.deviceObj.getHandle(), (new CreateInfo.UploaderCreateInfo().use(3)));

        //
        Context.glEntityVertexCache = glCreateBuffer(GL_ARRAY_BUFFER, 1024 * 1024 * 4);
        Context.glEntityIndexCache = glCreateBuffer(GL_ELEMENT_ARRAY_BUFFER, 65536 * 4);

        //
        Context.entityVertexCache = Context.resourceBufferMap.get(Context.glEntityVertexCache).obj;
        Context.entityIndexCache = Context.resourceBufferMap.get(Context.glEntityIndexCache).obj;

        //
        Context.entityVertexOffset = 0;
        Context.entityIndexOffset = 0;

        // in finale...
        // TODO: create geometry level for entities (blank)

        // in finale...
        // TODO: create instance level for everything (blank)
    }

    @Inject(method="deleteTexture(I)V", at=@At("HEAD"))
    private static void mDeleteTexture(int texture, CallbackInfo ci) {
        ResourceImage resource = Context.resourceImageMap.get(texture);
        ResourceObj resourceObj = null;
        if (resource != null) {
            resourceObj = resource.obj;
            // TODO: deallocation of Vulkan Objects

            Context.resourceImageMap.remove(texture);
        }
    };

    @Inject(method="glDeleteBuffers(I)V", at=@At("HEAD"))
    private static void mDeleteBuffer(int buffer, CallbackInfo ci) {
        ResourceBuffer resource = Context.resourceBufferMap.get(buffer);
        ResourceObj resourceObj = null;
        if (resource != null) {
            resourceObj = resource.obj;
            // TODO: deallocation of Vulkan Objects

            Context.resourceBufferMap.remove(buffer);
        }
    };
}
