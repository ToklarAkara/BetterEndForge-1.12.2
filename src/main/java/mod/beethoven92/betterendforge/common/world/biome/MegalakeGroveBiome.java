package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.client.audio.BetterEndMusicType;
import mod.beethoven92.betterendforge.common.entity.CubozoaEntity;
import mod.beethoven92.betterendforge.common.entity.DragonflyEntity;
import mod.beethoven92.betterendforge.common.entity.EndFishEntity;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;

public class MegalakeGroveBiome extends BetterEndBiome 
{
	public MegalakeGroveBiome() 
	{
		super(new BiomeTemplate("megalake_grove").
				setFoliageColor(73, 210, 209).
				setGrassColor(73, 210, 209).
				setFogColor(178, 209, 248).
				setWaterColor(96, 163, 255).
				setWaterFogColor(96, 163, 255).
				setFogDensity(2.0F).
				//setParticles(ModParticleTypes.GLOWING_SPHERE, 0.001F).
				setMusic(BetterEndMusicType.MUSIC_WATER).
				setAmbientSound(ModSoundEvents.AMBIENT_MEGALAKE_GROVE).
				setSurface(ModBlocks.END_MOSS).
				//addStructure(ModConfiguredStructures.MEGALAKE_SMALL_STRUCTURE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LACUGROVE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.END_LOTUS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.END_LOTUS_LEAF).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BUBBLE_CORAL_RARE).
			    addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.END_LILY_RARE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.UMBRELLA_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CREEPING_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.MENGER_SPONGE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_CYAN).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_LIGHT_BLUE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED_RARE).
				addMobSpawn(EnumCreatureType.AMBIENT, DragonflyEntity.class, 20, 1, 3).
				addMobSpawn(EnumCreatureType.AMBIENT, EndFishEntity.class, 20, 3, 8).
				addMobSpawn(EnumCreatureType.AMBIENT, CubozoaEntity.class, 50, 3, 8).
				addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 10, 1, 2));
	}
}
