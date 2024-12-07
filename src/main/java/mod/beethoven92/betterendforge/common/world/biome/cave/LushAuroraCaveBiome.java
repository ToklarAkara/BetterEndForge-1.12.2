package mod.beethoven92.betterendforge.common.world.biome.cave;

import mod.beethoven92.betterendforge.client.audio.BetterEndMusicType;
import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndCaveBiome;
import mod.beethoven92.betterendforge.common.world.biome.BiomeTemplate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class LushAuroraCaveBiome extends BetterEndCaveBiome 
{
	public LushAuroraCaveBiome() 
	{
		super(new BiomeTemplate("lush_aurora_cave")
				.setFogColor(150, 30, 68)
				.setFogDensity(2.0F)
				.setFoliageColor(108, 25, 46)
				.setGrassColor(108, 25, 46)
				.setWaterColor(186, 77, 237)
				.setWaterFogColor(186, 77, 237)
				.setMusic(BetterEndMusicType.MUSIC_CAVES)
				.setAmbientSound(ModSoundEvents.AMBIENT_CAVES)
				//.setParticles(ModParticleTypes.GLOWING_SPHERE, 0.001F)
				.setSurface(ModBlocks.CAVE_MOSS));
		
		this.addFloorFeature(ModFeatures.BIG_AURORA_CRYSTAL, 1);
		this.addFloorFeature(ModFeatures.CAVE_BUSH, 5);
		this.addFloorFeature(ModFeatures.CAVE_GRASS, 40);
		this.addFloorFeature(ModFeatures.END_STONE_STALAGMITE_CAVEMOSS, 5);		
		this.addCeilFeature(ModFeatures.CAVE_BUSH, 1);
		this.addCeilFeature(ModFeatures.RUBINEA, 3);
		this.addCeilFeature(ModFeatures.END_STONE_STALACTITE_CAVEMOSS, 10);
	}
	
	@Override
	public float getFloorDensity()
	{
		return 0.2F;
	}
	
	@Override
	public float getCeilDensity() 
	{
		return 0.1F;
	}
	
	@Override
	public IBlockState getCeil(BlockPos pos)
	{
		return ModBlocks.CAVE_MOSS.getDefaultState().withProperty(BlockProperties.TRIPLE_SHAPE, TripleShape.TOP);
	}
}
