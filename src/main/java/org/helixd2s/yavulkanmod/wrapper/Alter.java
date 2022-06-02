package org.helixd2s.yavulkanmod.wrapper;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import java.util.List;

@Platform(include="Alter/Alter.hpp")
@Namespace("alter")
public class Alter {

    public static class InstanceCreateInfo extends Pointer {
        static { Loader.load(); }
        public InstanceCreateInfo() { allocate(); }
        private native void allocate();

        @Name("appName") @MemberGetter @StdString public native String getAppName();
        @Name("appName") @MemberSetter @StdString public native void putAppName(String name);

        @Name("layerList") @MemberGetter @StdVector @StdString public native List<String> getLayerList();
        @Name("layerList") @MemberSetter @StdVector @StdString public native void putLayerList(List<String> extensions);

        @Name("extensionList") @MemberGetter @StdVector @StdString public native List<String> getExtensionList();
        @Name("extensionList") @MemberSetter @StdVector @StdString public native void putExtensionList(List<String> extensions);
    };

    public static class DeviceCreateInfo extends Pointer {
        static { Loader.load(); }
        public DeviceCreateInfo() { allocate(); }
        private native void allocate();

        @Name("appName") @MemberGetter @StdString public native String getAppName();
        @Name("appName") @MemberSetter @StdString public native void putAppName(String name);

        @Name("layerList") @MemberGetter @StdVector @StdString public native List<String> getLayerList();
        @Name("layerList") @MemberSetter @StdVector @StdString public native void putLayerList(List<String> extensions);

        @Name("extensionList") @MemberGetter @StdVector @StdString public native List<String> getExtensionList();
        @Name("extensionList") @MemberSetter @StdVector @StdString public native void putExtensionList(List<String> extensions);

        @Name("physicalDeviceGroupIndex") @MemberGetter public native int getPhysicalDeviceGroupIndex();
        @Name("physicalDeviceGroupIndex") @MemberSetter public native void putPhysicalDeviceGroupIndex(int index);

        @Name("physicalDeviceIndex") @MemberGetter public native int getPhysicalDeviceIndex();
        @Name("physicalDeviceIndex") @MemberSetter public native void putPhysicalDeviceIndex(int index);
    };

}
