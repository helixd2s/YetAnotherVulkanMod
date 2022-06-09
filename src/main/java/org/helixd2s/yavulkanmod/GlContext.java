package org.helixd2s.yavulkanmod;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import org.helixd2s.yavulkanmod.alter.header.Core;
import org.helixd2s.yavulkanmod.alter.objects.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlContext {

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


};
