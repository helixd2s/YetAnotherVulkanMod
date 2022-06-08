package org.helixd2s.yavulkanmod;

import org.helixd2s.yavulkanmod.alter.header.CreateInfo;
import org.helixd2s.yavulkanmod.alter.objects.ResourceObj;
import org.lwjgl.vulkan.VkExtent3D;

public class ResourceBuffer {
    public int glMemory;
    public ResourceObj obj;
    public CreateInfo.BufferCreateInfo bufferCreateInfo;
    public CreateInfo.ResourceCreateInfo resourceCreateInfo;
}
