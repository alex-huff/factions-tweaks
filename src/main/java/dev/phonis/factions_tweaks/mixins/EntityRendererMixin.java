package dev.phonis.factions_tweaks.mixins;

import dev.phonis.factions_tweaks.config.ConfigurationManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin
{

    @Redirect(method = "setupFog(IF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;setFogDensity(F)V"))
    private void setFogDensity(float density)
    {
        if (!ConfigurationManager.INSTANCE.utilityClearFluids)
        {
            GlStateManager.setFogDensity(density);
            return;
        }
        GlStateManager.setFogDensity(.01f);
    }

    @Redirect(method = "getFOVModifier(FZ)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getMaterial()Lnet/minecraft/block/material/Material;"))
    private Material getMaterial(Block block)
    {
        if (!ConfigurationManager.INSTANCE.utilityClearFluids)
        {
            return block.getMaterial();
        }
        return Material.air;
    }

}
