package mod.beethoven92.betterendforge.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class PathBlock extends Block
{
	private static final AxisAlignedBB SHAPE = new AxisAlignedBB(0, 0, 0, 1, 0.9375, 1);

	public PathBlock(Block block)
	{
		super(Material.ROCK);
		this.setHardness(block.getBlockHardness(null, null, BlockPos.ORIGIN));
		this.setResistance(block.getExplosionResistance(null));
		this.setSoundType(block.getSoundType());
		this.setTickRandomly(block.getTickRandomly());
		if (block instanceof TerrainBlock)
		{
			TerrainBlock terrain = (TerrainBlock)block;
			terrain.setPathBlock(this);
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return SHAPE;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return SHAPE;
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ItemBlock.getItemFromBlock(Blocks.END_STONE);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
}
