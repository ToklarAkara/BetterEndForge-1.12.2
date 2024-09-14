package mod.beethoven92.betterendforge;

import mod.beethoven92.betterendforge.client.ClientOptions;
import mod.beethoven92.betterendforge.common.capability.EndData;
import mod.beethoven92.betterendforge.common.init.*;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import mod.beethoven92.betterendforge.config.Configs;
import mod.beethoven92.betterendforge.config.jsons.JsonConfig;
import mod.beethoven92.betterendforge.config.jsons.JsonConfigWriter;
import mod.beethoven92.betterendforge.data.InfusionRecipes;
import mod.beethoven92.betterendforge.data.ModRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.File;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.beethoven92.betterendforge.client.PhysicalClientSide;
import mod.beethoven92.betterendforge.common.recipes.ModRecipeManager;
import mod.beethoven92.betterendforge.common.teleporter.EndPortals;
import mod.beethoven92.betterendforge.common.world.feature.BiomeNBTStructures;


@Mod(modid = BetterEnd.MOD_ID)
public class BetterEnd
{
	public static final String MOD_ID = "betterendforge";

    public static final Logger LOGGER = LogManager.getLogger();


	@Mod.Instance(MOD_ID)
	public static BetterEnd instance;

	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		ModStructurePieces.registerAllPieces();

		ModEntityTypes.registryEntities();
		JsonConfigWriter.MOD_CONFIG_DIR = event.getModConfigurationDirectory();
		if (!JsonConfigWriter.MOD_CONFIG_DIR.exists()) JsonConfigWriter.MOD_CONFIG_DIR.mkdir();
		GeneratorOptions.init();
		ClientOptions.init();
		Configs.saveConfigs();

		EndPortals.loadPortals();
		if(event.getSide()==Side.CLIENT) {
			PhysicalClientSide.clientSetup();
		}
	}

	@Mod.EventHandler
	public static void init(FMLInitializationEvent event) {
		CapabilityManager.INSTANCE.register(EndData.class, new EndData.Storage(), EndData::new);
		ModTileEntityTypes.registerTileEntities();
		ModEntityTypes.registerEntitySpawns();
		BiomeNBTStructures.loadStructures();
		ModTags.initTags();
	}
    
    @Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
    public static class WorldGenRegistryEvents
    {
    	@SubscribeEvent
    	public static void registerBiomes(RegistryEvent.Register<Biome> event) 
    	{
    		ModBiomes.register();
    		ModBiomes.getModBiomes().forEach((end_biome) -> event.getRegistry().register(end_biome.getBiome()));
    	}

		@SubscribeEvent
		public static void registerRecipes(RegistryEvent.Register<IRecipe> event){
			ModRecipes.registerRecipes();
			//InfusionRecipes.registerRecipes();
		}

//    	@SubscribeEvent
//    	public static void registerStructures(RegistryEvent.Register<Structure<?>> event)
//    	{
//    		ModStructures.registerStructures(event);
//    		ModConfiguredStructures.registerConfiguredStructures();
//    	}
//
//    	@SubscribeEvent
//    	public static void registerFeatures(RegistryEvent.Register<Feature<?>> event)
//    	{
//    		ModFeatures.registerFeatures(event);
//    		ModConfiguredFeatures.registerConfiguredFeatures();
//    	}
//
//    	//Terraforged integration
//        @SubscribeEvent
//        public static void registerWorldtype(RegistryEvent.Register<ForgeWorldType> event)
//        {
//            event.getRegistry().register(new TerraforgedIntegrationWorldType().setRegistryName(new ResourceLocation(MOD_ID, "world_type")));
//        }
    }

    // Registration helper
    public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T entry, String registryKey) 
    {
        entry.setRegistryName(new ResourceLocation(BetterEnd.MOD_ID, registryKey));
        registry.register(entry);
        return entry;
    }


	public static boolean isClient() {
		return FMLCommonHandler.instance().getSide() == Side.CLIENT;
	}

	public static ResourceLocation makeID(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

}
