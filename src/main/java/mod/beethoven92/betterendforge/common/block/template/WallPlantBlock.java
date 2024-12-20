package mod.beethoven92.betterendforge.common.block.template;

import java.util.EnumMap;
import java.util.Random;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class WallPlantBlock extends PlantBlock
{
	private static final EnumMap<EnumFacing, AxisAlignedBB> SHAPES = Maps.newEnumMap(ImmutableMap.of(
			EnumFacing.NORTH, new AxisAlignedBB(1.0D / 16.0D, 1.0D / 16.0D, 8.0D / 16.0D, 15.0D / 16.0D, 15.0D / 16.0D, 16.0D / 16.0D),
			EnumFacing.SOUTH, new AxisAlignedBB(1.0D / 16.0D, 1.0D / 16.0D, 0.0D, 15.0D / 16.0D, 15.0D / 16.0D, 8.0D / 16.0D),
			EnumFacing.WEST, new AxisAlignedBB(8.0D / 16.0D, 1.0D / 16.0D, 1.0D / 16.0D, 16.0D / 16.0D, 15.0D / 16.0D, 15.0D / 16.0D),
			EnumFacing.EAST, new AxisAlignedBB(0.0D, 1.0D / 16.0D, 1.0D / 16.0D, 8.0D / 16.0D, 15.0D / 16.0D, 15.0D / 16.0D)));

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public WallPlantBlock()
	{
		super(Material.PLANTS);
		setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return SHAPES.get(state.getValue(FACING));
	}

	@Override
	public EnumOffsetType getOffsetType()
	{
		return EnumOffsetType.NONE;
	}

	public boolean isValidPosition(IBlockState state, World worldIn, BlockPos pos)
	{
		EnumFacing direction = state.getValue(FACING);
		BlockPos blockPos = pos.offset(direction.getOpposite());
		IBlockState blockState = worldIn.getBlockState(blockPos);
		return isValidSupport(worldIn, blockPos, blockState, direction);
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

	protected boolean canPlaceBlock(World worldIn, BlockPos pos, EnumFacing direction)
	{
		BlockPos blockPos = pos.offset(direction.getOpposite());
		IBlockState blockState = worldIn.getBlockState(blockPos);
		return isValidSupport(worldIn, blockPos, blockState, direction);
	}


	public boolean isValidSupport(IBlockAccess world, BlockPos pos, IBlockState blockState, EnumFacing direction)
	{
		return blockState.getMaterial().isSolid() && blockState.isSideSolid(world, pos, direction);
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, net.minecraft.entity.EntityLivingBase placer)
	{
		IBlockState blockState = this.getDefaultState();
		EnumFacing[] directions = EnumFacing.HORIZONTALS;
		for (EnumFacing direction : directions)
		{
			EnumFacing direction2 = direction.getOpposite();
			blockState = blockState.withProperty(FACING, direction2);
			if (canPlaceBlock(worldIn, pos, direction2))
			{
				return blockState;
			}
		}
		return null;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!this.canPlaceBlockAt(worldIn, pos))
		{
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, net.minecraft.entity.player.EntityPlayer player)
	{
		super.onBlockHarvested(worldIn, pos, state, player);
		worldIn.notifyNeighborsOfStateChange(pos, Blocks.AIR, false);
	}

	public int quantityDropped(Random random)
	{
		return 0;
	}

	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
	{
		if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
		{
			spawnAsEntity(worldIn, pos, new ItemStack(state.getBlock(), 1, 0));
		}
		else
		{
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
	}

	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
		return super.getSoundType(state, world, pos, entity);
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand){

	}
}
