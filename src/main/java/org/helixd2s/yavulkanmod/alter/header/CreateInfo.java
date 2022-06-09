package org.helixd2s.yavulkanmod.alter.header;

import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.LongPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.*;
import org.bytedeco.ngraph.StringVector;
import org.bytedeco.opencv.opencv_text.FloatVector;
import org.bytedeco.opencv.opencv_text.IntVector;

@Platform(
        library="YAV",
        include={"Alter/Alter.hpp",}
)
public class CreateInfo {

    @Name("alter::BaseCreateInfo")
    public static class BaseCreateInfo extends Pointer {
        static { Loader.load(); }

        public BaseCreateInfo() { allocate(); }
        private native void allocate();


    };

    @Name("alter::ContextCreateInfo")
    public static class ContextCreateInfo extends BaseCreateInfo {
        static { Loader.load(); }

        public ContextCreateInfo() { allocate(); }
        private native void allocate();
    };

    @Name("alter::InstanceCreateInfo")
    public static class InstanceCreateInfo extends BaseCreateInfo {
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




    @Name("alter::QueueFamilyCreateInfo")
    public class QueueFamilyCreateInfo extends BaseCreateInfo {
        static { Loader.load(); }

        @MemberGetter @ByRef @Cast("int*") public native IntPointer queueFamilyIndex();
        public int getQueueFamilyIndex() { return this.queueFamilyIndex().get(0); };
        public void putQueueFamilyIndex(int queueFamilyIndex) { this.queueFamilyIndex().put(0, queueFamilyIndex); };

        @Name("queuePriorities") @MemberGetter public native FloatVector setQueuePriorities();
        @Name("queuePriorities") @MemberSetter public native void setQueuePriorities(FloatVector name);

        //@Name("queueFamilyIndex") @MemberGetter public native int getQueueFamilyIndex();
        //@Name("queueFamilyIndex") @MemberSetter public native void putQueueFamilyIndex(int name);
    };

    @Name("std::vector<alter::QueueFamilyCreateInfo>")
    public class VectorOfQueueFamilyCreateInfo extends Pointer {
        static { Loader.load(); }

        public VectorOfQueueFamilyCreateInfo() { allocate(); }
        private native void allocate();

        @Name("operator[]")
        @ByRef public native QueueFamilyCreateInfo get(int index);

        @Name("push_back")
        public native void pushBack(@ByRef QueueFamilyCreateInfo ptr);

        //@Name("operator[]")
        //@ByRef public native void put(int index, @ByVal QueueFamilyCreateInfo cInfo);
    };

    @Name("alter::DeviceCreateInfo")
    public static class DeviceCreateInfo extends BaseCreateInfo {
        static { Loader.load(); }

        public DeviceCreateInfo() { allocate(); }
        private native void allocate();

        @Name("queueFamilyInfos") @MemberGetter public native @SharedPtr VectorOfQueueFamilyCreateInfo getQueueFamilyInfos();
        @Name("queueFamilyInfos") @MemberSetter public native void putQueueFamilyInfos(@SharedPtr VectorOfQueueFamilyCreateInfo ptr);

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

    @Name("alter::BufferCreateInfo")
    public static class BufferCreateInfo extends BaseCreateInfo {
        static { Loader.load(); }

        public BufferCreateInfo() { allocate(); }
        private native void allocate();

        @MemberGetter @ByRef @Cast("intptr_t*") public native LongPointer size();
        public long getSize() { return this.size().get(0); };
        public void putSize(long size) { this.size().put(0, size); };

        // needs in future
        @Name("info") @MemberGetter @ByRef public native Core.QueueGetInfo getInfo();
        @Name("info") @MemberSetter public native void putInfo(@ByRef Core.QueueGetInfo info);

        @MemberGetter @ByRef @Cast("int*") public native IntPointer type();
        public int getType() { return this.type().get(0); };
        public void putType(int type) { this.type().put(0, type); };

    };

    @Name("alter::ImageCreateInfo")
    public static class ImageCreateInfo extends BaseCreateInfo {
        static { Loader.load(); }

        public ImageCreateInfo() { allocate(); }
        private native void allocate();

        @MemberGetter @ByRef @Cast("int*") public native IntPointer flags();
        public int getFlags() { return this.flags().get(0); };
        public void putFlags(int flags) { this.flags().put(0, flags); };

        @MemberGetter @ByRef @Cast("int*") public native IntPointer imageType();
        public int getImageType() { return this.imageType().get(0); };
        public void putImageType(int imageType) { this.imageType().put(0, imageType); };

        @MemberGetter @ByRef @Cast("int*") public native IntPointer format();
        public int getFormat() { return this.format().get(0); };
        public void putFormat(int format) { this.format().put(0, format); };

        @MemberGetter @ByRef @Cast("int*") public native IntPointer mipLevelCount();
        public int getMipLevelCount() { return this.mipLevelCount().get(0); };
        public void putMipLevelCount(int mipLevels) { this.mipLevelCount().put(0, mipLevels); };

        @MemberGetter @ByRef @Cast("int*") public native IntPointer layerCount();
        public int getLayerCount() { return this.layerCount().get(0); };
        public void putLayerCount(int layerCount) { this.layerCount().put(0, layerCount); };

        @MemberGetter @ByRef @Cast("int*") public native IntPointer layout();
        public int getLayout() { return this.layout().get(0); };
        public void putLayout(int imageLayout) { this.layout().put(0, imageLayout); };

        @MemberGetter @ByRef @Cast("int*") public native IntPointer type();
        public int getType() { return this.type().get(0); };
        public void putType(int type) { this.type().put(0, type); };

        //
        @Name("extent") @MemberGetter @Cast("void*") @ByRef public native long getExtent();
        @Name("extent") @MemberSetter public native void putExtent(@ByRef @Cast("vk::Extent3D const*") long extent);

        // needs in future
        @Name("info") @MemberGetter @ByRef public native Core.QueueGetInfo getInfo();
        @Name("info") @MemberSetter public native void putInfo(@ByRef Core.QueueGetInfo info);


    };


    @Name("alter::PipelineLayoutCreateInfo")
    public static class PipelineLayoutCreateInfo extends BaseCreateInfo {
        static {
            Loader.load();
        }

        public PipelineLayoutCreateInfo() {
            allocate();
        }

        private native void allocate();

        @Name("info") @MemberGetter @ByRef public native Core.QueueGetInfo getInfo();
        @Name("info") @MemberSetter public native void putInfo(@ByRef Core.QueueGetInfo info);

    };



    @Name("alter::MemoryAllocatorCreateInfo")
    public static class MemoryAllocatorCreateInfo extends BaseCreateInfo {
        static { Loader.load(); }

        public MemoryAllocatorCreateInfo() { allocate(); }
        private native void allocate();
    };

    @Name("alter::UploaderCreateInfo")
    public static class UploaderCreateInfo extends BaseCreateInfo {
        static { Loader.load(); }

        public UploaderCreateInfo() { allocate(); }
        private native void allocate();

        public native @ByVal UploaderCreateInfo use(@Cast("alter::ExtensionName const&") int extensionName);
    };

    @Name("alter::ResourceCreateInfo")
    public static class ResourceCreateInfo extends BaseCreateInfo {
        static { Loader.load(); }

        public ResourceCreateInfo() { allocate(); }
        private native void allocate();

        @Name("imageInfo") public native @MemberGetter @ByRef ImageCreateInfo getImageInfo();
        @Name("imageInfo") public native @MemberSetter void putImageInfo(@ByRef ImageCreateInfo imageCreateInfo);

        @Name("bufferInfo") public native @MemberGetter @ByRef BufferCreateInfo getBufferInfo();
        @Name("bufferInfo") public native @MemberSetter void putBufferInfo(@ByRef BufferCreateInfo bufferCreateInfo);

        public native @ByVal UploaderCreateInfo use(@Cast("alter::ExtensionName const&") int extensionName);
    };


    @Name("alter::GeometryLevelCreateInfo")
    public static class GeometryLevelCreateInfo extends BaseCreateInfo {
        static { Loader.load(); }

        public GeometryLevelCreateInfo() { allocate(); }
        private native void allocate();

        public native @MemberGetter @Name("geometries") @ByRef Core.BucketOfGeometryInfo getGeometries();
        public native @MemberSetter @Name("geometries") void putGeometries(@ByRef Core.BucketOfGeometryInfo geometries);

        public native @MemberGetter @Name("limits") @Cast("std::vector<int>*") @ByRef IntVector getLimits();
        public native @MemberSetter @Name("limits") void putLimits(@Cast("std::vector<uint32_t>*") @ByRef IntVector limits);

        @MemberGetter @ByRef @Cast("intptr_t*") public native LongPointer uploader();
        public long getUploader() { return this.uploader().get(0); };
        public void putUploader(long uploader) { this.uploader().put(0, uploader); };

        @Name("info") @MemberGetter @ByRef public native Core.QueueGetInfo getInfo();
        @Name("info") @MemberSetter public native void putInfo(@ByRef Core.QueueGetInfo info);

    };


    @Name("alter::InstanceLevelCreateInfo")
    public static class InstanceLevelCreateInfo extends BaseCreateInfo {
        static { Loader.load(); }

        public InstanceLevelCreateInfo() { allocate(); }
        private native void allocate();

        public native @MemberGetter @Name("instances") @ByRef Core.BucketOfInstanceDataInfo getInstances();
        public native @MemberSetter @Name("instances") void putInstances(@ByRef Core.BucketOfInstanceDataInfo geometries);

        //
        @MemberGetter @ByRef @Name("limit") @Cast("int*") public native IntPointer limitCpp();
        public int getLimit() { return this.limitCpp().get(0); };
        public void putLimit(int limit) { this.limitCpp().put(0, limit); };

        //
        @MemberGetter @ByRef @Cast("intptr_t*") public native LongPointer uploader();
        public long getUploader() { return this.uploader().get(0); };
        public void putUploader(long uploader) { this.uploader().put(0, uploader); };

        @Name("info") @MemberGetter @ByRef public native Core.QueueGetInfo getInfo();
        @Name("info") @MemberSetter public native void putInfo(@ByRef Core.QueueGetInfo info);

    };


}
