package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.client.audio.BetterEndMusicNames;
import mod.beethoven92.betterendforge.common.entity.SilkMothEntity;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;

public class BlossomingSpiresBiome extends BetterEndBiome
{
	public BlossomingSpiresBiome() 
	{
		super(new BiomeTemplate("blossoming_spires").
				setFogColor(241, 146, 229).
				setFogDensity(1.7F).
				setGrassColor(122, 45, 122).
				setFoliageColor(122, 45, 122).
				setCaves(false).
				setSurface(ModBlocks.PINK_MOSS).
				setAmbientSound(ModSoundEvents.AMBIENT_BLOSSOMING_SPIRES).
				setMusic(BetterEndMusicNames.MUSIC_FOREST).
				addFeature(Decoration.RAW_GENERATION, ModConfiguredFeatures.SPIRE).
				addFeature(Decoration.RAW_GENERATION, ModConfiguredFeatures.FLOATING_SPIRE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TENANEA).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TENANEA_BUSH).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BULB_VINE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BUSHY_GRASS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BUSHY_GRASS_WG).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BLOSSOM_BERRY).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TWISTED_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TWISTED_MOSS_WOOD).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BLOSSOM_BERRY).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SILK_MOTH_NEST).
				addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 50, 1, 4).
		        addMobSpawn(EnumCreatureType.AMBIENT, SilkMothEntity.class, 5, 1, 2));
	}
}
