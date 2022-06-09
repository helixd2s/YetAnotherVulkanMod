package org.helixd2s.yavulkanmod.mixin.render;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.helixd2s.yavulkanmod.GlContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MGameRenderer {

    @Inject(method="render(FJZ)V", at=@At("HEAD"))
    public void renderBegin(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        GlContext.entityVertex.offset = 0;
        GlContext.entityIndex.offset = 0;
        GlContext.entityVertex.size = 0;
        GlContext.entityIndex.size = 0;
    };

}
