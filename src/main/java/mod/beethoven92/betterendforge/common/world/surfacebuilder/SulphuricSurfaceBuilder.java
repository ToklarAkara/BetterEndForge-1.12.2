package mod.beethoven92.betterendforge.common.world.surfacebuilder;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModSurfaceBuilders;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders.SurfaceBuilder;
import mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;

public class SulphuricSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>
{
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(5123);

	public SulphuricSurfaceBuilder()
	{
		super();
	}

	@Override
	public void buildSurface(Random random, ChunkPrimer chunk, Biome biome, int x, int z, int height, double noise,
							 IBlockState defaultBlock, IBlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		double value = NOISE.eval(x * 0.03, z * 0.03) + NOISE.eval(x * 0.1, z * 0.1) * 0.3 + ModMathHelper.randRange(-0.1, 0.1, ModMathHelper.RANDOM);
		if (value < -0.6)
		{
			SurfaceBuilder.DEFAULT.buildSurface(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, ModSurfaceBuilders.Configs.DEFAULT_END_CONFIG);
		}
		else if (value < -0.3)
		{
			SurfaceBuilder.DEFAULT.buildSurface(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, ModSurfaceBuilders.Configs.FLAVOLITE_CONFIG);
		}
		else if (value < 0.5)
		{
			SurfaceBuilder.DEFAULT.buildSurface(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, ModSurfaceBuilders.Configs.SULFURIC_ROCK_CONFIG);
		}
		else
		{
			SurfaceBuilder.DEFAULT.buildSurface(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, ModSurfaceBuilders.Configs.BRIMSTONE_CONFIG);
		}
	}
}
