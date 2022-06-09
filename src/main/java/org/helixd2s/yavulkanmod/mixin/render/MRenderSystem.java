package org.helixd2s.yavulkanmod.mixin.render;

import com.mojang.blaze3d.systems.RenderSystem;
import org.helixd2s.yavulkanmod.GlContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public class MRenderSystem {

    // UNSAFE!
    //@Inject(method="drawElements(III)V", at=@At("TAIL"))
    //static void drawElements(int mode, int count, int type, CallbackInfo ci) {
        //GlContext.inclusion();
    //};
};
