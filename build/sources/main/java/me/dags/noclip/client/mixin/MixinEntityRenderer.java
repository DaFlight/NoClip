package me.dags.noclip.client.mixin;

import me.dags.noclip.client.NoClipClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author dags <dags@dags.me>
 */
@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    private float gammaSetting = 0F;

    @Inject(method = "updateLightmap(F)V", at = @At("HEAD"))
    private void updateLightmapHead(float f, CallbackInfo callbackInfo) {
        if (NoClipClient.getNoClipData().fullBright() || NoClipClient.getNoClipData().isInsideBlock()) {
            gammaSetting = Minecraft.getMinecraft().gameSettings.gammaSetting;
            Minecraft.getMinecraft().gameSettings.gammaSetting = 10000F;
        }
    }

    @Inject(method = "updateLightmap(F)V", at = @At("RETURN"))
    private void updateLightmapReturn(float f, CallbackInfo callbackInfo) {
        if (NoClipClient.getNoClipData().fullBright() || NoClipClient.getNoClipData().isInsideBlock()) {
            Minecraft.getMinecraft().gameSettings.gammaSetting = gammaSetting;
        }
    }
}
