package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WallPlantOnLogFeature extends WallPlantFeature
{
	public WallPlantOnLogFeature(Block block, int radius) 
	{
		super(block, radius);
	}

	@Override
	public boolean canGenerate(World world, Random random, BlockPos pos, EnumFacing dir)
	{
		BlockPos blockPos = pos.offset(dir.getOpposite());
		IBlockState blockState = world.getBlockState(blockPos);
		return blockState.getBlock() instanceof BlockLog;
	}
}
