package org.helixd2s.yavulkanmod.mixin.texture;

import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.client.texture.SpriteAtlasTexture;
import org.helixd2s.yavulkanmod.Context;
import org.helixd2s.yavulkanmod.Resource;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;
import org.helixd2s.yavulkanmod.alter.objects.ResourceObj;
import org.lwjgl.vulkan.VkExtent3D;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static org.lwjgl.opengl.EXTMemoryObject.glCreateMemoryObjectsEXT;
import static org.lwjgl.opengl.EXTMemoryObject.glTexStorageMem2DEXT;
import static org.lwjgl.opengl.EXTMemoryObjectWin32.GL_HANDLE_TYPE_OPAQUE_WIN32_EXT;
import static org.lwjgl.opengl.EXTMemoryObjectWin32.glImportMemoryWin32HandleEXT;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.vulkan.VK10.VK_FORMAT_R8G8B8A8_UNORM;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_TYPE_2D;

@Mixin(SpriteAtlasTexture.class)
public abstract class MSpriteAtlasTexture {

    /*
    @Redirect(method = "upload(Lnet/minecraft/client/texture/SpriteAtlasTexture$Data;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/TextureUtil;prepareImage(IIII)V"))
    private void mPrepareImage(int id, int maxLevel, int width, int height) {
        //
        var extent = VkExtent3D.create();
        extent.set(width, height, 1);

        //
        var imageCreateInfo = new CreateInfo.ImageCreateInfo();
        imageCreateInfo.putExtent(extent.address());
        imageCreateInfo.putImageType(VK_IMAGE_TYPE_2D);
        imageCreateInfo.putFormat(VK_FORMAT_R8G8B8A8_UNORM);
        imageCreateInfo.putType(1);
        imageCreateInfo.putMipLevelCount(maxLevel+1);
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
        glTexStorageMem2DEXT(GL_TEXTURE_2D, maxLevel+1, GL_RGBA8, width, height, glMemory, resourceObj.getAllocationOffset());

        //
        Resource resource = new Resource();
        resource.glMemory = glMemory;
        resource.obj = resourceObj;
        resource.extent = extent;
        resource.imageCreateInfo = imageCreateInfo;
        resource.resourceCreateInfo = resourceCreateInfo;
        Context.resourceMap.put(id, resource);
    };*/

    /**
     * @author
     */
    //@Overwrite
    //private void upload(NativeImage image, boolean blur, boolean clamp) {
        //TextureUtil.prepareImage(this.getGlId(), 0, image.getWidth(), image.getHeight());

        /*
        //
        var extent = VkExtent3D.create();
        extent.set(image.getWidth(), image.getHeight(), 1);

        //
        var resourceCreateInfo = new CreateInfo.ResourceCreateInfo();

        //
        var imageCreateInfo = new CreateInfo.ImageCreateInfo();
        imageCreateInfo.putExtent(extent.address());
        imageCreateInfo.putImageType(VK_IMAGE_TYPE_2D);
        imageCreateInfo.putFormat(VK_FORMAT_R8G8B8A8_UNORM);
        imageCreateInfo.putType(1);
        imageCreateInfo.putMipLevelCount(1);
        imageCreateInfo.putLayerCount(1);

        //
        resourceCreateInfo.putImageInfo(imageCreateInfo);

        //
        var resourceObj = ResourceObj.make(Context.deviceObj.getHandle(), resourceCreateInfo);

        //
        var glMemory = glCreateMemoryObjectsEXT();
        glBindTexture(GL_TEXTURE_2D, this.getGlId());
        glImportMemoryWin32HandleEXT(glMemory, image.getWidth() * image.getHeight() * 4, GL_HANDLE_TYPE_OPAQUE_WIN32_EXT, resourceObj.getExtHandle());
        glTexStorageMem2DEXT(GL_TEXTURE_2D, 1, GL_RGBA, image.getWidth(), image.getHeight(), glMemory, resourceObj.getAllocationOffset());

        //
        image.upload(0, 0, 0, 0, 0, image.getWidth(), image.getHeight(), blur, clamp, false, true);
        */
    //}

    /*
[07:49:27] [Render thread/ERROR] (FabricLoader/Mixin) Mixin apply for mod yavulkanmod failed yavulkanmod.mixins.json:texture.MResourceTexture from mod yavulkanmod -> net.minecraft.client.texture.ResourceTexture: org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException @Shadow method getGlId in yavulkanmod.mixins.json:texture.MResourceTexture from mod yavulkanmod was not located in the target class net.minecraft.client.texture.ResourceTexture. No refMap loaded.
org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException: @Shadow method getGlId in yavulkanmod.mixins.json:texture.MResourceTexture from mod yavulkanmod was not located in the target class net.minecraft.client.texture.ResourceTexture. No refMap loaded.
     */
    //@Shadow
    //abstract public int getGlId();

}
