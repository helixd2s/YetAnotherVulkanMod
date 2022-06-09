package org.helixd2s.yavulkanmod;

import org.helixd2s.yavulkanmod.alter.objects.ResourceObj;

import static org.helixd2s.yavulkanmod.GlOverride.glCreateBuffer;

//
public class ResourceCache {
    public static ResourceObj cache;
    public static int glCache = -1;
    public static long offset = 0;
    public static long size = 0;

    //
    public ResourceCache(int target, long defaultSize) {
        //
        this.glCache = glCreateBuffer(target, defaultSize);
        this.cache = GlContext.resourceBufferMap.get(this.glCache).obj;

        //
        this.offset = 0;
        this.size = 0;
    }
}
