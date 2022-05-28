package org.helixd2s.yavulkanmod.renderer.objects;

import org.helixd2s.yavulkanmod.renderer.BaseObj;
import org.helixd2s.yavulkanmod.renderer.Handle;
import org.lwjgl.PointerBuffer;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkExtensionProperties;
import org.lwjgl.vulkan.VkLayerProperties;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;

public class DeviceObj extends BaseObj {
    public class CreateInfo {
        List<String> extensions;
        List<String> layers;
    };

    //
    PointerBuffer extensions; IntBuffer extensionCount;
    PointerBuffer layers; IntBuffer layerCount;

    //
    List<ByteBuffer> extensionPtrs;
    List<ByteBuffer> layerPtrs;

    //
    VkExtensionProperties.Buffer extensionsProperties; IntBuffer extensionPropertyCount;
    VkLayerProperties.Buffer layersProperties; IntBuffer layerPropertyCount;

    //
    public CreateInfo cInfo;
    public VkDevice device;

    public DeviceObj(Handle base, CreateInfo cInfo) {
        super(base);
        this.cInfo = cInfo;
    };
};
