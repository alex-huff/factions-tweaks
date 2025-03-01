package dev.phonis.factions_tweaks.mixins;

import dev.phonis.factions_tweaks.config.ConfigurationManager;
import dev.phonis.factions_tweaks.tweaks.HidePlayers;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityLivingBase.class)
public class EntityLivingBaseMixin
{

    /**
     * @author phonis
     * @reason selective entity targeting
     */
    @Overwrite
    public boolean canBeCollidedWith()
    {
        EntityLivingBase entityLivingBase = (EntityLivingBase) (Object) this;
        boolean shouldHide = HidePlayers.INSTANCE.shouldHidePlayers() &&
                             entityLivingBase instanceof EntityOtherPlayerMP;
        return !shouldHide && !entityLivingBase.isDead;
    }

    @Redirect(method = "moveEntityWithHeading(FF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Block;slipperiness:F", opcode = Opcodes.GETFIELD))
    private float moveEntityWithHeading(Block block)
    {
        if (!((Object) this instanceof EntityPlayerSP) || !ConfigurationManager.INSTANCE.movementSmoothWalk)
        {
            return block.slipperiness;
        }
        return .6F;
    }

}
