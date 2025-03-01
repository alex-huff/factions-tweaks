package dev.phonis.factions_tweaks.tweaks;

import dev.phonis.factions_tweaks.config.ConfigurationManager;
import dev.phonis.factions_tweaks.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoClicker
{

    public static final AutoClicker INSTANCE = new AutoClicker();

    private final Minecraft minecraft = Minecraft.getMinecraft();
    private boolean autoAttack = false;
    private boolean autoUse = false;
    private long tick = 0;
    private long nextAttackMillis = 0;
    private long nextUseMillis = 0;
    private long lastAttackTick = 0;
    private long lastUseTick = 0;

    public void toggleAutoAttack()
    {
        this.autoAttack = !this.autoAttack;
        String enabledSpecifier = this.autoAttack ? "enabled" : "disabled";
        MinecraftUtil.sendLocalMessage("auto attack " + enabledSpecifier);
    }

    public void toggleAutoUse()
    {
        this.autoUse = !this.autoUse;
        String enabledSpecifier = this.autoUse ? "enabled" : "disabled";
        MinecraftUtil.sendLocalMessage("auto use " + enabledSpecifier);
    }

    private boolean playerFocusingBlock()
    {
        return this.minecraft.objectMouseOver != null &&
               this.minecraft.objectMouseOver.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK);
    }

    private boolean shouldPauseAutoAttack()
    {
        return ConfigurationManager.INSTANCE.autoClickerBlockCheck && this.playerFocusingBlock();
    }

    public boolean shouldHoldAttack()
    {
        boolean attacking = ConfigurationManager.INSTANCE.autoClickerSemiAutoAttack || this.autoAttack;
        if (!attacking || this.shouldPauseAutoAttack())
        {
            return this.minecraft.gameSettings.keyBindAttack.isKeyDown();
        }
        return this.tick == this.lastAttackTick;
    }

    public boolean shouldHoldUse()
    {
        boolean using = ConfigurationManager.INSTANCE.autoClickerSemiAutoUse || this.autoUse;
        if (!using)
        {
            return this.minecraft.gameSettings.keyBindUseItem.isKeyDown();
        }
        return this.tick == this.lastUseTick;
    }

    public void onProcessClicks()
    {
        long interval = 1000 / ConfigurationManager.INSTANCE.autoClickerCPS;
        long currentTimeMillis = System.currentTimeMillis();
        // @formatter:off
        boolean shouldAttack =
            currentTimeMillis >= this.nextAttackMillis &&
            (
                this.autoAttack ||
                (
                    ConfigurationManager.INSTANCE.autoClickerSemiAutoAttack &&
                    this.minecraft.gameSettings.keyBindAttack.isKeyDown()
                )
            ) &&
            (
                !ConfigurationManager.INSTANCE.autoClickerIgnoreMissedAttack ||
                (
                    this.minecraft.objectMouseOver != null &&
                    !this.minecraft.objectMouseOver.typeOfHit.equals(MovingObjectPosition.MovingObjectType.MISS)
                )
            ) &&
            !this.shouldPauseAutoAttack();
        boolean shouldUse =
            currentTimeMillis >= this.nextUseMillis &&
            (
                this.autoUse ||
                (
                    ConfigurationManager.INSTANCE.autoClickerSemiAutoUse &&
                    this.minecraft.gameSettings.keyBindUseItem.isKeyDown()
                )
            );
        // @formatter:on
        if (shouldAttack)
        {
            this.minecraft.clickMouse();
            this.lastAttackTick = this.tick;
            this.nextAttackMillis += interval;
            if (this.nextAttackMillis <= currentTimeMillis)
            {
                this.nextAttackMillis = currentTimeMillis + interval;
            }
        }
        if (shouldUse)
        {
            this.minecraft.rightClickMouse();
            this.lastUseTick = this.tick;
            this.nextUseMillis += interval;
            if (this.nextUseMillis <= currentTimeMillis)
            {
                this.nextUseMillis = currentTimeMillis + interval;
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase.equals(TickEvent.Phase.START))
        {
            return;
        }
        PlayerControllerMP playerController = this.minecraft.playerController;
        boolean processingClicks = this.minecraft.currentScreen == null && this.minecraft.inGameHasFocus &&
                                   playerController != null;
        if (processingClicks && this.minecraft.leftClickCounter > 0)
        {
            boolean hittingBlock = playerController.getIsHittingBlock();
            boolean playerFocusingBlock = this.playerFocusingBlock();
            boolean shouldClickBlock = !hittingBlock && playerFocusingBlock &&
                                       this.minecraft.gameSettings.keyBindAttack.isKeyDown() &&
                                       (ConfigurationManager.INSTANCE.autoClickerSemiAutoAttack || this.autoAttack) &&
                                       ConfigurationManager.INSTANCE.autoClickerBlockCheck;
            if (shouldClickBlock)
            {
                this.minecraft.leftClickCounter = 0;
            }
        }
        this.tick++;
    }

}
