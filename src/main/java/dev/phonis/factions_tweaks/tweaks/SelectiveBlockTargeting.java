package dev.phonis.factions_tweaks.tweaks;

import dev.phonis.factions_tweaks.util.MinecraftUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.MovingObjectPosition;

public class SelectiveBlockTargeting
{

    public static final SelectiveBlockTargeting INSTANCE = new SelectiveBlockTargeting();

    private final Minecraft minecraft = Minecraft.getMinecraft();
    private boolean selectiveBlockTargetingEnabled = false;
    private int selectedBlockID = -1;

    public boolean shouldIgnoreBlock(Block block)
    {
        if (!this.selectiveBlockTargetingEnabled)
        {
            return false;
        }
        return Block.blockRegistry.getIDForObject(block) != this.selectedBlockID;
    }

    public void toggleSelectiveBlockTargeting()
    {
        if (!this.selectiveBlockTargetingEnabled)
        {
            MovingObjectPosition objectPosition = this.minecraft.objectMouseOver;
            WorldClient world = this.minecraft.theWorld;
            if (world == null || objectPosition == null ||
                !objectPosition.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK))
            {
                MinecraftUtil.sendLocalMessage("invalid block");
                return;
            }
            Block block = world.getBlockState(objectPosition.getBlockPos()).getBlock();
            this.selectedBlockID = Block.blockRegistry.getIDForObject(block);
            MinecraftUtil.sendLocalMessage("block set to " + block.getLocalizedName().toLowerCase());
        }
        else
        {
            MinecraftUtil.sendLocalMessage("disabled selective block targeting");
        }
        this.selectiveBlockTargetingEnabled = !this.selectiveBlockTargetingEnabled;
    }

}
