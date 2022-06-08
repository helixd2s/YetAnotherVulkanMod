package org.helixd2s.yavulkanmod.mixin.blaze3d;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.texture.NativeImage;
import org.helixd2s.yavulkanmod.Context;
import org.helixd2s.yavulkanmod.Resource;
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
    //@Redirect(method = "upload(Lnet/minecraft/client/texture/SpriteAtlasTexture$Data;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/TextureUtil;prepareImage(IIII)V"))

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
        if (internalFormat == NativeImage.InternalFormat.RGB) {
            for(int i = 0; i <= maxLevel; ++i) {
                GlStateManager._texImage2D(3553, i, internalFormat.getValue(), width >> i, height >> i, 0, 6408, 5121, (IntBuffer)null);
            }
        } else {

            //
            var extent = VkExtent3D.create();
            extent.set(width, height, 1);

            //
            int vkFormat = VK_FORMAT_R8G8B8A8_UNORM, glFormat = GL_RGBA8;
            if (internalFormat == NativeImage.InternalFormat.RG) { vkFormat = VK_FORMAT_R8G8_UNORM; glFormat = GL_RG8; };
            if (internalFormat == NativeImage.InternalFormat.RED) { vkFormat = VK_FORMAT_R8_UNORM; glFormat = GL_R8; };

            //
            var imageCreateInfo = new CreateInfo.ImageCreateInfo();
            imageCreateInfo.putExtent(extent.address());
            imageCreateInfo.putImageType(VK_IMAGE_TYPE_2D);
            imageCreateInfo.putFormat(vkFormat);
            imageCreateInfo.putType(1);
            imageCreateInfo.putMipLevelCount(maxLevel + 1);
            imageCreateInfo.putLayerCount(1);

            //
            var resourceCreateInfo = new CreateInfo.ResourceCreateInfo();
            resourceCreateInfo.putImageInfo(imageCreateInfo);

            //
            var resourceObj = ResourceObj.make(Context.deviceObj.getHandle(), resourceCreateInfo);

            //
            var glMemory = glCreateMemoryObjectsEXT();
            glBindTexture(GL_TEXTURE_2D, id);
            glImportMemoryWin32HandleEXT(glMemory, width * height * 4, GL_HANDLE_TYPE_OPAQUE_WIN32_EXT, resourceObj.getExtHandle());
            glTexStorageMem2DEXT(GL_TEXTURE_2D, maxLevel + 1, glFormat, width, height, glMemory, resourceObj.getAllocationOffset());

            //
            Resource resource = new Resource();
            resource.glMemory = glMemory;
            resource.obj = resourceObj;
            resource.extent = extent;
            resource.imageCreateInfo = imageCreateInfo;
            resource.resourceCreateInfo = resourceCreateInfo;
            Context.resourceMap.put(id, resource);
        };
    };
}
