package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class SingleBlockFeature extends WorldGenerator
{
	private final Block block;
	
	public SingleBlockFeature(Block block) 
	{
		this.block = block;
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		if (!ModTags.GEN_TERRAIN.contains(world.getBlockState(pos.down()).getBlock()))
		{
			return false;
		}
		
		IBlockState state = block.getDefaultState();
//		if (block.getBlockState().getProperty("waterlogged") != null)
//		{
//			boolean waterlogged = !world.getFluidState(pos).isEmpty();
//			state = state.withProperty(BlockStateProperties.WATERLOGGED, waterlogged);
//		}
		BlockHelper.setWithoutUpdate(world, pos, state);
		
		return true;
	}
}
