package org.helixd2s.yavulkanmod.alter.header;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.*;
import org.bytedeco.opencv.opencv_core.StringVector;
import org.bytedeco.opencv.opencv_text.FloatVector;

@Platform(
        library="YAV",
        include={"Alter/Alter.hpp",}
)
public class Core {

    @Name("alter::Handle")
    public class Handle extends Pointer {
        static { Loader.load(); }
    };

    @Name("alter::QueueGetInfo")
    public class QueueGetInfo extends Pointer {
        static { Loader.load(); }

        private native void allocate();

        @Name("queueFamilyIndex") @MemberGetter
        public native int getQueueFamilyIndex();
        @Name("queueFamilyIndex") @MemberSetter
        public native void putQueueFamilyIndex(int name);

        @Name("queueIndex") @MemberGetter public native int getQueueIndex();
        @Name("queueIndex") @MemberSetter public native void putQueueIndex(int name);
    };

    @Name("alter::InstanceDataInfo")
    public class InstanceDataInfo extends Pointer {
        static { Loader.load(); }

        private native void allocate();
    };

    @Name("alter::GeometryInfo")
    public class GeometryInfo extends Pointer {
        static { Loader.load(); }

        private native void allocate();
    };

    @Name("cpp21::bucket<alter::InstanceDataInfo>")
    public class BucketOfInstanceDataInfo extends Pointer {
        static { Loader.load(); }

        public native int add(@ByRef InstanceDataInfo ptr);
    };

    @Name("cpp21::bucket<alter::GeometryInfo>")
    public class BucketOfGeometryInfo extends Pointer {
        static { Loader.load(); }

        public native int add(@ByRef GeometryInfo ptr);
    };

    @Name("cpp21::bucket<vk::DescriptorImageInfo>")
    public class BucketOfDescriptorImageInfo extends Pointer {
        static { Loader.load(); }

        public native int add(@ByRef @Cast("vk::DescriptorImageInfo*") long ptr);
    };

}
