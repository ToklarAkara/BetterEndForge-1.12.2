package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.HydraluxShape;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class HydraluxBlock extends BlockBush {
	public static final PropertyEnum<HydraluxShape> SHAPE = PropertyEnum.create("shape", HydraluxShape.class);

	public HydraluxBlock() {
		super(Material.WATER);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, HydraluxShape.ROOTS));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHAPE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(SHAPE, HydraluxShape.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(SHAPE).ordinal();
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState down = worldIn.getBlockState(pos.down());
		HydraluxShape shape = worldIn.getBlockState(pos).getValue(SHAPE);
		if (shape == HydraluxShape.FLOWER_BIG_TOP || shape == HydraluxShape.FLOWER_SMALL_TOP) {
			return down.getBlock() == this;
		} else if (shape == HydraluxShape.ROOTS) {
			return down.getBlock() == ModBlocks.SULPHURIC_ROCK.stone && worldIn.getBlockState(pos.up()).getBlock() == this;
		} else {
			return down.getBlock() == this && worldIn.getBlockState(pos.up()).getBlock() == this;
		}
	}
}
