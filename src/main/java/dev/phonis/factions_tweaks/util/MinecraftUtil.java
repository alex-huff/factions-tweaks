package dev.phonis.factions_tweaks.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.*;

import java.util.Optional;

public class MinecraftUtil
{

    public static void sendLocalMessage(String message)
    {
        MinecraftUtil.sendLocalMessage(new ChatComponentText(message));
    }

    public static void sendLocalMessage(ChatComponentText message)
    {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(message);
    }

    public static Vec3 getPlayerDirection(EntityPlayerSP player)
    {
        float f = MathHelper.cos(-player.rotationYaw * 0.017453292f - 3.1415927f);
        float f1 = MathHelper.sin(-player.rotationYaw * 0.017453292f - 3.1415927f);
        float f2 = -MathHelper.cos(-player.rotationPitch * 0.017453292f);
        float f3 = MathHelper.sin(-player.rotationPitch * 0.017453292f);
        return new Vec3(f1 * f2, f3, f * f2);
    }

    public static float getPartialTicks()
    {
        return Minecraft.getMinecraft().timer.renderPartialTicks;
    }

    public static Optional<MovingObjectPosition> rayTraceBlocks(WorldClient realWorld, Vec3 start, Vec3 end)
    {
        return MinecraftUtil.rayTraceBlocks(realWorld, start, end, false, false, true);
    }

    public static Optional<MovingObjectPosition> rayTraceBlocks(WorldClient realWorld, Vec3 start, Vec3 end,
                                                                boolean stopOnLiquid,
                                                                boolean ignoreBlockWithoutBoundingBox,
                                                                boolean returnLastUncollidableBlock)
    {
        return Optional.ofNullable(realWorld.rayTraceBlocks(start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock));
    }

    public static boolean rayTraceHitBlock(MovingObjectPosition hitPosition)
    {
        return hitPosition != null && hitPosition.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK);
    }

    public static void mutableBlockPosAdd(BlockPos.MutableBlockPos blockPos, int x, int y, int z)
    {
        blockPos.set(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
    }

    public static void mutableBlockSetX(BlockPos.MutableBlockPos blockPos, int x)
    {
        blockPos.set(x, blockPos.getY(), blockPos.getZ());
    }

    public static void mutableBlockSetY(BlockPos.MutableBlockPos blockPos, int y)
    {
        blockPos.set(blockPos.getX(), y, blockPos.getZ());
    }

    public static void mutableBlockSetZ(BlockPos.MutableBlockPos blockPos, int z)
    {
        blockPos.set(blockPos.getX(), blockPos.getY(), z);
    }
}
