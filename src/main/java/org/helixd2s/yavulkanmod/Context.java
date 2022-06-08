package org.helixd2s.yavulkanmod;

import org.helixd2s.yavulkanmod.alter.objects.*;

import java.util.HashMap;
import java.util.Map;

public class Context {

    //
    public static DeviceObj deviceObj;
    public static InstanceObj instanceObj;
    public static ContextObj contextObj;
    public static PipelineLayoutObj pipelineLayoutObj;
    public static MemoryAllocatorVma memoryAllocatorObj;
    public static UploaderObj uploaderObj;

    //
    public static Map<Integer, Resource> resourceMap = new HashMap<Integer, Resource>();
}
