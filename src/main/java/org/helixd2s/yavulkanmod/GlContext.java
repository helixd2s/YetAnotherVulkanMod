package org.helixd2s.yavulkanmod;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.texture.NativeImage;
import org.helixd2s.yavulkanmod.alter.Alter;
import org.helixd2s.yavulkanmod.alter.header.Core;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;
import org.helixd2s.yavulkanmod.alter.objects.*;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL45;
import org.lwjgl.vulkan.VkExtent3D;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.EXTMemoryObject.*;
import static org.lwjgl.opengl.EXTMemoryObjectWin32.GL_HANDLE_TYPE_OPAQUE_WIN32_EXT;
import static org.lwjgl.opengl.EXTMemoryObjectWin32.glImportMemoryWin32HandleEXT;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL30.GL_R8;
import static org.lwjgl.opengl.GL30.GL_RG8;
import static org.lwjgl.opengl.GL31.GL_COPY_WRITE_BUFFER;
import static org.lwjgl.opengl.GL31.glCopyBufferSubData;
import static org.lwjgl.vulkan.VK10.*;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_TYPE_2D;

public class GlContext {

    public static boolean worldRendering;

    static public class ResourceImage {
        public int glMemory;
        public ResourceObj obj;
        public VkExtent3D extent;
        public CreateInfo.ImageCreateInfo imageCreateInfo;
        public CreateInfo.ResourceCreateInfo resourceCreateInfo;
    };

    static public class ResourceBuffer {
        public int glMemory;
        public ResourceObj obj;
        public CreateInfo.BufferCreateInfo bufferCreateInfo;
        public CreateInfo.ResourceCreateInfo resourceCreateInfo;
    };

    static public class ResourceCache {
        public static ResourceObj cache;
        public static int glCache = -1;
        public static long offset = 0;
        public static long size = 0;

        //
        public ResourceCache(int target, long defaultSize) {
            //
            this.glCache = GlContext.glCreateBuffer(target, defaultSize);
            this.cache = GlContext.resourceBufferMap.get(this.glCache).obj;

            //
            this.offset = 0;
            this.size = 0;
        }
    };



    //
    public static DeviceObj deviceObj;
    public static InstanceObj instanceObj;
    public static ContextObj contextObj;
    public static PipelineLayoutObj pipelineLayoutObj;
    public static MemoryAllocatorVma memoryAllocatorObj;
    public static UploaderObj uploaderObj;

    //
    public static Map<Integer, ResourceImage> resourceImageMap = new HashMap<Integer, ResourceImage>();
    public static Map<Integer, ResourceBuffer> resourceBufferMap = new HashMap<Integer, ResourceBuffer>();

    // TODO: entities
    public static Core.BucketOfGeometryInfo entityGeometryInfos = null;
    public static GeometryLevelObj entityGeometryLevel = null;

    // TODO:
    public static Core.BucketOfInstanceDataInfo instances = null;
    public static InstanceLevelObj instanceLevel = null;

    //
    public static ResourceCache entityVertex = null;
    public static ResourceCache entityIndex = null;

    //
    public static boolean hasIndexBuffer = false;
    public static VertexFormat currentVertexFormat = null;

    //
    public static int currentGlVertexBuffer = 0;
    public static int currentGlIndexBuffer = 0;

    //
    public static long currentVertexOffset = 0;
    public static long currentIndexOffset = 0;

    //
    public static void initialize() {
        GlContext.contextObj = Alter.initialize(new CreateInfo.ContextCreateInfo());
        GlContext.instanceObj = InstanceObj.make(new CreateInfo.InstanceCreateInfo());
        GlContext.deviceObj = DeviceObj.make(GlContext.instanceObj.getHandle(), new CreateInfo.DeviceCreateInfo());
        GlContext.memoryAllocatorObj = MemoryAllocatorVma.make(GlContext.deviceObj.getHandle(), new CreateInfo.MemoryAllocatorCreateInfo());
        GlContext.pipelineLayoutObj = PipelineLayoutObj.make(GlContext.deviceObj.getHandle(), new CreateInfo.PipelineLayoutCreateInfo());
        GlContext.uploaderObj = UploaderObj.make(GlContext.deviceObj.getHandle(), (new CreateInfo.UploaderCreateInfo().use(3)));

        //
        GlContext.entityVertex = new GlContext.ResourceCache(GL_ARRAY_BUFFER, 1024 * 1024 * 4);
        GlContext.entityIndex = new GlContext.ResourceCache(GL_ELEMENT_ARRAY_BUFFER, 65536 * 4);


        // in finale...
        // TODO: create geometry level for entities (blank)

        // in finale...
        // TODO: create instance level for everything (blank)
    };


    // TODO: fill geometryLevel acceleration structure
    public static void inclusion() {
        // colored texture (albedo)
        Shader shader = RenderSystem.getShader();

        //
        List<ResourceImage> resources = new ArrayList<ResourceImage>();
        for(int j = 0; j < 8; ++j) {
            resources.add(GlContext.resourceImageMap.get(RenderSystem.getShaderTexture(j)));
        };

        //
        ResourceImage Sampler0 = resources.get(0);

        // also, us needs VertexFormat
        // TODO: support normal map and PBR (i.e. IRIS)
    };



    //
    static public void glCopyBufferToCache(int target, ByteBuffer data, int usage) {
        if (target == GL_ARRAY_BUFFER) {
            GlContext.entityVertex.offset = GlContext.entityVertex.size; GlContext.entityVertex.size += data.limit();
            glBindBuffer(GL_COPY_WRITE_BUFFER, GlContext.entityVertex.glCache);
            glCopyBufferSubData(GL_ARRAY_BUFFER, GL_COPY_WRITE_BUFFER, 0, GlContext.entityVertex.offset, data.limit());
        };
        if (target == GL_ELEMENT_ARRAY_BUFFER) {
            GlContext.entityIndex.offset = GlContext.entityIndex.size; GlContext.entityIndex.size += data.limit();
            glBindBuffer(GL_COPY_WRITE_BUFFER, GlContext.entityIndex.glCache);
            glCopyBufferSubData(GL_ELEMENT_ARRAY_BUFFER, GL_COPY_WRITE_BUFFER, 0, GlContext.entityIndex.offset, data.limit());
        };
    };

    //
    public static void glPrepareImage(NativeImage.InternalFormat internalFormat, int id, int maxLevel, int width, int height) {

        //
        if (internalFormat == NativeImage.InternalFormat.RGB) {
            for(int i = 0; i <= maxLevel; ++i) {
                GlStateManager._texImage2D(3553, i, internalFormat.getValue(), width >> i, height >> i, 0, 6408, 5121, (IntBuffer)null);
            }
        } else {
            ResourceImage resource = GlContext.resourceImageMap.get(id);
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
                resourceObj = resource.obj = ResourceObj.make(GlContext.deviceObj.getHandle(), resource.resourceCreateInfo);

                //
                glBindTexture(GL_TEXTURE_2D, id);
                glImportMemoryWin32HandleEXT(resource.glMemory = glCreateMemoryObjectsEXT(), width * height * 4, GL_HANDLE_TYPE_OPAQUE_WIN32_EXT, resourceObj.getExtHandle());
                glTexStorageMem2DEXT(GL_TEXTURE_2D, maxLevel + 1, glFormat, width, height, resource.glMemory, resource.obj.getAllocationOffset());

                //
                GlContext.resourceImageMap.put(id, resource);
            } else {
                resourceObj = resource.obj;
            };
        };
    };

    public static int glCreateBuffer(int target, long defaultSize) {
        int glBuffer[] = {-1};
        ResourceBuffer resource = GlContext.resourceBufferMap.get(glBuffer[0]);
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
        resourceObj = resource.obj = ResourceObj.make(GlContext.deviceObj.getHandle(), resource.resourceCreateInfo);

        //
        GL45.glCreateBuffers(glBuffer);
        glImportMemoryWin32HandleEXT(resource.glMemory = glCreateMemoryObjectsEXT(), defaultSize, GL_HANDLE_TYPE_OPAQUE_WIN32_EXT, resourceObj.getExtHandle());
        glNamedBufferStorageMemEXT(glBuffer[0], defaultSize, resource.glMemory, resource.obj.getAllocationOffset());

        //
        GlContext.resourceBufferMap.put(glBuffer[0], resource);
        //} else {
        //resourceObj = resource.obj;
        //}
        return glBuffer[0];
    };

    public static void glBufferData(long defaultSize, int target, ByteBuffer data, int usage) {
        if (target == GL_ARRAY_BUFFER || target == GL_ELEMENT_ARRAY_BUFFER) {

            //
            int id = glGetInteger(target == GL_ARRAY_BUFFER ? GL_ARRAY_BUFFER_BINDING : GL_ELEMENT_ARRAY_BUFFER_BINDING);
            ResourceBuffer resource = GlContext.resourceBufferMap.get(id);
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
                resourceObj = resource.obj = ResourceObj.make(GlContext.deviceObj.getHandle(), resource.resourceCreateInfo);

                //
                glImportMemoryWin32HandleEXT(resource.glMemory = glCreateMemoryObjectsEXT(), defaultSize, GL_HANDLE_TYPE_OPAQUE_WIN32_EXT, resourceObj.getExtHandle());
                glBufferStorageMemEXT(target, defaultSize, resource.glMemory, resource.obj.getAllocationOffset());

                //
                GlContext.resourceBufferMap.put(id, resource);
            } else {
                resourceObj = resource.obj;
            }

            // BUT needs also Math.min(defaultSize, data.limit())
            glBufferSubData(target, 0, data);

        } else {
            GL20.glBufferData(target, data, usage);
        };
    }



};
