package dev.phonis.factions_tweaks.config;

import dev.phonis.factions_tweaks.FactionsTweaks;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationManager
{

    public static final ConfigurationManager INSTANCE = new ConfigurationManager();
    public static final String VERSION = "1";

    private final static boolean AUTO_CLICKER_SEMI_AUTO_ATTACK_DEFAULT = false;
    private final static boolean AUTO_CLICKER_SEMI_AUTO_USE_DEFAULT = false;
    private final static boolean AUTO_CLICKER_IGNORE_MISSED_ATTACK_DEFAULT = false;
    private final static boolean AUTO_CLICKER_BLOCK_CHECK_DEFAULT = true;
    private final static int AUTO_CLICKER_CPS_DEFAULT = 12;

    private final static boolean MOVEMENT_SMOOTH_WALK_DEFAULT = false;

    private final static boolean UTILITY_CLEAR_FLUIDS_DEFAULT = false;

    public Configuration configuration;
    public List<IConfigElement> configCategoryList = new ArrayList<>();

    public boolean autoClickerSemiAutoAttack = ConfigurationManager.AUTO_CLICKER_SEMI_AUTO_ATTACK_DEFAULT;
    public boolean autoClickerSemiAutoUse = ConfigurationManager.AUTO_CLICKER_SEMI_AUTO_USE_DEFAULT;
    public boolean autoClickerIgnoreMissedAttack = ConfigurationManager.AUTO_CLICKER_IGNORE_MISSED_ATTACK_DEFAULT;
    public boolean autoClickerBlockCheck = ConfigurationManager.AUTO_CLICKER_BLOCK_CHECK_DEFAULT;
    public int autoClickerCPS = ConfigurationManager.AUTO_CLICKER_CPS_DEFAULT;

    public boolean movementSmoothWalk = ConfigurationManager.MOVEMENT_SMOOTH_WALK_DEFAULT;

    public boolean utilityClearFluids = ConfigurationManager.UTILITY_CLEAR_FLUIDS_DEFAULT;

    private Property autoClickerSemiAutoAttackProperty;
    private Property autoClickerSemiAutoUseProperty;
    private Property autoClickerIgnoreMissedAttackProperty;
    private Property autoClickerBlockCheckProperty;
    private Property autoClickerCPSProperty;

    private Property movementSmoothWalkProperty;

    private Property utilityClearFluidsProperty;

    public void init(File configFile)
    {
        if (this.configuration != null)
        {
            return;
        }
        this.configuration = new Configuration(configFile, ConfigurationManager.VERSION);

        this.autoClickerSemiAutoAttackProperty
            = this.configuration.get("Auto Clicker", "Semi-auto Attack", ConfigurationManager.AUTO_CLICKER_SEMI_AUTO_ATTACK_DEFAULT, "Automatically attack when holding down attack keybinding");
        this.autoClickerSemiAutoUseProperty
            = this.configuration.get("Auto Clicker", "Semi-auto Use", ConfigurationManager.AUTO_CLICKER_SEMI_AUTO_USE_DEFAULT, "Automatically use when holding down use keybinding");
        this.autoClickerIgnoreMissedAttackProperty
            = this.configuration.get("Auto Clicker", "Ignore Missed Attack", ConfigurationManager.AUTO_CLICKER_IGNORE_MISSED_ATTACK_DEFAULT, "Do not attack when attack will miss");
        this.autoClickerBlockCheckProperty
            = this.configuration.get("Auto Clicker", "Block Check", ConfigurationManager.AUTO_CLICKER_BLOCK_CHECK_DEFAULT, "Do not attack when targeting block");
        this.autoClickerCPSProperty
            = this.configuration.get("Auto Clicker", "CPS", ConfigurationManager.AUTO_CLICKER_CPS_DEFAULT, "Clicks per second");
        ConfigCategory autoClickerCategory = new ConfigCategory("Auto Clicker");
        autoClickerCategory.put("Semi-auto Attack", this.autoClickerSemiAutoAttackProperty);
        autoClickerCategory.put("Semi-auto Use", this.autoClickerSemiAutoUseProperty);
        autoClickerCategory.put("Ignore Missed Attack", this.autoClickerIgnoreMissedAttackProperty);
        autoClickerCategory.put("Block Check", this.autoClickerBlockCheckProperty);
        autoClickerCategory.put("CPS", this.autoClickerCPSProperty);
        this.configCategoryList.add(new ConfigElement(autoClickerCategory));

        this.movementSmoothWalkProperty
            = this.configuration.get("Movement", "Smooth-walk", ConfigurationManager.MOVEMENT_SMOOTH_WALK_DEFAULT, "Walk smoothly over blocks like slime and ice");
        ConfigCategory movementCategory = new ConfigCategory("Movement");
        movementCategory.put("Smooth-walk", this.movementSmoothWalkProperty);
        this.configCategoryList.add(new ConfigElement(movementCategory));

        this.utilityClearFluidsProperty
            = this.configuration.get("Utility", "Clear Fluids", ConfigurationManager.UTILITY_CLEAR_FLUIDS_DEFAULT, "Render water and lava without fog and FOV modification");
        ConfigCategory utilityCategory = new ConfigCategory("Utility");
        utilityCategory.put("Clear Fluids", this.utilityClearFluidsProperty);
        this.configCategoryList.add(new ConfigElement(utilityCategory));

        this.loadConfiguration();
    }

    public void loadConfiguration()
    {
        this.reloadConfigurationAutoClicker();
        this.reloadConfigurationMovement();
        this.reloadConfigurationUtility();
        if (this.configuration.hasChanged())
        {
            this.configuration.save();
        }
    }

    private void reloadConfigurationAutoClicker()
    {
        this.autoClickerSemiAutoAttack = this.autoClickerSemiAutoAttackProperty.getBoolean();
        this.autoClickerSemiAutoUse = this.autoClickerSemiAutoUseProperty.getBoolean();
        this.autoClickerIgnoreMissedAttack = this.autoClickerIgnoreMissedAttackProperty.getBoolean();
        this.autoClickerBlockCheck = this.autoClickerBlockCheckProperty.getBoolean();
        this.autoClickerCPS = this.autoClickerCPSProperty.getInt();
    }

    private void reloadConfigurationMovement()
    {
        this.movementSmoothWalk = this.movementSmoothWalkProperty.getBoolean();
    }

    private void reloadConfigurationUtility()
    {
        this.utilityClearFluids = this.utilityClearFluidsProperty.getBoolean();
    }

    public boolean toggleAutoClickerSemiAutoAttack()
    {
        this.autoClickerSemiAutoAttackProperty.set(!this.autoClickerSemiAutoAttackProperty.getBoolean());
        this.autoClickerSemiAutoAttack = this.autoClickerSemiAutoAttackProperty.getBoolean();
        this.configuration.save();
        return this.autoClickerSemiAutoAttack;
    }

    public boolean toggleAutoClickerSemiAutoUse()
    {
        this.autoClickerSemiAutoUseProperty.set(!this.autoClickerSemiAutoUseProperty.getBoolean());
        this.autoClickerSemiAutoUse = this.autoClickerSemiAutoUseProperty.getBoolean();
        this.configuration.save();
        return this.autoClickerSemiAutoUse;
    }

    public boolean toggleSmoothWalk()
    {
        this.movementSmoothWalkProperty.set(!this.movementSmoothWalkProperty.getBoolean());
        this.movementSmoothWalk = this.movementSmoothWalkProperty.getBoolean();
        this.configuration.save();
        return this.movementSmoothWalk;
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(FactionsTweaks.MODID))
        {
            this.loadConfiguration();
        }
    }

}
