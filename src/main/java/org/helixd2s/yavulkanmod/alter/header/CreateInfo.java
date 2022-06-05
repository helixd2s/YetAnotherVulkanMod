package org.helixd2s.yavulkanmod.alter.header;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.*;
import org.bytedeco.ngraph.StringVector;
import org.bytedeco.opencv.opencv_text.FloatVector;

@Platform(
        library="YAV",
        include={"Alter/Alter.hpp",}
)
public class CreateInfo {

    @Name("alter::ContextCreateInfo")
    public static class ContextCreateInfo extends Pointer {
        static { Loader.load(); }

        public ContextCreateInfo() { allocate(); }
        private native void allocate();
    };

    @Name("alter::InstanceCreateInfo")
    public static class InstanceCreateInfo extends Pointer {
        static { Loader.load(); }

        public InstanceCreateInfo() { allocate(); }
        private native void allocate();

        @Name("appName") @MemberGetter
        @StdString
        public native String getAppName();
        @Name("appName") @MemberSetter
        public native void putAppName(@StdString String name);

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

        @Name("layerList") @MemberGetter
        public native StringVector getLayerList();
        @Name("layerList") @MemberSetter
        public native void putLayerList(@ByRef StringVector extensions);

        @Name("extensionList") @MemberGetter public native StringVector getExtensionList();
        @Name("extensionList") @MemberSetter public native void putExtensionList(@ByRef StringVector extensions);

        @Name("physicalDeviceGroupIndex") @MemberGetter public native int getPhysicalDeviceGroupIndex();
        @Name("physicalDeviceGroupIndex") @MemberSetter public native void putPhysicalDeviceGroupIndex(int index);

        @Name("physicalDeviceIndex") @MemberGetter public native int getPhysicalDeviceIndex();
        @Name("physicalDeviceIndex") @MemberSetter public native void putPhysicalDeviceIndex(int index);
    };

    @Name("alter::QueueFamilyCreateInfo")
    public class QueueFamilyCreateInfo extends Pointer {
        static { Loader.load(); }

        public QueueFamilyCreateInfo() { allocate(); }
        private native void allocate();

        @Name("queuePriorities") @MemberGetter
        public native FloatVector setQueuePriorities();
        @Name("queuePriorities") @MemberSetter
        public native void setQueuePriorities(FloatVector name);

        @Name("queueFamilyIndex") @MemberGetter public native int getQueueFamilyIndex();
        @Name("queueFamilyIndex") @MemberSetter public native void putQueueFamilyIndex(int name);
    };

    @Name("alter::ImageCreateInfo")
    public class ImageCreateInfo extends Pointer {
        static { Loader.load(); }

        public ImageCreateInfo() { allocate(); }
        private native void allocate();

        long getExtent() { return this.address() + 12; }; // temporary workaround
        //@Name("extent") @MemberGetter @Cast("uintptr_t") @ByPtrRef public native long getExtent();
        @Name("extent") @MemberSetter public native void putExtent(@ByRef @Cast("vk::Extent3D*") long extent);
    };

}
