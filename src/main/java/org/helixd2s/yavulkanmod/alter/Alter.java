package org.helixd2s.yavulkanmod.alter;

import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Namespace;
import org.bytedeco.javacpp.annotation.Platform;

@Namespace("alter")
@Platform(
        library="AlterWrapper",
        include={"Alter/Alter.hpp",}
)
public class Alter {
};
