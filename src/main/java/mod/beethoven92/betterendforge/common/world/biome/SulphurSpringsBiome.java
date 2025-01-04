package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.client.audio.BetterEndMusicNames;
import mod.beethoven92.betterendforge.common.entity.CubozoaEntity;
import mod.beethoven92.betterendforge.common.entity.EndFishEntity;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModConfiguredSurfaceBuilders;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;

public class SulphurSpringsBiome extends BetterEndBiome
{
	public SulphurSpringsBiome() 
	{
		super(new BiomeTemplate("sulphur_springs").
				setFogColor(207, 194, 62).
				setFogDensity(1.5F).
				setWaterColor(219, 159, 48).
				setWaterFogColor(30, 65, 61).
				setCaves(false).
				setSurface(ModConfiguredSurfaceBuilders.SULPHURIC_SURFACE).
				//setSurface(ModConfiguredSurfaceBuilders.get(ModConfiguredSurfaceBuilders.SULPHURIC_SURFACE)).
				setAmbientSound(ModSoundEvents.AMBIENT_SULPHUR_SPRINGS).
				setMusic(BetterEndMusicNames.MUSIC_OPENSPACE).
				//setParticles(ModParticleTypes.SULPHUR_PARTICLE, 0.001F).
				addFeature(Decoration.RAW_GENERATION, ModConfiguredFeatures.GEYSER).
				addFeature(Decoration.LAKES, ModConfiguredFeatures.SULPHURIC_LAKE).
				addFeature(Decoration.RAW_GENERATION, ModConfiguredFeatures.SULPHURIC_CAVE).
				addFeature(Decoration.SURFACE_STRUCTURES, ModConfiguredFeatures.SURFACE_VENT).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.HYDRALUX).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_ORANGE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_GREEN).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED_RARE).
				addMobSpawn(EnumCreatureType.AMBIENT, EndFishEntity.class, 50, 3, 8).
				addMobSpawn(EnumCreatureType.AMBIENT, CubozoaEntity.class, 50, 3, 8).
				addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 50, 1, 4));
	}
}
