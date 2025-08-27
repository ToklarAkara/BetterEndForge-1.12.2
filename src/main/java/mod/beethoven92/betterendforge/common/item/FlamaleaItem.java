package mod.beethoven92.betterendforge.common.item;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemLilyPad;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;

public class FlamaleaItem extends ItemLilyPad {
    public FlamaleaItem(Block p_i45357_1_) {
        super(p_i45357_1_);
    }

    public ActionResult<ItemStack> onItemRightClick(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
        ItemStack itemstack = p_77659_2_.getHeldItem(p_77659_3_);
        RayTraceResult raytraceresult = this.rayTrace(p_77659_1_, p_77659_2_, true);
        if (raytraceresult == null) {
            return new ActionResult(EnumActionResult.PASS, itemstack);
        } else {
            if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos blockpos = raytraceresult.getBlockPos();
                if (!p_77659_1_.isBlockModifiable(p_77659_2_, blockpos) || !p_77659_2_.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack)) {
                    return new ActionResult(EnumActionResult.FAIL, itemstack);
                }

                BlockPos blockpos1 = blockpos.up();
                IBlockState iblockstate = p_77659_1_.getBlockState(blockpos);
                if (iblockstate.getMaterial() == Material.WATER && (Integer)iblockstate.getValue(BlockLiquid.LEVEL) == 0 && p_77659_1_.isAirBlock(blockpos1)) {
                    BlockSnapshot blocksnapshot = BlockSnapshot.getBlockSnapshot(p_77659_1_, blockpos1);
                    p_77659_1_.setBlockState(blockpos1, ModBlocks.FLAMAEA.getDefaultState());
                    if (ForgeEventFactory.onPlayerBlockPlace(p_77659_2_, blocksnapshot, EnumFacing.UP, p_77659_3_).isCanceled()) {
                        blocksnapshot.restore(true, false);
                        return new ActionResult(EnumActionResult.FAIL, itemstack);
                    }

                    p_77659_1_.setBlockState(blockpos1, ModBlocks.FLAMAEA.getDefaultState(), 11);
                    if (p_77659_2_ instanceof EntityPlayerMP) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)p_77659_2_, blockpos1, itemstack);
                    }

                    if (!p_77659_2_.capabilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    p_77659_2_.addStat(StatList.getObjectUseStats(this));
                    p_77659_1_.playSound(p_77659_2_, blockpos, SoundEvents.BLOCK_WATERLILY_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return new ActionResult(EnumActionResult.SUCCESS, itemstack);
                }
            }

            return new ActionResult(EnumActionResult.FAIL, itemstack);
        }
    }
}
