package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UmbrellaTreeClusterEmptyBlock extends Block {
	public static final PropertyBool NATURAL = PropertyBool.create("natural");

	public UmbrellaTreeClusterEmptyBlock() {
		super(Material.PLANTS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NATURAL, false));
		this.setTickRandomly(true);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, NATURAL);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(NATURAL, (meta & 1) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(NATURAL) ? 1 : 0;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (state.getValue(NATURAL) && rand.nextInt(16) == 0) {
			BlockHelper.setWithUpdate(worldIn, pos, ModBlocks.UMBRELLA_TREE_CLUSTER.getDefaultState().withProperty(UmbrellaTreeClusterBlock.NATURAL, true));
		}
	}
}
