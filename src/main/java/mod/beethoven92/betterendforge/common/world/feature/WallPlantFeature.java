package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.AttachedBlock;
import mod.beethoven92.betterendforge.common.block.template.WallPlantBlock;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WallPlantFeature extends WallScatterFeature
{	
	private final Block block;
	
	public WallPlantFeature(Block block, int radius)
	{
		super(radius);
		this.block = block;
	}

	@Override
	public boolean canGenerate(World world, Random random, BlockPos pos, EnumFacing dir)
	{
		if (block instanceof WallPlantBlock) 
		{
			IBlockState state = block.getDefaultState().withProperty(WallPlantBlock.FACING, dir);
			return block.canPlaceBlockAt(world, pos);
		}
		else if (block instanceof AttachedBlock) 
		{
			IBlockState state = block.getDefaultState().withProperty(AttachedBlock.FACING, dir);
			return block.canPlaceBlockAt(world, pos);
		}
		return block.canPlaceBlockAt(world, pos);
	}

	@Override
	public void generate(World world, Random random, BlockPos pos, EnumFacing dir)
	{
		IBlockState state = block.getDefaultState();
		if (block instanceof WallPlantBlock)
		{
			state = state.withProperty(WallPlantBlock.FACING, dir);
		}
		else if (block instanceof AttachedBlock)
		{
			state = state.withProperty(AttachedBlock.FACING, dir);
		}
		BlockHelper.setWithoutUpdate(world, pos, state);
	}
}
