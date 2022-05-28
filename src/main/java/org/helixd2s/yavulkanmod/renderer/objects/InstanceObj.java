package org.helixd2s.yavulkanmod.renderer.objects;

import org.bytedeco.javacpp.LongPointer;
import org.helixd2s.yavulkanmod.renderer.BaseObj;
import org.helixd2s.yavulkanmod.renderer.Handle;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.PointerBuffer;
import org.lwjgl.vulkan.*;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.util.List;

import static org.helixd2s.yavulkanmod.renderer.Utils.bb_to_str;
import static org.helixd2s.yavulkanmod.renderer.Utils.str_to_bb;
import static org.lwjgl.vulkan.VK10.*;
import static org.lwjgl.vulkan.VK13.VK_API_VERSION_1_3;

public class InstanceObj extends BaseObj {
    public class CreateInfo {
        List<String> extensions;
        List<String> layers;
    };

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
    public VkInstance instance;

    //
    public PointerBuffer searchExtensions(@NotNull CreateInfo cInfo){
        //
        vkEnumerateInstanceExtensionProperties((String)null, extensionPropertyCount = IntBuffer.allocate(1), null);
        extensionsProperties = VkExtensionProperties.calloc(extensionPropertyCount.get(0));
        vkEnumerateInstanceExtensionProperties((String)null, extensionPropertyCount, extensionsProperties);

        //
        for (int j=0;j<cInfo.extensions.size();j++) {
            var extensionName = cInfo.extensions.get(j);
            for (int i=0;i<extensionPropertyCount.get();i++) {
                var extensionProperty = extensionsProperties.get(i);
                if (bb_to_str(extensionProperty.extensionName(), Charset.defaultCharset()).equals(extensionName)) {
                    extensionPtrs.add(extensionProperty.extensionName());
                }
            }
        }

        //
        extensions = PointerBuffer.allocateDirect(extensionPtrs.size());
        for (int i=0;i<extensionPtrs.size();i++) { extensions.put(0,extensionPtrs.get(i)); };
        return extensions;
    };

    public PointerBuffer searchLayers(@NotNull CreateInfo cInfo) {
        //
        vkEnumerateInstanceLayerProperties(layerPropertyCount = IntBuffer.allocate(1), null);
        layersProperties = VkLayerProperties.calloc(layerPropertyCount.get(0));
        vkEnumerateInstanceLayerProperties(layerPropertyCount, layersProperties);

        //
        for (int j=0;j<cInfo.layers.size();j++) {
            var layerName = cInfo.layers.get(j);
            for (int i=0;i<layerPropertyCount.get();i++) {
                var  layerProperty = layersProperties.get(i);
                if (bb_to_str(layerProperty.layerName(), Charset.defaultCharset()).equals(layerName)) {
                    layerPtrs.add(layerProperty.layerName());
                }
            }
        }

        //
        layers = PointerBuffer.allocateDirect(layerPtrs.size());
        for (int i=0;i<layerPtrs.size();i++) { layers.put(0,layerPtrs.get(i)); };
        return layers;
    };

    //
    public InstanceObj(Handle base, CreateInfo cInfo) {
        super(base);
        this.cInfo = cInfo;

        //
        var applicationInfo = VkApplicationInfo.calloc();
        applicationInfo.apiVersion(VK_API_VERSION_1_3);
        applicationInfo.pApplicationName(str_to_bb("Minecraft", Charset.defaultCharset()));
        applicationInfo.applicationVersion(VK_MAKE_VERSION(1,0,0));
        applicationInfo.pEngineName(str_to_bb("YetAnother", Charset.defaultCharset()));
        applicationInfo.engineVersion(VK_MAKE_VERSION(1,0,0));

        //
        var instanceInfo = VkInstanceCreateInfo.calloc();
        instanceInfo.sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO);
        instanceInfo.pApplicationInfo(applicationInfo);
        instanceInfo.ppEnabledExtensionNames(this.searchExtensions(cInfo));
        instanceInfo.ppEnabledLayerNames(this.searchLayers(cInfo));

        //
        PointerBuffer pInstance = PointerBuffer.allocateDirect(1);
        vkCreateInstance(instanceInfo, null, pInstance);

        //
        this.handle = new Handle();
        this.handle.value = pInstance.get(0);
    };
};