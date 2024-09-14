package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class EndLotusFlowerBlock extends Block
{
	private static final AxisAlignedBB SHAPE_OUTLINE = new AxisAlignedBB(2D/16D, 0D/16D, 2D/16D, 14D/16D, 14D/16D, 14D/16D);
	private static final AxisAlignedBB SHAPE_COLLISION = new AxisAlignedBB(0D/16D, 0D/16D, 0D/16D, 16D/16D, 2D/16D, 16D/16D);

	public EndLotusFlowerBlock(Material materialIn)
	{
		super(materialIn);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SHAPE_OUTLINE;
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return SHAPE_COLLISION;
	}

	@Override
	public EnumOffsetType getOffsetType()
	{
		return EnumOffsetType.NONE;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		IBlockState down = worldIn.getBlockState(pos.down());
		return down.getBlock() == ModBlocks.END_LOTUS_STEM;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!canPlaceBlockAt(worldIn, pos))
		{
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	public int quantityDropped(IBlockState state, int fortune, Random random)
	{
		return 1 + random.nextInt(2);
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ItemBlock.getItemFromBlock(ModBlocks.END_LOTUS_SEED);
	}
}
