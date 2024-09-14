package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EndLilySeedBlock extends UnderwaterPlantBlockWithAge
{

	@Override
	public void doGrow(World world, Random random, BlockPos pos)
	{
		if (searchForAirAbove(world, pos))
		{
			BlockHelper.setWithoutUpdate(world, pos, ModBlocks.END_LILY.getDefaultState().withProperty(EndLilyBlock.SHAPE, TripleShape.BOTTOM));
			BlockPos up = pos.up();
			while (world.getBlockState(up).getMaterial() == Material.WATER)
			{
				BlockHelper.setWithoutUpdate(world, up, ModBlocks.END_LILY.getDefaultState().withProperty(EndLilyBlock.SHAPE, TripleShape.MIDDLE));
				up = up.up();
			}
			BlockHelper.setWithoutUpdate(world, up, ModBlocks.END_LILY.getDefaultState().withProperty(EndLilyBlock.SHAPE, TripleShape.TOP));
		}
	}

	private boolean searchForAirAbove(World world, BlockPos pos)
	{
		BlockPos up = pos.up();
		while (world.getBlockState(up).getMaterial() == Material.WATER)
		{
			up = up.up();
		}
		return world.isAirBlock(up);
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return true;
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
