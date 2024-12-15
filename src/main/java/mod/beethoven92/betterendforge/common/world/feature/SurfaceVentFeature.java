package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.HydrothermalVentBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.FeatureHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class SurfaceVentFeature extends WorldGenerator
{

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		pos = FeatureHelper.getPosOnSurface(world, new BlockPos(pos.getX() + rand.nextInt(16), pos.getY(), pos.getZ() + rand.nextInt(16)));

		if (!ModTags.GEN_TERRAIN.contains(world.getBlockState(pos.down(3)).getBlock()))
		{
			return false;
		}
		
		Mutable mut = new Mutable();
		int count = ModMathHelper.randRange(15, 30, rand);
		IBlockState vent = ModBlocks.HYDROTHERMAL_VENT.getDefaultState().withProperty(HydrothermalVentBlock.WATERLOGGED, false);
		for (int i = 0; i < count; i++) 
		{
			mut.setPos(pos).add(ModMathHelper.floor(rand.nextGaussian() * 2 + 0.5), 5, ModMathHelper.floor(rand.nextGaussian() * 2 + 0.5));
			int dist = ModMathHelper.floor(2 - ModMathHelper.length(mut.getX() - pos.getX(), mut.getZ() - pos.getZ())) + rand.nextInt(2);
			if (dist > 0) 
			{
				IBlockState state = world.getBlockState(mut);
				for (int n = 0; n < 10 && state.getBlock()== Blocks.AIR; n++)
				{
					mut.setY(mut.getY() - 1);
					state = world.getBlockState(mut);
				}
				if (ModTags.GEN_TERRAIN.contains(state) && world.getBlockState(mut.up()).getBlock()!=(ModBlocks.HYDROTHERMAL_VENT))
				{
					for (int j = 0; j <= dist; j++) 
					{
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.SULPHURIC_ROCK.stone);
						mut.setY(mut.getY() + 1);
					}
					BlockHelper.setWithoutUpdate(world, mut, vent);
				}
			}
		}
		
		return true;
	}

}
