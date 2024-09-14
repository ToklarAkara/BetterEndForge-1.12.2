package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class WallScatterFeature extends WorldGenerator
{
	private static final EnumFacing[] DIR = BlockHelper.makeHorizontal();
	private final int radius;
	
	public WallScatterFeature(int radius) 
	{
		this.radius = radius;
	}
	
	public abstract boolean canGenerate(World world, Random random, BlockPos pos, EnumFacing dir);
	
	public abstract void generate(World world, Random random, BlockPos pos, EnumFacing dir);

	@Override
	public boolean generate(World world, Random random, BlockPos center) {
		int maxY = world.getHeight(center.getX(), center.getZ());
		int minY = BlockHelper.upRay(world, new BlockPos(center.getX(), 0, center.getZ()), maxY);
		if (maxY < 10 || maxY < minY) 
		{
			return false;
		}
		int py = ModMathHelper.randRange(minY, maxY, random);
		
		Mutable mut = new Mutable();
		for (int x = -radius; x <= radius; x++) 
		{
			mut.setX(center.getX() + x);
			for (int y = -radius; y <= radius; y++) 
			{
				mut.setY(py + y);
				for (int z = -radius; z <= radius; z++) 
				{
					mut.setZ(center.getZ() + z);
					if (random.nextInt(4) == 0 && world.isAirBlock(mut)) 
					{
						shuffle(random);
						for (EnumFacing dir: DIR)
						{
							if (canGenerate(world, random, mut, dir)) 
							{
								generate(world, random, mut, dir);
								break;
							}
						}
					}
				}
			}
		}
		
		return true;
	}
	
	private void shuffle(Random random) 
	{
		for (int i = 0; i < 4; i++) 
		{
			int j = random.nextInt(4);
			EnumFacing d = DIR[i];
			DIR[i] = DIR[j];
			DIR[j] = d;
		}
	}
}
