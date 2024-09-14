package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.LumecornShape;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LumecornBlock extends Block {
	public static final PropertyEnum<LumecornShape> SHAPE = PropertyEnum.create("shape", LumecornShape.class);
	private static final AxisAlignedBB SHAPE_BOTTOM = new AxisAlignedBB(6D/16D, 0D/16D, 6D/16D, 10D/16D, 16D/16D, 10D/16D);
	private static final AxisAlignedBB SHAPE_TOP = new AxisAlignedBB(6D/16D, 0D/16D, 6D/16D, 10D/16D, 8D/16D, 10D/16D);

	public LumecornBlock(Material materialIn) {
		super(materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, LumecornShape.BOTTOM_BIG));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHAPE);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return state.getValue(SHAPE) == LumecornShape.LIGHT_TOP ? SHAPE_TOP : SHAPE_BOTTOM;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		LumecornShape shape = state.getValue(SHAPE);
		if (shape == LumecornShape.BOTTOM_BIG || shape == LumecornShape.BOTTOM_SMALL) {
			return ModTags.END_GROUND.contains(worldIn.getBlockState(pos.down()).getBlock());
		} else if (shape == LumecornShape.LIGHT_TOP) {
			return worldIn.getBlockState(pos.down()).getBlock() == this;
		} else {
			return worldIn.getBlockState(pos.down()).getBlock() == this && worldIn.getBlockState(pos.up()).getBlock() == this;
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!canPlaceBlockAt((World) worldIn, pos)) {
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		} else {
			worldIn.setBlockState(pos, state);
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}//TODO META
}
