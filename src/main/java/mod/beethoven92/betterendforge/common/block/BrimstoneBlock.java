package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BrimstoneBlock extends Block
{
	public static final PropertyBool ACTIVATED = PropertyBool.create("activated");

	public BrimstoneBlock()
	{
		super(Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVATED, false));
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random random)
	{
		boolean deactivate = true;
		for (EnumFacing dir : EnumFacing.values())
		{
			if (worldIn.getBlockState(pos.offset(dir)).getMaterial() == Material.WATER)
			{
				deactivate = false;
				break;
			}
		}
		if (state.getValue(ACTIVATED))
		{
			if (deactivate)
			{
				worldIn.setBlockState(pos, getDefaultState().withProperty(ACTIVATED, false));
			}
			else if (random.nextInt(16) == 0)
			{
				EnumFacing dir = EnumFacing.random(random);
				BlockPos side = pos.offset(dir);
				IBlockState sideState = worldIn.getBlockState(side);
				if (sideState.getBlock() instanceof SulphurCrystalBlock)
				{
					if (sideState.getValue(SulphurCrystalBlock.AGE) < 2)// && sideState.getValue(SulphurCrystalBlock.WATERLOGGED))
					{
						int age = sideState.getValue(SulphurCrystalBlock.AGE) + 1;
						worldIn.setBlockState(side, sideState.withProperty(SulphurCrystalBlock.AGE, age));
					}
				}
				else if (sideState.getMaterial() == Material.WATER)
				{
					IBlockState crystal = ModBlocks.SULPHUR_CRYSTAL.getDefaultState()
							.withProperty(SulphurCrystalBlock.FACING, dir)
							//.withProperty(SulphurCrystalBlock.WATERLOGGED, true)
							.withProperty(SulphurCrystalBlock.AGE, 0);
					worldIn.setBlockState(side, crystal);
				}
			}
		}
		else if (!deactivate)
		{
			worldIn.setBlockState(pos, getDefaultState().withProperty(ACTIVATED, true));
		}
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, ACTIVATED);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(ACTIVATED, (meta & 1) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(ACTIVATED) ? 1 : 0;
	}
}
