package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.AttachedBlock;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SingleInvertedScatterFeature extends InvertedScatterFeature
{
	private final Block block;
	
	public SingleInvertedScatterFeature(Block block, int radius) 
	{
		super(radius);
		this.block = block;
	}

	@Override
	public boolean canGenerate(World world, Random random, BlockPos center, BlockPos blockPos, float radius)
	{
		if (!world.isAirBlock(blockPos)) 
		{
			return false;
		}
		IBlockState state = block.getDefaultState();
		if (block instanceof AttachedBlock) 
		{
			state = state.withProperty(AttachedBlock.FACING, EnumFacing.DOWN);
		}
		return state.getBlock().canPlaceBlockAt(world, blockPos);
	}

	@Override
	public void generateInner(World world, Random random, BlockPos blockPos)
	{
		IBlockState state = block.getDefaultState();
		if (block instanceof AttachedBlock) 
		{
			state = state.withProperty(AttachedBlock.FACING, EnumFacing.DOWN);
		}
		BlockHelper.setWithoutUpdate(world, blockPos, state);
	}
}
