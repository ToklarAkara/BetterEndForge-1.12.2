package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModConfiguredSurfaceBuilders;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;

import java.util.AbstractMap;

public class NeonOasisBiome extends BetterEndBiome {
	public NeonOasisBiome() {
		super(new BiomeTemplate("neon_oasis").setGenChance(0.5F).setFogColor(226, 239, 168).setFogDensity(2)
				.setWaterFogColor(106, 238, 215).setWaterColor(106, 238, 215)
				//.setSurface(ModConfiguredSurfaceBuilders.get(ModConfiguredSurfaceBuilders.NEON_OASUS_SURFACE))
				.setSurface(new AbstractMap.SimpleEntry<>(ModBlocks.ENDSTONE_DUST, Blocks.END_STONE))
				//.setParticles(ParticleTypes.WHITE_ASH, 0.01F)
				.setAmbientSound(ModSoundEvents.AMBIENT_DUST_WASTELANDS)
				.setMusic(ModSoundEvents.MUSIC_OPENSPACE)
				.addFeature(Decoration.LAKES, ModConfiguredFeatures.DESERT_LAKE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.NEON_CACTUS)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.UMBRELLA_MOSS)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CREEPING_MOSS)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_GREEN)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_CYAN)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED)
				//.addStructure(StructureFeatures.END_CITY)
				.addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 50, 1, 2));
	}
}
