package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.client.audio.BetterEndMusicType;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;

public class DragonGraveyardsBiome extends BetterEndBiome {
	public DragonGraveyardsBiome() {
		super(new BiomeTemplate("dragon_graveyards")
				.setGenChance(0.1F)
				.setFogColor(244, 46, 79)
				.setFogDensity(1.3F)
				//.setParticles(ModParticleTypes.FIREFLY, 0.0007F)
				.setMusic(BetterEndMusicType.MUSIC_OPENSPACE)
				.setAmbientSound(ModSoundEvents.AMBIENT_GLOWING_GRASSLANDS)
				.setSurface(ModBlocks.SANGNUM)
				.setWaterFogColor(203, 59, 167).setFoliageColor(244, 46, 79)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.OBSIDIAN_PILLAR_BASEMENT)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.FALLEN_PILLAR)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.OBSIDIAN_BOULDER)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.GIGANTIC_AMARANITA)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LARGE_AMARANITA)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SMALL_AMARANITA)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.GLOBULAGUS)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CLAWFERN)
				.addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 50, 1, 2));
	}
}
