package org.helixd2s.yavulkanmod.wrapper;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import java.util.List;

@Platform(include="Alter/Alter.hpp")
@Namespace("alter")
public class Alter {
    static { Loader.load(); }

    public static class InstanceCreateInfo extends Pointer {
        static { Loader.load(); }

        public InstanceCreateInfo() { allocate(); }
        private native void allocate();

        @Name("appName") @MemberGetter @StdString public native String getAppName();
        @Name("appName") @MemberSetter public native void putAppName(@StdString String name);

        @Name("layerList") @MemberGetter public native Std.StringVector getLayerList();
        @Name("layerList") @MemberSetter public native void putLayerList(@ByRef Std.StringVector extensions);

        @Name("extensionList") @MemberGetter public native Std.StringVector getExtensionList();
        @Name("extensionList") @MemberSetter public native void putExtensionList(@ByRef Std.StringVector extensions);
    };

    public static class DeviceCreateInfo extends Pointer {
        static { Loader.load(); }

        public DeviceCreateInfo() { allocate(); }
        private native void allocate();

        @Name("layerList") @MemberGetter public native Std.StringVector getLayerList();
        @Name("layerList") @MemberSetter public native void putLayerList(@ByRef Std.StringVector extensions);

        @Name("extensionList") @MemberGetter public native Std.StringVector getExtensionList();
        @Name("extensionList") @MemberSetter public native void putExtensionList(@ByRef Std.StringVector extensions);

        @Name("physicalDeviceGroupIndex") @MemberGetter public native int getPhysicalDeviceGroupIndex();
        @Name("physicalDeviceGroupIndex") @MemberSetter public native void putPhysicalDeviceGroupIndex(int index);

        @Name("physicalDeviceIndex") @MemberGetter public native int getPhysicalDeviceIndex();
        @Name("physicalDeviceIndex") @MemberSetter public native void putPhysicalDeviceIndex(int index);
    };

}
