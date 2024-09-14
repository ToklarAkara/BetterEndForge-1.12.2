package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.AttachedBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class FilaluxFeature extends SkyScatterFeature {
	public FilaluxFeature() {
		super(10);
	}

	@Override
	public void generateInner(World world, Random random, BlockPos blockPos) {
		IBlockState vine = ModBlocks.FILALUX.getDefaultState();
		IBlockState wings = ModBlocks.FILALUX_WINGS.getDefaultState();
		BlockHelper.setWithoutUpdate(world, blockPos, ModBlocks.FILALUX_LANTERN);
		BlockHelper.setWithoutUpdate(world, blockPos.up(), wings.withProperty(AttachedBlock.FACING, EnumFacing.UP));
		for (EnumFacing dir: BlockHelper.HORIZONTAL_DIRECTIONS) {
			BlockHelper.setWithoutUpdate(world, blockPos.offset(dir), wings.withProperty(AttachedBlock.FACING, dir));
		}
		int length = ModMathHelper.randRange(1, 3, random);
		for (int i = 1; i <= length; i++) {
			TripleShape shape = length > 1 ? TripleShape.TOP : TripleShape.BOTTOM;
			if (i > 1) {
				shape = i == length ? TripleShape.BOTTOM : TripleShape.MIDDLE;
			}
			BlockHelper.setWithoutUpdate(world, blockPos.down(i), vine.withProperty(BlockProperties.TRIPLE_SHAPE, shape));
		}
	}
}
