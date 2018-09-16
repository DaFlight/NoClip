package me.dags.noclip.client.mixin;

import com.mojang.authlib.GameProfile;
import me.dags.noclip.client.NoClipClient;
import me.dags.noclip.common.EntityNoClipper;
import me.dags.noclip.common.NoClipData;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author dags <dags@dags.me>
 */
@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer implements EntityNoClipper {

    public MixinEntityPlayerSP(World worldIn, GameProfile playerProfile) {
        super(worldIn, playerProfile);
    }

    @Override
    public NoClipData getNoClipData() {
        // client keeps a static version of NoClipData so that preferences persist after re-spawning
        return NoClipClient.getNoClipData();
    }

    @Override
    public void preparePlayerToSpawn() {
        super.preparePlayerToSpawn();
        NoClipClient.sendNoClipData();
    }

    @Override
    public boolean pushOutOfBlocks(double x, double y, double z) {
        return !(getNoClipData().noClip() && capabilities.isFlying) && super.pushOutOfBlocks(x, y, z);
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        boolean result = super.isEntityInsideOpaqueBlock();
        getNoClipData().setInsideBlock(result);
        return !getNoClipData().noClip() && result;
    }

    @Override
    public boolean isSpectator() {
        return getNoClipData().spectator() || super.isSpectator();
    }
}
