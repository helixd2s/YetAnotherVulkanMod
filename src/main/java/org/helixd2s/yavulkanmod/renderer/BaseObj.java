package org.helixd2s.yavulkanmod.renderer;

//
import org.bytedeco.javacpp.BytePointer;
import org.lwjgl.vulkan.VkBaseOutStructure;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures2;

import java.util.LinkedHashMap;
import java.util.Map;

//
public class BaseObj {
    public Handle base;
    public Handle handle;

    //
    protected Map<Handle, BaseObj> typedMap = new LinkedHashMap<Handle, BaseObj>();
    protected Map<Integer, Long> infoMap = new LinkedHashMap<Integer, Long>();

    // TODO: support for sizeof from sType, needs C++23
    protected long setInfo(int structureType, long structure) {
        this.infoMap.put(structureType, structure);
        VkBaseOutStructure.create(structure).sType(structureType);
        return structure;
    };

    //
    public BaseObj(Handle base) {
        this.base = base;
        this.typedMap = new LinkedHashMap<Handle, BaseObj>();
        this.infoMap = new LinkedHashMap<Integer, Long>();
    };

};
