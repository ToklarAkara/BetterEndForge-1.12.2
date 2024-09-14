package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
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
}
