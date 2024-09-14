package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.DoublePlantBlock;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UnderwaterPlantFeature extends UnderwaterPlantScatter
{
	private final Block plant;
	
	public UnderwaterPlantFeature(Block plant, int radius) 
	{
		super(radius);
		this.plant = plant;
	}

	@Override
	public boolean canGenerate(World world, Random random, BlockPos center, BlockPos blockPos, float radius)
	{
		return super.canSpawn(world, blockPos) && plant.canPlaceBlockAt(world, blockPos);
	}
	
	@Override
	public void generateInner(World world, Random random, BlockPos blockPos)
	{
		if (plant instanceof DoublePlantBlock) 
		{
			int rot = random.nextInt(4);
			IBlockState state = plant.getDefaultState().withProperty(DoublePlantBlock.ROTATION, rot);
			BlockHelper.setWithoutUpdate(world, blockPos, state);
			BlockHelper.setWithoutUpdate(world, blockPos.up(), state.withProperty(DoublePlantBlock.TOP, true));
		}
		else 
		{
			BlockHelper.setWithoutUpdate(world, blockPos, plant.getDefaultState());
		}
	}

}
