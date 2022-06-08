package org.helixd2s.yavulkanmod.mixin.render;

import net.minecraft.client.render.BufferRenderer;
import org.helixd2s.yavulkanmod.Context;
import org.helixd2s.yavulkanmod.GlOverride;
import org.helixd2s.yavulkanmod.ResourceBuffer;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;
import org.helixd2s.yavulkanmod.alter.objects.ResourceObj;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.ByteBuffer;

import static com.mojang.blaze3d.systems.RenderSystem.assertOnRenderThreadOrInit;
import static org.lwjgl.opengl.EXTMemoryObject.glBufferStorageMemEXT;
import static org.lwjgl.opengl.EXTMemoryObject.glCreateMemoryObjectsEXT;
import static org.lwjgl.opengl.EXTMemoryObjectWin32.GL_HANDLE_TYPE_OPAQUE_WIN32_EXT;
import static org.lwjgl.opengl.EXTMemoryObjectWin32.glImportMemoryWin32HandleEXT;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL15.*;

@Mixin(BufferRenderer.class)
public abstract class MBufferRenderer {

    //
    @Redirect(method = "draw(Ljava/nio/ByteBuffer;Lnet/minecraft/client/render/VertexFormat$DrawMode;Lnet/minecraft/client/render/VertexFormat;ILnet/minecraft/client/render/VertexFormat$IntType;IZ)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glBufferData(ILjava/nio/ByteBuffer;I)V"))
    private static void draw_glBufferData(int target, ByteBuffer data, int usage) {
        assertOnRenderThreadOrInit();
        long defaultSize = Math.max((target == GL_ARRAY_BUFFER ? 1024 * 1024 : 65536), data.limit());
        GlOverride.glBufferData(defaultSize, target, data, usage);
    };

    //
    @Redirect(method = "postDraw(Lnet/minecraft/client/render/BufferBuilder;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glBufferData(ILjava/nio/ByteBuffer;I)V"))
    private static void postDraw_glBufferData(int target, ByteBuffer data, int usage) {
        assertOnRenderThreadOrInit();
        long defaultSize = Math.max((target == GL_ARRAY_BUFFER ? 1024 * 1024 : 65536), data.limit());
        GlOverride.glBufferData(defaultSize, target, data, usage);
    };

}
