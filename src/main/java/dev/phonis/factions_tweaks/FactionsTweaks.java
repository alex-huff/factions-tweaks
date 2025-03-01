package dev.phonis.factions_tweaks;

import dev.phonis.factions_tweaks.config.ConfigurationManager;
import dev.phonis.factions_tweaks.keybind.Keybinds;
import dev.phonis.factions_tweaks.tweaks.AutoClicker;
import dev.phonis.factions_tweaks.tweaks.InstantBlockClicker;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = FactionsTweaks.MODID, version = FactionsTweaks.VERSION, guiFactory = FactionsTweaks.GUI_FACTORY)
public class FactionsTweaks
{

    public static final String GUI_FACTORY = "dev.phonis.factions_tweaks.config.FactionsTweaksGuiFactory";
    public static final String MODID = "factions_tweaks";
    public static final String VERSION = "0.0.1";

    @EventHandler
    public void init(FMLPreInitializationEvent event)
    {
        ConfigurationManager.INSTANCE.init(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        Keybinds.register();
        MinecraftForge.EVENT_BUS.register(Keybinds.INSTANCE);
        MinecraftForge.EVENT_BUS.register(ConfigurationManager.INSTANCE);
        MinecraftForge.EVENT_BUS.register(AutoClicker.INSTANCE);
        MinecraftForge.EVENT_BUS.register(InstantBlockClicker.INSTANCE);
    }

}
