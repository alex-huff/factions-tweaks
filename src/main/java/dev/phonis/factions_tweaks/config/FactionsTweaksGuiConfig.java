package dev.phonis.factions_tweaks.config;

import dev.phonis.factions_tweaks.FactionsTweaks;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;

public class FactionsTweaksGuiConfig extends GuiConfig
{

    public FactionsTweaksGuiConfig(GuiScreen parent)
    {
        super(parent, ConfigurationManager.INSTANCE.configCategoryList, FactionsTweaks.MODID, false, false, "Factions Tweaks Configuration");
    }

}
