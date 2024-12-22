package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import mod.beethoven92.betterendforge.common.world.feature.BigAuroraCrystalFeature;
import mod.beethoven92.betterendforge.common.world.feature.BiomeNBTStructures;
import mod.beethoven92.betterendforge.common.world.feature.BlueVineFeature;
import mod.beethoven92.betterendforge.common.world.feature.BushFeature;
import mod.beethoven92.betterendforge.common.world.feature.BushWithOuterFeature;
import mod.beethoven92.betterendforge.common.world.feature.CharniaFeature;
import mod.beethoven92.betterendforge.common.world.feature.CrashedShipFeature;
import mod.beethoven92.betterendforge.common.world.feature.DesertLakeFeature;
import mod.beethoven92.betterendforge.common.world.feature.DoublePlantFeature;
import mod.beethoven92.betterendforge.common.world.feature.DragonTreeFeature;
import mod.beethoven92.betterendforge.common.world.feature.EndLakeFeature;
import mod.beethoven92.betterendforge.common.world.feature.EndLilyFeature;
import mod.beethoven92.betterendforge.common.world.feature.EndLotusFeature;
import mod.beethoven92.betterendforge.common.world.feature.EndLotusLeafFeature;
import mod.beethoven92.betterendforge.common.world.feature.FallenPillarFeature;
import mod.beethoven92.betterendforge.common.world.feature.FilaluxFeature;
import mod.beethoven92.betterendforge.common.world.feature.FloatingSpireFeature;
import mod.beethoven92.betterendforge.common.world.feature.GeyserFeature;
import mod.beethoven92.betterendforge.common.world.feature.GiganticAmaranitaFeature;
import mod.beethoven92.betterendforge.common.world.feature.GlowPillarFeature;
import mod.beethoven92.betterendforge.common.world.feature.HelixTreeFeature;
import mod.beethoven92.betterendforge.common.world.feature.HydraluxFeature;
import mod.beethoven92.betterendforge.common.world.feature.IceStarFeature;
import mod.beethoven92.betterendforge.common.world.feature.JellyshroomFeature;
import mod.beethoven92.betterendforge.common.world.feature.LacugroveFeature;
import mod.beethoven92.betterendforge.common.world.feature.LanceleafFeature;
import mod.beethoven92.betterendforge.common.world.feature.LargeAmaranitaFeature;
import mod.beethoven92.betterendforge.common.world.feature.LucerniaFeature;
import mod.beethoven92.betterendforge.common.world.feature.Lumecorn;
import mod.beethoven92.betterendforge.common.world.feature.MengerSpongeFeature;
import mod.beethoven92.betterendforge.common.world.feature.MossyGlowshroomFeature;
import mod.beethoven92.betterendforge.common.world.feature.NeonCactusFeature;
import mod.beethoven92.betterendforge.common.world.feature.ObsidianBoulderFeature;
import mod.beethoven92.betterendforge.common.world.feature.ObsidianPillarBasementFeature;
import mod.beethoven92.betterendforge.common.world.feature.OreLayerFeature;
import mod.beethoven92.betterendforge.common.world.feature.OverworldIslandFeature;
import mod.beethoven92.betterendforge.common.world.feature.PythadendronFeature;
import mod.beethoven92.betterendforge.common.world.feature.SilkMothNestFeature;
import mod.beethoven92.betterendforge.common.world.feature.SingleBlockFeature;
import mod.beethoven92.betterendforge.common.world.feature.SingleInvertedScatterFeature;
import mod.beethoven92.betterendforge.common.world.feature.SinglePlantFeature;
import mod.beethoven92.betterendforge.common.world.feature.SmaragdantCrystalFeature;
import mod.beethoven92.betterendforge.common.world.feature.SpireFeature;
import mod.beethoven92.betterendforge.common.world.feature.StalactiteFeature;
import mod.beethoven92.betterendforge.common.world.feature.SulphuricCaveFeature;
import mod.beethoven92.betterendforge.common.world.feature.SulphuricLakeFeature;
import mod.beethoven92.betterendforge.common.world.feature.SurfaceVentFeature;
import mod.beethoven92.betterendforge.common.world.feature.TenaneaBushFeature;
import mod.beethoven92.betterendforge.common.world.feature.TenaneaFeature;
import mod.beethoven92.betterendforge.common.world.feature.UmbrellaTreeFeature;
import mod.beethoven92.betterendforge.common.world.feature.UnderwaterPlantFeature;
import mod.beethoven92.betterendforge.common.world.feature.VineFeature;
import mod.beethoven92.betterendforge.common.world.feature.WallPlantFeature;
import mod.beethoven92.betterendforge.common.world.feature.WallPlantOnLogFeature;
import mod.beethoven92.betterendforge.common.world.feature.*;
import mod.beethoven92.betterendforge.common.world.feature.caves.RoundCaveFeature;
import mod.beethoven92.betterendforge.common.world.feature.caves.TunelCaveFeature;
import mod.beethoven92.betterendforge.common.world.surfacebuilder.UmbraSurfaceBuilder;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.RegistryEvent.Register;

import java.util.List;
import java.util.function.Supplier;

public class ModFeatures
{
	// WATER PLANTS
	public static final WorldGenerator END_LOTUS = new EndLotusFeature(7);
	public static final WorldGenerator END_LOTUS_LEAF = new EndLotusLeafFeature(20);	
	public static final WorldGenerator BUBBLE_CORAL = new UnderwaterPlantFeature(ModBlocks.BUBBLE_CORAL, 6);
	public static final WorldGenerator BUBBLE_CORAL_RARE = new UnderwaterPlantFeature(ModBlocks.BUBBLE_CORAL, 3);
	public static final WorldGenerator END_LILY = new EndLilyFeature(6);
	public static final WorldGenerator END_LILY_RARE = new EndLilyFeature(3);
	public static final WorldGenerator MENGER_SPONGE = new MengerSpongeFeature(5);
	public static final WorldGenerator CHARNIA_RED = new CharniaFeature(ModBlocks.CHARNIA_RED);
	public static final WorldGenerator CHARNIA_PURPLE = new CharniaFeature(ModBlocks.CHARNIA_PURPLE);
	public static final WorldGenerator CHARNIA_ORANGE = new CharniaFeature(ModBlocks.CHARNIA_ORANGE);
	public static final WorldGenerator CHARNIA_LIGHT_BLUE = new CharniaFeature(ModBlocks.CHARNIA_LIGHT_BLUE);
	public static final WorldGenerator CHARNIA_CYAN = new CharniaFeature(ModBlocks.CHARNIA_CYAN);
	public static final WorldGenerator CHARNIA_GREEN = new CharniaFeature(ModBlocks.CHARNIA_GREEN);
	public static final WorldGenerator CHARNIA_RED_RARE = new CharniaFeature(ModBlocks.CHARNIA_RED);
	public static final WorldGenerator HYDRALUX = new HydraluxFeature(5);
	public static final WorldGenerator FLAMAEA = new SinglePlantFeature(ModBlocks.FLAMAEA, 12, false, 5);
	public static final WorldGenerator POND_ANEMONE = new UnderwaterPlantFeature(ModBlocks.POND_ANEMONE, 6);


	// BUSHES
	public static final WorldGenerator PYTHADENDRON_BUSH = new BushFeature(ModBlocks.PYTHADENDRON_LEAVES, ModBlocks.PYTHADENDRON.bark);
	public static final WorldGenerator DRAGON_TREE_BUSH = new BushFeature(ModBlocks.DRAGON_TREE_LEAVES, ModBlocks.DRAGON_TREE.bark);
	public static final WorldGenerator TENANEA_BUSH = new TenaneaBushFeature();
	public static final WorldGenerator LARGE_AMARANITA = new LargeAmaranitaFeature();
	public static final WorldGenerator LUCERNIA_BUSH = new BushWithOuterFeature(ModBlocks.LUCERNIA_LEAVES, ModBlocks.LUCERNIA_OUTER_LEAVES, ModBlocks.LUCERNIA.bark);
	public static final WorldGenerator LUCERNIA_BUSH_RARE = new BushWithOuterFeature(ModBlocks.LUCERNIA_LEAVES, ModBlocks.LUCERNIA_OUTER_LEAVES, ModBlocks.LUCERNIA.bark);
	public static final WorldGenerator NEON_CACTUS = new NeonCactusFeature();
	
	// PLANTS
	public static final WorldGenerator UMBRELLA_MOSS = new DoublePlantFeature(ModBlocks.UMBRELLA_MOSS, ModBlocks.UMBRELLA_MOSS_TALL, 5);
	public static final WorldGenerator CREEPING_MOSS = new SinglePlantFeature(ModBlocks.CREEPING_MOSS, 5);
	public static final WorldGenerator CHORUS_GRASS = new SinglePlantFeature(ModBlocks.CHORUS_GRASS, 4);
	public static final WorldGenerator CHORUS_MUSHROOM = new SinglePlantFeature(ModBlocks.CHORUS_MUSHROOM, 5, 5);
	public static final WorldGenerator CRYSTAL_GRASS = new SinglePlantFeature(ModBlocks.CRYSTAL_GRASS, 8, false);
	public static final WorldGenerator AMBER_GRASS = new SinglePlantFeature(ModBlocks.AMBER_GRASS, 6);
	public static final WorldGenerator AMBER_ROOT = new SinglePlantFeature(ModBlocks.AMBER_ROOT, 5, 5);
	public static final WorldGenerator SHADOW_PLANT = new SinglePlantFeature(ModBlocks.SHADOW_PLANT, 6);
	public static final WorldGenerator BLUE_VINE = new BlueVineFeature(5);
	public static final WorldGenerator MURKWEED = new SinglePlantFeature(ModBlocks.MURKWEED, 3);
	public static final WorldGenerator NEEDLEGRASS = new SinglePlantFeature(ModBlocks.NEEDLEGRASS, 3);
	public static final WorldGenerator SHADOW_BERRY = new SinglePlantFeature(ModBlocks.SHADOW_BERRY, 2);
	public static final WorldGenerator BUSHY_GRASS = new SinglePlantFeature(ModBlocks.BUSHY_GRASS, 8, false);
	public static final WorldGenerator BUSHY_GRASS_WG = new SinglePlantFeature(ModBlocks.BUSHY_GRASS, 5);
	public static final WorldGenerator LANCELEAF = new LanceleafFeature();
	public static final WorldGenerator GLOW_PILLAR = new GlowPillarFeature();
	public static final WorldGenerator TWISTED_UMBRELLA_MOSS = new DoublePlantFeature(ModBlocks.TWISTED_UMBRELLA_MOSS, ModBlocks.TWISTED_UMBRELLA_MOSS_TALL, 6);
	public static final WorldGenerator JUNGLE_GRASS = new SinglePlantFeature(ModBlocks.JUNGLE_GRASS, 7, 3);
	public static final WorldGenerator SMALL_JELLYSHROOM_FLOOR = new SinglePlantFeature(ModBlocks.SMALL_JELLYSHROOM, 5, 5);
	public static final WorldGenerator BLOSSOM_BERRY = new SinglePlantFeature(ModBlocks.BLOSSOM_BERRY, 3, 3);
	public static final WorldGenerator BLOOMING_COOKSONIA = new SinglePlantFeature(ModBlocks.BLOOMING_COOKSONIA, 5);
	public static final WorldGenerator SALTEAGO = new SinglePlantFeature(ModBlocks.SALTEAGO, 5);
	public static final WorldGenerator VAIOLUSH_FERN = new SinglePlantFeature(ModBlocks.VAIOLUSH_FERN, 5);
	public static final WorldGenerator FRACTURN = new SinglePlantFeature(ModBlocks.FRACTURN, 5);
	public static final WorldGenerator UMBRELLA_MOSS_RARE = new SinglePlantFeature(ModBlocks.UMBRELLA_MOSS, 3);
	public static final WorldGenerator CREEPING_MOSS_RARE = new SinglePlantFeature(ModBlocks.CREEPING_MOSS, 3);
	public static final WorldGenerator TWISTED_UMBRELLA_MOSS_RARE = new SinglePlantFeature(ModBlocks.TWISTED_UMBRELLA_MOSS, 3);
	public static final WorldGenerator LUMECORN = new Lumecorn();
	public static final WorldGenerator SMALL_AMARANITA = new SinglePlantFeature(ModBlocks.SMALL_AMARANITA_MUSHROOM, 5, 5);
	public static final WorldGenerator GLOBULAGUS = new SinglePlantFeature(ModBlocks.GLOBULAGUS, 5, 3);
	public static final WorldGenerator CLAWFERN = new SinglePlantFeature(ModBlocks.CLAWFERN, 5, 4);
	public static final WorldGenerator AERIDIUM = new SinglePlantFeature(ModBlocks.AERIDIUM, 5, 4);
	public static final WorldGenerator LAMELLARIUM = new SinglePlantFeature(ModBlocks.LAMELLARIUM, 5);
	public static final WorldGenerator BOLUX_MUSHROOM = new SinglePlantFeature(ModBlocks.BOLUX_MUSHROOM, 5, 5);
	public static final WorldGenerator ORANGO = new SinglePlantFeature(ModBlocks.ORANGO, 5);
	public static final WorldGenerator LUTEBUS = new SinglePlantFeature(ModBlocks.LUTEBUS, 5, 2);
	public static final WorldGenerator INFLEXIA = new SinglePlantFeature(ModBlocks.INFLEXIA, 7, false, 3);
	public static final WorldGenerator FLAMMALIX = new SinglePlantFeature(ModBlocks.FLAMMALIX, 3, false, 7);


	// SKY PLANTS
	public static final WorldGenerator FILALUX = new FilaluxFeature();
	
	// WALL PLANTS
	public static final WorldGenerator PURPLE_POLYPORE = new WallPlantOnLogFeature(ModBlocks.PURPLE_POLYPORE, 3);
	public static final WorldGenerator AURANT_POLYPORE = new WallPlantOnLogFeature(ModBlocks.AURANT_POLYPORE, 3);
	public static final WorldGenerator PURPLE_POLYPORE_DENSE = new WallPlantOnLogFeature(ModBlocks.PURPLE_POLYPORE, 5);
	public static final WorldGenerator TAIL_MOSS = new WallPlantFeature(ModBlocks.TAIL_MOSS, 3);
	public static final WorldGenerator TAIL_MOSS_WOOD = new WallPlantOnLogFeature(ModBlocks.TAIL_MOSS, 4);
	public static final WorldGenerator CYAN_MOSS = new WallPlantFeature(ModBlocks.CYAN_MOSS, 3);
	public static final WorldGenerator CYAN_MOSS_WOOD = new WallPlantOnLogFeature(ModBlocks.CYAN_MOSS, 4);
	public static final WorldGenerator TWISTED_MOSS = new WallPlantFeature(ModBlocks.TWISTED_MOSS, 6);
	public static final WorldGenerator TWISTED_MOSS_WOOD = new WallPlantOnLogFeature(ModBlocks.TWISTED_MOSS, 6);
	public static final WorldGenerator BULB_MOSS = new WallPlantFeature(ModBlocks.BULB_MOSS, 6);
	public static final WorldGenerator BULB_MOSS_WOOD = new WallPlantOnLogFeature(ModBlocks.BULB_MOSS, 6);
	public static final WorldGenerator SMALL_JELLYSHROOM_WALL = new WallPlantFeature(ModBlocks.SMALL_JELLYSHROOM, 4);
	public static final WorldGenerator SMALL_JELLYSHROOM_WOOD = new WallPlantOnLogFeature(ModBlocks.SMALL_JELLYSHROOM, 4);
	public static final WorldGenerator JUNGLE_FERN_WOOD = new WallPlantOnLogFeature(ModBlocks.JUNGLE_FERN, 3);
	public static final WorldGenerator RUSCUS = new WallPlantFeature(ModBlocks.RUSCUS, 6);
	public static final WorldGenerator RUSCUS_WOOD = new WallPlantOnLogFeature(ModBlocks.RUSCUS, 6);
	
	// VINES
	public static final WorldGenerator DENSE_VINE = new VineFeature(ModBlocks.DENSE_VINE, 24);
	public static final WorldGenerator TWISTED_VINE = new VineFeature(ModBlocks.TWISTED_VINE, 24);
	public static final WorldGenerator BULB_VINE = new VineFeature(ModBlocks.BULB_VINE, 24);
	public static final WorldGenerator JUNGLE_VINE = new VineFeature(ModBlocks.JUNGLE_VINE, 24);
	
	// CEIL PLANTS
	public static final WorldGenerator SMALL_JELLYSHROOM_CEIL = new SingleInvertedScatterFeature(ModBlocks.SMALL_JELLYSHROOM, 8);
		
	// TERRAIN
	public static final WorldGenerator ROUND_CAVE = new RoundCaveFeature();
	public static final WorldGenerator END_LAKE = new EndLakeFeature();
	public static final WorldGenerator END_LAKE_RARE = new EndLakeFeature();
	public static final WorldGenerator DESERT_LAKE = new DesertLakeFeature();
	public static final WorldGenerator SPIRE = new SpireFeature();
	public static final WorldGenerator FLOATING_SPIRE = new FloatingSpireFeature();
	public static final WorldGenerator GEYSER = new GeyserFeature();
	public static final WorldGenerator SULPHURIC_LAKE = new SulphuricLakeFeature();
	public static final WorldGenerator SULPHURIC_CAVE = new SulphuricCaveFeature();
	public static final WorldGenerator SURFACE_VENT = new SurfaceVentFeature();
	public static final WorldGenerator ICE_STAR = new IceStarFeature(5, 15, 10, 25);
	public static final WorldGenerator ICE_STAR_SMALL = new IceStarFeature(3, 5, 7, 12);
	public static final WorldGenerator OVERWORLD_ISLAND = new OverworldIslandFeature();
	public static final WorldGenerator OBSIDIAN_PILLAR_BASEMENT = new ObsidianPillarBasementFeature();
	public static final WorldGenerator FALLEN_PILLAR = new FallenPillarFeature();
	public static final WorldGenerator OBSIDIAN_BOULDER = new ObsidianBoulderFeature();
	public static final WorldGenerator TUNEL_CAVE = new TunelCaveFeature();
	public static final WorldGenerator THIN_ARCH = new ThinArchFeature(ModBlocks.UMBRALITH.stone);
	public static final WorldGenerator UMBRALITH_ARCH = new ArchFeature(ModBlocks.UMBRALITH.stone, UmbraSurfaceBuilder::getSurfaceState);


	// TREES
	public static final WorldGenerator MOSSY_GLOWSHROOM = new MossyGlowshroomFeature();
	public static final WorldGenerator LACUGROVE = new LacugroveFeature();
	public static final WorldGenerator PYTHADENDRON = new PythadendronFeature();
	public static final WorldGenerator DRAGON_TREE = new DragonTreeFeature();
	public static final WorldGenerator TENANEA = new TenaneaFeature();
	public static final WorldGenerator HELIX_TREE = new HelixTreeFeature();
	public static final WorldGenerator UMBRELLA_TREE = new UmbrellaTreeFeature();
	public static final WorldGenerator JELLYSHROOM = new JellyshroomFeature();
	public static final WorldGenerator GIGANTIC_AMARANITA = new GiganticAmaranitaFeature();
	public static final WorldGenerator LUCERNIA = new LucerniaFeature();
	
	// ORES
//	public static final Feature<OreFeatureConfig> ENDER_ORE = new OreFeature(OreFeatureConfig.CODEC);
//	public static final Feature<OreFeatureConfig> AMBER_ORE = new OreFeature(OreFeatureConfig.CODEC);
//	public static final Feature<OreFeatureConfig> THALLASIUM_ORE = new OreFeature(OreFeatureConfig.CODEC);
	public static final WorldGenerator FLAVOLITE_LAYER = new OreLayerFeature(ModBlocks.FLAVOLITE.stone.getDefaultState(), 12, 4, 96);
	public static final WorldGenerator VIOLECITE_LAYER = new OreLayerFeature(ModBlocks.VIOLECITE.stone.getDefaultState(), 15, 4, 96);
	
	// BUILDINGS
	public static final WorldGenerator CRASHED_SHIP = new CrashedShipFeature();
	public static final WorldGenerator NBT_STRUCTURES = new BiomeNBTStructures();
	
	// Mobs
	public static final WorldGenerator SILK_MOTH_NEST = new SilkMothNestFeature();
	
	// Caves
    public static final WorldGenerator SMARAGDANT_CRYSTAL = new SmaragdantCrystalFeature();
    public static final WorldGenerator SMARAGDANT_CRYSTAL_SHARD = new SingleBlockFeature(ModBlocks.SMARAGDANT_CRYSTAL_SHARD);
    public static final WorldGenerator BIG_AURORA_CRYSTAL = new BigAuroraCrystalFeature();
	public static final WorldGenerator END_STONE_STALACTITE = new StalactiteFeature(true, ModBlocks.END_STONE_STALACTITE, Blocks.END_STONE);
	public static final WorldGenerator END_STONE_STALAGMITE = new StalactiteFeature(false, ModBlocks.END_STONE_STALACTITE, Blocks.END_STONE);
	public static final WorldGenerator END_STONE_STALACTITE_CAVEMOSS = new StalactiteFeature(true, ModBlocks.END_STONE_STALACTITE_CAVEMOSS, Blocks.END_STONE, ModBlocks.CAVE_MOSS);
	public static final WorldGenerator END_STONE_STALAGMITE_CAVEMOSS = new StalactiteFeature(false, ModBlocks.END_STONE_STALACTITE_CAVEMOSS, ModBlocks.CAVE_MOSS);
	public static final WorldGenerator CAVE_BUSH = new BushFeature(ModBlocks.CAVE_BUSH, ModBlocks.CAVE_BUSH);
	public static final WorldGenerator CAVE_GRASS = new SingleBlockFeature(ModBlocks.CAVE_GRASS);
	public static final WorldGenerator RUBINEA = new VineFeature(ModBlocks.RUBINEA, 8);

	//Integration

//	public static void registerFeatures(Register<Feature<?>> event)
//    {
//		// WATER PLANTS
//    	BetterEnd.register(event.getRegistry(), END_LOTUS, "end_lotus");
//    	BetterEnd.register(event.getRegistry(), END_LOTUS_LEAF, "end_lotus_leaf");
//    	BetterEnd.register(event.getRegistry(), BUBBLE_CORAL, "bubble_coral");
//    	BetterEnd.register(event.getRegistry(), BUBBLE_CORAL_RARE, "bubble_coral_rare");
//    	BetterEnd.register(event.getRegistry(), END_LILY, "end_lily");
//    	BetterEnd.register(event.getRegistry(), END_LILY_RARE, "end_lily_rare");
//    	BetterEnd.register(event.getRegistry(), MENGER_SPONGE, "menger_sponge");
//    	BetterEnd.register(event.getRegistry(), CHARNIA_RED, "charnia_red");
//    	BetterEnd.register(event.getRegistry(), CHARNIA_PURPLE, "charnia_purple");
//    	BetterEnd.register(event.getRegistry(), CHARNIA_ORANGE, "charnia_orange");
//    	BetterEnd.register(event.getRegistry(), CHARNIA_LIGHT_BLUE, "charnia_light_blue");
//    	BetterEnd.register(event.getRegistry(), CHARNIA_CYAN, "charnia_cyan");
//    	BetterEnd.register(event.getRegistry(), CHARNIA_GREEN, "charnia_green");
//    	BetterEnd.register(event.getRegistry(), CHARNIA_RED_RARE, "charnia_red_rare");
//    	BetterEnd.register(event.getRegistry(), HYDRALUX, "hydralux");
//    	BetterEnd.register(event.getRegistry(), FLAMAEA, "flamaea");
//    	BetterEnd.register(event.getRegistry(), POND_ANEMONE, "pond_anemone");
//    	//BUSHES
//    	BetterEnd.register(event.getRegistry(), PYTHADENDRON_BUSH, "pythadendron_bush");
//    	BetterEnd.register(event.getRegistry(), DRAGON_TREE_BUSH, "dragon_tree_bush");
//    	BetterEnd.register(event.getRegistry(), TENANEA_BUSH, "tenanea_bush");
//    	BetterEnd.register(event.getRegistry(), LARGE_AMARANITA, "large_amaranita");
//    	BetterEnd.register(event.getRegistry(), LUCERNIA_BUSH, "lucernia_bush");
//		BetterEnd.register(event.getRegistry(), LUCERNIA_BUSH_RARE, "lucernia_bush_rare");
//		BetterEnd.register(event.getRegistry(), NEON_CACTUS, "neon_cactus");
//    	// PLANTS
//    	BetterEnd.register(event.getRegistry(), UMBRELLA_MOSS, "umbrella_moss");
//    	BetterEnd.register(event.getRegistry(), CREEPING_MOSS, "creeping_moss");
//    	BetterEnd.register(event.getRegistry(), CHORUS_GRASS, "chorus_grass");
//    	BetterEnd.register(event.getRegistry(), CRYSTAL_GRASS, "crystal_grass");
//    	BetterEnd.register(event.getRegistry(), AMBER_GRASS, "amber_grass");
//    	BetterEnd.register(event.getRegistry(), SHADOW_PLANT, "shadow_plant");
//    	BetterEnd.register(event.getRegistry(), BLUE_VINE, "blue_vine");
//    	BetterEnd.register(event.getRegistry(), MURKWEED, "murkweed");
//    	BetterEnd.register(event.getRegistry(), NEEDLEGRASS, "needlegrass");
//    	BetterEnd.register(event.getRegistry(), SHADOW_BERRY, "shadow_berry");
//    	BetterEnd.register(event.getRegistry(), BUSHY_GRASS, "bushy_grass");
//    	BetterEnd.register(event.getRegistry(), BUSHY_GRASS_WG, "bushy_grass_wg");
//    	BetterEnd.register(event.getRegistry(), LANCELEAF, "lanceleaf");
//    	BetterEnd.register(event.getRegistry(), GLOW_PILLAR, "glow_pillar");
//    	BetterEnd.register(event.getRegistry(), TWISTED_UMBRELLA_MOSS, "twisted_umbrella_moss");
//    	BetterEnd.register(event.getRegistry(), JUNGLE_GRASS, "jungle_grass");
//    	BetterEnd.register(event.getRegistry(), SMALL_JELLYSHROOM_FLOOR, "small_jellyshroom_floor");
//    	BetterEnd.register(event.getRegistry(), BLOSSOM_BERRY, "blossom_berry");
//    	BetterEnd.register(event.getRegistry(), LUMECORN, "lumecorn");
//    	BetterEnd.register(event.getRegistry(), BLOOMING_COOKSONIA, "blooming_cooksonia");
//    	BetterEnd.register(event.getRegistry(), SALTEAGO, "salteago");
//    	BetterEnd.register(event.getRegistry(), VAIOLUSH_FERN, "vaiolush_fern");
//    	BetterEnd.register(event.getRegistry(), FRACTURN, "fracturn");
//    	BetterEnd.register(event.getRegistry(), UMBRELLA_MOSS_RARE, "umbrella_moss_rare");
//    	BetterEnd.register(event.getRegistry(), CREEPING_MOSS_RARE, "creeping_moss_rare");
//    	BetterEnd.register(event.getRegistry(), TWISTED_UMBRELLA_MOSS_RARE, "twisted_umbrella_moss_rare");
//    	BetterEnd.register(event.getRegistry(), SMALL_AMARANITA, "small_amaranita");
//    	BetterEnd.register(event.getRegistry(), GLOBULAGUS, "globulagus");
//    	BetterEnd.register(event.getRegistry(), CLAWFERN, "clawfern");
//    	BetterEnd.register(event.getRegistry(), AERIDIUM, "aeridium");
//    	BetterEnd.register(event.getRegistry(), LAMELLARIUM, "lamellarium");
//    	BetterEnd.register(event.getRegistry(), BOLUX_MUSHROOM, "bolux_mushroom");
//    	BetterEnd.register(event.getRegistry(), ORANGO, "orango");
//    	BetterEnd.register(event.getRegistry(), LUTEBUS, "lutebus");
//		BetterEnd.register(event.getRegistry(), FLAMMALIX, "flammalix");
//		BetterEnd.register(event.getRegistry(), INFLEXIA, "inflexia");
//		BetterEnd.register(event.getRegistry(), AMBER_ROOT, "amber_root");
//    	// SKY PLANTS
//    	BetterEnd.register(event.getRegistry(), FILALUX, "filalux");
//    	// WALL_PLANTS
//    	BetterEnd.register(event.getRegistry(), PURPLE_POLYPORE, "purple_polypore");
//    	BetterEnd.register(event.getRegistry(), AURANT_POLYPORE, "aurant_polypore");
//    	BetterEnd.register(event.getRegistry(), PURPLE_POLYPORE_DENSE, "purple_polypore_dense");
//    	BetterEnd.register(event.getRegistry(), TAIL_MOSS, "tail_moss");
//    	BetterEnd.register(event.getRegistry(), TAIL_MOSS_WOOD, "tail_moss_wood");
//    	BetterEnd.register(event.getRegistry(), CYAN_MOSS, "cyan_moss");
//    	BetterEnd.register(event.getRegistry(), CYAN_MOSS_WOOD, "cyan_moss_wood");
//    	BetterEnd.register(event.getRegistry(), TWISTED_MOSS, "twisted_moss");
//    	BetterEnd.register(event.getRegistry(), TWISTED_MOSS_WOOD, "twisted_moss_wood");
//    	BetterEnd.register(event.getRegistry(), BULB_MOSS, "bulb_moss");
//    	BetterEnd.register(event.getRegistry(), BULB_MOSS_WOOD, "bulb_moss_wood");
//       	BetterEnd.register(event.getRegistry(), SMALL_JELLYSHROOM_WALL, "small_jellyshroom_wall");
//    	BetterEnd.register(event.getRegistry(), SMALL_JELLYSHROOM_WOOD, "small_jellyshroom_wood");
//    	BetterEnd.register(event.getRegistry(), JUNGLE_FERN_WOOD, "jungle_fern_wood");
//    	BetterEnd.register(event.getRegistry(), RUSCUS, "ruscus");
//    	BetterEnd.register(event.getRegistry(), RUSCUS_WOOD, "ruscus_wood");
//    	// VINES
//    	BetterEnd.register(event.getRegistry(), DENSE_VINE, "dense_vine");
//    	BetterEnd.register(event.getRegistry(), TWISTED_VINE, "twisted_vine");
//    	BetterEnd.register(event.getRegistry(), BULB_VINE, "bulb_vine");
//    	BetterEnd.register(event.getRegistry(), JUNGLE_VINE, "jungle_vine");
//    	// CEIL PLANTS
//    	BetterEnd.register(event.getRegistry(), SMALL_JELLYSHROOM_CEIL, "small_jellyshroom_ceil");
//    	// TERRAIN
//    	BetterEnd.register(event.getRegistry(), ROUND_CAVE, "round_cave");
//    	BetterEnd.register(event.getRegistry(), END_LAKE, "end_lake");
//    	BetterEnd.register(event.getRegistry(), END_LAKE_RARE, "end_lake_rare");
//    	BetterEnd.register(event.getRegistry(), DESERT_LAKE, "desert_lake");
//    	BetterEnd.register(event.getRegistry(), SPIRE, "spire");
//    	BetterEnd.register(event.getRegistry(), FLOATING_SPIRE, "floating_spire");
//    	BetterEnd.register(event.getRegistry(), GEYSER, "geyser");
//    	BetterEnd.register(event.getRegistry(), SULPHURIC_LAKE, "sulphuric_lake");
//    	BetterEnd.register(event.getRegistry(), SULPHURIC_CAVE, "sulphuric_cave");
//    	BetterEnd.register(event.getRegistry(), SURFACE_VENT, "surface_vent");
//    	BetterEnd.register(event.getRegistry(), ICE_STAR, "ice_star");
//    	BetterEnd.register(event.getRegistry(), ICE_STAR_SMALL, "ice_star_small");
//    	BetterEnd.register(event.getRegistry(), OVERWORLD_ISLAND, "overworld_island");
//    	BetterEnd.register(event.getRegistry(), OBSIDIAN_PILLAR_BASEMENT, "obsidian_pillar_basement");
//    	BetterEnd.register(event.getRegistry(), FALLEN_PILLAR, "fallen_pillar");
//    	BetterEnd.register(event.getRegistry(), OBSIDIAN_BOULDER, "obsidian_boulder");
//		BetterEnd.register(event.getRegistry(), THIN_ARCH, "thin_arch");
//		BetterEnd.register(event.getRegistry(), UMBRALITH_ARCH, "umbralith_arch");
//
//		// TREES
//    	BetterEnd.register(event.getRegistry(), MOSSY_GLOWSHROOM, "mossy_glowshroom");
//    	BetterEnd.register(event.getRegistry(), LACUGROVE, "lacugrove");
//    	BetterEnd.register(event.getRegistry(), PYTHADENDRON, "pythadendron");
//    	BetterEnd.register(event.getRegistry(), DRAGON_TREE, "dragon_tree");
//    	BetterEnd.register(event.getRegistry(), TENANEA, "tenanea");
//    	BetterEnd.register(event.getRegistry(), HELIX_TREE, "helix_tree");
//    	BetterEnd.register(event.getRegistry(), UMBRELLA_TREE, "umbrella_tree");
//    	BetterEnd.register(event.getRegistry(), JELLYSHROOM, "jellyshroom");
//    	BetterEnd.register(event.getRegistry(), GIGANTIC_AMARANITA, "gigantic_amaranita");
//    	BetterEnd.register(event.getRegistry(), LUCERNIA, "lucernia");
//    	// ORES
//    	BetterEnd.register(event.getRegistry(), THALLASIUM_ORE, "thallasium_ore");
//    	BetterEnd.register(event.getRegistry(), ENDER_ORE, "ender_ore");
//    	BetterEnd.register(event.getRegistry(), AMBER_ORE, "amber_ore");
//    	BetterEnd.register(event.getRegistry(), FLAVOLITE_LAYER, "flavolite_layer");
//    	BetterEnd.register(event.getRegistry(), VIOLECITE_LAYER, "violecite_layer");
//    	// BUILDINGS
//    	BetterEnd.register(event.getRegistry(), CRASHED_SHIP, "crashed_ship");
//    	BetterEnd.register(event.getRegistry(), NBT_STRUCTURES, "nbt_structures");
//    	// MOBS
//    	BetterEnd.register(event.getRegistry(), SILK_MOTH_NEST, "silk_moth_nest");
//    	// CAVES
//    	BetterEnd.register(event.getRegistry(), SMARAGDANT_CRYSTAL, "smaragdant_crystal");
//    	BetterEnd.register(event.getRegistry(), SMARAGDANT_CRYSTAL_SHARD, "smaragdant_crystal_shard");
//    	BetterEnd.register(event.getRegistry(), BIG_AURORA_CRYSTAL, "big_aurora_crystal");
//    	BetterEnd.register(event.getRegistry(), END_STONE_STALACTITE, "end_stone_stalactite");
//    	BetterEnd.register(event.getRegistry(), END_STONE_STALAGMITE, "end_stone_stalagmite");
//    	BetterEnd.register(event.getRegistry(), END_STONE_STALACTITE_CAVEMOSS, "end_stone_stalactite_cavemoss");
//    	BetterEnd.register(event.getRegistry(), END_STONE_STALAGMITE_CAVEMOSS, "end_stone_stalagmite_cavemoss");
//    	BetterEnd.register(event.getRegistry(), CAVE_BUSH, "cave_bush");
//      	BetterEnd.register(event.getRegistry(), CAVE_GRASS, "cave_grass");
//      	BetterEnd.register(event.getRegistry(), RUBINEA, "rubinea");
//		BetterEnd.register(event.getRegistry(), TUNEL_CAVE, "tunel_cave");
//
//		//Integration
//	}





}
