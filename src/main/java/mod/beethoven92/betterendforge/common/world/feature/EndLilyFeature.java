package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.EndLilySeedBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EndLilyFeature extends UnderwaterPlantScatter
{
	public EndLilyFeature(int radius) 
	{
		super(radius);
	}

	@Override
	public void generateInner(World world, Random random, BlockPos blockPos)
	{	
		EndLilySeedBlock seed = (EndLilySeedBlock) ModBlocks.END_LILY_SEED;
		seed.doGrow(world, random, blockPos);
	}
	@Override
	protected int getChance()
	{
		return 15;
	}
}
