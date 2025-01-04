package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.client.audio.BetterEndMusicNames;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;

public class UmbrellaJungleBiome extends BetterEndBiome
{
	public UmbrellaJungleBiome() 
	{
		super(new BiomeTemplate("umbrella_jungle").
				setFogColor(87, 223, 221).
				setFogDensity(2.3F).
				setWaterColor(119, 198, 253).
				setWaterFogColor(119, 198, 253).
				setFoliageColor(27, 183, 194).
				setSurface(ModBlocks.JUNGLE_MOSS).
				setAmbientSound(ModSoundEvents.AMBIENT_UMBRELLA_JUNGLE).
				setMusic(BetterEndMusicNames.MUSIC_FOREST).
				//setParticles(ModParticleTypes.JUNGLE_SPORE, 0.001F).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.UMBRELLA_TREE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.JELLYSHROOM).
				addFeature(Decoration.LAKES, ModConfiguredFeatures.END_LAKE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TWISTED_UMBRELLA_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.JUNGLE_GRASS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SMALL_JELLYSHROOM_FLOOR).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SMALL_JELLYSHROOM_CEIL).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SMALL_JELLYSHROOM_WALL).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SMALL_JELLYSHROOM_WOOD).			
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CYAN_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CYAN_MOSS_WOOD).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.JUNGLE_FERN_WOOD).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.JUNGLE_VINE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_CYAN).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_GREEN).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_LIGHT_BLUE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED_RARE).
				//addStructure(StructureFeatures.END_CITY).
				addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 50, 1, 2));
	}
}
