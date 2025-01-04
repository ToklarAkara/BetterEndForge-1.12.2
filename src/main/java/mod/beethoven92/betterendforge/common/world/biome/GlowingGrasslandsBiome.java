package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.client.audio.BetterEndMusicNames;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;

public class GlowingGrasslandsBiome extends BetterEndBiome 
{
	public GlowingGrasslandsBiome() {
		super(new BiomeTemplate("glowing_grasslands")
				.setWaterColor(92, 250, 230)
				.setWaterFogColor(92, 250, 230)
				.setFoliageColor(73, 210, 209)
				.setGrassColor(73, 210, 209)
				.setFogColor(99, 228, 247)				
				.setFogDensity(1.3F)
				.setMusic(BetterEndMusicNames.MUSIC_OPENSPACE)
				.setAmbientSound(ModSoundEvents.AMBIENT_GLOWING_GRASSLANDS)
				//.setParticles(ModParticleTypes.FIREFLY, 0.001F)
				.setSurface(ModBlocks.END_MOSS)
				.addFeature(Decoration.LAKES, ModConfiguredFeatures.END_LAKE_RARE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LUMECORN)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BLOOMING_COOKSONIA)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SALTEAGO)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.VAIOLUSH_FERN)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.FRACTURN)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.UMBRELLA_MOSS_RARE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CREEPING_MOSS_RARE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TWISTED_UMBRELLA_MOSS_RARE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_CYAN)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_GREEN)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_LIGHT_BLUE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED_RARE)
				//.addStructure(StructureFeatures.END_CITY)
				.addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 50, 1, 2));
	}
}
