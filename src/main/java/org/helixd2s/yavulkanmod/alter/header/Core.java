package org.helixd2s.yavulkanmod.alter.header;

import org.bytedeco.javacpp.IntPointer;
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

        private native void allocate();
        public Handle() { allocate(); }
    };

    @Name("alter::QueueGetInfo")
    public class QueueGetInfo extends Pointer {
        static { Loader.load(); }

        private native void allocate();
        public QueueGetInfo() { allocate(); }

        @MemberGetter @Cast("int*") @ByRef public native IntPointer queueFamilyIndex();
        public int getQueueFamilyIndex() { return this.queueFamilyIndex().get(0); };
        public void putQueueFamilyIndex(int queueFamilyIndex) { this.queueFamilyIndex().put(0, queueFamilyIndex); };

        @MemberGetter @Cast("int*") @ByRef public native IntPointer queueIndex();
        public int getQueueIndex() { return this.queueIndex().get(0); };
        public void putQueueIndex(int queueIndex) { this.queueIndex().put(0, queueIndex); };
    };

    @Name("alter::InstanceDataInfo")
    public class InstanceDataInfo extends Pointer {
        static { Loader.load(); }

        private native void allocate();
        public InstanceDataInfo() { allocate(); }
    };

    @Name("alter::GeometryInfo")
    public class GeometryInfo extends Pointer {
        static { Loader.load(); }

        private native void allocate();
        public GeometryInfo() { allocate(); }
    };

    @Name("cpp21::bucket<alter::InstanceDataInfo>")
    public class BucketOfInstanceDataInfo extends Pointer {
        static { Loader.load(); }

        public native int add(@ByRef InstanceDataInfo ptr);
        @Name("operator[]") @ByRef public native InstanceDataInfo get(int index);

        private native void allocate();
        public BucketOfInstanceDataInfo() { allocate(); }
    };

    @Name("cpp21::bucket<alter::GeometryInfo>")
    public class BucketOfGeometryInfo extends Pointer {
        static { Loader.load(); }

        //public native static @ByRef BucketOfGeometryInfo make();
        public native int add(@ByRef GeometryInfo ptr);
        @Name("operator[]") @ByRef public native GeometryInfo get(int index);

        private native void allocate();
        public BucketOfGeometryInfo() { allocate(); }
    };

    @Name("cpp21::bucket<vk::DescriptorImageInfo>")
    public class BucketOfDescriptorImageInfo extends Pointer {
        static { Loader.load(); }

        //public native static @ByRef BucketOfDescriptorImageInfo make();
        public native int add(@ByRef @Cast("vk::DescriptorImageInfo*") long ptr);

        @Name("operator[]") @Cast("void*") @ByRef public native long get(int index);

        private native void allocate();
        public BucketOfDescriptorImageInfo() { allocate(); }
    };

}
