package dev.phonis.factions_tweaks.tweaks;

import dev.phonis.factions_tweaks.util.MinecraftUtil;
import dev.phonis.factions_tweaks.util.VecUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class InstantBlockClicker
{

    public static final InstantBlockClicker INSTANCE = new InstantBlockClicker();

    private final Minecraft minecraft = Minecraft.getMinecraft();
    private final int maxBreaksPerTick = 1;
    private boolean instantBlockClickerEnabled = false;
    private int selectedBlockID = -1;
    private int selectedBlockLevel = -1;
    private BlockPos closestBlockOfCurrentTarget = null;
    private double closestBlockOfCurrentTargetSquaredDistance = Double.MAX_VALUE;

    public void toggleInstantBlockClicker()
    {
        if (!this.instantBlockClickerEnabled)
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
            this.selectedBlockLevel = objectPosition.getBlockPos().getY();
            MinecraftUtil.sendLocalMessage(
                "block set to " + block.getLocalizedName().toLowerCase() + " at y-level " + this.selectedBlockLevel);
        }
        else
        {
            MinecraftUtil.sendLocalMessage("disabled instant block clicker");
        }
        this.instantBlockClickerEnabled = !this.instantBlockClickerEnabled;
    }

    private boolean updateClosestBlockOfCurrentTarget(Vec3 playerEyePosition, float reachDistance)
    {
        this.closestBlockOfCurrentTarget = null;
        this.closestBlockOfCurrentTargetSquaredDistance = Double.MAX_VALUE;
        WorldClient world = this.minecraft.theWorld;
        if (world == null)
        {
            return false;
        }
        BlockPos.MutableBlockPos currentBlockPos
            = new BlockPos.MutableBlockPos((int) Math.floor(playerEyePosition.xCoord), this.selectedBlockLevel, (int) Math.floor(playerEyePosition.zCoord));
        BlockPos playerBlockPos = currentBlockPos.getImmutable();
        if (this.checkAndUpdateClosestBlock(world, currentBlockPos, playerEyePosition))
        {
            return true;
        }
        /*
        i i i i i i j
        l e e e e f j
        l h a a b f j
        l h d 0 b f j
        l h d c c f j
        l h g g g g j
        l k k k k k k
         */
        MinecraftUtil.mutableBlockPosAdd(currentBlockPos, -1, 0, 0);
        for (int i = 0; i < Math.ceil(reachDistance + 1) &&
                        this.squaredDistanceToPlayerEyes(currentBlockPos, playerEyePosition) <
                        this.closestBlockOfCurrentTargetSquaredDistance; i++)
        {
            int wrap = i + 1;
            MinecraftUtil.mutableBlockPosAdd(currentBlockPos, 0, 0, -wrap);
            for (; currentBlockPos.getX() < playerBlockPos.getX() + wrap;
                 MinecraftUtil.mutableBlockPosAdd(currentBlockPos, 1, 0, 0))
            {
                this.checkAndUpdateClosestBlock(world, currentBlockPos, playerEyePosition);
            }
            for (; currentBlockPos.getZ() < playerBlockPos.getZ() + wrap;
                 MinecraftUtil.mutableBlockPosAdd(currentBlockPos, 0, 0, 1))
            {
                this.checkAndUpdateClosestBlock(world, currentBlockPos, playerEyePosition);
            }
            for (; currentBlockPos.getX() > playerBlockPos.getX() - wrap;
                 MinecraftUtil.mutableBlockPosAdd(currentBlockPos, -1, 0, 0))
            {
                this.checkAndUpdateClosestBlock(world, currentBlockPos, playerEyePosition);
            }
            for (; currentBlockPos.getZ() > playerBlockPos.getZ() - wrap;
                 MinecraftUtil.mutableBlockPosAdd(currentBlockPos, 0, 0, -1))
            {
                this.checkAndUpdateClosestBlock(world, currentBlockPos, playerEyePosition);
            }
            MinecraftUtil.mutableBlockPosAdd(currentBlockPos, -1, 0, wrap);
        }
        return this.closestBlockOfCurrentTarget != null;
    }

    private double squaredDistanceToPlayerEyes(BlockPos.MutableBlockPos blockPos, Vec3 playerEyePosition)
    {
        return VecUtil.vec3SquaredDistance(playerEyePosition,
            blockPos.getX() + .5, blockPos.getY() + 1, blockPos.getZ() + .5);
    }

    private boolean checkAndUpdateClosestBlock(WorldClient world, BlockPos.MutableBlockPos blockPos,
                                               Vec3 playerEyePosition)
    {
        if (!this.shouldClickBlock(world, blockPos))
        {
            return false;
        }
        double squaredDistance = this.squaredDistanceToPlayerEyes(blockPos, playerEyePosition);
        if (squaredDistance < this.closestBlockOfCurrentTargetSquaredDistance)
        {
            this.closestBlockOfCurrentTargetSquaredDistance = squaredDistance;
            this.closestBlockOfCurrentTarget = blockPos.getImmutable();
            return true;
        }
        return false;
    }

    private boolean shouldClickBlock(WorldClient world, BlockPos.MutableBlockPos blockPos)
    {
        return Block.blockRegistry.getIDForObject(world.getBlockState(blockPos).getBlock()) == this.selectedBlockID;
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (!event.phase.equals(TickEvent.Phase.START) || !event.side.equals(Side.CLIENT) ||
            !this.instantBlockClickerEnabled)
        {
            return;
        }
        PlayerControllerMP playerController = this.minecraft.playerController;
        Vec3 playerEyePosition = this.minecraft.thePlayer.getPositionEyes(MinecraftUtil.getPartialTicks());
        float reachDistance = playerController.getBlockReachDistance();
        int blocksBroken = 0;
        while (this.updateClosestBlockOfCurrentTarget(playerEyePosition, reachDistance) &&
               Math.sqrt(this.closestBlockOfCurrentTargetSquaredDistance) < reachDistance &&
               blocksBroken < this.maxBreaksPerTick)
        {
            playerController.clickBlock(this.closestBlockOfCurrentTarget, EnumFacing.UP);
            blocksBroken++;
        }
    }

}
