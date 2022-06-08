package org.helixd2s.yavulkanmod;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.texture.NativeImage;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;
import org.helixd2s.yavulkanmod.alter.objects.ResourceObj;
import org.lwjgl.opengl.*;
import org.lwjgl.vulkan.VkExtent3D;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.EXTMemoryObject.*;
import static org.lwjgl.opengl.EXTMemoryObjectWin32.GL_HANDLE_TYPE_OPAQUE_WIN32_EXT;
import static org.lwjgl.opengl.EXTMemoryObjectWin32.glImportMemoryWin32HandleEXT;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.GL_R8;
import static org.lwjgl.opengl.GL30.GL_RG8;
import static org.lwjgl.opengl.GL31.GL_COPY_WRITE_BUFFER;
import static org.lwjgl.opengl.GL31.glCopyBufferSubData;
import static org.lwjgl.vulkan.VK10.*;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_TYPE_2D;

public class GlOverride {

    //
    static public void glCopyBufferToCache(int target, ByteBuffer data, int usage) {
        if (target == GL_ARRAY_BUFFER) {
            glBindBuffer(GL_COPY_WRITE_BUFFER, Context.glEntityVertexCache);
            glCopyBufferSubData(GL_ARRAY_BUFFER, GL_COPY_WRITE_BUFFER, 0, Context.entityVertexOffset, data.limit());
            Context.entityVertexOffset += data.limit();
        };
        if (target == GL_ELEMENT_ARRAY_BUFFER) {
            glBindBuffer(GL_COPY_WRITE_BUFFER, Context.glEntityIndexCache);
            glCopyBufferSubData(GL_ELEMENT_ARRAY_BUFFER, GL_COPY_WRITE_BUFFER, 0, Context.entityIndexOffset, data.limit());
            Context.entityIndexOffset += data.limit();
        };
    };

    //
    static public void glPrepareImage(NativeImage.InternalFormat internalFormat, int id, int maxLevel, int width, int height) {

        //
        if (internalFormat == NativeImage.InternalFormat.RGB) {
            for(int i = 0; i <= maxLevel; ++i) {
                GlStateManager._texImage2D(3553, i, internalFormat.getValue(), width >> i, height >> i, 0, 6408, 5121, (IntBuffer)null);
            }
        } else {
            ResourceImage resource = Context.resourceImageMap.get(id);
            ResourceObj resourceObj = null;

            //
            if (resource == null) {
                resource = new ResourceImage();
                resource.extent = VkExtent3D.create();
                resource.extent.set(width, height, 1);

                //
                int vkFormat = VK_FORMAT_R8G8B8A8_UNORM, glFormat = GL_RGBA8;

                //
                if (internalFormat == NativeImage.InternalFormat.RG) {
                    vkFormat = VK_FORMAT_R8G8_UNORM;
                    glFormat = GL_RG8;
                }

                //
                if (internalFormat == NativeImage.InternalFormat.RED) {
                    vkFormat = VK_FORMAT_R8_UNORM;
                    glFormat = GL_R8;
                }

                //
                resource.imageCreateInfo = new CreateInfo.ImageCreateInfo();
                resource.imageCreateInfo.putExtent(resource.extent.address());
                resource.imageCreateInfo.putImageType(VK_IMAGE_TYPE_2D);
                resource.imageCreateInfo.putFormat(vkFormat);
                resource.imageCreateInfo.putType(1);
                resource.imageCreateInfo.putMipLevelCount(maxLevel + 1);
                resource.imageCreateInfo.putLayerCount(1);

                //
                resource.resourceCreateInfo = new CreateInfo.ResourceCreateInfo();
                resource.resourceCreateInfo.putImageInfo(resource.imageCreateInfo);

                //
                resourceObj = resource.obj = ResourceObj.make(Context.deviceObj.getHandle(), resource.resourceCreateInfo);

                //
                glBindTexture(GL_TEXTURE_2D, id);
                glImportMemoryWin32HandleEXT(resource.glMemory = glCreateMemoryObjectsEXT(), width * height * 4, GL_HANDLE_TYPE_OPAQUE_WIN32_EXT, resourceObj.getExtHandle());
                glTexStorageMem2DEXT(GL_TEXTURE_2D, maxLevel + 1, glFormat, width, height, resource.glMemory, resource.obj.getAllocationOffset());

                //
                Context.resourceImageMap.put(id, resource);
            } else {
                resourceObj = resource.obj;
            };
        };
    };

    static public int glCreateBuffer(int target, long defaultSize) {
        int glBuffer[] = {-1};
        ResourceBuffer resource = Context.resourceBufferMap.get(glBuffer[0]);
        ResourceObj resourceObj = null;

        //
        //if (resource == null) {
            resource = new ResourceBuffer();

            //
            resource.bufferCreateInfo = new CreateInfo.BufferCreateInfo();
            resource.bufferCreateInfo.putType(target == GL_ARRAY_BUFFER ? 3 : 4);
            resource.bufferCreateInfo.putSize(defaultSize);

            //
            resource.resourceCreateInfo = new CreateInfo.ResourceCreateInfo();
            resource.resourceCreateInfo.putBufferInfo(resource.bufferCreateInfo);

            //
            resourceObj = resource.obj = ResourceObj.make(Context.deviceObj.getHandle(), resource.resourceCreateInfo);

            //
            GL45.glCreateBuffers(glBuffer);
            glImportMemoryWin32HandleEXT(resource.glMemory = glCreateMemoryObjectsEXT(), defaultSize, GL_HANDLE_TYPE_OPAQUE_WIN32_EXT, resourceObj.getExtHandle());
            glNamedBufferStorageMemEXT(glBuffer[0], defaultSize, resource.glMemory, resource.obj.getAllocationOffset());

            //
            Context.resourceBufferMap.put(glBuffer[0], resource);
        //} else {
            //resourceObj = resource.obj;
        //}
        return glBuffer[0];
    };

    static public void glBufferData(long defaultSize, int target, ByteBuffer data, int usage) {
        if (target == GL_ARRAY_BUFFER || target == GL_ELEMENT_ARRAY_BUFFER) {

            //
            int id = glGetInteger(target == GL_ARRAY_BUFFER ? GL_ARRAY_BUFFER_BINDING : GL_ELEMENT_ARRAY_BUFFER_BINDING);
            ResourceBuffer resource = Context.resourceBufferMap.get(id);
            ResourceObj resourceObj = null;

            //
            if (resource == null) {
                resource = new ResourceBuffer();

                //
                resource.bufferCreateInfo = new CreateInfo.BufferCreateInfo();
                resource.bufferCreateInfo.putType(target == GL_ARRAY_BUFFER ? 3 : 4);
                resource.bufferCreateInfo.putSize(defaultSize);

                //
                resource.resourceCreateInfo = new CreateInfo.ResourceCreateInfo();
                resource.resourceCreateInfo.putBufferInfo(resource.bufferCreateInfo);

                //
                resourceObj = resource.obj = ResourceObj.make(Context.deviceObj.getHandle(), resource.resourceCreateInfo);

                //
                glImportMemoryWin32HandleEXT(resource.glMemory = glCreateMemoryObjectsEXT(), defaultSize, GL_HANDLE_TYPE_OPAQUE_WIN32_EXT, resourceObj.getExtHandle());
                glBufferStorageMemEXT(target, defaultSize, resource.glMemory, resource.obj.getAllocationOffset());

                //
                Context.resourceBufferMap.put(id, resource);
            } else {
                resourceObj = resource.obj;
            }

            // BUT needs also Math.min(defaultSize, data.limit())
            glBufferSubData(target, 0, data);

        } else {
            GL20.glBufferData(target, data, usage);
        };
    }
}
