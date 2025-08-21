package mod.beethoven92.betterendforge.common.event.forge;

import git.jbredwards.nether_api.api.event.NetherAPIRegistryEvent;
import git.jbredwards.nether_api.mod.common.registry.NetherAPIRegistry;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModStructures;
import mod.beethoven92.betterendforge.config.Configs;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class NetherAPIHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    static void onNetherAPIRegistry(@Nonnull final NetherAPIRegistryEvent.End event)
    {
        //ModStructures.registerStructures(event.registry);
        ModBiomes.getModBiomes().forEach((end_biome) -> {
            if(!ModBiomes.CAVE_BIOMES.getBiomes().contains(end_biome))
                event.registry.registerBiome(end_biome.getBiome(), Configs.BIOME_CONFIG.getInt(end_biome.getID(), "netherapi_weight", 80));
        });
        Configs.BIOME_CONFIG.saveChanges();
    }
}
