package org.helixd2s.yavulkanmod;

import org.helixd2s.yavulkanmod.alter.header.CreateInfo;
import org.helixd2s.yavulkanmod.alter.objects.ResourceObj;
import org.lwjgl.vulkan.VkExtent3D;

public class Resource {
    public int glMemory;
    public ResourceObj obj;
    public VkExtent3D extent;
    public CreateInfo.ImageCreateInfo imageCreateInfo;
    public CreateInfo.ResourceCreateInfo resourceCreateInfo;
}
