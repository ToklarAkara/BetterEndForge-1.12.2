package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.client.audio.BetterEndMusicType;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;

public class DustWastelandsBiome extends BetterEndBiome
{

	public DustWastelandsBiome()
	{
		super(new BiomeTemplate("dust_wastelands").
				setFogColor(226, 239, 168).
				setFogDensity(2).
                setWaterColor(192, 180, 131).
                setWaterFogColor(192, 180, 131).
                setCaves(false).
				setMusic(BetterEndMusicType.MUSIC_OPENSPACE).
				setAmbientSound(ModSoundEvents.AMBIENT_DUST_WASTELANDS).
				setSurface(ModBlocks.ENDSTONE_DUST).
				//setParticles(ParticleTypes.WHITE_ASH, 0.01F).
                //addStructure(StructureFeatures.END_CITY).
                addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 50, 1, 2));
	}
}
