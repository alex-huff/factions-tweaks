package dev.phonis.factions_tweaks.mixins;

import dev.phonis.factions_tweaks.tweaks.HidePlayers;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Render.class)
public class RenderMixin<T extends Entity>
{

    @Inject(method = "shouldRender(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;DDD)Z", at = @At(value = "HEAD"), cancellable = true)
    private void shouldRender(T entity, ICamera camera, double x, double y, double z,
                              CallbackInfoReturnable<Boolean> cir)
    {
        if (HidePlayers.INSTANCE.shouldHidePlayers() && entity instanceof EntityOtherPlayerMP)
        {
            cir.setReturnValue(false);
        }
    }

}
