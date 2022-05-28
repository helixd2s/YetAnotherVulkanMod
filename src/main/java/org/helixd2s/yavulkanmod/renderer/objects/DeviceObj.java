package org.helixd2s.yavulkanmod.renderer.objects;

import org.bytedeco.javacpp.BytePointer;
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
import static org.lwjgl.vulkan.VK10.*;
import static org.lwjgl.vulkan.VK11.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_FEATURES_2;
import static org.lwjgl.vulkan.VK11.vkGetPhysicalDeviceFeatures2;
import static org.lwjgl.vulkan.VK12.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_1_FEATURES;
import static org.lwjgl.vulkan.VK12.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_2_FEATURES;
import static org.lwjgl.vulkan.VK13.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_3_FEATURES;

public class DeviceObj extends BaseObj {
    public class CreateInfo {
        List<String> extensions;
        List<String> layers;
        VkPhysicalDevice physicalDevice;
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
    public PointerBuffer searchExtensions(@NotNull CreateInfo cInfo){
        //
        vkEnumerateDeviceExtensionProperties(cInfo.physicalDevice, (String)null, extensionPropertyCount = IntBuffer.allocate(1), null);
        extensionsProperties = VkExtensionProperties.calloc(extensionPropertyCount.get(0));
        vkEnumerateDeviceExtensionProperties(cInfo.physicalDevice, (String)null, extensionPropertyCount, extensionsProperties);

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
        vkEnumerateDeviceLayerProperties(cInfo.physicalDevice, layerPropertyCount = IntBuffer.allocate(1), null);
        layersProperties = VkLayerProperties.calloc(layerPropertyCount.get(0));
        vkEnumerateDeviceLayerProperties(cInfo.physicalDevice, layerPropertyCount, layersProperties);

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
    public CreateInfo cInfo;
    public VkDevice device;
    public PointerBuffer pDevice;

    public DeviceObj(Handle base, CreateInfo cInfo) {
        super(base);
        this.cInfo = cInfo;

        // TODO: bind map to physical device
        var features = VkPhysicalDeviceFeatures2.create(this.setInfo(VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_FEATURES_2, VkPhysicalDeviceFeatures2.calloc(1).address()))
                .pNext(VkPhysicalDeviceVulkan11Features.create(this.setInfo(VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_1_FEATURES, VkPhysicalDeviceVulkan11Features.calloc(1).address())))
                .pNext(VkPhysicalDeviceVulkan12Features.create(this.setInfo(VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_2_FEATURES, VkPhysicalDeviceVulkan12Features.calloc(1).address())))
                .pNext(VkPhysicalDeviceVulkan13Features.create(this.setInfo(VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_3_FEATURES, VkPhysicalDeviceVulkan13Features.calloc(1).address())));;

        //
        vkGetPhysicalDeviceFeatures2(cInfo.physicalDevice, features);

        //
        var deviceInfo = VkDeviceCreateInfo.calloc();
        deviceInfo.pNext(features);
        deviceInfo.sType(VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO);
        deviceInfo.ppEnabledExtensionNames(this.searchExtensions(cInfo));
        deviceInfo.ppEnabledLayerNames(this.searchLayers(cInfo));

        //
        this.setInfo(VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO, deviceInfo.address());

        //
        vkCreateDevice(cInfo.physicalDevice, deviceInfo, null, pDevice = PointerBuffer.allocateDirect(1));
        this.device = new VkDevice(pDevice.get(), cInfo.physicalDevice, deviceInfo);
    };
};
