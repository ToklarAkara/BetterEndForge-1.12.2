package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BulbVineSeedBlock extends PlantBlockWithAge
{
	public BulbVineSeedBlock()
	{
		super(Material.PLANTS);
		this.setTickRandomly(true);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState up = worldIn.getBlockState(pos.up());
		return ModTags.GEN_TERRAIN.contains(up.getBlock()) || up.getBlock() instanceof BlockLog || up.getBlock() instanceof BlockLeaves;
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
	{
		IBlockState up = worldIn.getBlockState(pos.up());
		return ModTags.GEN_TERRAIN.contains(up.getBlock()) || up.getBlock() instanceof BlockLog || up.getBlock() instanceof BlockLeaves;
	}

	public void growAdult(World world, Random random, BlockPos pos)
	{
		int h = BlockHelper.downRay(world, pos, random.nextInt(24)) - 1;
		if (h > 2)
		{
			BlockHelper.setWithoutUpdate(world, pos, ModBlocks.BULB_VINE.getDefaultState().withProperty(BlockProperties.TRIPLE_SHAPE, TripleShape.TOP));
			for (int i = 1; i < h; i++)
			{
				BlockHelper.setWithoutUpdate(world, pos.down(i), ModBlocks.BULB_VINE.getDefaultState().withProperty(BlockProperties.TRIPLE_SHAPE, TripleShape.MIDDLE));
			}
			BlockHelper.setWithoutUpdate(world, pos.down(h), ModBlocks.BULB_VINE.getDefaultState().withProperty(BlockProperties.TRIPLE_SHAPE, TripleShape.BOTTOM));
		}
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
