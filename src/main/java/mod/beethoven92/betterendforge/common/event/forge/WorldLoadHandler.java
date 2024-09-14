package mod.beethoven92.betterendforge.common.event.forge;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.ChunkGeneratorFlat;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class WorldLoadHandler 
{
   	// Thanks to TelepathicGrunt (https://github.com/TelepathicGrunt) for this code
	@SubscribeEvent
	public static void addDimensionalSpacing(final WorldEvent.Load event) 
	{
		if(event.getWorld() instanceof WorldServer)
		{
			WorldServer serverWorld = (WorldServer)event.getWorld();
			
			// Initialize biome registry if it was not initialized during world generation
			// (example, when using mods/datapacks, that overrides the End generation)
			ModBiomes.initRegistry(serverWorld.getMinecraftServer());
			
			BetterEnd.LOGGER.debug("BETTER_END_FORGE: registering structure separation settings for dimension " +
					serverWorld.provider.getDimension());
			
			// Prevent spawning of structures in Vanilla's superflat world
			if(serverWorld.getChunkProvider().chunkGenerator instanceof ChunkGeneratorFlat &&
					serverWorld.provider.getDimension()==0)
			{
				return;
			}

//			// Need temp map as some mods use custom chunk generators with immutable maps in themselves. TODO REGISTER STRUCTURES?
//			Map<Structure<?>, StructureSeparationSettings> tempMap =
//					new HashMap<>(serverWorld.getChunkProvider().chunkGenerator..func_235957_b_().func_236195_a_());
//			tempMap.put(ModStructures.MOUNTAIN,
//					DimensionStructuresSettings.field_236191_b_.get(ModStructures.MOUNTAIN));
//			tempMap.put(ModStructures.MEGALAKE,
//					DimensionStructuresSettings.field_236191_b_.get(ModStructures.MEGALAKE));
//			tempMap.put(ModStructures.MEGALAKE_SMALL,
//					DimensionStructuresSettings.field_236191_b_.get(ModStructures.MEGALAKE_SMALL));
//			tempMap.put(ModStructures.GIANT_MOSSY_GLOWSHROOM,
//					DimensionStructuresSettings.field_236191_b_.get(ModStructures.GIANT_MOSSY_GLOWSHROOM));
//			tempMap.put(ModStructures.PAINTED_MOUNTAIN,
//					DimensionStructuresSettings.field_236191_b_.get(ModStructures.PAINTED_MOUNTAIN));
//			tempMap.put(ModStructures.ETERNAL_PORTAL,
//					DimensionStructuresSettings.field_236191_b_.get(ModStructures.ETERNAL_PORTAL));
//			tempMap.put(ModStructures.GIANT_ICE_STAR,
//					DimensionStructuresSettings.field_236191_b_.get(ModStructures.GIANT_ICE_STAR));
//			serverWorld.getChunkProvider().getChunkGenerator().func_235957_b_().field_236193_d_ = tempMap;
		}
	}
}
