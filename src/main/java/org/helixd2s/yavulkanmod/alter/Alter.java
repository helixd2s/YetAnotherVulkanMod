package org.helixd2s.yavulkanmod.alter;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;
//import org.lwjgl.vulkan.VkDescriptorImageInfo; // IDK how to use Java Libraries with JavaCpp
import org.bytedeco.opencv.opencv_text.FloatVector;
import org.bytedeco.opencv.opencv_core.StringVector;

@Platform(include="Alter/Alter.hpp")
public class Alter {
    static { Loader.load(); }


    @Name("alter::QueueFamilyCreateInfo")
    public class QueueFamilyCreateInfo extends Pointer {
        static { Loader.load(); }

        private native void allocate();

        @Name("queuePriorities") @MemberGetter public native FloatVector setQueuePriorities();
        @Name("queuePriorities") @MemberSetter public native void setQueuePriorities( FloatVector name);

        @Name("queueFamilyIndex") @MemberGetter public native int getQueueFamilyIndex();
        @Name("queueFamilyIndex") @MemberSetter public native void putQueueFamilyIndex(int name);
    };

    @Name("alter::QueueGetInfo")
    public class QueueGetInfo extends Pointer {
        static { Loader.load(); }

        private native void allocate();

        @Name("queueFamilyIndex") @MemberGetter public native int getQueueFamilyIndex();
        @Name("queueFamilyIndex") @MemberSetter public native void putQueueFamilyIndex(int name);

        @Name("queueIndex") @MemberGetter public native int getQueueIndex();
        @Name("queueIndex") @MemberSetter public native void putQueueIndex(int name);
    };

    @Name("alter::GeometryInfo")
    public class GeometryInfo extends Pointer {
        static { Loader.load(); }

        private native void allocate();
    };

    @Name("alter::InstanceDataInfo")
    public class InstanceDataInfo extends Pointer {
        static { Loader.load(); }

        private native void allocate();
    };

    @Name("cpp21::bucket<alter::GeometryInfo>")
    public class BucketOfGeometryInfo extends Pointer {
        static { Loader.load(); }

        public native int add(@ByRef GeometryInfo ptr);
    };

    @Name("cpp21::bucket<alter::InstanceDataInfo>")
    public class BucketOfInstanceDataInfo extends Pointer {
        static { Loader.load(); }

        public native int add(@ByRef InstanceDataInfo ptr);
    };

    @Name("vk::DescriptorImageInfo")
    public class WrapDescriptorImageInfo extends Pointer {
        static { Loader.load(); };
        private native void allocate();
        WrapDescriptorImageInfo(long address) { super(new Pointer() { { address = address; } }); };
    };

    @Name("cpp21::bucket<vk::DescriptorImageInfo>")
    public class BucketOfDescriptorImageInfo extends Pointer {
        static { Loader.load(); }

        //public native int add(@ByRef @Cast("vk::DescriptorImageInfo*") long ptr);
        public native int add(@ByRef WrapDescriptorImageInfo ptr);
    };

    @Name("alter::InstanceCreateInfo")
    public static class InstanceCreateInfo extends Pointer {
        static { Loader.load(); }

        public InstanceCreateInfo() { allocate(); }
        private native void allocate();

        @Name("appName") @MemberGetter @StdString public native String getAppName();
        @Name("appName") @MemberSetter public native void putAppName(@StdString String name);

        @Name("layerList") @MemberGetter public native StringVector getLayerList();
        @Name("layerList") @MemberSetter public native void putLayerList(@ByRef StringVector extensions);

        @Name("extensionList") @MemberGetter public native StringVector getExtensionList();
        @Name("extensionList") @MemberSetter public native void putExtensionList(@ByRef StringVector extensions);
    };

    @Name("alter::DeviceCreateInfo")
    public static class DeviceCreateInfo extends Pointer {
        static { Loader.load(); }

        public DeviceCreateInfo() { allocate(); }
        private native void allocate();

        @Name("layerList") @MemberGetter public native StringVector getLayerList();
        @Name("layerList") @MemberSetter public native void putLayerList(@ByRef StringVector extensions);

        @Name("extensionList") @MemberGetter public native StringVector getExtensionList();
        @Name("extensionList") @MemberSetter public native void putExtensionList(@ByRef StringVector extensions);

        @Name("physicalDeviceGroupIndex") @MemberGetter public native int getPhysicalDeviceGroupIndex();
        @Name("physicalDeviceGroupIndex") @MemberSetter public native void putPhysicalDeviceGroupIndex(int index);

        @Name("physicalDeviceIndex") @MemberGetter public native int getPhysicalDeviceIndex();
        @Name("physicalDeviceIndex") @MemberSetter public native void putPhysicalDeviceIndex(int index);
    };

}
