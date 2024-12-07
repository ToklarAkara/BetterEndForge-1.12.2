package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.client.audio.BetterEndMusicType;
import mod.beethoven92.betterendforge.common.entity.CubozoaEntity;
import mod.beethoven92.betterendforge.common.entity.DragonflyEntity;
import mod.beethoven92.betterendforge.common.entity.EndFishEntity;
import mod.beethoven92.betterendforge.common.entity.EndSlimeEntity;
import mod.beethoven92.betterendforge.common.init.*;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;

import java.util.AbstractMap;

public class FoggyMushroomlandBiome extends BetterEndBiome 
{
	public FoggyMushroomlandBiome()
	{
		super(new BiomeTemplate("foggy_mushroomland").
				setFoliageColor(73, 210, 209).
				setGrassColor(73, 210, 209).
				setFogColor(41, 122, 173).
				setFogDensity(3).
				setWaterColor(119, 227, 250).
				setWaterFogColor(119, 227, 250).
				setSurface(ModConfiguredSurfaceBuilders.MUSHROOMLAND_SURFACE).
				//setSurface(ModConfiguredSurfaceBuilders.get(ModConfiguredSurfaceBuilders.MUSHROOMLAND_SURFACE)).
				//setParticles(ModParticleTypes.GLOWING_SPHERE, 0.001F).
				setAmbientSound(ModSoundEvents.AMBIENT_FOGGY_MUSHROOMLAND).
				setMusic(BetterEndMusicType.MUSIC_FOREST).
				addFeature(Decoration.LAKES, ModConfiguredFeatures.END_LAKE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.MOSSY_GLOWSHROOM).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BLUE_VINE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.UMBRELLA_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CREEPING_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.DENSE_VINE).
				//addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.PEARLBERRY).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CYAN_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CYAN_MOSS_WOOD).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.END_LILY).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BUBBLE_CORAL).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_CYAN).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_LIGHT_BLUE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED_RARE).
				//addStructure(ModConfiguredStructures.GIANT_MOSSY_GLOWSHROOM).
				//addStructure(StructureFeatures.END_CITY).
				addMobSpawn(EnumCreatureType.AMBIENT, DragonflyEntity.class, 80, 2, 5).
				addMobSpawn(EnumCreatureType.AMBIENT, EndFishEntity.class, 20, 2, 5).
				addMobSpawn(EnumCreatureType.MONSTER, EndSlimeEntity.class, 10, 1, 2).
				addMobSpawn(EnumCreatureType.AMBIENT, CubozoaEntity.class, 10, 3, 8).
				addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 10, 1, 2));
	}
}
