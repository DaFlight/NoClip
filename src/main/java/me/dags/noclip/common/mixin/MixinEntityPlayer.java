package me.dags.noclip.common.mixin;

import me.dags.noclip.common.EntityNoClipper;
import me.dags.noclip.common.NoClipData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author dags <dags@dags.me>
 */
@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase implements EntityNoClipper {

    private final NoClipData noClipData = new NoClipData();
    private boolean noClipping = false;
    private boolean disableDamage = false;

    public MixinEntityPlayer(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Override
    public NoClipData getNoClipData() {
        return noClipData;
    }

    @Inject(method = "onUpdate()V", at = @At("RETURN"))
    public void onUpdate(CallbackInfo callbackInfo) {
        if (getNoClipData().noClip()) {
            noClip = true;
            if (!noClipping) {
                noClipping = true;
                disableDamage = getCapabilities().disableDamage;
                getCapabilities().disableDamage = true;
                sendAbilities();
            }
        } else if (noClipping) {
            noClipping = false;
            getCapabilities().disableDamage = disableDamage;
            sendAbilities();
        }
    }

    @Override
    public void moveEntity(double x, double y, double z) {
        if (getNoClipData().noClip() && getCapabilities().isFlying) {
            this.noClip = true;
        }
        super.moveEntity(x, y, z);
    }

    private PlayerCapabilities getCapabilities() {
        return EntityPlayer.class.cast(this).capabilities;
    }

    private void sendAbilities() {
        if (EntityPlayer.class.isInstance(this)) {
            EntityPlayer.class.cast(this).sendPlayerAbilities();
        }
    }
}
