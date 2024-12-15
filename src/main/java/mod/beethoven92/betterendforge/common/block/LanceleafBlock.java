package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.PentaShape;
import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LanceleafBlock extends PlantBlock {
	public static final PropertyEnum<PentaShape> SHAPE = BlockProperties.PENTA_SHAPE;
	public static final PropertyInteger ROTATION = BlockProperties.CUT_ROTATION;

	public LanceleafBlock(Material materialIn) {
		super(materialIn);
		setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, PentaShape.BOTTOM).withProperty(ROTATION, 0));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHAPE, ROTATION);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		PentaShape shape = state.getValue(SHAPE);
		if (shape == PentaShape.TOP) {
			return worldIn.getBlockState(pos.down()).getBlock() == this;
		} else if (shape == PentaShape.BOTTOM) {
			return worldIn.getBlockState(pos.down()).getBlock() == ModBlocks.AMBER_MOSS
					&& worldIn.getBlockState(pos.up()).getBlock() == this;
		} else {
			return worldIn.getBlockState(pos.down()).getBlock() == this
					&& worldIn.getBlockState(pos.up()).getBlock() == this;
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
        return state.getValue(ROTATION)*5+state.getValue(SHAPE).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(ROTATION, meta/5).withProperty(SHAPE, PentaShape.values()[meta%5]);
    }
}
