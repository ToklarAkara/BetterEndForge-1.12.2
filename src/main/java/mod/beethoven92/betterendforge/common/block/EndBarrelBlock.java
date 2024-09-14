package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import mod.beethoven92.betterendforge.common.tileentity.EndBarrelTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EndBarrelBlock extends BlockContainer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", p_apply_1_ -> true);
	public static final PropertyBool OPEN = PropertyBool.create("open");;
	public EndBarrelBlock(Material materialIn) {
		super(materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPEN, Boolean.valueOf(false)));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new EndBarrelTileEntity();
	}


	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		} else {
			TileEntity tileEntity = world.getTileEntity(pos);
			if (tileEntity instanceof EndBarrelTileEntity) {
				player.displayGUIChest((EndBarrelTileEntity) tileEntity);
				player.addStat(StatList.CHEST_OPENED);
			}

			return true;
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof EndBarrelTileEntity) {
			((EndBarrelTileEntity) tileEntity).update();
		}
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileEntity = world.getTileEntity(pos);
			if (tileEntity instanceof EndBarrelTileEntity) {
				((EndBarrelTileEntity) tileEntity).setCustomName(stack.getDisplayName());
			}
		}
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, net.minecraft.entity.EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, facing.getOpposite());
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(OPEN, meta%2!=0).withProperty(FACING,  EnumFacing.byIndex(meta/2));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex()*2+(state.getValue(OPEN) ?1:0);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING, OPEN);
	}

	@Override
	public IBlockState withRotation(IBlockState state, net.minecraft.util.Rotation rot)
	{
		return state;
	}

	@Override
	public IBlockState withMirror(IBlockState state, net.minecraft.util.Mirror mirrorIn)
	{
		return state;
	}
}
