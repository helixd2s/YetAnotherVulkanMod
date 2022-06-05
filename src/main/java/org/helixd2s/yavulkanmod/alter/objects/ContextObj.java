package org.helixd2s.yavulkanmod.alter.objects;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.SharedPtr;
import org.helixd2s.yavulkanmod.alter.header.Core;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;

@Name("alter::ContextObj")
@Platform(
        library="YAV",
        include={"Alter/Alter.hpp",}
)
public class ContextObj extends Pointer {
    static { Loader.load(); }
    public ContextObj() { allocate(); }
    private native void allocate();

    @Name("make")
    native static @SharedPtr ContextObj make(@ByRef Core.Handle handle, @ByRef CreateInfo.ContextCreateInfo cInfo);
};
