package mod.beethoven92.betterendforge.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class RunedFlavoliteBlock extends Block {
	public static final PropertyBool ACTIVATED = PropertyBool.create("activated");

	public RunedFlavoliteBlock() {
		super(Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVATED, false));
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(ACTIVATED) ? 8 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ACTIVATED);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ACTIVATED, (meta & 1) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ACTIVATED) ? 1 : 0;
	}
}
