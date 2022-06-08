package org.helixd2s.yavulkanmod.mixin.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.VertexFormat;
import org.helixd2s.yavulkanmod.Context;
import org.helixd2s.yavulkanmod.GlOverride;
import org.helixd2s.yavulkanmod.ResourceImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.ByteBuffer;

import static com.mojang.blaze3d.systems.RenderSystem.assertOnRenderThreadOrInit;
import static org.lwjgl.opengl.EXTMemoryObject.glCreateMemoryObjectsEXT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glGetIntegeri;
import static org.lwjgl.opengl.GL31.GL_COPY_WRITE_BUFFER;
import static org.lwjgl.opengl.GL31.glCopyBufferSubData;
import static org.lwjgl.opengl.GL45.glCopyNamedBufferSubData;

@Mixin(BufferRenderer.class)
public abstract class MBufferRenderer {

    //
    @Unique
    private static long lastVertexOffset = 0;
    @Unique
    private static long lastIndexOffset = 0;

    //
    @Unique static boolean hasIndexBuffer = false;
    @Unique static VertexFormat currentVertexFormat = null;

    //
    @Redirect(method = "draw(Ljava/nio/ByteBuffer;Lnet/minecraft/client/render/VertexFormat$DrawMode;Lnet/minecraft/client/render/VertexFormat;ILnet/minecraft/client/render/VertexFormat$IntType;IZ)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glBufferData(ILjava/nio/ByteBuffer;I)V"))
    private static void draw_glBufferData(int target, ByteBuffer data, int usage) {
        //assertOnRenderThreadOrInit();
        long defaultSize = Math.max((target == GL_ARRAY_BUFFER ? 1024 * 1024 : 65536), data.limit());
        //GlOverride.glBufferData(defaultSize, target, data, usage);
        GlStateManager._glBufferData(target, data, usage);
        GlOverride.glCopyBufferToCache(target, data, usage);
    };

    //
    @Redirect(method = "postDraw(Lnet/minecraft/client/render/BufferBuilder;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_drawElements(IIIJ)V"))
    private static void postDraw_glDrawElements(int mode, int count, int type, long indices) {
        GlStateManager._drawElements(mode, count, type, indices);

        // TODO:
        inclusion();
    };

    @Redirect(method = "draw(Ljava/nio/ByteBuffer;Lnet/minecraft/client/render/VertexFormat$DrawMode;Lnet/minecraft/client/render/VertexFormat;ILnet/minecraft/client/render/VertexFormat$IntType;IZ)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_drawElements(IIIJ)V"))
    private static void draw_glDrawElements(int mode, int count, int type, long indices) {
        GlStateManager._drawElements(mode, count, type, indices);

        // TODO:
        inclusion();
    };


    //
    @Redirect(method = "postDraw(Lnet/minecraft/client/render/BufferBuilder;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glBindBuffer(II)V"))
    private static void postDraw_glBindBuffer(int target, int buffer) {
        GlStateManager._glBindBuffer(target, buffer);
        if (target == 34963) { hasIndexBuffer = true; };
    };

    @Redirect(method = "draw(Ljava/nio/ByteBuffer;Lnet/minecraft/client/render/VertexFormat$DrawMode;Lnet/minecraft/client/render/VertexFormat;ILnet/minecraft/client/render/VertexFormat$IntType;IZ)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glBindBuffer(II)V"))
    private static void draw_glBindBuffer(int target, int buffer) {
        GlStateManager._glBindBuffer(target, buffer);
        if (target == 34963) { hasIndexBuffer = true; };
    };

    // TODO: fill geometryLevel acceleration structure
    private static void inclusion() {
        // colored texture (albedo)
        int Sampler0 = glGetIntegeri(GL_TEXTURE_BINDING_2D, 0);
        ResourceImage resource = Context.resourceImageMap.get(Sampler0);

        // also, us needs VertexFormat
        // TODO: support normal map and PBR (i.e. IRIS)
    };

    @Inject(method = "bind(Lnet/minecraft/client/render/VertexFormat;)V", at=@At("TAIL"))
    private static void onBind(VertexFormat vertexFormat, CallbackInfo ci) {
        currentVertexFormat = vertexFormat;
    };

    @Inject(method = "draw(Ljava/nio/ByteBuffer;Lnet/minecraft/client/render/VertexFormat$DrawMode;Lnet/minecraft/client/render/VertexFormat;ILnet/minecraft/client/render/VertexFormat$IntType;IZ)V", at=@At("HEAD"))
    private static void onBeginDraw(ByteBuffer buffer, VertexFormat.DrawMode drawMode, VertexFormat vertexFormat, int count, VertexFormat.IntType elementFormat, int vertexCount, boolean textured, CallbackInfo ci) {
        lastVertexOffset = Context.entityVertexOffset;
        lastIndexOffset = Context.entityIndexOffset;
        hasIndexBuffer = false;
    };

    @Inject(method = "postDraw(Lnet/minecraft/client/render/BufferBuilder;)V", at=@At("HEAD"))
    private static void onBeginPostDraw(BufferBuilder builder, CallbackInfo ci) {
        lastVertexOffset = Context.entityVertexOffset;
        lastIndexOffset = Context.entityIndexOffset;
        hasIndexBuffer = false;
    };

}
