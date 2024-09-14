package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;

public class IceStarfieldBiome extends BetterEndBiome
{
	public IceStarfieldBiome() 
	{
		super(new BiomeTemplate("ice_starfield").
				setFogColor(224, 245, 254).
				setFogDensity(2.2F).
				setFoliageColor(193, 244, 244).
				setGenChance(0.25f).
				//setParticles(ModParticleTypes.SNOWFLAKE_PARTICLE, 0.002F).
				//addStructure(ModConfiguredStructures.GIANT_ICE_STAR).
				addFeature(Decoration.RAW_GENERATION, ModConfiguredFeatures.ICE_STAR).
				addFeature(Decoration.RAW_GENERATION, ModConfiguredFeatures.ICE_STAR_SMALL).
				addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 20, 1, 4));
	}
}
