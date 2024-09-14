package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GlowingPillarLuminophorBlock extends Block {
	public static final PropertyBool NATURAL = PropertyBool.create("natural");

	public GlowingPillarLuminophorBlock() {
		super(Material.ROCK);
		setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NATURAL, false));
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return this.isNatural(worldIn.getBlockState(pos.down())) || super.canPlaceBlockAt(worldIn, pos);
	}

	private boolean isNatural(IBlockState state) {
		return state.getBlock() == ModBlocks.GLOWING_PILLAR_ROOTS;
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
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, NATURAL);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state;
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!this.canPlaceBlockAt(worldIn, pos)) {
			worldIn.setBlockToAir(pos);
		}
	}
}
