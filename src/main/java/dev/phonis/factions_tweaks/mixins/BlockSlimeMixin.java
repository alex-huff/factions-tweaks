package dev.phonis.factions_tweaks.mixins;

import dev.phonis.factions_tweaks.config.ConfigurationManager;
import net.minecraft.block.BlockSlime;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockSlime.class)
public class BlockSlimeMixin
{

    @Inject(method = "onEntityCollidedWithBlock(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/entity/Entity;)V", at = @At(value = "HEAD"), cancellable = true)
    private void onEntityCollidedWithBlock(World world, BlockPos blockPos, Entity entity, CallbackInfo ci)
    {
        if (!(entity instanceof EntityPlayerSP) || !ConfigurationManager.INSTANCE.movementSmoothWalk)
        {
            return;
        }
        ci.cancel();
    }

    @Inject(method = "onLanded(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;)V", at = @At(value = "HEAD"), cancellable = true)
    private void onLanded(World world, Entity entity, CallbackInfo ci)
    {
        if (!(entity instanceof EntityPlayerSP) || !ConfigurationManager.INSTANCE.movementSmoothWalk)
        {
            return;
        }
        entity.motionY = 0.0;
        ci.cancel();
    }

    @Inject(method = "onFallenUpon(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/entity/Entity;F)V", at = @At(value = "HEAD"), cancellable = true)
    private void onFallenUpon(World world, BlockPos blockPos, Entity entity, float fallDistance, CallbackInfo ci)
    {
        if (!(entity instanceof EntityPlayerSP) || !ConfigurationManager.INSTANCE.movementSmoothWalk)
        {
            return;
        }
        entity.fall(fallDistance, 1.0F);
        ci.cancel();
    }

}
