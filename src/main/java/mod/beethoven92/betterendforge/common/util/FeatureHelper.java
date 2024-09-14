package mod.beethoven92.betterendforge.common.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FeatureHelper
{
	protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
	protected static final IBlockState WATER = Blocks.WATER.getDefaultState();

	public static int getYOnSurface(World world, int x, int z)
	{
		return world.getHeight(x, z);
	}
	
	public static int getYOnSurfaceWG(World world, int x, int z)
	{
		return world.getHeight(x, z); //TODO CHECK
	}
	
	public static BlockPos getPosOnSurface(World world, BlockPos pos)
	{
		return world.getHeight(pos);
	}
	
	public static BlockPos getPosOnSurfaceWG(World world, BlockPos pos)
	{
		return world.getHeight(pos); //TODO CHECK
	}
	
	public static BlockPos getPosOnSurfaceRaycast(World world, BlockPos pos)
	{
		return getPosOnSurfaceRaycast(world, pos, 256);
	}
	
	public static BlockPos getPosOnSurfaceRaycast(World world, BlockPos pos, int dist)
	{
		int h = BlockHelper.downRay(world, pos, dist);
		return pos.down(h);
	}
}
