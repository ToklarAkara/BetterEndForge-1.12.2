package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.client.audio.BetterEndMusicType;
import mod.beethoven92.betterendforge.common.entity.ShadowWalkerEntity;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;

public class ShadowForestBiome extends BetterEndBiome
{
	public ShadowForestBiome() 
	{
		super(new BiomeTemplate("shadow_forest").
				setFogColor(0, 0, 0).
				setFogDensity(2.5F).
				setGrassColor(45, 45, 45).
				setFoliageColor(45, 45, 45).
				setWaterColor(42, 45, 80).
				setWaterFogColor(42, 45, 80).
				setSurface(ModBlocks.SHADOW_GRASS).
				//setParticles(ParticleTypes.MYCELIUM, 0.01F).
				setAmbientSound(ModSoundEvents.AMBIENT_CHORUS_FOREST).
				setMusic(BetterEndMusicType.MUSIC_DARK).
				addFeature(Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.VIOLECITE_LAYER).
				addFeature(Decoration.LAKES, ModConfiguredFeatures.END_LAKE_RARE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.DRAGON_TREE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.DRAGON_TREE_BUSH).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SHADOW_PLANT).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.MURKWEED).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.NEEDLEGRASS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TWISTED_VINE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SHADOW_BERRY).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.PURPLE_POLYPORE_DENSE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TAIL_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TAIL_MOSS_WOOD).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_PURPLE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED_RARE).
				//addStructure(StructureFeatures.END_CITY).
				addMobSpawn(EnumCreatureType.MONSTER, ShadowWalkerEntity.class, 80, 2, 4).
				addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 40, 1, 4));
	}
}
