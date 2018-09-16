package me.dags.noclip.common.mixin;

import me.dags.noclip.common.EntityNoClipper;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author dags <dags@dags.me>
 */
@Mixin(NetHandlerPlayServer.class)
public class MixinNetHandlerServer {

    @Inject(method = "processCustomPayload(Lnet/minecraft/network/play/client/CPacketCustomPayload;)V", at = @At("RETURN"))
    public void processCustomPayload(CPacketCustomPayload packet, CallbackInfo callbackInfo) {
        if (packet.getChannelName().equals(EntityNoClipper.NOCLIP_CHANNEL)) {
            boolean value = packet.getBufferData().readBoolean();
            getNoClipPlayer().getNoClipData().setNoClipping(value);
        }
    }

    private NetHandlerPlayServer getNetHandler() {
        return NetHandlerPlayServer.class.cast(this);
    }

    private EntityNoClipper getNoClipPlayer() {
        return EntityNoClipper.class.cast(getNetHandler().player);
    }
}
