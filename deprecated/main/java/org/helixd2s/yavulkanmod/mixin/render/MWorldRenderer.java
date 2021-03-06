package org.helixd2s.yavulkanmod.mixin.render;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.helixd2s.yavulkanmod.GlContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class MWorldRenderer {

    @Inject(method="render(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Matrix4f;)V", at=@At("HEAD"))
    public void renderBegin(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
        GlContext.entityVertex.offset = 0;
        GlContext.entityIndex.offset = 0;
        GlContext.entityVertex.size = 0;
        GlContext.entityIndex.size = 0;
        GlContext.worldRendering = true;
        GlContext.currentVertexFormat = null;
        GlContext.currentVertexOffset = 0;
        GlContext.currentIndexOffset = 0;
        GlContext.currentGlVertexBuffer = 0;
        GlContext.currentGlIndexBuffer = 0;
    };

    @Inject(method="render(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Matrix4f;)V", at=@At("TAIL"))
    public void renderEnd(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
        GlContext.entityVertex.offset = 0;
        GlContext.entityIndex.offset = 0;
        GlContext.entityVertex.size = 0;
        GlContext.entityIndex.size = 0;
        GlContext.worldRendering = false;
        GlContext.currentVertexFormat = null;
        GlContext.currentVertexOffset = 0;
        GlContext.currentIndexOffset = 0;
        GlContext.currentGlVertexBuffer = 0;
        GlContext.currentGlIndexBuffer = 0;
    };
    
}
