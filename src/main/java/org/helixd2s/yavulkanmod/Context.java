package org.helixd2s.yavulkanmod;

import org.helixd2s.yavulkanmod.alter.header.Core;
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
    public static Map<Integer, ResourceImage> resourceImageMap = new HashMap<Integer, ResourceImage>();
    public static Map<Integer, ResourceBuffer> resourceBufferMap = new HashMap<Integer, ResourceBuffer>();

    // TODO: entities
    public static Core.BucketOfGeometryInfo entityGeometryInfos;
    public static GeometryLevelObj entityGeometryLevel;

    // TODO:
    public static Core.BucketOfInstanceDataInfo instances;
    public static InstanceLevelObj instanceLevel;

    //
    public static ResourceObj entityVertexCache;
    public static ResourceObj entityIndexCache;

    //
    public static int glEntityVertexCache = -1;
    public static int glEntityIndexCache = -1;

    //
    public static long entityVertexOffset = 0;
    public static long entityIndexOffset = 0;
}

