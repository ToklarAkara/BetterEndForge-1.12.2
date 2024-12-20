package mod.beethoven92.betterendforge.common.event.forge;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.world.biome.ExtendedBiome;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.EndDecorator;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BiomeModification {
    public static void addFeaturesToEndBiomes(Biome biome) {
        EndDecorator decorator = (EndDecorator) biome.decorator;
//			// Add surface structures to biomes
//			if (!event.getName().getPath().contains("mountain") &&
//					!event.getName().getPath().contains("lake"))
//			{
//			    event.getGeneration().getStructures().add(() -> ModConfiguredStructures.ETERNAL_PORTAL);
//			}
        decorator.getFeatures(Decoration.SURFACE_STRUCTURES).add(ModConfiguredFeatures.CRASHED_SHIP);
        decorator.getFeatures(Decoration.LOCAL_MODIFICATIONS).add(ModConfiguredFeatures.TUNEL_CAVE);
//        // Add ores to all biomes
//            decorator.getFeatures(Decoration.UNDERGROUND_ORES).add(ModConfiguredFeatures.THALLASIUM_ORE);
//            decorator.getFeatures(Decoration.UNDERGROUND_ORES).add(ModConfiguredFeatures.ENDER_ORE);
        decorator.getFeatures(Decoration.UNDERGROUND_ORES).add(ModConfiguredFeatures.FLAVOLITE_LAYER);


        // Add end caves to biomes that have caves enabled
//        if (ModBiomes.getBiome(biome.getRegistryName()).hasCaves()) {
//            decorator.getFeatures(Decoration.RAW_GENERATION).add(ModConfiguredFeatures.ROUND_CAVE);
//        }

        // Add scattered nbt structures to biomes
        if (!ModBiomes.getBiome(biome.getRegistryName()).getNBTStructures().isEmpty()) {
            decorator.getFeatures(Decoration.SURFACE_STRUCTURES).add(ModConfiguredFeatures.NBT_STRUCTURES);
        }
    }
//
//	@SubscribeEvent(priority = EventPriority.NORMAL)
//    public static void removeChorusFromVanillaBiomes(final BiomeLoadingEvent event)
//    {
//		if (GeneratorOptions.removeChorusFromVanillaBiomes())
//		{
//			if (event.getCategory() == Category.THEEND)
//			{
//				if (event.getName() == null || !event.getName().getNamespace().equals("minecraft")) return;
//
//				String path = event.getName().getPath();
//				if (path.equals("end_highlands") || path.equals("end_midlands") || path.equals("small_end_islands"))
//				{
//					event.getGeneration().getFeatures(Decoration.VEGETAL_DECORATION).removeIf((supplier) ->
//					{
//						ConfiguredFeature<?, ?> feature = supplier.get();
//
//						// Retrieve the original feature
//						while(feature.getFeature() instanceof DecoratedFeature)
//						{
//							feature = ((DecoratedFeatureConfig)feature.getConfig()).feature.get();
//						}
//
//			            if (feature.feature instanceof ChorusPlantFeature)
//			            {
//			            	return true;
//			            }
//			            return false;
//			        });
//				}
//			}
//		}
//    }
}
