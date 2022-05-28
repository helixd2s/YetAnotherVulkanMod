package org.helixd2s.yavulkanmod.renderer;

//
import java.util.LinkedHashMap;
import java.util.Map;

//
public class BaseObj {
    public Handle base;
    public Handle handle;

    //
    protected Map<Handle, BaseObj> typedMap = new LinkedHashMap<Handle, BaseObj>();
    protected Map<Long, Object> infoMap = new LinkedHashMap<Long, Object>();

    //
    public BaseObj(Handle base) {
        this.base = base;
        this.typedMap = new LinkedHashMap<Handle, BaseObj>();
        this.infoMap = new LinkedHashMap<Long, Object>();
    };

};
