package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.client.audio.BetterEndMusicNames;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;

public class DryShrublandBiome extends BetterEndBiome {
	public DryShrublandBiome() {
		super(new BiomeTemplate("dry_shrubland")
				.setFogColor(132, 35, 13)
				.setFogDensity(1.2F)
				.setWaterColor(113, 88, 53)
				.setWaterFogColor(113, 88, 53)
				.setFoliageColor(237, 122, 66)
				.setSurface(ModBlocks.RUTISCUS)
				.setMusic(BetterEndMusicNames.MUSIC_OPENSPACE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.ORANGO)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AERIDIUM)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LUTEBUS)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LAMELLARIUM)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LUCERNIA_BUSH_RARE)
				//.addStructure(StructureFeatures.END_CITY)
				.addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 50, 1, 2));
	}
}
