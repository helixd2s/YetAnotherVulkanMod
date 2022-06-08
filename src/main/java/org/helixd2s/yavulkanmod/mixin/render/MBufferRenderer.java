package org.helixd2s.yavulkanmod.mixin.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.VertexFormat;
import org.helixd2s.yavulkanmod.Context;
import org.helixd2s.yavulkanmod.GlOverride;
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
    @Redirect(method = "draw(Ljava/nio/ByteBuffer;Lnet/minecraft/client/render/VertexFormat$DrawMode;Lnet/minecraft/client/render/VertexFormat;ILnet/minecraft/client/render/VertexFormat$IntType;IZ)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glBufferData(ILjava/nio/ByteBuffer;I)V"))
    private static void draw_glBufferData(int target, ByteBuffer data, int usage) {
        //assertOnRenderThreadOrInit();
        long defaultSize = Math.max((target == GL_ARRAY_BUFFER ? 1024 * 1024 : 65536), data.limit());
        //GlOverride.glBufferData(defaultSize, target, data, usage);
        GlStateManager._glBufferData(target, data, usage);
        GlOverride.glCopyBufferToCache(target, data, usage);
    };

    //
    @Redirect(method = "postDraw(Lnet/minecraft/client/render/BufferBuilder;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glBufferData(ILjava/nio/ByteBuffer;I)V"))
    private static void postDraw_glBufferData(int target, ByteBuffer data, int usage) {
        //assertOnRenderThreadOrInit();
        long defaultSize = Math.max((target == GL_ARRAY_BUFFER ? 1024 * 1024 : 65536), data.limit());
        //GlOverride.glBufferData(defaultSize, target, data, usage);
        GlStateManager._glBufferData(target, data, usage);
        GlOverride.glCopyBufferToCache(target, data, usage);
    };

    @Inject(method = "draw(Ljava/nio/ByteBuffer;Lnet/minecraft/client/render/VertexFormat$DrawMode;Lnet/minecraft/client/render/VertexFormat;ILnet/minecraft/client/render/VertexFormat$IntType;IZ)V", at=@At("HEAD"))
    private static void onBeginDraw(ByteBuffer buffer, VertexFormat.DrawMode drawMode, VertexFormat vertexFormat, int count, VertexFormat.IntType elementFormat, int vertexCount, boolean textured, CallbackInfo ci) {
        lastVertexOffset = Context.entityVertexOffset;
        lastIndexOffset = Context.entityIndexOffset;
    };

    @Inject(method = "postDraw(Lnet/minecraft/client/render/BufferBuilder;)V", at=@At("HEAD"))
    private static void onBeginPostDraw(BufferBuilder builder, CallbackInfo ci) {
        lastVertexOffset = Context.entityVertexOffset;
        lastIndexOffset = Context.entityIndexOffset;
    };
}
