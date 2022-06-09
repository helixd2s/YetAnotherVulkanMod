package org.helixd2s.yavulkanmod.mixin.gl;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import org.helixd2s.yavulkanmod.GlContext;
import org.helixd2s.yavulkanmod.GlOverride;
import org.helixd2s.yavulkanmod.ResourceImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.mojang.blaze3d.systems.RenderSystem.assertOnRenderThreadOrInit;
import static org.lwjgl.opengl.GL15.*;

// TODO: inject into glDrawElements
@Mixin(VertexBuffer.class)
public abstract class MVertexBuffer {

    @Shadow private int vertexCount;
    @Shadow private int vertexBufferId;
    @Shadow private int indexBufferId;
    @Shadow private boolean hasNoIndexBuffer;
    @Shadow private VertexFormat vertexFormat;

    @Shadow abstract public VertexFormat getVertexFormat();

    //
    @Redirect(method = "uploadInternal(Lnet/minecraft/client/render/BufferBuilder;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;glBufferData(ILjava/nio/ByteBuffer;I)V"))
    public void glBufferData(int target, ByteBuffer data, int usage) {
        assertOnRenderThreadOrInit();
        long defaultSize = Math.max((target == GL_ARRAY_BUFFER ? 1024 * 1024 : 65536) * 4 , data.limit());
        GlOverride.glBufferData(defaultSize, target, data, usage);
    };

    // bind to draw currency
    @Inject(method="bind()V", at=@At("HEAD"))
    public void bind(CallbackInfo ci) {
        assertOnRenderThreadOrInit();
        GlContext.currentVertexFormat = this.getVertexFormat();
        GlContext.currentGlVertexBuffer = vertexBufferId;
        GlContext.currentGlIndexBuffer = indexBufferId;
        GlContext.hasIndexBuffer = !hasNoIndexBuffer;
        GlContext.currentVertexOffset = 0;
        GlContext.currentIndexOffset = 0;

        //
    };



};
