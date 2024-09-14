package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.EndLotusLeafBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EndLotusLeafFeature extends ScatterFeature
{
	public EndLotusLeafFeature(int radius) 
	{
		super(radius);
	}

	private boolean canGenerate(World world, BlockPos blockPos) 
	{
		Mutable p = new Mutable();
		p.setY(blockPos.getY());
		int count = 0;
		for (int x = -1; x < 2; x ++) 
		{
			p.setX(blockPos.getX() + x);
			for (int z = -1; z < 2; z ++) 
			{
				p.setZ(blockPos.getZ() + z);
				if (world.isAirBlock(p) && world.getBlockState(p.down())==(Blocks.WATER))
					count ++;
			}
		}
		return count == 9;
	}

	@Override
	public boolean canGenerate(World world, Random random, BlockPos center, BlockPos blockPos, float radius) 
	{
		return world.isAirBlock(blockPos) && world.getBlockState(blockPos.down())==(Blocks.WATER);
	}

	@Override
	public void generateInner(World world, Random random, BlockPos blockPos)
	{
		if (canGenerate(world, blockPos)) 
		{
			generateLeaf(world, blockPos);
		}
	}
	
	private void generateLeaf(World world, BlockPos pos) 
	{
		Mutable p = new Mutable();
		IBlockState leaf = ModBlocks.END_LOTUS_LEAF.getDefaultState();
		BlockHelper.setWithoutUpdate(world, pos, leaf.withProperty(EndLotusLeafBlock.SHAPE, TripleShape.BOTTOM));

		for (EnumFacing move: BlockHelper.HORIZONTAL_DIRECTIONS)
		{
			BlockHelper.setWithoutUpdate(world, p.setPos(pos).move(move), leaf.withProperty(EndLotusLeafBlock.HORIZONTAL_FACING, move).withProperty(EndLotusLeafBlock.SHAPE, TripleShape.MIDDLE));
		}

		for (int i = 0; i < 4; i ++) 
		{
			EnumFacing d1 = BlockHelper.HORIZONTAL_DIRECTIONS[i];
			EnumFacing d2 = BlockHelper.HORIZONTAL_DIRECTIONS[(i + 1) & 3];
			BlockHelper.setWithoutUpdate(world, p.setPos(pos).move(d1).move(d2), leaf.withProperty(EndLotusLeafBlock.HORIZONTAL_FACING, d1).withProperty(EndLotusLeafBlock.SHAPE, TripleShape.TOP));
		}
		//BlockHelper.setWithoutUpdate(world, p.setPos(pos).move(Direction.NORTH).move(Direction.EAST), leaf.with(EndLotusLeafBlock.HORIZONTAL_FACING, Direction.NORTH).with(EndLotusLeafBlock.SHAPE, TripleShape.TOP));
		//BlockHelper.setWithoutUpdate(world, p.setPos(pos).move(Direction.EAST).move(Direction.SOUTH), leaf.with(EndLotusLeafBlock.HORIZONTAL_FACING, Direction.EAST).with(EndLotusLeafBlock.SHAPE, TripleShape.TOP));
		//BlockHelper.setWithoutUpdate(world, p.setPos(pos).move(Direction.SOUTH).move(Direction.WEST), leaf.with(EndLotusLeafBlock.HORIZONTAL_FACING, Direction.SOUTH).with(EndLotusLeafBlock.SHAPE, TripleShape.TOP));
		//BlockHelper.setWithoutUpdate(world, p.setPos(pos).move(Direction.WEST).move(Direction.NORTH), leaf.with(EndLotusLeafBlock.HORIZONTAL_FACING, Direction.WEST).with(EndLotusLeafBlock.SHAPE, TripleShape.TOP));
	}
	
	@Override
	protected int getChance() 
	{
		return 15;
	}
	
	@Override
	protected BlockPos getCenterGround(World world, BlockPos pos) 
	{
		return world.getHeight(pos);
	}
}
