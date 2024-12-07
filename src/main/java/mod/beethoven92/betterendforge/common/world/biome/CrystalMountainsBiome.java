package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.client.audio.BetterEndMusicType;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;

public class CrystalMountainsBiome extends BetterEndBiome
{
	public CrystalMountainsBiome() 
	{
		super(new BiomeTemplate("crystal_mountains").setGrassColor(255, 133, 211).
				                  setFoliageColor(255, 133, 211).
				                  setMusic(BetterEndMusicType.MUSIC_OPENSPACE).
				                  setSurface(ModBlocks.CRYSTAL_MOSS).
				                  addFeature(Decoration.RAW_GENERATION, ModConfiguredFeatures.ROUND_CAVE).
				                  addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CRYSTAL_GRASS).
				                  addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CAVE_GRASS).
				                  //addStructure(ModConfiguredStructures.MOUNTAIN_STRUCTURE).
				                  addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 50, 1, 2));
	}
}
