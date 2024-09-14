package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;
import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.SilkMothNestBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class SilkMothNestFeature extends WorldGenerator {

	private static final Mutable POS = new Mutable();
	
	private boolean canGenerate(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos.up());
		if (state.getBlock() instanceof BlockLeaves || state.getBlock() instanceof BlockLog) {
			state = world.getBlockState(pos);
			if ((state.getBlock()== Blocks.AIR || state.getBlock()==(ModBlocks.TENANEA_OUTER_LEAVES)) && world.isAirBlock(pos.down())) {
				for (EnumFacing dir: BlockHelper.HORIZONTAL_DIRECTIONS) {
					if (world.getBlockState(pos.down().offset(dir)).getMaterial().blocksMovement()) {
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{		
		int maxY = world.getHeight(pos.getX(), pos.getZ());
		int minY = BlockHelper.upRay(world, new BlockPos(pos.getX(), 0, pos.getZ()), maxY);
		POS.setPos(pos);
		for (int y = maxY; y > minY; y--) {
			POS.setY(y);
			if (canGenerate(world, POS)) {
				EnumFacing dir = BlockHelper.randomHorizontal(rand);
				BlockHelper.setWithoutUpdate(world, POS, ModBlocks.SILK_MOTH_NEST.getDefaultState().withProperty(SilkMothNestBlock.FACING, dir).withProperty(BlockProperties.ACTIVATED, false));
				POS.setY(y - 1);
				BlockHelper.setWithoutUpdate(world, POS, ModBlocks.SILK_MOTH_NEST.getDefaultState().withProperty(SilkMothNestBlock.FACING, dir));
				return true;
			}
		}
		return false;
	}
}
