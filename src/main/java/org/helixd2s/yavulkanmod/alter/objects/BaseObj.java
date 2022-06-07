package org.helixd2s.yavulkanmod.alter.objects;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.*;
import org.helixd2s.yavulkanmod.alter.header.Core;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;

@Name("alter::BaseObj")
@Platform(
        library="YAV",
        include={"Alter/Alter.hpp",}
)
public class BaseObj extends Pointer {
    static { Loader.load(); }
    public BaseObj() { allocate(); }
    private native void allocate();

    @Name("getHandle")
    public native @ByRef Core.Handle getHandle();

    @Name("getExtHandle")
    public native @Cast("intptr_t&") @ByRef long getExtHandle();
};
