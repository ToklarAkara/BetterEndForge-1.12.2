package mod.beethoven92.betterendforge.common.block.template;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class MetalPaneBlock extends BlockPane {
	public MetalPaneBlock(Material material, boolean canDrop) {
		super(material, canDrop);
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		IBlockState adjacentBlockState = blockAccess.getBlockState(pos.offset(side));
		if (side.getAxis().isVertical() && adjacentBlockState.getBlock() == this && adjacentBlockState != blockState) {
			return false;
		}
		return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
	{
		BlockPos offset = pos.offset(facing);
		return attachesTo(world, world.getBlockState(offset), offset, facing.getOpposite()) || world.getBlockState(offset).getBlock() == ModBlocks.FLAVOLITE.wall;
	}

	public boolean canPaneConnectTo(IBlockAccess world, BlockPos pos, EnumFacing dir)
	{
		BlockPos other = pos.offset(dir);
		IBlockState state = world.getBlockState(other);
		return state.getBlock().canBeConnectedTo(world, other, dir.getOpposite()) || attachesTo(world, state, other, dir.getOpposite())  || state.getBlock() == ModBlocks.FLAVOLITE.wall;
	}
}
