package org.helixd2s.yavulkanmod.mixin.blaze3d;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.texture.NativeImage;
import org.helixd2s.yavulkanmod.Context;
import org.helixd2s.yavulkanmod.GlOverride;
import org.helixd2s.yavulkanmod.ResourceImage;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;
import org.helixd2s.yavulkanmod.alter.objects.ResourceObj;
import org.lwjgl.vulkan.VkExtent3D;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.EXTMemoryObject.glCreateMemoryObjectsEXT;
import static org.lwjgl.opengl.EXTMemoryObject.glTexStorageMem2DEXT;
import static org.lwjgl.opengl.EXTMemoryObjectWin32.GL_HANDLE_TYPE_OPAQUE_WIN32_EXT;
import static org.lwjgl.opengl.EXTMemoryObjectWin32.glImportMemoryWin32HandleEXT;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_R8;
import static org.lwjgl.opengl.GL30.GL_RG8;
import static org.lwjgl.vulkan.VK10.*;

@Mixin(TextureUtil.class)
public abstract class MTextureUtils {

    /**
     * @author
     */
    @Overwrite
    public static void prepareImage(NativeImage.InternalFormat internalFormat, int id, int maxLevel, int width, int height) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlStateManager._bindTexture(id);

        //
        if (maxLevel >= 0) {
            GlStateManager._texParameter(GL_TEXTURE_2D, 33085, maxLevel);
            GlStateManager._texParameter(GL_TEXTURE_2D, 33082, 0);
            GlStateManager._texParameter(GL_TEXTURE_2D, 33083, maxLevel);
            GlStateManager._texParameter(GL_TEXTURE_2D, 34049, 0.0F);
        };

        //
        GlOverride.glPrepareImage(internalFormat, id, maxLevel, width, height);
    };
}
