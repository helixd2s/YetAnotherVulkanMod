package org.helixd2s.yavulkanmod.mixin.blaze3d;

import com.mojang.blaze3d.systems.RenderSystem;
import org.helixd2s.yavulkanmod.GlContext;
import org.helixd2s.yavulkanmod.ResourceBuffer;
import org.helixd2s.yavulkanmod.ResourceCache;
import org.helixd2s.yavulkanmod.ResourceImage;
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
        GlContext.contextObj = Alter.initialize(new CreateInfo.ContextCreateInfo());
        GlContext.instanceObj = InstanceObj.make(new CreateInfo.InstanceCreateInfo());
        GlContext.deviceObj = DeviceObj.make(GlContext.instanceObj.getHandle(), new CreateInfo.DeviceCreateInfo());
        GlContext.memoryAllocatorObj = MemoryAllocatorVma.make(GlContext.deviceObj.getHandle(), new CreateInfo.MemoryAllocatorCreateInfo());
        GlContext.pipelineLayoutObj = PipelineLayoutObj.make(GlContext.deviceObj.getHandle(), new CreateInfo.PipelineLayoutCreateInfo());
        GlContext.uploaderObj = UploaderObj.make(GlContext.deviceObj.getHandle(), (new CreateInfo.UploaderCreateInfo().use(3)));

        //
        GlContext.entityVertex = new ResourceCache(GL_ARRAY_BUFFER, 1024 * 1024 * 4);
        GlContext.entityIndex = new ResourceCache(GL_ELEMENT_ARRAY_BUFFER, 65536 * 4);


        // in finale...
        // TODO: create geometry level for entities (blank)

        // in finale...
        // TODO: create instance level for everything (blank)
    }

    @Inject(method="deleteTexture(I)V", at=@At("HEAD"))
    private static void mDeleteTexture(int texture, CallbackInfo ci) {
        ResourceImage resource = GlContext.resourceImageMap.get(texture);
        ResourceObj resourceObj = null;
        if (resource != null) {
            resourceObj = resource.obj;
            // TODO: deallocation of Vulkan Objects

            GlContext.resourceImageMap.remove(texture);
        }
    };

    @Inject(method="glDeleteBuffers(I)V", at=@At("HEAD"))
    private static void mDeleteBuffer(int buffer, CallbackInfo ci) {
        ResourceBuffer resource = GlContext.resourceBufferMap.get(buffer);
        ResourceObj resourceObj = null;
        if (resource != null) {
            resourceObj = resource.obj;
            // TODO: deallocation of Vulkan Objects

            GlContext.resourceBufferMap.remove(buffer);
        }
    };
}
