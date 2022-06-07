package org.helixd2s.yavulkanmod.mixin.texture;

import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.ResourceTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ResourceTexture.class)
public abstract class MResourceTexture {

    /**
     * @author
     */
    @Overwrite
    private void upload(NativeImage image, boolean blur, boolean clamp) {
        TextureUtil.prepareImage(this.getGlId(), 0, image.getWidth(), image.getHeight());
        image.upload(0, 0, 0, 0, 0, image.getWidth(), image.getHeight(), blur, clamp, false, true);
    }

    @Shadow
    abstract public int getGlId();

}
