package mod.beethoven92.betterendforge.common.world.biome;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.ConfiguredFeature;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.EndDecorator;
import mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders.ConfiguredSurfaceBuilder;
import mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders.SurfaceBuilder;
import mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import org.apache.commons.lang3.tuple.Pair;

public class BiomeTemplate
{
	private static final int DEFAULT_FOLIAGE = ModMathHelper.color(197, 210, 112);

	private final List<MapGenStructure> structures = new ArrayList<>();
	private final List<List<ConfiguredFeature<?,?>>> features = Lists.newArrayList();
//	private final List<CarverInfo> carvers = Lists.newArrayList();
	private final List<SpawnInfo> mobs = Lists.newArrayList();
	private final List<Biome.SpawnListEntry> spawns = Lists.newArrayList();

	private float depth = 0.1F;
    private float scale = 0.2F;
    private float temperature = 2.0F;
    private float downfall = 0.0F;

	private float fogDensity = 1F;;
	private int fogColor = 10518688;;
	private int waterColor = 4159204;;
	private int waterFogColor = 329011;
	private int foliageColor = DEFAULT_FOLIAGE;
	private int grassColor = DEFAULT_FOLIAGE;

	private SoundEvent ambient;
//	private MoodSoundAmbience mood;
//	private SoundAdditionsAmbience additions;
	private SoundEvent music;

//	private ParticleEffectAmbience particle;

	private final ResourceLocation id;
	private float genChance = 1F;
	private boolean hasCaves = true;
	private boolean isCaveBiome = false;
	private ConfiguredSurfaceBuilder<?> surface;

	public BiomeTemplate(String name)
	{
		this.id = new ResourceLocation(BetterEnd.MOD_ID, name);
		for(int i=0;i<Decoration.values().length;i++){
			features.add(new ArrayList<>());
		}
	}

	public BiomeTemplate setDepth(float depth)
	{
		this.depth = depth;
		return this;
	}

	public BiomeTemplate setScale(float scale)
	{
		this.scale = scale;
		return this;
	}

	public BiomeTemplate setTemperature(float temperature)
	{
		this.temperature = temperature;
		return this;
	}

	public BiomeTemplate setDownfall(float downfall)
	{
		this.downfall = downfall;
		return this;
	}

//	public BiomeTemplate setParticles(IParticleData particle, float probability)
//	{
//		this.particle = new ParticleEffectAmbience(particle, probability);
//		return this;
//	}

	public BiomeTemplate setFogDensity(float density)
	{
		this.fogDensity = density;
		return this;
	}

	public BiomeTemplate setFogColor(int r, int g, int b)
	{
		fogColor = ModMathHelper.getColor(r, g, b);
		return this;
	}

	public BiomeTemplate setFogColor(int color)
	{
		fogColor = color;
		return this;
	}

	public BiomeTemplate setWaterColor(int r, int g, int b)
	{
		this.waterColor = ModMathHelper.getColor(r, g, b);
		return this;
	}

	public BiomeTemplate setWaterColor(int color)
	{
		this.waterColor = color;
		return this;
	}

	public BiomeTemplate setWaterFogColor(int r, int g, int b)
	{
		this.waterFogColor = ModMathHelper.getColor(r, g, b);
		return this;
	}

	public BiomeTemplate setWaterFogColor(int color)
	{
		this.waterFogColor = color;
		return this;
	}

	public BiomeTemplate setFoliageColor(int r, int g, int b)
	{
		this.foliageColor = ModMathHelper.getColor(r, g, b);
		return this;
	}

	public BiomeTemplate setFoliageColor(int color)
	{
		this.foliageColor = color;
		return this;
	}

	public BiomeTemplate setGrassColor(int r, int g, int b)
	{
		this.grassColor = ModMathHelper.getColor(r, g, b);
		return this;
	}

	public BiomeTemplate setGrassColor(int color)
	{
		this.grassColor = color;
		return this;
	}

	public BiomeTemplate setAmbientSound(SoundEvent ambient)
	{
		this.ambient = ambient;
		return this;
	}

//	public BiomeTemplate setMoodSound(SoundEvent mood)
//	{
//		this.mood = new MoodSoundAmbience(mood, 6000, 8, 2.0D);
//		return this;
//	}
//
//	public BiomeTemplate setAdditionsSounds(SoundEvent additions)
//	{
//		this.additions = new SoundAdditionsAmbience(additions, 0.0111D);
//		return this;
//	}

	public BiomeTemplate setMusic(SoundEvent music)
	{
		this.music = music;
		return this;
	}

	public BiomeTemplate setCaves(boolean hasCaves)
	{
		this.hasCaves = hasCaves;
		return this;
	}

	public BiomeTemplate setCaveBiome()
	{
		isCaveBiome = true;
		return this;
	}

	public BiomeTemplate setGenChance(float genChance)
	{
		this.genChance = genChance;
		return this;
	}

	public BiomeTemplate addMobSpawn(Biome.SpawnListEntry entry)
	{
		spawns.add(entry);
		return this;
	}

	public BiomeTemplate addMobSpawn(EnumCreatureType type, Class<? extends EntityLiving> entity,
			int weight, int minCount, int maxCount)
	{
		SpawnInfo info = new SpawnInfo();
		info.type = type;
		info.entity = entity;
		info.weight = weight;
		info.minGroupSize = minCount;
		info.maxGroupSize = maxCount;
		mobs.add(info);
		return this;
	}

	public BiomeTemplate setSurface(Block block)
	{
		setSurface(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(
				block.getDefaultState(),
				Blocks.END_STONE.getDefaultState(),
				Blocks.END_STONE.getDefaultState()
		)));
		return this;
	}

	public BiomeTemplate setSurface(ConfiguredSurfaceBuilder<?> surface)
	{
		this.surface = surface;
		return this;
	}

//	public BiomeTemplate setSurface(Block block)
//	{
//		topBlock = block;
//		fillerBlock = Blocks.END_STONE;
////		setSurface(() -> SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(
////				block.getDefaultState(),
////				Blocks.END_STONE.getDefaultState(),
////				Blocks.END_STONE.getDefaultState()
////		)));
//		return this;
//	}
//
//	public BiomeTemplate setSurface(AbstractMap.SimpleEntry<Block, Block> surface)
//	{
//		topBlock = surface.getKey();
//		fillerBlock = surface.getValue();
//		return this;
//	}

	public BiomeTemplate addStructure(MapGenStructure structure)
	{
		structures.add(structure);
		return this;
	}

	public BiomeTemplate addFeature(Decoration stage, ConfiguredFeature<?,?> feature){
		features.get(stage.ordinal()).add(feature);
		return this;
	}

//	public BiomeTemplate addFeature(Decoration stage, ConfiguredFeature<?,?> feature)
//	{
//		FeatureInfo info = new FeatureInfo();
//		info.stage = stage;
//		info.feature = feature;
//		features.add(info);
//		return this;
//	}

//	public BiomeTemplate addCarver(Carving carverStep, ConfiguredCarver<ProbabilityConfig> carver)
//	{
//		CarverInfo info = new CarverInfo();
//		info.carverStep = carverStep;
//		info.carver = carver;
//		carvers.add(info);
//		return this;
//	}


	public Biome build(){
		ExtendedBiome biome = new ExtendedBiome(
				new Biome.BiomeProperties(id.getPath())
						.setWaterColor(waterColor)
						.setTemperature(temperature)
						.setRainfall(downfall)
						.setBaseHeight(depth)
						.setHeightVariation(scale)
						.setRainDisabled()
		);

		biome.getSpawnableList(EnumCreatureType.MONSTER).clear();
		biome.getSpawnableList(EnumCreatureType.CREATURE).clear();
		biome.getSpawnableList(EnumCreatureType.AMBIENT).clear();
		biome.getSpawnableList(EnumCreatureType.WATER_CREATURE).clear();
		biome.setFoliageColor(foliageColor);
		biome.setGrassColor(grassColor);
		biome.setSurface(surface);
		biome.setFogColor(fogColor);

		for (SpawnInfo info : mobs)
		{
			biome.getSpawnableList(info.type).add(new Biome.SpawnListEntry(info.entity, info.weight, info.minGroupSize, info.maxGroupSize));
		}

		spawns.forEach((entry) -> {
			biome.getSpawnableList(EnumCreatureType.CREATURE).add(entry);
		});

		biome.decorator = new EndDecorator(features);

//		if(topBlock!=null) {
//			biome.topBlock = topBlock.getDefaultState();
//		}else{
//			biome.topBlock = Blocks.END_STONE.getDefaultState();
//		}
//		if(fillerBlock!=null) {
//			biome.fillerBlock = fillerBlock.getDefaultState();
//		}else{
//			biome.fillerBlock = Blocks.END_STONE.getDefaultState();
//		}

		return biome;
	}

//	public Biome build()
//	{
//		BiomeAmbience.Builder effects = new BiomeAmbience.Builder();
//		BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
//		MobSpawnInfo.Builder spawnSettings = new MobSpawnInfo.Builder();
//
//		// Set mob spawns
//		for (SpawnInfo info : mobs)
//		{
//			spawnSettings.withSpawner(info.type,
//					new MobSpawnInfo.Spawners(info.entity, info.weight, info.minGroupSize, info.maxGroupSize));
//		}
//
//		spawns.forEach((entry) -> {
//			spawnSettings.withSpawner(entry.type.getClassification(), entry);
//		});
//
//		// Set biome general features
//		if (surface != null)
//		{
//			generationSettings.withSurfaceBuilder(surface);
//		}
//		else
//		{
//			generationSettings.withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244173_e);
//		}
//
//		for (CarverInfo info : carvers)
//		{
//			generationSettings.withCarver(info.carverStep, info.carver);
//		}
//		for(FeatureInfo info : features)
//		{
//			generationSettings.withFeature(info.stage, info.feature);
//		}
//		for(StructureFeature<?,?> structure : structures)
//		{
//			generationSettings.withStructure(structure);
//		}
//
//		// Set effects
//		effects.withSkyColor(0).
//		        setWaterFogColor(waterFogColor).
//		        setWaterColor(waterColor). //+
//		        setFogColor(fogColor).
//		        withFoliageColor(foliageColor).
//		        withGrassColor(grassColor);
//
//		// Set sound effects
//		if (ambient != null) effects.setAmbientSound(ambient);
//		if (mood != null) effects.setMoodSound(mood);
//		if (additions != null) effects.setAdditionsSound(additions);
//		if (music != null)
//		{
//			effects.setMusic(new BackgroundMusicSelector(music, 600, 2400, true));
//		}
//		else
//		{
//			effects.setMusic(BackgroundMusicTracks.END_MUSIC);
//		}
//
//		// Set particles
//		if (particle != null) effects.setParticle(particle);
//
//		return new Biome.Builder().
//				         category(isCaveBiome ? Category.NONE : Category.THEEND).
//				         precipitation(RainType.NONE).
//				         depth(this.depth).
//				         scale(this.scale).
//				         temperature(this.temperature).
//				         withTemperatureModifier(TemperatureModifier.NONE).
//				         downfall(this.downfall).
//				         setEffects(effects.build()).
//				         withGenerationSettings(generationSettings.build()).
//				         withMobSpawnSettings(spawnSettings.copy()).
//				         build();
//	}

	private static final class SpawnInfo
	{
		EnumCreatureType type;
		Class<? extends EntityLiving> entity;
		int weight;
		int minGroupSize;
		int maxGroupSize;
	}

//	private static final class CarverInfo
//	{
//		Carving carverStep;
//		ConfiguredCarver<ProbabilityConfig> carver;
//	}

	public ResourceLocation getID()
	{
		return id;
	}

	public float getFogDensity()
	{
		return fogDensity;
	}

	public float getGenChance()
	{
		return genChance;
	}

	public boolean hasCaves()
	{
		return hasCaves;
	}
}
