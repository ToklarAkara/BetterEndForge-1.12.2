package mod.beethoven92.betterendforge.mixin;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import mod.beethoven92.betterendforge.common.init.ModConfiguredSurfaceBuilders;
import mod.beethoven92.betterendforge.common.world.biome.ExtendedBiome;
import mod.beethoven92.betterendforge.common.world.generator.SharedSeedRandom;
import mod.beethoven92.betterendforge.common.world.moderngen.PerlinNoiseGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.beethoven92.betterendforge.common.world.generator.TerrainGenerator;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkGeneratorEnd.class)
public abstract class NoiseChunkGeneratorMixin
{

	@Unique
	private PerlinNoiseGenerator surfaceDepthNoise;
	@Shadow @Final private World world;

	@Shadow @Final private Random rand;

	@Shadow private int chunkX;

	@Shadow private int chunkZ;

	private long seed;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void beOnInit(World p_i47241_1_, boolean p_i47241_2_, long seed, BlockPos p_i47241_5_, CallbackInfo ci)
	{
		TerrainGenerator.initNoise(seed);
		surfaceDepthNoise = new PerlinNoiseGenerator(new SharedSeedRandom(seed), IntStream.rangeClosed(-3, 0));
	}
	
	@Inject(method = "getHeights", at = @At("HEAD"), cancellable = true, allow = 2)
	private void beFillNoiseColumn(double[] buffer, int x, int p_185963_3_, int z, int p_185963_5_, int p_185963_6_, int p_185963_7_, CallbackInfoReturnable<double[]> cir)
	{
//		if (GeneratorOptions.useNewGenerator()) // End settings
//		{
//			if (buffer == null)
//			{
//				buffer = new double[p_185963_5_ * p_185963_6_ * p_185963_7_];
//			}
//			TerrainGenerator.fillTerrainDensity(buffer, x, z, world.getBiomeProvider());
//			cir.setReturnValue(buffer);
//			cir.cancel();
//		}
	}

	@Inject(method = "buildSurfaces", at = @At("HEAD"), cancellable = true)
	public void buildSurfaces(ChunkPrimer p_185962_1_, CallbackInfo ci)
	{
		int i = chunkX;
		int j = chunkZ;
		SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
		sharedseedrandom.setBaseChunkSeed(i, j);
		int k = chunkX<<4;
		int l = chunkZ<<4;
		double d0 = 0.0625D;
		BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

		for(int i1 = 0; i1 < 16; ++i1) {
			for(int j1 = 0; j1 < 16; ++j1) {
				int k1 = k + i1;
				int l1 = l + j1;
				int i2 = 127;
				double d1 = this.surfaceDepthNoise.noiseAt((double)k1 * 0.0625D, (double)l1 * 0.0625D, 0.0625D, (double)i1 * 0.0625D) * 15.0D;
				Biome biome = world.getBiome(blockpos$mutable.setPos(k + i1, i2, l + j1));
				if(biome instanceof ExtendedBiome) {
					((ExtendedBiome)biome).buildSurface(sharedseedrandom, p_185962_1_, k1, l1, i2, d1, Blocks.END_STONE.getDefaultState(), Blocks.AIR.getDefaultState(), 0, world.getSeed());
				}else{
					ModConfiguredSurfaceBuilders.END_SURFACE.setSeed(world.getSeed());
					ModConfiguredSurfaceBuilders.END_SURFACE.buildSurface(sharedseedrandom, p_185962_1_, biome, k1, l1, i2, d1, Blocks.END_STONE.getDefaultState(), Blocks.AIR.getDefaultState(), 0, world.getSeed());
				}
			}
		}
		ci.cancel();
	}
}
