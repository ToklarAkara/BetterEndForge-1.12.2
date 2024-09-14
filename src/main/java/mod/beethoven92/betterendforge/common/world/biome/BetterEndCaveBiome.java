package mod.beethoven92.betterendforge.common.world.biome;

import java.util.Random;

import mod.beethoven92.betterendforge.common.world.moderngen.WeightedList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BetterEndCaveBiome extends BetterEndBiome
{
	private WeightedList<WorldGenerator> floorFeatures = new WeightedList<WorldGenerator>();
	private WeightedList<WorldGenerator> ceilFeatures = new WeightedList<WorldGenerator>();

	public BetterEndCaveBiome(BiomeTemplate definition)
	{
		super(definition.setCaveBiome());
	}

	public void addFloorFeature(WorldGenerator feature, int weight)
	{
		floorFeatures.add(feature, weight);
	}

	public void addCeilFeature(WorldGenerator feature, int weight)
	{
		ceilFeatures.add(feature, weight);
	}

	public WorldGenerator getFloorFeature(Random random)
	{
		return floorFeatures.isEmpty() ? null : floorFeatures.getOne(random);
	}

	public WorldGenerator getCeilFeature(Random random)
	{
		return ceilFeatures.isEmpty() ? null : ceilFeatures.getOne(random);
	}
	
	public float getFloorDensity() 
	{
		return 0;
	}
	
	public float getCeilDensity() 
	{
		return 0;
	}
	
	public IBlockState getCeil(BlockPos pos)
	{
		return null;
	}

	public IBlockState getWall(BlockPos pos) {
		return null;
	}
}
