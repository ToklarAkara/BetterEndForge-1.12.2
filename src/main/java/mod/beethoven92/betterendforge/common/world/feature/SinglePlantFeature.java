package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.DoublePlantBlock;
import mod.beethoven92.betterendforge.common.block.template.EndCropBlock;
import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.FeatureHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SinglePlantFeature extends ScatterFeature
{
	private final Block plant;
	private final boolean rawHeightmap;
	private final int chance;
	
	public SinglePlantFeature(Block plant, int radius) 
	{
		this(plant, radius, true, 1);
	}
	
	public SinglePlantFeature(Block plant, int radius, int chance) 
	{
		this(plant, radius, true, chance);
	}
	
	public SinglePlantFeature(Block plant, int radius, boolean rawHeightmap) 
	{
		this(plant, radius, rawHeightmap, 1);
	}
	
	public SinglePlantFeature(Block plant, int radius, boolean rawHeightmap, int chance) 
	{
		super(radius);
		this.plant = plant;
		this.rawHeightmap = rawHeightmap;
		this.chance = chance;
	}
	
	public int getChance() 
	{
		return chance;
	}
	
	@Override
	protected BlockPos getCenterGround(World world, BlockPos pos)
	{
		return rawHeightmap ? FeatureHelper.getPosOnSurfaceWG(world, pos) : FeatureHelper.getPosOnSurface(world, pos);
	}
	
	@Override
	public boolean canGenerate(World world, Random random, BlockPos center, BlockPos blockPos, float radius)
	{
		return plant.canPlaceBlockAt(world, blockPos);
	}

	@Override
	public void generateInner(World world, Random random, BlockPos blockPos)
	{
		if (plant instanceof DoublePlantBlock) 
		{
			int rot = random.nextInt(4);
			IBlockState state = plant.getDefaultState().withProperty(DoublePlantBlock.ROTATION, rot);
			BlockHelper.setWithUpdate(world, blockPos, state);
			world.scheduleUpdate(blockPos, state.getBlock(), 1);
			BlockHelper.setWithUpdate(world, blockPos.up(), state.withProperty(DoublePlantBlock.TOP, true));
			world.scheduleUpdate(blockPos.up(), state.getBlock(), 1);
		}
		else if (plant instanceof EndCropBlock) 
		{
			IBlockState state = plant.getDefaultState().withProperty(EndCropBlock.AGE, 3);
			BlockHelper.setWithUpdate(world, blockPos, state);
		}
		else if (plant instanceof PlantBlockWithAge) 
		{
			int age = random.nextInt(4);
			IBlockState state = plant.getDefaultState().withProperty(PlantBlockWithAge.AGE, age);
			BlockHelper.setWithUpdate(world, blockPos, state);
		}
		else 
		{
			BlockHelper.setWithUpdate(world, blockPos, plant);
		}
	}

}
