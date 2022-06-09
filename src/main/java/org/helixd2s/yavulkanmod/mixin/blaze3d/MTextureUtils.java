package org.helixd2s.yavulkanmod.mixin.blaze3d;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.texture.NativeImage;
import org.helixd2s.yavulkanmod.GlContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static org.lwjgl.opengl.EXTMemoryObject.glCreateMemoryObjectsEXT;
import static org.lwjgl.opengl.GL11.*;

@Mixin(TextureUtil.class)
public abstract class MTextureUtils {

    /**
     * @author
     * @reason 
     */
    @Overwrite
    public static void prepareImage(NativeImage.InternalFormat internalFormat, int id, int maxLevel, int width, int height) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlStateManager._bindTexture(id);

        //
        if (maxLevel >= 0) {
            GlStateManager._texParameter(GL_TEXTURE_2D, 33085, maxLevel);
            GlStateManager._texParameter(GL_TEXTURE_2D, 33082, 0);
            GlStateManager._texParameter(GL_TEXTURE_2D, 33083, maxLevel);
            GlStateManager._texParameter(GL_TEXTURE_2D, 34049, 0.0F);
        };

        //
        GlContext.glPrepareImage(internalFormat, id, maxLevel, width, height);
    };
}
