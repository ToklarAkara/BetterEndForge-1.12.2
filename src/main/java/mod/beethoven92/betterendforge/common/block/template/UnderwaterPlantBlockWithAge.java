package mod.beethoven92.betterendforge.common.block.template;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;

public abstract class UnderwaterPlantBlockWithAge extends UnderwaterPlantBlock
{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);

	public UnderwaterPlantBlockWithAge()
	{
		super(Material.PLANTS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
	}

	public abstract void doGrow(World world, Random random, BlockPos pos);

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (rand.nextInt(4) == 0)
		{
			int age = state.getValue(AGE);
			if (age < 3)
			{
				worldIn.setBlockState(pos, state.withProperty(AGE, age + 1));
			}
			else
			{
				doGrow(worldIn, rand, pos);
			}
		}
	}

	@Override
	public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random random)
	{
		if (canGrow(worldIn, pos, state, false))
		{
			updateTick(worldIn, pos, state, random);
		}
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
}
