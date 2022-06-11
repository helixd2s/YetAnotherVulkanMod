package org.helixd2s.yavulkanmod.alter.objects;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.SharedPtr;
import org.helixd2s.yavulkanmod.alter.header.Core;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;

@Name("alter::GeometryLevelObj")
@Platform(
        library="YAV",
        include={"Alter/Alter.hpp",}
)
public class GeometryLevelObj extends BaseObj {
    static { Loader.load(); }
    public GeometryLevelObj() { allocate(); }
    private native void allocate();

    @Name("make")
    public native static @SharedPtr GeometryLevelObj make(@ByRef Core.Handle handle, @ByRef CreateInfo.GeometryLevelCreateInfo cInfo);
};