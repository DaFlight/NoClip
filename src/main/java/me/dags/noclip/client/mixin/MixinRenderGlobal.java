package me.dags.noclip.client.mixin;

import me.dags.noclip.client.NoClipClient;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author dags <dags@dags.me>
 */
@Mixin(EntityRenderer.class)
public abstract class MixinRenderGlobal {

    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE", shift = At.Shift.BEFORE,
            target = "Lnet/minecraft/client/renderer/RenderGlobal;setupTerrain(Lnet/minecraft/entity/Entity;DLnet/minecraft/client/renderer/culling/ICamera;IZ)V"))
    private void renderWorldPassHead(int pass, float partialTicks, long finishTimeNano, CallbackInfo callbackInfo) {
        if (NoClipClient.getNoClipData().noClip()) {
            NoClipClient.getNoClipData().setDummySpectator(true);
        }
    }


    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE", shift = At.Shift.AFTER,
            target = "Lnet/minecraft/client/renderer/RenderGlobal;setupTerrain(Lnet/minecraft/entity/Entity;DLnet/minecraft/client/renderer/culling/ICamera;IZ)V"))
    private void renderWorldPassReturn(int pass, float partialTicks, long finishTimeNano, CallbackInfo callbackInfo) {
        if (NoClipClient.getNoClipData().noClip()) {
            NoClipClient.getNoClipData().setDummySpectator(false);
        }
    }
}
