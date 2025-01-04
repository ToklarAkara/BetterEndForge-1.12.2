package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.ConfiguredFeature;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.IFeatureConfig;
import mod.beethoven92.betterendforge.common.world.moderngen.placement.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
public class ModConfiguredFeatures 
{
	// WATER PLANTS
	public static final ConfiguredFeature<?, ?> END_LOTUS = 
			new ConfiguredFeature<>(ModFeatures.END_LOTUS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(5));
	
	public static final ConfiguredFeature<?, ?> END_LOTUS_LEAF = 
			new ConfiguredFeature<>(ModFeatures.END_LOTUS_LEAF,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(25));
	
	public static final ConfiguredFeature<?, ?> BUBBLE_CORAL = 
			new ConfiguredFeature<>(ModFeatures.BUBBLE_CORAL,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(10));
	
	public static final ConfiguredFeature<?, ?> BUBBLE_CORAL_RARE = 
			new ConfiguredFeature<>(ModFeatures.BUBBLE_CORAL_RARE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(4));
	
	public static final ConfiguredFeature<?, ?> END_LILY = 
			new ConfiguredFeature<>(ModFeatures.END_LILY,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(10));
	
	public static final ConfiguredFeature<?, ?> END_LILY_RARE = 
			new ConfiguredFeature<>(ModFeatures.END_LILY_RARE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(4));
	
	public static final ConfiguredFeature<?, ?> MENGER_SPONGE = 
			new ConfiguredFeature<>(ModFeatures.MENGER_SPONGE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(1));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_RED = 
			new ConfiguredFeature<>(ModFeatures.CHARNIA_RED,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_PURPLE = 
			new ConfiguredFeature<>(ModFeatures.CHARNIA_PURPLE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_ORANGE = 
			new ConfiguredFeature<>(ModFeatures.CHARNIA_ORANGE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_LIGHT_BLUE = 
			new ConfiguredFeature<>(ModFeatures.CHARNIA_LIGHT_BLUE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_CYAN = 
			new ConfiguredFeature<>(ModFeatures.CHARNIA_CYAN,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_GREEN = 
			new ConfiguredFeature<>(ModFeatures.CHARNIA_GREEN,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(10));
	
	public static final ConfiguredFeature<?, ?> CHARNIA_RED_RARE = 
			new ConfiguredFeature<>(ModFeatures.CHARNIA_RED_RARE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(2));
	
	public static final ConfiguredFeature<?, ?> HYDRALUX = 
			new ConfiguredFeature<>(ModFeatures.HYDRALUX,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(5));
	
	public static final ConfiguredFeature<?, ?> FLAMAEA = 
			new ConfiguredFeature<>(ModFeatures.FLAMAEA,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(20);
	
	public static final ConfiguredFeature<?, ?> POND_ANEMONE = 
			new ConfiguredFeature<>(ModFeatures.POND_ANEMONE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(10);

	
	// BUSHES
	public static final ConfiguredFeature<?, ?> PYTHADENDRON_BUSH = 
			new ConfiguredFeature<>(ModFeatures.PYTHADENDRON_BUSH,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	public static final ConfiguredFeature<?, ?> DRAGON_TREE_BUSH = 
			new ConfiguredFeature<>(ModFeatures.DRAGON_TREE_BUSH,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	public static final ConfiguredFeature<?, ?> LARGE_AMARANITA = 
			new ConfiguredFeature<>(ModFeatures.LARGE_AMARANITA,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(5);
	
	public static final ConfiguredFeature<?, ?> TENANEA_BUSH = 
			new ConfiguredFeature<>(ModFeatures.TENANEA_BUSH,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	public static final ConfiguredFeature<?, ?> LUCERNIA_BUSH = 
			new ConfiguredFeature<>(ModFeatures.LUCERNIA_BUSH,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(10);

	public static final ConfiguredFeature<?, ?> LUCERNIA_BUSH_RARE =
			new ConfiguredFeature<>(ModFeatures.LUCERNIA_BUSH_RARE,IFeatureConfig.NO_FEATURE_CONFIG).
					decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(1);
	
	public static final ConfiguredFeature<?, ?> NEON_CACTUS = 
			new ConfiguredFeature<>(ModFeatures.NEON_CACTUS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(2);

	
	// PLANTS
	public static final ConfiguredFeature<?, ?> UMBRELLA_MOSS = 
			new ConfiguredFeature<>(ModFeatures.UMBRELLA_MOSS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(5));
	
	public static final ConfiguredFeature<?, ?> CREEPING_MOSS = 
			new ConfiguredFeature<>(ModFeatures.CREEPING_MOSS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(5));
	
	public static final ConfiguredFeature<?, ?> CHORUS_GRASS = 
			new ConfiguredFeature<>(ModFeatures.CHORUS_GRASS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(5));

	public static final ConfiguredFeature<?, ?> CHORUS_MUSHROOM =
			new ConfiguredFeature<>(ModFeatures.CHORUS_MUSHROOM,IFeatureConfig.NO_FEATURE_CONFIG).
					decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(1));

	public static final ConfiguredFeature<?, ?> CRYSTAL_GRASS = 
			new ConfiguredFeature<>(ModFeatures.CRYSTAL_GRASS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(5));
	
	public static final ConfiguredFeature<?, ?> AMBER_GRASS = 
			new ConfiguredFeature<>(ModFeatures.AMBER_GRASS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(9));

	public static final ConfiguredFeature<?, ?> AMBER_ROOT =
			new ConfiguredFeature<>(ModFeatures.AMBER_ROOT,IFeatureConfig.NO_FEATURE_CONFIG).
					decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(1));

	public static final ConfiguredFeature<?, ?> SHADOW_PLANT = 
			new ConfiguredFeature<>(ModFeatures.SHADOW_PLANT,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(9));
	
	public static final ConfiguredFeature<?, ?> BLUE_VINE = 
			new ConfiguredFeature<>(ModFeatures.BLUE_VINE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(1));
	
	public static final ConfiguredFeature<?, ?> MURKWEED = 
			new ConfiguredFeature<>(ModFeatures.MURKWEED,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(2));
	
	public static final ConfiguredFeature<?, ?> NEEDLEGRASS = 
			new ConfiguredFeature<>(ModFeatures.NEEDLEGRASS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(2));
	
	public static final ConfiguredFeature<?, ?> SHADOW_BERRY = 
			new ConfiguredFeature<>(ModFeatures.SHADOW_BERRY,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(1));
	
	public static final ConfiguredFeature<?, ?> BUSHY_GRASS = 
			new ConfiguredFeature<>(ModFeatures.BUSHY_GRASS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(20));
	
	public static final ConfiguredFeature<?, ?> BUSHY_GRASS_WG = 
			new ConfiguredFeature<>(ModFeatures.BUSHY_GRASS_WG,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(10));
	
	public static final ConfiguredFeature<?, ?> LANCELEAF = 
			new ConfiguredFeature<>(ModFeatures.LANCELEAF,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(3));
	
	public static final ConfiguredFeature<?, ?> GLOW_PILLAR = 
			new ConfiguredFeature<>(ModFeatures.GLOW_PILLAR,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(1));
	
	public static final ConfiguredFeature<?, ?> TWISTED_UMBRELLA_MOSS = 
			new ConfiguredFeature<>(ModFeatures.TWISTED_UMBRELLA_MOSS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(5));
	
	public static final ConfiguredFeature<?, ?> JUNGLE_GRASS = 
			new ConfiguredFeature<>(ModFeatures.JUNGLE_GRASS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(8));
	
	public static final ConfiguredFeature<?, ?> SMALL_JELLYSHROOM_FLOOR = 
			new ConfiguredFeature<>(ModFeatures.SMALL_JELLYSHROOM_FLOOR,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(4));
	
	public static final ConfiguredFeature<?, ?> BLOSSOM_BERRY = 
			new ConfiguredFeature<>(ModFeatures.BLOSSOM_BERRY,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(2));
	
	public static final ConfiguredFeature<?, ?> LUMECORN = 
			new ConfiguredFeature<>(ModFeatures.LUMECORN,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(5);
	
	public static final ConfiguredFeature<?, ?> BLOOMING_COOKSONIA = 
			new ConfiguredFeature<>(ModFeatures.BLOOMING_COOKSONIA,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(5);
	
	public static final ConfiguredFeature<?, ?> SALTEAGO = 
			new ConfiguredFeature<>(ModFeatures.SALTEAGO,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(5);
	
	public static final ConfiguredFeature<?, ?> VAIOLUSH_FERN = 
			new ConfiguredFeature<>(ModFeatures.VAIOLUSH_FERN,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(5);
	
	public static final ConfiguredFeature<?, ?> FRACTURN = 
			new ConfiguredFeature<>(ModFeatures.FRACTURN,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(5);
	
	public static final ConfiguredFeature<?, ?> UMBRELLA_MOSS_RARE = 
			new ConfiguredFeature<>(ModFeatures.UMBRELLA_MOSS_RARE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(2);
	
	public static final ConfiguredFeature<?, ?> CREEPING_MOSS_RARE = 
			new ConfiguredFeature<>(ModFeatures.CREEPING_MOSS_RARE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(2);
	
	public static final ConfiguredFeature<?, ?> TWISTED_UMBRELLA_MOSS_RARE = 
			new ConfiguredFeature<>(ModFeatures.TWISTED_UMBRELLA_MOSS_RARE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(2);
	
	public static final ConfiguredFeature<?, ?> SMALL_AMARANITA = 
			new ConfiguredFeature<>(ModFeatures.SMALL_AMARANITA,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(4);
	
	public static final ConfiguredFeature<?, ?> GLOBULAGUS = 
			new ConfiguredFeature<>(ModFeatures.GLOBULAGUS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(6);
	
	public static final ConfiguredFeature<?, ?> CLAWFERN = 
			new ConfiguredFeature<>(ModFeatures.CLAWFERN,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(5);
	
	public static final ConfiguredFeature<?, ?> AERIDIUM = 
			new ConfiguredFeature<>(ModFeatures.AERIDIUM,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(5);
	
	public static final ConfiguredFeature<?, ?> LAMELLARIUM = 
			new ConfiguredFeature<>(ModFeatures.LAMELLARIUM,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(6); 
	
	public static final ConfiguredFeature<?, ?> BOLUX_MUSHROOM = 
			new ConfiguredFeature<>(ModFeatures.BOLUX_MUSHROOM,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(2); 
	
	public static final ConfiguredFeature<?, ?> ORANGO = 
			new ConfiguredFeature<>(ModFeatures.ORANGO,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(6); 
	
	public static final ConfiguredFeature<?, ?> LUTEBUS = 
			new ConfiguredFeature<>(ModFeatures.LUTEBUS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(5);

	public static final ConfiguredFeature<?, ?> FLAMMALIX =
			new ConfiguredFeature<>(ModFeatures.FLAMMALIX,IFeatureConfig.NO_FEATURE_CONFIG).
					decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(5);

	public static final ConfiguredFeature<?, ?> INFLEXIA =
			new ConfiguredFeature<>(ModFeatures.INFLEXIA,IFeatureConfig.NO_FEATURE_CONFIG).
					decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(16);


	// SKY PLANTS
	public static final ConfiguredFeature<?, ?> FILALUX = 
			new ConfiguredFeature<>(ModFeatures.FILALUX,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(1);

	
	
	// WALL PLANTS
	public static final ConfiguredFeature<?, ?> PURPLE_POLYPORE = 
			new ConfiguredFeature<>(ModFeatures.PURPLE_POLYPORE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(5));
	
	public static final ConfiguredFeature<?, ?> AURANT_POLYPORE = 
			new ConfiguredFeature<>(ModFeatures.AURANT_POLYPORE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(5));
	
	public static final ConfiguredFeature<?, ?> PURPLE_POLYPORE_DENSE = 
			new ConfiguredFeature<>(ModFeatures.PURPLE_POLYPORE_DENSE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(15));
	
	public static final ConfiguredFeature<?, ?> TAIL_MOSS = 
			new ConfiguredFeature<>(ModFeatures.TAIL_MOSS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(15));
	
	public static final ConfiguredFeature<?, ?> TAIL_MOSS_WOOD = 
			new ConfiguredFeature<>(ModFeatures.TAIL_MOSS_WOOD,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(25));
	
	public static final ConfiguredFeature<?, ?> CYAN_MOSS = 
			new ConfiguredFeature<>(ModFeatures.CYAN_MOSS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(15));
	
	public static final ConfiguredFeature<?, ?> CYAN_MOSS_WOOD = 
			new ConfiguredFeature<>(ModFeatures.CYAN_MOSS_WOOD,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(25));
	
	public static final ConfiguredFeature<?, ?> TWISTED_MOSS = 
			new ConfiguredFeature<>(ModFeatures.TWISTED_MOSS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(15));
	
	public static final ConfiguredFeature<?, ?> TWISTED_MOSS_WOOD = 
			new ConfiguredFeature<>(ModFeatures.TWISTED_MOSS_WOOD,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(25));
	
	public static final ConfiguredFeature<?, ?> BULB_MOSS = 
			new ConfiguredFeature<>(ModFeatures.BULB_MOSS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(1));
	
	public static final ConfiguredFeature<?, ?> BULB_MOSS_WOOD = 
			new ConfiguredFeature<>(ModFeatures.BULB_MOSS_WOOD,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(15));
	
	public static final ConfiguredFeature<?, ?> SMALL_JELLYSHROOM_WALL = 
			new ConfiguredFeature<>(ModFeatures.SMALL_JELLYSHROOM_WALL,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(4));
	
	public static final ConfiguredFeature<?, ?> SMALL_JELLYSHROOM_WOOD = 
			new ConfiguredFeature<>(ModFeatures.SMALL_JELLYSHROOM_WOOD,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(8));
	
	public static final ConfiguredFeature<?, ?> JUNGLE_FERN_WOOD = 
			new ConfiguredFeature<>(ModFeatures.JUNGLE_FERN_WOOD,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(12));
	
	public static final ConfiguredFeature<?, ?> RUSCUS = 
			new ConfiguredFeature<>(ModFeatures.RUSCUS,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(10));
	
	public static final ConfiguredFeature<?, ?> RUSCUS_WOOD = 
			new ConfiguredFeature<>(ModFeatures.RUSCUS_WOOD,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(10));

	
	// VINES
	public static final ConfiguredFeature<?, ?> DENSE_VINE = 
			new ConfiguredFeature<>(ModFeatures.DENSE_VINE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(3));
	
	public static final ConfiguredFeature<?, ?> TWISTED_VINE = 
			new ConfiguredFeature<>(ModFeatures.TWISTED_VINE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(3));
	
	public static final ConfiguredFeature<?, ?> BULB_VINE = 
			new ConfiguredFeature<>(ModFeatures.BULB_VINE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(5));
	
	public static final ConfiguredFeature<?, ?> JUNGLE_VINE = 
			new ConfiguredFeature<>(ModFeatures.JUNGLE_VINE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(25));
	
	// CEIL PLANTS
	public static final ConfiguredFeature<?, ?> SMALL_JELLYSHROOM_CEIL = 
			new ConfiguredFeature<>(ModFeatures.SMALL_JELLYSHROOM_CEIL,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).count(8));
	
	// TERRAIN
	public static final ConfiguredFeature<?, ?> ROUND_CAVE = 
			new ConfiguredFeature<>(ModFeatures.ROUND_CAVE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.CHANCE.configure(new ChanceConfig(2)));
	
	public static final ConfiguredFeature<?, ?> END_LAKE = 
			new ConfiguredFeature<>(ModFeatures.END_LAKE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.WATER_LAKE.configure(new ChanceConfig(4)));
	public static final ConfiguredFeature<?, ?> END_LAKE_RARE = 
			new ConfiguredFeature<>(ModFeatures.END_LAKE_RARE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.WATER_LAKE.configure(new ChanceConfig(40)));
	public static final ConfiguredFeature<?, ?> END_LAKE_NORMAL = 
			new ConfiguredFeature<>(ModFeatures.END_LAKE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.WATER_LAKE.configure(new ChanceConfig(20)));
	
	public static final ConfiguredFeature<?, ?> DESERT_LAKE = 
			new ConfiguredFeature<>(ModFeatures.DESERT_LAKE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.WATER_LAKE.configure(new ChanceConfig(8)));
	
	public static final ConfiguredFeature<?, ?> SPIRE = 
			new ConfiguredFeature<>(ModFeatures.SPIRE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.CHANCE.configure(new ChanceConfig(2)));
	public static final ConfiguredFeature<?, ?> FLOATING_SPIRE = 
			new ConfiguredFeature<>(ModFeatures.FLOATING_SPIRE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.CHANCE.configure(new ChanceConfig(8)));
	
	public static final ConfiguredFeature<?, ?> GEYSER = 
			new ConfiguredFeature<>(ModFeatures.GEYSER,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.CHANCE.configure(new ChanceConfig(8)));
	
	public static final ConfiguredFeature<?, ?> SULPHURIC_LAKE = 
			new ConfiguredFeature<>(ModFeatures.SULPHURIC_LAKE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.WATER_LAKE.configure(new ChanceConfig(8)));
	
	public static final ConfiguredFeature<?, ?> SULPHURIC_CAVE = 
			new ConfiguredFeature<>(ModFeatures.SULPHURIC_CAVE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.COUNT.configure(new FeatureSpreadConfig(2)));
	
	public static final ConfiguredFeature<?, ?> SURFACE_VENT = 
			new ConfiguredFeature<>(ModFeatures.SURFACE_VENT,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.CHANCE.configure(new ChanceConfig(4)));
	
	public static final ConfiguredFeature<?, ?> ICE_STAR = 
			new ConfiguredFeature<>(ModFeatures.ICE_STAR,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.CHANCE.configure(new ChanceConfig(15)));
	
	public static final ConfiguredFeature<?, ?> ICE_STAR_SMALL = 
			new ConfiguredFeature<>(ModFeatures.ICE_STAR_SMALL,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.CHANCE.configure(new ChanceConfig(8)));
	
	public static final ConfiguredFeature<?, ?> OVERWORLD_ISLAND = 
			new ConfiguredFeature<>(ModFeatures.OVERWORLD_ISLAND,IFeatureConfig.NO_FEATURE_CONFIG);
	
	public static final ConfiguredFeature<?, ?> OBSIDIAN_PILLAR_BASEMENT = 
			new ConfiguredFeature<>(ModFeatures.OBSIDIAN_PILLAR_BASEMENT,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.CHANCE.configure(new ChanceConfig(8)));
	
	public static final ConfiguredFeature<?, ?> FALLEN_PILLAR = 
			new ConfiguredFeature<>(ModFeatures.FALLEN_PILLAR,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.CHANCE.configure(new ChanceConfig(20)));
	
	public static final ConfiguredFeature<?, ?> OBSIDIAN_BOULDER = 
			new ConfiguredFeature<>(ModFeatures.OBSIDIAN_BOULDER,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.CHANCE.configure(new ChanceConfig(10)));

	public static final ConfiguredFeature<?, ?> TUNEL_CAVE =
			new ConfiguredFeature<>(ModFeatures.TUNEL_CAVE,IFeatureConfig.NO_FEATURE_CONFIG)
			.decorated(Placement.COUNT.configure(new FeatureSpreadConfig(1)));

	public static final ConfiguredFeature<?, ?> THIN_ARCH =
			new ConfiguredFeature<>(ModFeatures.THIN_ARCH,IFeatureConfig.NO_FEATURE_CONFIG)
					.decorated(Placement.CHANCE.configure(new ChanceConfig(15)));

	public static final ConfiguredFeature<?, ?> UMBRALITH_ARCH =
			new ConfiguredFeature<>(ModFeatures.UMBRALITH_ARCH,IFeatureConfig.NO_FEATURE_CONFIG)
					.decorated(Placement.CHANCE.configure(new ChanceConfig(10)));


	// TREES
	public static final ConfiguredFeature<?, ?> MOSSY_GLOWSHROOM = 
			new ConfiguredFeature<>(ModFeatures.MOSSY_GLOWSHROOM,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));//count(3));
	
	public static final ConfiguredFeature<?, ?> LACUGROVE = 
			new ConfiguredFeature<>(ModFeatures.LACUGROVE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	public static final ConfiguredFeature<?, ?> PYTHADENDRON = 
			new ConfiguredFeature<>(ModFeatures.PYTHADENDRON, IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	public static final ConfiguredFeature<?, ?> DRAGON_TREE = 
			new ConfiguredFeature<>(ModFeatures.DRAGON_TREE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	public static final ConfiguredFeature<?, ?> TENANEA = 
			new ConfiguredFeature<>(ModFeatures.TENANEA,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	public static final ConfiguredFeature<?, ?> HELIX_TREE = 
			new ConfiguredFeature<>(ModFeatures.HELIX_TREE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	public static final ConfiguredFeature<?, ?> UMBRELLA_TREE = 
			new ConfiguredFeature<>(ModFeatures.UMBRELLA_TREE,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	public static final ConfiguredFeature<?, ?> JELLYSHROOM = 
			new ConfiguredFeature<>(ModFeatures.JELLYSHROOM,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.TOP_SOLID_HEIGHTMAP.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG).
			decorated(Placement.SQUARE.configure(NoPlacementConfig.NO_PLACEMENT_CONFIG)));
	
	public static final ConfiguredFeature<?, ?> GIGANTIC_AMARANITA = 
			new ConfiguredFeature<>(ModFeatures.GIGANTIC_AMARANITA,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(1);
	
	public static final ConfiguredFeature<?, ?> LUCERNIA = 
			new ConfiguredFeature<>(ModFeatures.LUCERNIA,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG).squared()).countRandom(3);

//	// ORES
//	public static final ConfiguredFeature<?, ?> THALLASIUM_ORE =
//			new ConfiguredFeature<>(ModFeatures.THALLASIUM_ORE,
//					new OreFeatureConfig(
//							new BlockMatchRuleTest(Blocks.END_STONE), ModBlocks.THALLASIUM.ore.getDefaultState(), 6)).
//			range(96).square().count(20);
//
//	public static final ConfiguredFeature<?, ?> ENDER_ORE =
//			new ConfiguredFeature<>(ModFeatures.ENDER_ORE,
//					new OreFeatureConfig(
//							new BlockMatchRuleTest(Blocks.END_STONE), ModBlocks.ENDER_ORE.getDefaultState(), 3)).
//			range(96).square().count(20);
//
//	public static final ConfiguredFeature<?, ?> AMBER_ORE =
//			new ConfiguredFeature<>(ModFeatures.AMBER_ORE,
//					new OreFeatureConfig(
//							new BlockMatchRuleTest(Blocks.END_STONE), ModBlocks.AMBER_ORE.getDefaultState(), 6)).
//			range(96).square().count(20);
	
	public static final ConfiguredFeature<?, ?> FLAVOLITE_LAYER = 
			new ConfiguredFeature<>(ModFeatures.FLAVOLITE_LAYER,IFeatureConfig.NO_FEATURE_CONFIG).
			//decorated(Placement.field_242898_b.configure(new ChanceConfig(6)));
			decorated(Placement.COUNT.configure(new FeatureSpreadConfig(6)));
	
	public static final ConfiguredFeature<?, ?> VIOLECITE_LAYER = 
			new ConfiguredFeature<>(ModFeatures.VIOLECITE_LAYER,IFeatureConfig.NO_FEATURE_CONFIG).
			//decorated(Placement.field_242898_b.configure(new ChanceConfig(8)));
			decorated(Placement.COUNT.configure(new FeatureSpreadConfig(8)));
	
	// BUILDINGS
	public static final ConfiguredFeature<?, ?> CRASHED_SHIP = 
			new ConfiguredFeature<>(ModFeatures.CRASHED_SHIP,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.CHANCE.configure(new ChanceConfig(500)));
	
	public static final ConfiguredFeature<?, ?> NBT_STRUCTURES = 
			new ConfiguredFeature<>(ModFeatures.NBT_STRUCTURES,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.CHANCE.configure(new ChanceConfig(10)));
	
	// MOBS
	public static final ConfiguredFeature<?, ?> SILK_MOTH_NEST = 
			new ConfiguredFeature<>(ModFeatures.SILK_MOTH_NEST,IFeatureConfig.NO_FEATURE_CONFIG).
			decorated(Placement.CHANCE.configure(new ChanceConfig(2)));

	// CAVES
	public static final ConfiguredFeature<?, ?> SMARAGDANT_CRYSTAL = 
			new ConfiguredFeature<>(ModFeatures.SMARAGDANT_CRYSTAL,IFeatureConfig.NO_FEATURE_CONFIG);
	
	public static final ConfiguredFeature<?, ?> SMARAGDANT_CRYSTAL_SHARD = 
			new ConfiguredFeature<>(ModFeatures.SMARAGDANT_CRYSTAL_SHARD,IFeatureConfig.NO_FEATURE_CONFIG);
	
	public static final ConfiguredFeature<?, ?> BIG_AURORA_CRYSTAL = 
			new ConfiguredFeature<>(ModFeatures.BIG_AURORA_CRYSTAL,IFeatureConfig.NO_FEATURE_CONFIG);
	
	public static final ConfiguredFeature<?, ?> END_STONE_STALACTITE = 
			new ConfiguredFeature<>(ModFeatures.END_STONE_STALACTITE,IFeatureConfig.NO_FEATURE_CONFIG);
	
	public static final ConfiguredFeature<?, ?> END_STONE_STALAGMITE = 
			new ConfiguredFeature<>(ModFeatures.END_STONE_STALAGMITE,IFeatureConfig.NO_FEATURE_CONFIG);
	
	public static final ConfiguredFeature<?, ?> END_STONE_STALACTITE_CAVEMOSS = 
			new ConfiguredFeature<>(ModFeatures.END_STONE_STALACTITE_CAVEMOSS,IFeatureConfig.NO_FEATURE_CONFIG);
	
	public static final ConfiguredFeature<?, ?> END_STONE_STALAGMITE_CAVEMOSS = 
			new ConfiguredFeature<>(ModFeatures.END_STONE_STALAGMITE_CAVEMOSS,IFeatureConfig.NO_FEATURE_CONFIG);
	
	public static final ConfiguredFeature<?, ?> CAVE_BUSH = 
			new ConfiguredFeature<>(ModFeatures.CAVE_BUSH,IFeatureConfig.NO_FEATURE_CONFIG);
	
	public static final ConfiguredFeature<?, ?> CAVE_GRASS = 
			new ConfiguredFeature<>(ModFeatures.CAVE_GRASS,IFeatureConfig.NO_FEATURE_CONFIG);
	
	public static final ConfiguredFeature<?, ?> RUBINEA = 
			new ConfiguredFeature<>(ModFeatures.RUBINEA,IFeatureConfig.NO_FEATURE_CONFIG);

}
