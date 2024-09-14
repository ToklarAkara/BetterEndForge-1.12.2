package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;

public class PaintedMountainsBiome extends BetterEndBiome
{
	public PaintedMountainsBiome()
	{
		super(new BiomeTemplate("painted_mountains").
				setFogColor(226, 239, 168).
				setFogDensity(2).
				setWaterColor(192, 180, 131).
				setWaterFogColor(192, 180, 131).
				setCaves(false).
				setMusic(ModSoundEvents.MUSIC_OPENSPACE).
				setAmbientSound(ModSoundEvents.AMBIENT_DUST_WASTELANDS).
				setSurface(ModBlocks.ENDSTONE_DUST).
				//addStructure(ModConfiguredStructures.PAINTED_MOUNTAIN).
				//setParticles(ParticleTypes.WHITE_ASH, 0.01F).
				addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 50, 1, 2));
	}
}
