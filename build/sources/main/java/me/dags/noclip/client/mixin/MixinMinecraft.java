package me.dags.noclip.client.mixin;

import me.dags.noclip.client.NoClipClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author dags <dags@dags.me>
 */
@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Inject(method = "runTick()V", at = @At("RETURN"))
    public void endRunTick(CallbackInfo callbackInfo) {
        NoClipClient.onTick(Minecraft.getMinecraft().inGameHasFocus);
    }
}
