package mod.beethoven92.betterendforge.common.world.generator;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;

public class BetterEndBiomeProvider extends BiomeProvider
{
	
	private static final OpenSimplexNoise SMALL_NOISE = new OpenSimplexNoise(8324);
	private final SimplexNoiseGenerator generator;
	private final IForgeRegistry<Biome> lookupRegistry;
	private final Biome centerBiome;
//	private final Biome barrens;
	private BiomeMap mapLand;
	private BiomeMap mapVoid;
	private final long seed;

	
	public BetterEndBiomeProvider(IForgeRegistry<Biome> lookupRegistry, long seed)
	{
		//super(getBiomes(lookupRegistry));
		
		this.mapLand = new BiomeMap(seed, GeneratorOptions.getBiomeSizeLand(), ModBiomes.LAND_BIOMES);
		this.mapVoid = new BiomeMap(seed, GeneratorOptions.getBiomeSizeVoid(), ModBiomes.VOID_BIOMES);
		this.centerBiome = Biomes.SKY;
		//this.barrens = lookupRegistry.getValue(Biomes.END_BARRENS);
		this.lookupRegistry = lookupRegistry;
		this.seed = seed;

		SharedSeedRandom sharedseedrandom = new SharedSeedRandom(seed);
	    sharedseedrandom.consumeCount(17292);
	    this.generator = new SimplexNoiseGenerator(sharedseedrandom);
	    
	    ModBiomes.mutateRegistry(lookupRegistry);
	}
	
	private static List<Biome> getBiomes(IForgeRegistry<Biome> biomeRegistry)
	{
		List<Biome> list = Lists.newArrayList();
		biomeRegistry.forEach((biome) -> {
			if (ModBiomes.hasBiome(biomeRegistry.getKey(biome)))
			{
				list.add(biome);
			}
			/*if (biome.getCategory() == Category.THEEND) 
			{
				list.add(biome);
			}*/
		});
		return list;
	}
	public Biome getNoiseBiome(int x, int y, int z)
	{
		boolean hasVoid = !GeneratorOptions.useNewGenerator() || !GeneratorOptions.noRingVoid();
		
		long i = (long) x * (long) x;
		long j = (long) z * (long) z;
		if (hasVoid && i + j <= 65536L) return this.centerBiome;
		
		if (x == 0 && z == 0) 
		{
			mapLand.clearCache();
			mapVoid.clearCache();
		}
		
		if (GeneratorOptions.useNewGenerator())
		{
			if (TerrainGenerator.isLand(x, z)) 
			{
				return mapLand.getBiome(x << 2, z << 2).getActualBiome();
			}
			else 
			{
				return mapVoid.getBiome(x << 2, z << 2).getActualBiome();
			}
		}
		else 
		{
			float height = getHeightValue(generator, (x >> 1) + 1, (z >> 1) + 1) + (float) SMALL_NOISE.eval(x, z) * 5;
	
//			if (height > -20F && height < -5F)
//			{
//				return barrens;
//			}
//
			BetterEndBiome endBiome = height < -10F ? mapVoid.getBiome(x << 2, z << 2) : mapLand.getBiome(x << 2, z << 2);
			return endBiome.getActualBiome();
		}
	}

	public static float getHeightValue(SimplexNoiseGenerator p_235317_0_, int p_235317_1_, int p_235317_2_) {
		int i = p_235317_1_ / 2;
		int j = p_235317_2_ / 2;
		int k = p_235317_1_ % 2;
		int l = p_235317_2_ % 2;
		float f = 100.0F - MathHelper.sqrt((float)(p_235317_1_ * p_235317_1_ + p_235317_2_ * p_235317_2_)) * 8.0F;
		f = MathHelper.clamp(f, -100.0F, 80.0F);

		for(int i1 = -12; i1 <= 12; ++i1) {
			for(int j1 = -12; j1 <= 12; ++j1) {
				long k1 = (long)(i + i1);
				long l1 = (long)(j + j1);
				if (k1 * k1 + l1 * l1 > 4096L && p_235317_0_.getValue((double)k1, (double)l1) < (double)-0.9F) {
					float f1 = (MathHelper.abs((float)k1) * 3439.0F + MathHelper.abs((float)l1) * 147.0F) % 13.0F + 9.0F;
					float f2 = (float)(k - i1 * 2);
					float f3 = (float)(l - j1 * 2);
					float f4 = 100.0F - MathHelper.sqrt(f2 * f2 + f3 * f3) * f1;
					f4 = MathHelper.clamp(f4, -100.0F, 80.0F);
					f = Math.max(f, f4);
				}
			}
		}

		return f;
	}

	public Biome[] getBiomes(@Nullable Biome[] oldBiomeList, int x, int z, int width, int depth)
	{
		if (oldBiomeList == null || oldBiomeList.length < width * depth)
		{
			oldBiomeList = new Biome[width * depth];
		}

		for (int i = 0; i < depth; ++i)
		{
			for (int j = 0; j < width; ++j)
			{
				oldBiomeList[i*width+j]=getNoiseBiome(x+j, 0, z+i);
			}
		}
		return oldBiomeList;
	}

	public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag)
	{
		return this.getBiomes(listToReuse, x, z, width, length);
	}


//	@Override
//	public BiomeProvider getBiomeProvider(long seed)
//	{
//		return new BetterEndBiomeProvider(this.lookupRegistry, seed);
//	}
	
//	public static void register()
//	{
//		Registry.register(Registry.BIOME_PROVIDER_CODEC,
//				new ResourceLocation(BetterEnd.MOD_ID, "betterend_biome_provider"), BETTER_END_CODEC);
//	}
}
