package org.helixd2s.yavulkanmod.alter.header;

import org.bytedeco.javacpp.*;
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

        @MemberGetter @ByRef @Cast("intptr_t*") public native LongPointer value();
        public long getValue() { return this.value().get(0); };
        public void putValue(long size) { this.value().put(0, size); };

        @MemberGetter @ByRef @Cast("int*") public native IntPointer type();
        public int getType() { return this.type().get(0); };
        public void putType(int type) { this.type().put(0, type); };

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




    @Name("alter::BufferViewRegion")
    public class BufferViewRegion extends Pointer {
        static { Loader.load(); }

        private native void allocate();
        public BufferViewRegion() { allocate(); }

        @MemberGetter @Cast("intptr_t*") @ByRef public native LongPointer deviceAddress();
        public long getDeviceAddress() { return this.deviceAddress().get(0); };
        public void putDeviceAddress(long deviceAddress) { this.deviceAddress().put(0, deviceAddress); };

        @MemberGetter @Cast("int*") @ByRef public native IntPointer stride();
        public int getStride() { return this.stride().get(0); };
        public void putStride(int geometryCount) { this.stride().put(0, geometryCount); };

        @MemberGetter @Cast("int*") @ByRef public native IntPointer size();
        public int getSize() { return this.size().get(0); };
        public void putSize(int size) { this.size().put(0, size); };
    };


    @Name("alter::BufferViewInfo")
    public class BufferViewInfo extends Pointer {
        static { Loader.load(); }

        private native void allocate();
        public BufferViewInfo() { allocate(); }

        @Name("region") @MemberGetter @ByRef public native BufferViewRegion getBufferRegion();
        @Name("region") @MemberSetter public native void putBufferRegion(@ByRef BufferViewRegion region);

        @MemberGetter @Cast("int*") @ByRef public native IntPointer format();
        public int getFormat() { return this.format().get(0); };
        public void putFormat(int format) { this.format().put(0, format); };

        @MemberGetter @Cast("int*") @ByRef public native IntPointer flags();
        public int getFlags() { return this.flags().get(0); };
        public void getFlags(int flags) { this.flags().put(0, flags); };
    };


    @Name("alter::InstanceInfo")
    public class InstanceInfo extends Pointer {
        static { Loader.load(); }

        private native void allocate();
        public InstanceInfo() { allocate(); }


        @MemberGetter @Cast("intptr_t*") @ByRef public native LongPointer geometryReference();
        public long getGeometryReference() { return this.geometryReference().get(0); };
        public void putGeometryReference(long geometryReference) { this.geometryReference().put(0, geometryReference); };

        @MemberGetter @Cast("int*") @ByRef public native IntPointer geometryCount();
        public int getGeometryCount() { return this.geometryCount().get(0); };
        public void putGeometryCount(int geometryCount) { this.geometryCount().put(0, geometryCount); };

        @MemberGetter @Cast("float*") @ByRef public native FloatPointer transform();
        @MemberGetter @Cast("float*") @ByRef public native FloatPointer prevTransform();
    };

    @Name("alter::InstanceDataInfo")
    public class InstanceDataInfo extends Pointer {
        static { Loader.load(); }

        private native void allocate();
        public InstanceDataInfo() { allocate(); }

        @Name("instanceInfo") public native @MemberGetter @ByRef InstanceInfo getInstanceData();
        @Name("instanceInfo") public native @MemberSetter void putInstanceData(@ByRef InstanceInfo instanceData);

        // needs LWJGL VkAccelerationStructureInstanceKHR;
        @Name("instanceDevInfo") public native @MemberGetter @Cast("void*") @ByRef long getInstanceDevInfo();
        @Name("instanceDevInfo") public native @MemberSetter void putInstanceDevInfo(@Cast("alter::InstanceDevInfo*") @ByRef long instanceDev);

    };

    @Name("alter::GeometryInfo")
    public class GeometryInfo extends Pointer {
        static { Loader.load(); }

        private native void allocate();
        public GeometryInfo() { allocate(); }

        @Name("vertices") @MemberGetter @ByRef public native BufferViewInfo getVertices();
        @Name("vertices") @MemberSetter public native void putVertices(@ByRef BufferViewInfo region);

        @Name("indices") @MemberGetter @ByRef public native BufferViewInfo getIndices();
        @Name("indices") @MemberSetter public native void putIndices(@ByRef BufferViewInfo region);

        @Name("transform") @MemberGetter @ByRef public native BufferViewInfo getTransform();
        @Name("transform") @MemberSetter public native void putTransform(@ByRef BufferViewInfo region);


        @MemberGetter @Cast("intptr_t*") @ByRef public native LongPointer previousRef();
        public long getPreviousRef() { return this.previousRef().get(0); };
        public void putPreviousRef(long previousRef) { this.previousRef().put(0, previousRef); };


        @MemberGetter @Cast("intptr_t*") @ByRef public native LongPointer extensionRef();
        public long getExtensionRef() { return this.extensionRef().get(0); };
        public void putExtensionRef(long extensionRef) { this.extensionRef().put(0, extensionRef); };


        @MemberGetter @Cast("intptr_t*") @ByRef public native LongPointer materialRef();
        public long getMaterialRef() { return this.materialRef().get(0); };
        public void putMaterialRef(long materialRef) { this.materialRef().put(0, materialRef); };

        @MemberGetter @Cast("int*") @ByRef public native IntPointer primitiveCount();
        public int getPrimitiveCount() { return this.primitiveCount().get(0); };
        public void putPrimitiveCount(int primitiveCount) { this.primitiveCount().put(0, primitiveCount); };

        @MemberGetter @Cast("int*") @ByRef public native IntPointer flags();
        public int getFlags() { return this.flags().get(0); };
        public void putFlags(int flags) { this.flags().put(0, flags); };

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
