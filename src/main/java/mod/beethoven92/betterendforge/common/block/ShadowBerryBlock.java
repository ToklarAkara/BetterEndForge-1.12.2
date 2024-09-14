package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;

public class ShadowBerryBlock extends BlockCrops {
	private static final AxisAlignedBB SHAPE = new AxisAlignedBB(1 / 16.0, 0, 1 / 16.0, 15 / 16.0, 8 / 16.0, 15 / 16.0);

	public ShadowBerryBlock() {
		super();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SHAPE;
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == ModBlocks.SHADOW_GRASS;
	}
}
