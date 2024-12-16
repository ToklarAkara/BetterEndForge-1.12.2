package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class AttachedBlock extends Block {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");

	public AttachedBlock(Material material) {
		super(material);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
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

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos currentPos, IBlockState stateIn, EntityLivingBase placer, ItemStack stack){
		if (!this.canPlaceBlockAt(worldIn, currentPos)) {
			worldIn.setBlockState(currentPos, Blocks.AIR.getDefaultState());
		}
	}

	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
	{
		return canPlaceBlock(worldIn, pos, side);
	}

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		for (EnumFacing enumfacing : EnumFacing.values())
		{
			if (canPlaceBlock(worldIn, pos, enumfacing))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!this.canPlaceBlock(worldIn, pos, state.getValue(FACING))) {
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	protected boolean canPlaceBlock(World worldIn, BlockPos pos, EnumFacing direction)
	{
		BlockPos blockPos = pos.offset(direction.getOpposite());
		return worldIn.getBlockState(blockPos).isSideSolid(worldIn, blockPos, direction) || worldIn.getBlockState(blockPos).getBlock().isLeaves(worldIn.getBlockState(blockPos), worldIn, blockPos);
	}
}
