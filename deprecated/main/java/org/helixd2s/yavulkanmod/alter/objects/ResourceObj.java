package org.helixd2s.yavulkanmod.alter.objects;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.annotation.*;
import org.helixd2s.yavulkanmod.alter.header.Core;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;

@Name("alter::ResourceObj")
@Platform(
        library="YAV",
        include={"Alter/Alter.hpp",}
)
public class ResourceObj extends BaseObj {
    static { Loader.load(); }
    public ResourceObj() { allocate(); }
    private native void allocate();

    //
    @Name("make") public native static @SharedPtr ResourceObj make(@ByRef Core.Handle handle, @ByRef CreateInfo.ResourceCreateInfo cInfo);

    //
    public native @Cast("intptr_t") long getAllocationOffset();
    public native @Cast("intptr_t") long getDeviceAddress();
};

