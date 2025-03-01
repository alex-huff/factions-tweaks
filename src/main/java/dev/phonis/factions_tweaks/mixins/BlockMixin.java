package dev.phonis.factions_tweaks.mixins;

import dev.phonis.factions_tweaks.tweaks.SelectiveBlockTargeting;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Block.class)
public class BlockMixin
{

    /**
     * @author phonis
     * @reason selective block targeting
     */
    @Overwrite
    public boolean canCollideCheck(IBlockState p_canCollideCheck_1_, boolean p_canCollideCheck_2_)
    {
        if (SelectiveBlockTargeting.INSTANCE.shouldIgnoreBlock(p_canCollideCheck_1_.getBlock()))
        {
            return false;
        }
        return ((Block) (Object) this).isCollidable();
    }

}
