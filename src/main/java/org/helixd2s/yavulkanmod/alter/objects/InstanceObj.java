package org.helixd2s.yavulkanmod.alter.objects;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.SharedPtr;
import org.helixd2s.yavulkanmod.alter.header.Core;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;

@Name("alter::InstanceObj")
@Platform(
        library="YAV",
        include={"Alter/Alter.hpp",}
)
public class InstanceObj extends BaseObj {
    static { Loader.load(); }
    public InstanceObj() { allocate(); }
    private native void allocate();

    @Name("make")
    public native static @SharedPtr InstanceObj make(@ByRef CreateInfo.InstanceCreateInfo cInfo);

    @Name("getHandle")
    public native @ByRef Core.Handle getHandle();
};
