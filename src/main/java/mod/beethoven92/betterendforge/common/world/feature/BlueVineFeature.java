package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlueVineFeature extends ScatterFeature
{
	private boolean small;

	public BlueVineFeature(int radius)
	{
		super(radius);
	}

	public boolean canGenerate(World world, Random random, BlockPos center, BlockPos blockPos, float radius) {
		float d = ModMathHelper.length(center.getX() - blockPos.getX(), center.getZ() - blockPos.getZ()) / radius * 0.6F + random.nextFloat() * 0.4F;
		small = d > 0.5F;
		return ModBlocks.BLUE_VINE_SEED.canPlaceBlockAt(world, blockPos);
	}

	public void generateInner(World world, Random random, BlockPos blockPos) {
		if (small) {
			BlockHelper.setWithoutUpdate(world, blockPos, ModBlocks.BLUE_VINE_SEED.getDefaultState().withProperty(PlantBlockWithAge.AGE, random.nextInt(4)));
		} else {
			PlantBlockWithAge seed = (PlantBlockWithAge) ModBlocks.BLUE_VINE_SEED;
			seed.growAdult(world, random, blockPos);
		}
	}

}
