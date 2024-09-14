package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.NeonCactusPlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class NeonCactusFeature extends WorldGenerator {

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		IBlockState ground = world.getBlockState(pos.down());
		if (ground.getBlock()!=(ModBlocks.ENDSTONE_DUST) && ground.getBlock()!=(ModBlocks.END_MOSS)) {
			return false;
		}

		NeonCactusPlantBlock cactus = ((NeonCactusPlantBlock) ModBlocks.NEON_CACTUS);
		cactus.growPlant(world, pos, rand);

		return true;
	}
}
