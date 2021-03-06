package org.helixd2s.yavulkanmod.mixin.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.VertexFormat;
import org.helixd2s.yavulkanmod.GlContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.EXTMemoryObject.glCreateMemoryObjectsEXT;
import static org.lwjgl.opengl.GL15.*;

@Mixin(BufferRenderer.class)
public abstract class MBufferRenderer {

    //
    @Redirect(method = "draw(Ljava/nio/ByteBuffer;Lnet/minecraft/client/render/VertexFormat$DrawMode;Lnet/minecraft/client/render/VertexFormat;ILnet/minecraft/client/render/VertexFormat$IntType;IZ)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glBufferData(ILjava/nio/ByteBuffer;I)V"))
    private static void draw_glBufferData(int target, ByteBuffer data, int usage) {
        long defaultSize = Math.max((target == GL_ARRAY_BUFFER ? 1024 * 1024 : 65536), data.limit());
        GlStateManager._glBufferData(target, data, usage);
        if (GlContext.worldRendering) {
            GlContext.glCopyBufferToCache(target, data, usage);
        }
    }

    @Redirect(method = "draw(Ljava/nio/ByteBuffer;Lnet/minecraft/client/render/VertexFormat$DrawMode;Lnet/minecraft/client/render/VertexFormat;ILnet/minecraft/client/render/VertexFormat$IntType;IZ)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_drawElements(IIIJ)V"))
    private static void draw_glDrawElements(int mode, int count, int type, long indices) {
        GlStateManager._drawElements(mode, count, type, indices);
        if (GlContext.worldRendering && mode == GL_TRIANGLES) {
            GlContext.inclusion();
        }
    }

    @Redirect(method = "draw(Ljava/nio/ByteBuffer;Lnet/minecraft/client/render/VertexFormat$DrawMode;Lnet/minecraft/client/render/VertexFormat;ILnet/minecraft/client/render/VertexFormat$IntType;IZ)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glBindBuffer(II)V"))
    private static void draw_glBindBuffer(int target, int buffer) {
        GlStateManager._glBindBuffer(target, buffer);
        if (GlContext.worldRendering) {
            if (target == 34963) {
                GlContext.hasIndexBuffer = true;
            }
        }
    }

    @Inject(method = "bind(Lnet/minecraft/client/render/VertexFormat;)V", at = @At("TAIL"))
    private static void onBind(VertexFormat vertexFormat, CallbackInfo ci) {
        if (GlContext.worldRendering) {
            GlContext.currentVertexFormat = vertexFormat;
            GlContext.currentVertexOffset = GlContext.entityVertex.offset;
            GlContext.currentIndexOffset = GlContext.entityIndex.offset;
            GlContext.currentGlVertexBuffer = GlContext.entityVertex.glCache;
            GlContext.currentGlIndexBuffer = GlContext.entityIndex.glCache;
        }
    }

    @Inject(method = "draw(Ljava/nio/ByteBuffer;Lnet/minecraft/client/render/VertexFormat$DrawMode;Lnet/minecraft/client/render/VertexFormat;ILnet/minecraft/client/render/VertexFormat$IntType;IZ)V", at = @At("HEAD"))
    private static void onBeginDraw(ByteBuffer buffer, VertexFormat.DrawMode drawMode, VertexFormat vertexFormat, int count, VertexFormat.IntType elementFormat, int vertexCount, boolean textured, CallbackInfo ci) {
        GlContext.hasIndexBuffer = false;
    }

}
