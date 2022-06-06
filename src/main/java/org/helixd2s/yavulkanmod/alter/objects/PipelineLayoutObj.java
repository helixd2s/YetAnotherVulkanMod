package org.helixd2s.yavulkanmod.alter.objects;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.SharedPtr;
import org.helixd2s.yavulkanmod.alter.header.Core;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;

@Name("alter::PipelineLayoutObj")
@Platform(
        library="YAV",
        include={"Alter/Alter.hpp",}
)
public class PipelineLayoutObj extends BaseObj {
    static { Loader.load(); }
    public PipelineLayoutObj() { allocate(); }
    private native void allocate();

    @Name("make")
    public native static @SharedPtr PipelineLayoutObj make(@ByRef Core.Handle handle, @ByRef CreateInfo.PipelineLayoutCreateInfo cInfo);
};
