package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LargeAmaranitaFeature extends WorldGenerator {
	@Override
	public boolean generate(World world, Random random,	BlockPos pos) {
		if (!ModTags.END_GROUND.contains(world.getBlockState(pos.down()).getBlock())) return false;
		
		BlockPos.MutableBlockPos mut = new Mutable().setPos(pos);
		int height = ModMathHelper.randRange(2, 3, random);
		for (int i = 1; i < height; i++) {
			mut.setY(mut.getY() + 1);
			if (!world.isAirBlock(mut)) {
				return false;
			}
		}
		mut.setPos(pos);
		
		IBlockState state =  ModBlocks.LARGE_AMARANITA_MUSHROOM.getDefaultState();
		BlockHelper.setWithUpdate(world, mut, state.withProperty(BlockProperties.TRIPLE_SHAPE, TripleShape.BOTTOM));
		if (height > 2) {
			BlockHelper.setWithUpdate(world, mut.move(EnumFacing.UP), state.withProperty(BlockProperties.TRIPLE_SHAPE, TripleShape.MIDDLE));
		}
		BlockHelper.setWithUpdate(world, mut.move(EnumFacing.UP), state.withProperty(BlockProperties.TRIPLE_SHAPE, TripleShape.TOP));
		
		return true;
	}
}
