package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CavePlantFeature extends FullHeightScatterFeature
{
	private final Block plant;
	
	public CavePlantFeature(Block plant, int radius)
	{
		super(radius);
		this.plant = plant;
	}

	public boolean canGenerate(World world, Random random, BlockPos center, BlockPos blockPos, float radius) {
		return plant.canPlaceBlockAt(world, blockPos);
	}

	public void generateInner(World world, Random random, BlockPos blockPos) {
		BlockHelper.setWithoutUpdate(world, blockPos, plant.getDefaultState());
	}
}
