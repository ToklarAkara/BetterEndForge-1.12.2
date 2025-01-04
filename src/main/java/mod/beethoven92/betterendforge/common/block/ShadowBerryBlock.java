package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.EndCropBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;
import java.util.List;

public class ShadowBerryBlock extends EndCropBlock {
    private static final AxisAlignedBB SHAPE = new AxisAlignedBB(1 / 16.0, 0, 1 / 16.0, 15 / 16.0, 8 / 16.0, 15 / 16.0);

    public ShadowBerryBlock() {
        super(Material.PLANTS, ModBlocks.SHADOW_GRASS);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SHAPE;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (state.getValue(EndCropBlock.AGE) == 3) {
            ArrayList<ItemStack> drops = new ArrayList<>();
            drops.add(new ItemStack(ModItems.SHADOW_BERRY_RAW, 1, 0));
            return drops;
        }
        return super.getDrops(world, pos, state, fortune);
    }
}
