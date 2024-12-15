package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class SmaragdantCrystalFeature extends WorldGenerator
{

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		if (!ModTags.GEN_TERRAIN.contains(world.getBlockState(pos.down()).getBlock()))
		{
			return false;
		}
		
		Mutable mut = new Mutable();
		int count = ModMathHelper.randRange(15, 30, rand);
		IBlockState crystal = ModBlocks.SMARAGDANT_CRYSTAL.getDefaultState();
		IBlockState shard = ModBlocks.SMARAGDANT_CRYSTAL_SHARD.getDefaultState();
		for (int i = 0; i < count; i++) 
		{
			mut.setPos(pos).add(ModMathHelper.floor(rand.nextGaussian() * 2 + 0.5), 5, ModMathHelper.floor(rand.nextGaussian() * 2 + 0.5));
			int dist = ModMathHelper.floor(1.5F - ModMathHelper.length(mut.getX() - pos.getX(), mut.getZ() - pos.getZ())) + rand.nextInt(3);
			if (dist > 0) 
			{
				IBlockState state = world.getBlockState(mut);
				for (int n = 0; n < 10 && state.getBlock()== Blocks.AIR; n++)
				{
					mut.setY(mut.getY() - 1);
					state = world.getBlockState(mut);
				}
				if (ModTags.GEN_TERRAIN.contains(state.getBlock()) && world.getBlockState(mut.up()).getBlock()!=(crystal.getBlock()))
				{
					for (int j = 0; j <= dist; j++) 
					{
						BlockHelper.setWithoutUpdate(world, mut, crystal);
						mut.setY(mut.getY() + 1);
					}
//					boolean waterlogged = !world.getFluidState(mut).isEmpty(); TODO CHECK
//					BlockHelper.setWithoutUpdate(world, mut, shard.withProperty(BlockStateProperties.WATERLOGGED, waterlogged));
				}
			}
		}
		
		return true;
	}

}
