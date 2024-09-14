package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TripleTerrainBlock extends TerrainBlock {
	public static final PropertyEnum<TripleShape> SHAPE = PropertyEnum.create("shape", TripleShape.class);

	public TripleTerrainBlock() {
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, TripleShape.BOTTOM));
		this.setTickRandomly(true);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TripleShape shape = state.getValue(SHAPE);
		if (shape == TripleShape.BOTTOM) {
			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
		return false;
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		TripleShape shape = facing == EnumFacing.UP ? TripleShape.BOTTOM : facing == EnumFacing.DOWN ? TripleShape.TOP : TripleShape.MIDDLE;
		return this.getDefaultState().withProperty(SHAPE, shape);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		TripleShape shape = state.getValue(SHAPE);
		if (shape == TripleShape.BOTTOM) {
			super.updateTick(worldIn, pos, state, rand);
			return;
		} else if (rand.nextInt(16) == 0) {
			boolean bottom = canSurviveBottom(worldIn, pos);
			if (shape == TripleShape.TOP) {
				if (!bottom) {
					worldIn.setBlockState(pos, Blocks.END_STONE.getDefaultState());
				}
			} else {
				boolean top = canSurvive(state, worldIn, pos) || isMiddle(worldIn.getBlockState(pos.up()));
				if (!top && !bottom) {
					worldIn.setBlockState(pos, Blocks.END_STONE.getDefaultState());
				} else if (top && !bottom) {
					worldIn.setBlockState(pos, state.withProperty(SHAPE, TripleShape.BOTTOM));
				} else if (!top && bottom) {
					worldIn.setBlockState(pos, state.withProperty(SHAPE, TripleShape.TOP));
				}
			}
		}
	}

	protected boolean canSurviveBottom(IBlockAccess world, BlockPos pos) {
		BlockPos blockPos = pos.down();
		IBlockState blockState = world.getBlockState(blockPos);
		if (isMiddle(blockState)) {
			return true;
		} else if (blockState.getMaterial() == Material.WATER && blockState.getValue(BlockLiquid.LEVEL) == 0) {
			return false;
		} else {
			return !blockState.isSideSolid(world, blockPos, EnumFacing.UP);
		}
	}

	protected boolean isMiddle(IBlockState state) {
		return state.getBlock() == this && state.getValue(SHAPE) == TripleShape.MIDDLE;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHAPE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(SHAPE, TripleShape.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(SHAPE).ordinal();
	}
}
