package dev.phonis.factions_tweaks.keybind;

import dev.phonis.factions_tweaks.config.ConfigurationManager;
import dev.phonis.factions_tweaks.tweaks.AutoClicker;
import dev.phonis.factions_tweaks.tweaks.HidePlayers;
import dev.phonis.factions_tweaks.tweaks.InstantBlockClicker;
import dev.phonis.factions_tweaks.tweaks.SelectiveBlockTargeting;
import dev.phonis.factions_tweaks.util.MinecraftUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class Keybinds
{

    public static final Keybinds INSTANCE = new Keybinds();
    public static final KeyBinding togglePlayers = new KeyBinding("Toggle Players", Keyboard.KEY_P, "Factions Tweaks");
    public static final KeyBinding toggleAutoAttack
        = new KeyBinding("Toggle Auto Attack", Keyboard.KEY_NUMPAD7, "Factions Tweaks");
    public static final KeyBinding toggleSemiAutoAttack
        = new KeyBinding("Toggle Semi-auto Attack", Keyboard.KEY_NUMPAD8, "Factions Tweaks");
    public static final KeyBinding toggleAutoUse
        = new KeyBinding("Toggle Auto Use", Keyboard.KEY_NUMPAD9, "Factions Tweaks");
    public static final KeyBinding toggleSemiAutoUse
        = new KeyBinding("Toggle Semi-auto Use", Keyboard.KEY_NUMPAD0, "Factions Tweaks");
    public static final KeyBinding toggleSelectiveBlockTargeting
        = new KeyBinding("Toggle Selective Block Targeting", Keyboard.KEY_HOME, "Factions Tweaks");
    public static final KeyBinding toggleSmoothWalk
        = new KeyBinding("Toggle Smooth Walk", Keyboard.KEY_DELETE, "Factions Tweaks");
    public static final KeyBinding toggleInstantBlockClicker
        = new KeyBinding("Toggle Instant Block Clicker", Keyboard.KEY_PAUSE, "Factions Tweaks");

    public static void register()
    {
        ClientRegistry.registerKeyBinding(Keybinds.togglePlayers);
        ClientRegistry.registerKeyBinding(Keybinds.toggleAutoAttack);
        ClientRegistry.registerKeyBinding(Keybinds.toggleSemiAutoAttack);
        ClientRegistry.registerKeyBinding(Keybinds.toggleAutoUse);
        ClientRegistry.registerKeyBinding(Keybinds.toggleSemiAutoUse);
        ClientRegistry.registerKeyBinding(Keybinds.toggleSelectiveBlockTargeting);
        ClientRegistry.registerKeyBinding(Keybinds.toggleSmoothWalk);
        ClientRegistry.registerKeyBinding(Keybinds.toggleInstantBlockClicker);
    }

    @SubscribeEvent
    public void onKey(InputEvent event)
    {
        while (Keybinds.togglePlayers.isPressed())
        {
            String enabledSpecifier = HidePlayers.INSTANCE.togglePlayers() ? "enabled" : "disabled";
            MinecraftUtil.sendLocalMessage("players " + enabledSpecifier);
        }
        while (Keybinds.toggleAutoAttack.isPressed())
        {
            AutoClicker.INSTANCE.toggleAutoAttack();
        }
        while (Keybinds.toggleSemiAutoAttack.isPressed())
        {
            String enabledSpecifier = ConfigurationManager.INSTANCE.toggleAutoClickerSemiAutoAttack() ? "enabled"
                                                                                                      : "disabled";
            MinecraftUtil.sendLocalMessage("semi-auto attack " + enabledSpecifier);
        }
        while (Keybinds.toggleAutoUse.isPressed())
        {
            AutoClicker.INSTANCE.toggleAutoUse();
        }
        while (Keybinds.toggleSemiAutoUse.isPressed())
        {
            String enabledSpecifier = ConfigurationManager.INSTANCE.toggleAutoClickerSemiAutoUse() ? "enabled"
                                                                                                   : "disabled";
            MinecraftUtil.sendLocalMessage("semi-auto use " + enabledSpecifier);
        }
        while (Keybinds.toggleSelectiveBlockTargeting.isPressed())
        {
            SelectiveBlockTargeting.INSTANCE.toggleSelectiveBlockTargeting();
        }
        while (Keybinds.toggleSmoothWalk.isPressed())
        {
            String enabledSpecifier = ConfigurationManager.INSTANCE.toggleSmoothWalk() ? "enabled" : "disabled";
            MinecraftUtil.sendLocalMessage("smooth walk " + enabledSpecifier);
        }
        while (Keybinds.toggleInstantBlockClicker.isPressed())
        {
            InstantBlockClicker.INSTANCE.toggleInstantBlockClicker();
        }
    }

}
