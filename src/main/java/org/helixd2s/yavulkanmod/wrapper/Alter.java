package org.helixd2s.yavulkanmod.wrapper;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import java.util.List;

@Platform(include="Alter/Alter.hpp")
public class Alter {
    static { Loader.load(); }

    @Name("std::vector<std::string>")
    public class StringVector extends Pointer {
        static { Loader.load(); }

        /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
        public StringVector(Pointer p) { super(p); }
        public StringVector(BytePointer value) { this(1); put(0, value); }
        public StringVector(BytePointer ... array) { this(array.length); put(array); }
        public StringVector(String value) { this(1); put(0, value); }
        public StringVector(String ... array) { this(array.length); put(array); }
        public StringVector()       { allocate();  }
        public StringVector(long n) { allocate(n); }
        private native void allocate();
        private native void allocate(@Cast("size_t") long n);
        public native @Name("operator =") @ByRef StringVector put(@ByRef StringVector x);

        public boolean empty() { return size() == 0; }
        public native long size();
        public void clear() { resize(0); }
        public native void resize(@Cast("size_t") long n);

        @Index(function = "at") public native @StdString BytePointer get(@Cast("size_t") long i);
        public native StringVector put(@Cast("size_t") long i, BytePointer value);
        @ValueSetter @Index(function = "at") public native StringVector put(@Cast("size_t") long i, @StdString String value);

        public native @ByVal Iterator insert(@ByVal Iterator pos, @StdString BytePointer value);
        public native @ByVal Iterator erase(@ByVal Iterator pos);
        public native @ByVal Iterator begin();
        public native @ByVal Iterator end();
        @NoOffset @Name("iterator") public static class Iterator extends Pointer {
            public Iterator(Pointer p) { super(p); }
            public Iterator() { }

            public native @Name("operator ++") @ByRef Iterator increment();
            public native @Name("operator ==") boolean equals(@ByRef Iterator it);
            public native @Name("operator *") @StdString BytePointer get();
        }

        public BytePointer[] get() {
            BytePointer[] array = new BytePointer[size() < Integer.MAX_VALUE ? (int)size() : Integer.MAX_VALUE];
            for (int i = 0; i < array.length; i++) {
                array[i] = get(i);
            }
            return array;
        }
        @Override public String toString() {
            return java.util.Arrays.toString(get());
        }

        public BytePointer pop_back() {
            long size = size();
            BytePointer value = get(size - 1);
            resize(size - 1);
            return value;
        }
        public StringVector push_back(BytePointer value) {
            long size = size();
            resize(size + 1);
            return put(size, value);
        }
        public StringVector put(BytePointer value) {
            if (size() != 1) { resize(1); }
            return put(0, value);
        }
        public StringVector put(BytePointer ... array) {
            if (size() != array.length) { resize(array.length); }
            for (int i = 0; i < array.length; i++) {
                put(i, array[i]);
            }
            return this;
        }

        public StringVector push_back(String value) {
            long size = size();
            resize(size + 1);
            return put(size, value);
        }
        public StringVector put(String value) {
            if (size() != 1) { resize(1); }
            return put(0, value);
        }
        public StringVector put(String ... array) {
            if (size() != array.length) { resize(array.length); }
            for (int i = 0; i < array.length; i++) {
                put(i, array[i]);
            }
            return this;
        }
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
