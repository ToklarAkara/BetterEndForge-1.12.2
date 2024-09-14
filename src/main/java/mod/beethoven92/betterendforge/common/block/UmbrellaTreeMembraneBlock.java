package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockSlime;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class UmbrellaTreeMembraneBlock extends BlockSlime {
	public static final PropertyInteger COLOR = PropertyInteger.create("color", 0, 7);
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(0);

	public UmbrellaTreeMembraneBlock() {
		super();
		this.slipperiness = 0.8F;
		this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, 0));
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		double px = pos.getX() * 0.1;
		double py = pos.getY() * 0.1;
		double pz = pos.getZ() * 0.1;
		return this.getDefaultState().withProperty(COLOR, ModMathHelper.floor(NOISE.eval(px, py, pz) * 3.5 + 4));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, COLOR);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(COLOR, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(COLOR);
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.getValue(COLOR) > 0 ? super.shouldSideBeRendered(blockState, blockAccess, pos, side) : false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return state.getValue(COLOR) <= 0;
	}
}
