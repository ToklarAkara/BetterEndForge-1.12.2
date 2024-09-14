package mod.beethoven92.betterendforge.common.block;

import java.util.EnumMap;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.block.template.AttachedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SmaragdantCrystalShardBlock extends AttachedBlock {
	private static final EnumMap<EnumFacing, AxisAlignedBB> BOUNDING_SHAPES = Maps.newEnumMap(EnumFacing.class);

	static {
		BOUNDING_SHAPES.put(EnumFacing.UP, new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.875, 0.875));
		BOUNDING_SHAPES.put(EnumFacing.DOWN, new AxisAlignedBB(0.125, 0.125, 0.125, 0.875, 1.0, 0.875));
		BOUNDING_SHAPES.put(EnumFacing.NORTH, new AxisAlignedBB(0.125, 0.125, 0.125, 0.875, 0.875, 1.0));
		BOUNDING_SHAPES.put(EnumFacing.SOUTH, new AxisAlignedBB(0.125, 0.125, 0.0, 0.875, 0.875, 0.875));
		BOUNDING_SHAPES.put(EnumFacing.WEST, new AxisAlignedBB(0.125, 0.125, 0.125, 1.0, 0.875, 0.875));
		BOUNDING_SHAPES.put(EnumFacing.EAST, new AxisAlignedBB(0.0, 0.125, 0.125, 0.875, 0.875, 0.875));
	}

	public SmaragdantCrystalShardBlock() {
		super(Material.ROCK);
		this.setTickRandomly(true);
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
		EnumFacing facing = EnumFacing.byIndex(meta);
		return this.getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		EnumFacing direction = state.getValue(FACING);
		BlockPos blockPos = pos.offset(direction.getOpposite());
		IBlockState support = worldIn.getBlockState(blockPos);
		return support.isSideSolid(worldIn, blockPos, direction);
	}

}
