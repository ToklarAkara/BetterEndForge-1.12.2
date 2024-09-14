package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.FurBlock;
import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class BlueVineSeedBlock extends PlantBlockWithAge
{
	public BlueVineSeedBlock(Material mat)
	{
		super(mat);
		setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
	}

	@Override
	public void growAdult(World world, Random random, BlockPos pos)
	{
		int height = ModMathHelper.randRange(2, 5, random);
		int h = BlockHelper.upRay(world, pos, height + 2);
		if (h < height + 1)
		{
			return;
		}
		BlockHelper.setWithoutUpdate(world, pos, ModBlocks.BLUE_VINE.getDefaultState().withProperty(BlockProperties.TRIPLE_SHAPE, TripleShape.BOTTOM));
		for (int i = 1; i < height; i++)
		{
			BlockHelper.setWithoutUpdate(world, pos.up(i), ModBlocks.BLUE_VINE.getDefaultState().withProperty(BlockProperties.TRIPLE_SHAPE, TripleShape.MIDDLE));
		}
		BlockHelper.setWithoutUpdate(world, pos.up(height), ModBlocks.BLUE_VINE.getDefaultState().withProperty(BlockProperties.TRIPLE_SHAPE, TripleShape.TOP));
		placeLantern(world, pos.up(height + 1));
	}

	private void placeLantern(World world, BlockPos pos)
	{
		BlockHelper.setWithoutUpdate(world, pos, ModBlocks.BLUE_VINE_LANTERN.getDefaultState().withProperty(BlueVineLanternBlock.NATURAL, true));
		for (EnumFacing dir : EnumFacing.HORIZONTALS)
		{
			BlockPos p = pos.offset(dir);
			if (world.isAirBlock(p))
			{
				BlockHelper.setWithoutUpdate(world, p, ModBlocks.BLUE_VINE_FUR.getDefaultState().withProperty(FurBlock.FACING, dir));
			}
		}
		if (world.isAirBlock(pos.up()))
		{
			BlockHelper.setWithoutUpdate(world, pos.up(), ModBlocks.BLUE_VINE_FUR.getDefaultState().withProperty(FurBlock.FACING, EnumFacing.UP));
		}
	}

	@Override
	protected boolean isTerrain(IBlockState state)
	{
		return state.getBlock() == ModBlocks.END_MOSS || state.getBlock() == ModBlocks.END_MYCELIUM;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return true;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, AGE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(AGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(AGE);
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
}
