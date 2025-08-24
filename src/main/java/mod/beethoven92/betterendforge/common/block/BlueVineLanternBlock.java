package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;

import java.util.Random;

public class BlueVineLanternBlock extends Block
{
	public static final PropertyBool NATURAL = PropertyBool.create("natural");

	public BlueVineLanternBlock(Material mat)
	{
		super(mat);
		setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NATURAL, false));
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		IBlockState state = worldIn.getBlockState(pos);
		return state.getBlock() != this || !state.getValue(NATURAL) || worldIn.getBlockState(pos.down()).getBlock() == ModBlocks.BLUE_VINE;
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, net.minecraft.entity.EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(NATURAL, false);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(NATURAL, (meta & 1) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(NATURAL) ? 1 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, NATURAL);
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

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!this.canPlaceBlockAt(worldIn, pos))
		{
			worldIn.destroyBlock(pos, true);
		}
	}

	@Override
	public Item getItemDropped(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
		return Item.getItemFromBlock(ModBlocks.BLUE_VINE_LANTERN);
	}
}
