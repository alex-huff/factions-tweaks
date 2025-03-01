package dev.phonis.factions_tweaks.mixins;

import dev.phonis.factions_tweaks.tweaks.AutoClicker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin
{

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isUsingItem()Z", ordinal = 0))
    private void isUsingItem(CallbackInfo ci)
    {
        if (!Minecraft.getMinecraft().thePlayer.isUsingItem())
        {
            AutoClicker.INSTANCE.onProcessClicks();
        }
    }

    @Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"))
    private boolean isKeyDown(KeyBinding keyBinding)
    {
        if (keyBinding.compareTo(Minecraft.getMinecraft().gameSettings.keyBindAttack) == 0)
        {
            return AutoClicker.INSTANCE.shouldHoldAttack();
        }
        if (keyBinding.compareTo(Minecraft.getMinecraft().gameSettings.keyBindUseItem) == 0)
        {
            return AutoClicker.INSTANCE.shouldHoldUse();
        }
        return keyBinding.isKeyDown();
    }

}
