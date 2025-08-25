package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.client.audio.BetterEndMusicNames;
import mod.beethoven92.betterendforge.common.entity.EndSlimeEntity;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;

public class AmberLandBiome extends BetterEndBiome {
	public AmberLandBiome() {
		super(new BiomeTemplate("amber_land")
				.setFogColor(255, 184, 71)
				.setFogDensity(2.0F)
				.setFoliageColor(219, 115, 38)
				.setGrassColor(219, 115, 38)
				.setWaterFogColor(145, 108, 72)
				.setMusic(BetterEndMusicNames.MUSIC_FOREST)
				.setAmbientSound(ModSoundEvents.AMBIENT_AMBER_LAND)
				//.setParticles(ModParticleTypes.AMBER_SPHERE, 0.001F)
				.setSurface(ModBlocks.AMBER_MOSS)
				.addFeature(Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.AMBER_ORE)
				.addFeature(Decoration.LAKES, ModConfiguredFeatures.END_LAKE_RARE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.HELIX_TREE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LANCELEAF)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.GLOW_PILLAR)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AMBER_GRASS)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AMBER_ROOT)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BULB_MOSS)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BULB_MOSS_WOOD)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_ORANGE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED)
				//.addStructure(new MapGenEndCity())
				.addMobSpawn(EnumCreatureType.MONSTER, EndSlimeEntity.class, 10, 1, 2)
				.addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 50, 1, 4));
	}
}
