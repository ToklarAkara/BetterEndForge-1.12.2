package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class PallidiumBlock extends Block implements IGrowable
{
	private final Block nextLevel;

	public PallidiumBlock(String thickness, Block nextLevel)
	{
		super(Material.ROCK);
		this.setHardness(3.0F);
		this.setResistance(9.0F);
		this.setSoundType(SoundType.STONE);
		this.nextLevel = nextLevel;
	}

	public boolean canBePotted()
	{
		return this == ModBlocks.PALLIDIUM_FULL;
	}

	@Override
	public boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean b) {
		return nextLevel!=null;
	}

	@Override
	public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
		return nextLevel!=null;
	}

	@Override
	public void grow(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
		world.setBlockState(blockPos, nextLevel.getDefaultState(), 3);
	}
}
