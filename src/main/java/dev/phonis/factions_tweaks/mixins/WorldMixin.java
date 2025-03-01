package dev.phonis.factions_tweaks.mixins;

import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class WorldMixin
{

    @Inject(method = "checkLightFor(Lnet/minecraft/world/EnumSkyBlock;Lnet/minecraft/util/BlockPos;)Z", at = @At(value = "HEAD"), cancellable = true)
    private void checkLightFor(EnumSkyBlock p_checkLightFor_1_, BlockPos p_checkLightFor_2_,
                               CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(false);
    }

}
