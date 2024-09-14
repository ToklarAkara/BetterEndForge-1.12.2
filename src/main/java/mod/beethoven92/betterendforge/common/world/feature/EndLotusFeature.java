package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.EndLotusSeedBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EndLotusFeature extends ScatterFeature
{
	private static final Mutable POS = new Mutable();
	
	public EndLotusFeature(int radius) 
	{
		super(radius);
	}
	
	@Override
	protected BlockPos getCenterGround(World world, BlockPos pos) 
	{
		POS.setX(pos.getX());
		POS.setZ(pos.getZ());
		POS.setY(0);
		return getGround(world, POS).toImmutable();
	}
	
	@Override
	protected boolean canSpawn(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		return (state.getBlock()==Blocks.WATER || state.getBlock()==Blocks.FLOWING_WATER);
	}
	
	@Override
	protected boolean getGroundPlant(World world, BlockPos.MutableBlockPos pos)
	{
		return getGround(world, pos).getY() < 128;
	}
	
	@Override
	protected int getYOffset() 
	{
		return -5;
	}
	
	@Override
	protected int getChance() 
	{
		return 15;
	}
	
	private BlockPos getGround(World world, BlockPos.MutableBlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		while (pos.getY() < 128 && !(state.getBlock()==Blocks.WATER || state.getBlock()==Blocks.FLOWING_WATER))
		{
			pos.setY(pos.getY() + 1);
		}
		return pos;
	}
	
	@Override
	public boolean canGenerate(World world, Random random, BlockPos center, BlockPos blockPos, float radius) 
	{
		return world.getBlockState(blockPos)==(Blocks.WATER);
	}

	@Override
	public void generateInner(World world, Random random, BlockPos blockPos)
	{
		EndLotusSeedBlock seed = (EndLotusSeedBlock) ModBlocks.END_LOTUS_SEED;
		seed.generate(world, random, blockPos);
	}
}
