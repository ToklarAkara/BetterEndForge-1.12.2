package mod.beethoven92.betterendforge.common.block.template;

import java.util.EnumMap;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChandelierBlock extends AttachedBlock {
	private static final EnumMap<EnumFacing, AxisAlignedBB> BOUNDING_SHAPES = Maps.newEnumMap(EnumFacing.class);

	static {
		BOUNDING_SHAPES.put(EnumFacing.UP, new AxisAlignedBB(5D/16D, 0D/16D, 5D/16D, 11D/16D, 13D/16D, 11D/16D));
		BOUNDING_SHAPES.put(EnumFacing.DOWN, new AxisAlignedBB(5D/16D, 3D/16D, 5D/16D, 11D/16D, 16D/16D, 11D/16D));
		BOUNDING_SHAPES.put(EnumFacing.NORTH, new AxisAlignedBB(0.0, 0.0, 0.5, 1.0, 1.0, 1.0));
		BOUNDING_SHAPES.put(EnumFacing.SOUTH, new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.5));
		BOUNDING_SHAPES.put(EnumFacing.WEST, new AxisAlignedBB(0.5, 0.0, 0.0, 1.0, 1.0, 1.0));
		BOUNDING_SHAPES.put(EnumFacing.EAST, new AxisAlignedBB(0.0, 0.0, 0.0, 0.5, 1.0, 1.0));
	}

	public ChandelierBlock(Material material) {
		super(material);
		setLightLevel(1);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDING_SHAPES.get(state.getValue(FACING));
	}


	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.byIndex(meta);
		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}


	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
}
