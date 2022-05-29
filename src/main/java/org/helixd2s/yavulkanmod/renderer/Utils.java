package org.helixd2s.yavulkanmod.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;
import java.util.List;

public class Utils {
    public static FloatBuffer list2FloatBuffer(List<Float> arr) {

        ByteBuffer ibb = ByteBuffer.allocateDirect(arr.size() * 4);
        ibb.order(ByteOrder.nativeOrder());
        FloatBuffer fbb = ibb.asFloatBuffer();
        for (float f : arr) {
            fbb.put(f);
        }
        //??????
        fbb.position(0);
        return fbb;
    }

    public static ByteBuffer str_to_bb(String msg, Charset charset){
        return ByteBuffer.wrap(msg.getBytes(charset));
    }

    public static String bb_to_str(ByteBuffer buffer, Charset charset){
        byte[] bytes;
        if(buffer.hasArray()) {
            bytes = buffer.array();
        } else {
            bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
        }
        return new String(bytes, charset);
    }
}
