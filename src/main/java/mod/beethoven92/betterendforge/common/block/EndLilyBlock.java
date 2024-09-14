package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class EndLilyBlock extends UnderwaterPlantBlock
{
	public static final PropertyEnum<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	private static final AxisAlignedBB SHAPE_BOTTOM = new AxisAlignedBB(4D/16D, 0D/16D, 4D/16D, 12D/16D, 16D/16D, 12D/16D);
	private static final AxisAlignedBB SHAPE_TOP = new AxisAlignedBB(2D/16D, 0D/16D, 2D/16D, 14D/16D, 6D/16D, 14D/16D);
	
	public EndLilyBlock()
	{
		super(Material.PLANTS);
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(EndLilyBlock.SHAPE) == TripleShape.TOP ? 13 : 0;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		Vec3d vec3d = state.getOffset(source, pos);
		AxisAlignedBB shape = state.getValue(SHAPE) == TripleShape.TOP ? SHAPE_TOP : SHAPE_BOTTOM;
		return shape.offset(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		if (state.getValue(SHAPE) == TripleShape.TOP)
		{
			return worldIn.getBlockState(pos.down()).getBlock() == this;
		}
		else if (state.getValue(SHAPE) == TripleShape.BOTTOM)
		{
			return isTerrain(worldIn.getBlockState(pos.down()));
		}
		else
		{
			IBlockState up = worldIn.getBlockState(pos.up());
			IBlockState down = worldIn.getBlockState(pos.down());
			return up.getBlock() == this && down.getBlock() == this;
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos currentPos, IBlockState stateIn, EntityLivingBase placer, ItemStack stack) {
		if (!canPlaceBlockAt(worldIn, currentPos))
		{
			worldIn.setBlockState(currentPos, stateIn.getValue(SHAPE) == TripleShape.TOP ? Blocks.AIR.getDefaultState() : Blocks.WATER.getDefaultState());
		}
		else
		{
			worldIn.setBlockState(currentPos, stateIn);
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHAPE);
	}

//	@Override
//	public FluidState getFluidState(IBlockState state)
//	{
//		return state.get(SHAPE) == TripleShape.TOP ? Fluids.EMPTY.getDefaultState() : Fluids.WATER.getStillFluidState(false);
//	}
//


	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ItemBlock.getItemFromBlock(ModBlocks.END_LILY_SEED);
	}

	public int quantityDropped(Random random)
	{
		return 0;
	}

	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
	{
		if (!worldIn.isRemote)
		{
			spawnAsEntity(worldIn, pos, new ItemStack(ModItems.END_LILY_LEAF, 1+worldIn.rand.nextInt(2), 0));
			spawnAsEntity(worldIn, pos, new ItemStack(ModBlocks.END_LILY_SEED, 1+worldIn.rand.nextInt(2), 0));

			if(state.getValue(EndLilyBlock.SHAPE)==TripleShape.TOP){
				spawnAsEntity(worldIn, pos, new ItemStack(ModBlocks.END_LILY, 1, 0));
				spawnAsEntity(worldIn, pos, new ItemStack(ModBlocks.END_LILY, 1, 0));
			}
		}
		else
		{
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
	}
}
