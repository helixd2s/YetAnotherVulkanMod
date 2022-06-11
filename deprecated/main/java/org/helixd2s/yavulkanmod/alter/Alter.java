package org.helixd2s.yavulkanmod.alter;

import org.bytedeco.javacpp.annotation.*;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;
import org.helixd2s.yavulkanmod.alter.objects.ContextObj;

@Platform(
        library="YAV",
        include={"Alter/Alter.hpp",}
)
abstract public class Alter {
    @Name("alter::initialize")
    @SharedPtr
    public static native ContextObj initialize(@ByRef CreateInfo.ContextCreateInfo cInfo);

};
