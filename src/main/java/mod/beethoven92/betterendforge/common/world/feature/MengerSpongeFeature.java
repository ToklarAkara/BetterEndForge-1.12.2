package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;
import java.util.function.Function;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MengerSpongeFeature extends UnderwaterPlantScatter
{
	private static final Function<IBlockState, Boolean> REPLACE;
	
	static 
	{
		REPLACE = (state) -> {
			if (state.getBlock()==(ModBlocks.END_LOTUS_STEM))
			{
				return false;
			}
			/*if (state.isIn(ModBlocks.END_LILY.get()))
			{
				return false;
			}*/
			return (state.getBlock()== Blocks.WATER || state.getBlock()==Blocks.FLOWING_WATER) || state.getMaterial().isReplaceable();
		};
	}
	
	public MengerSpongeFeature(int radius) 
	{
		super(radius);
	}

	@Override
	public void generateInner(World world, Random random, BlockPos blockPos)
	{
		BlockHelper.setWithoutUpdate(world, blockPos, ModBlocks.MENGER_SPONGE_WET);
		if (random.nextBoolean()) 
		{
			for (EnumFacing dir: BlockHelper.DIRECTIONS)
			{
				BlockPos pos = blockPos.offset(dir);
				if (REPLACE.apply(world.getBlockState(pos))) 
				{
					BlockHelper.setWithoutUpdate(world, pos, ModBlocks.MENGER_SPONGE_WET);
				}
			}
		}
	}
}
