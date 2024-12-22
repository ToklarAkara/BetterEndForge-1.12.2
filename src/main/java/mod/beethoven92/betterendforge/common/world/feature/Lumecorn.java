package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;
import mod.beethoven92.betterendforge.common.block.BlockProperties.LumecornShape;
import mod.beethoven92.betterendforge.common.block.LumecornBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class Lumecorn extends WorldGenerator {

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		if (!ModTags.END_GROUND.contains(world.getBlockState(pos.down()).getBlock())) return false;
		
		int height = ModMathHelper.randRange(4, 7, random);
		BlockPos.MutableBlockPos mut = new Mutable().setPos(pos);
		for (int i = 1; i < height; i++) {
			mut.move(EnumFacing.UP);
			if (!world.isAirBlock(mut)) {
				return false;
			}
		}
		mut.setPos(pos);
		IBlockState topMiddle = ModBlocks.LUMECORN.getDefaultState().withProperty(LumecornBlock.SHAPE, LumecornShape.LIGHT_TOP_MIDDLE);
		IBlockState middle = ModBlocks.LUMECORN.getDefaultState().withProperty(LumecornBlock.SHAPE, LumecornShape.LIGHT_MIDDLE);
		IBlockState bottom = ModBlocks.LUMECORN.getDefaultState().withProperty(LumecornBlock.SHAPE, LumecornShape.LIGHT_BOTTOM);
		IBlockState top = ModBlocks.LUMECORN.getDefaultState().withProperty(LumecornBlock.SHAPE, LumecornShape.LIGHT_TOP);
		if (height == 4) {
			BlockHelper.setWithUpdate(world, mut, ModBlocks.LUMECORN.getDefaultState().withProperty(LumecornBlock.SHAPE, LumecornShape.BOTTOM_SMALL));
			BlockHelper.setWithUpdate(world, mut.move(EnumFacing.UP), bottom);
			BlockHelper.setWithUpdate(world, mut.move(EnumFacing.UP), topMiddle);
			BlockHelper.setWithUpdate(world, mut.move(EnumFacing.UP), top);
			return true;
		}
		if (random.nextBoolean()) {
			BlockHelper.setWithUpdate(world, mut, ModBlocks.LUMECORN.getDefaultState().withProperty(LumecornBlock.SHAPE, LumecornShape.BOTTOM_SMALL));
		}
		else {
			BlockHelper.setWithUpdate(world, mut, ModBlocks.LUMECORN.getDefaultState().withProperty(LumecornBlock.SHAPE, LumecornShape.BOTTOM_BIG));
			BlockHelper.setWithUpdate(world, mut.move(EnumFacing.UP), ModBlocks.LUMECORN.getDefaultState().withProperty(LumecornBlock.SHAPE, LumecornShape.MIDDLE));
			height --;
		}
		BlockHelper.setWithUpdate(world, mut.move(EnumFacing.UP), bottom);
		for (int i = 4; i < height; i++) {
			BlockHelper.setWithUpdate(world, mut.move(EnumFacing.UP), middle);
		}
		BlockHelper.setWithUpdate(world, mut.move(EnumFacing.UP), topMiddle);
		BlockHelper.setWithUpdate(world, mut.move(EnumFacing.UP), top);
		return false;
	}
}
