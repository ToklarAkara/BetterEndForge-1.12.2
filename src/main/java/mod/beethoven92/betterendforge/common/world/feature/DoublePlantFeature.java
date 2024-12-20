package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.DoublePlantBlock;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DoublePlantFeature extends ScatterFeature
{
	private final Block smallPlant;
	private final Block largePlant;
	private Block plant;

	public DoublePlantFeature(Block smallPlant, Block largePlant, int radius)
	{
		super(radius);
		this.smallPlant = smallPlant;
		this.largePlant = largePlant;
	}

	public boolean canGenerate(World world, Random random, BlockPos center, BlockPos blockPos, float radius) {
		float d = ModMathHelper.length(center.getX() - blockPos.getX(), center.getZ() - blockPos.getZ()) / radius * 0.6F + random.nextFloat() * 0.4F;
		plant = d < 0.5F ? largePlant : smallPlant;
		return plant.canPlaceBlockAt(world, blockPos);
	}

	public void generateInner(World world, Random random, BlockPos blockPos) {
		if (plant instanceof DoublePlantBlock) {
			int rot = random.nextInt(4);
			IBlockState state = plant.getDefaultState().withProperty(DoublePlantBlock.ROTATION, rot);
			BlockHelper.setWithoutUpdate(world, blockPos, state);
			world.scheduleUpdate(blockPos, state.getBlock(), 1);
			BlockHelper.setWithoutUpdate(world, blockPos.up(), state.withProperty(DoublePlantBlock.TOP, true));
			world.scheduleUpdate(blockPos.up(), state.getBlock(), 1);
		} else {
			BlockHelper.setWithoutUpdate(world, blockPos, plant.getDefaultState());
		}
	}
}
