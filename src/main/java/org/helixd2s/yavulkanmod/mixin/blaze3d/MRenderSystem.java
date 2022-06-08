package org.helixd2s.yavulkanmod.mixin.blaze3d;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.math.Matrix4f;
import org.helixd2s.yavulkanmod.Context;
import org.helixd2s.yavulkanmod.alter.Alter;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;
import org.helixd2s.yavulkanmod.alter.objects.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
    }
}