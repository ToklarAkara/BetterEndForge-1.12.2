package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LanceleafFeature extends ScatterFeature {
	public LanceleafFeature() {
		super(7);
	}

	@Override
	public boolean canGenerate(World world, Random random, BlockPos center, BlockPos blockPos, float radius) {
		return ModBlocks.LANCELEAF_SEED.canPlaceBlockAt(world, blockPos);
	}

	@Override
	public void generateInner(World world, Random random, BlockPos blockPos) {
		PlantBlockWithAge seed = ((PlantBlockWithAge) ModBlocks.LANCELEAF_SEED);
		seed.growAdult(world, random, blockPos);
	}

	@Override
	protected int getChance() {
		return 5;
	}
}
