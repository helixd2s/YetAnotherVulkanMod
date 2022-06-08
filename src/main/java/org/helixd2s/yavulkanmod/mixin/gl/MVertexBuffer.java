package org.helixd2s.yavulkanmod.mixin.gl;

import net.minecraft.client.gl.VertexBuffer;
import org.helixd2s.yavulkanmod.Context;
import org.helixd2s.yavulkanmod.GlOverride;
import org.helixd2s.yavulkanmod.ResourceBuffer;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;
import org.helixd2s.yavulkanmod.alter.objects.ResourceObj;
import org.lwjgl.vulkan.VkExtent3D;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.ByteBuffer;

import static com.mojang.blaze3d.systems.RenderSystem.assertOnRenderThreadOrInit;
import static org.lwjgl.opengl.EXTMemoryObject.*;
import static org.lwjgl.opengl.EXTMemoryObjectWin32.GL_HANDLE_TYPE_OPAQUE_WIN32_EXT;
import static org.lwjgl.opengl.EXTMemoryObjectWin32.glImportMemoryWin32HandleEXT;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.GL_R8;
import static org.lwjgl.opengl.GL30.GL_RG8;
import static org.lwjgl.vulkan.VK10.*;

@Mixin(VertexBuffer.class)
public abstract class MVertexBuffer {

    // TODO:
    //@Unique public GeometryLevelObj geometryLevel;

    //
    @Redirect(method = "uploadInternal(Lnet/minecraft/client/render/BufferBuilder;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;glBufferData(ILjava/nio/ByteBuffer;I)V"))
    public void glBufferData(int target, ByteBuffer data, int usage) {
        assertOnRenderThreadOrInit();
        long defaultSize = Math.max((target == GL_ARRAY_BUFFER ? 1024 * 1024 : 65536) * 4 , data.limit());
        GlOverride.glBufferData(defaultSize, target, data, usage);
    };

}
