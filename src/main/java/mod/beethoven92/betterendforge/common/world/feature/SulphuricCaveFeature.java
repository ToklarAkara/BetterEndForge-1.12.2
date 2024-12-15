package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;
import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BrimstoneBlock;
import mod.beethoven92.betterendforge.common.block.SulphurCrystalBlock;
import mod.beethoven92.betterendforge.common.block.template.UnderwaterWallPlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class SulphuricCaveFeature extends WorldGenerator
{
	private static final IBlockState CAVE_AIR = Blocks.AIR.getDefaultState();
	private static final IBlockState WATER = Blocks.WATER.getDefaultState();
	private static final EnumFacing[] HORIZONTAL = BlockHelper.makeHorizontal();

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) 
	{
		int radius = ModMathHelper.randRange(10, 30, rand);
		
		int top = world.getHeight(pos.getX(), pos.getZ());
		Mutable bpos = new Mutable();
		bpos.setX(pos.getX());
		bpos.setZ(pos.getZ());
		bpos.setY(top - 1);
		
		IBlockState state = world.getBlockState(bpos);
		while (!ModTags.GEN_TERRAIN.contains(state.getBlock()) && bpos.getY() > 5)
		{
			bpos.setY(bpos.getY() - 1);
			state = world.getBlockState(bpos);
		}
		if (bpos.getY() < 10)
		{
			return false;
		}
		top = (int) (bpos.getY() - (radius * 1.3F + 5));
		
		while (ModTags.GEN_TERRAIN.contains(state.getBlock()) || (state.getBlock()==Blocks.WATER || state.getBlock()==Blocks.FLOWING_WATER) && bpos.getY() > 5)
		{
			bpos.setY(bpos.getY() - 1);
			state = world.getBlockState(bpos);
		}
		int bottom = (int) (bpos.getY() + radius * 1.3F + 5);
		
		if (top <= bottom) 
		{
			return false;
		}
		
		Mutable mut = new Mutable();
		pos = new BlockPos(pos.getX(), ModMathHelper.randRange(bottom, top, rand), pos.getZ());
		
		OpenSimplexNoise noise = new OpenSimplexNoise(ModMathHelper.getSeed(534, pos.getX(), pos.getZ()));
		
		int x1 = pos.getX() - radius - 5;
		int z1 = pos.getZ() - radius - 5;
		int x2 = pos.getX() + radius + 5;
		int z2 = pos.getZ() + radius + 5;
		int y1 = ModMathHelper.floor(pos.getY() - (radius + 5) / 1.6);
		int y2 = ModMathHelper.floor(pos.getY() + (radius + 5) / 1.6);
		
		double hr = radius * 0.75;
		double nr = radius * 0.25;
		
		Set<BlockPos> brimstone = Sets.newHashSet();
		IBlockState rock = ModBlocks.SULPHURIC_ROCK.stone.getDefaultState();
		int waterLevel = pos.getY() + ModMathHelper.randRange(ModMathHelper.floor(radius * 0.8), radius, rand);
		for (int x = x1; x <= x2; x++)
		{
			int xsq = x - pos.getX();
			xsq *= xsq;
			mut.setX(x);
			for (int z = z1; z <= z2; z++) 
			{
				int zsq = z - pos.getZ();
				zsq *= zsq;
				mut.setZ(z);
				for (int y = y1; y <= y2; y++)
				{
					int ysq = y - pos.getY();
					ysq *= 1.6;
					ysq *= ysq;
					mut.setY(y);
					double r = noise.eval(x * 0.1, y * 0.1, z * 0.1) * nr + hr;
					double r2 = r + 5;
					double dist = xsq + ysq + zsq;
					if (dist < r * r) 
					{
						state = world.getBlockState(mut);
						if (isReplaceable(state)) 
						{
							BlockHelper.setWithoutUpdate(world, mut, y < waterLevel ? WATER : CAVE_AIR);
						}
					}
					else if (dist < r2 * r2) 
					{
						state = world.getBlockState(mut);
						if (ModTags.GEN_TERRAIN.contains(state.getBlock()) || state.getBlock()==(Blocks.AIR))
						{
							double v = noise.eval(x * 0.1, y * 0.1, z * 0.1) + noise.eval(x * 0.03, y * 0.03, z * 0.03) * 0.5;
							if (v > 0.4) 
							{
								brimstone.add(mut.toImmutable());
							}
							else 
							{
								BlockHelper.setWithoutUpdate(world, mut, rock);
							}
						}
					}
				}
			}
		}
		brimstone.forEach((blockPos) -> {
			placeBrimstone(world, blockPos, rand);
		});
		
		if (rand.nextInt(4) == 0) 
		{
			int count = ModMathHelper.randRange(5, 20, rand);
			for (int i = 0; i < count; i++) 
			{
				mut.setPos(pos).add(ModMathHelper.floor(rand.nextGaussian() * 2 + 0.5), 0, ModMathHelper.floor(rand.nextGaussian() * 2 + 0.5));
				int dist = ModMathHelper.floor(3 - ModMathHelper.length(mut.getX() - pos.getX(), mut.getZ() - pos.getZ())) + rand.nextInt(2);
				if (dist > 0)
				{
					state = world.getBlockState(mut);
//					while (!state.getFluidState().isEmpty() || state.getMaterial().equals(Material.OCEAN_PLANT))  TODO CHECK
//					{
//						mut.setY(mut.getY() - 1);
//						state = world.getBlockState(mut);
//					}
					if (ModTags.GEN_TERRAIN.contains(state.getBlock()) && world.getBlockState(mut.up()).getBlock()!=(ModBlocks.HYDROTHERMAL_VENT))
					{
						for (int j = 0; j <= dist; j++) 
						{
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.SULPHURIC_ROCK.stone);
							ModMathHelper.shuffle(HORIZONTAL, rand);
							for (EnumFacing dir: HORIZONTAL)
							{
								BlockPos p = mut.offset(dir);
								if (rand.nextBoolean() && world.getBlockState(p).getBlock()==(Blocks.WATER))
								{
									BlockHelper.setWithoutUpdate(world, p, ModBlocks.TUBE_WORM.getDefaultState().withProperty(UnderwaterWallPlantBlock.FACING, dir));
								}
							}
							mut.setY(mut.getY() + 1);
						}
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.HYDROTHERMAL_VENT);
						mut.setY(mut.getY() + 1);
						state = world.getBlockState(mut);
						while (state.getBlock()==(Blocks.WATER))
						{
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.VENT_BUBBLE_COLUMN.getDefaultState());
							world.scheduleUpdate(mut, ModBlocks.VENT_BUBBLE_COLUMN, ModMathHelper.randRange(8, 32, rand));
							mut.setY(mut.getY() + 1);
							state = world.getBlockState(mut);
						}
					}
				}
			}
		}
		
		BlockHelper.fixBlocks(world, new BlockPos(x1, y1, z1), new BlockPos(x2, y2, z2));
		
		return true;
	}

	private boolean isReplaceable(IBlockState state)
	{
		return ModTags.GEN_TERRAIN.contains(state.getBlock())
				|| state.getBlock()==(ModBlocks.HYDROTHERMAL_VENT)
				|| state.getBlock()==(ModBlocks.VENT_BUBBLE_COLUMN)
				|| state.getBlock()==(ModBlocks.SULPHUR_CRYSTAL)
				|| state.getMaterial().isReplaceable()
				|| state.getMaterial().equals(Material.PLANTS)
				//|| state.getMaterial().equals(Material.OCEAN_PLANT)
				|| state.getMaterial().equals(Material.LEAVES);
	}
	
	private void placeBrimstone(World world, BlockPos pos, Random random)
	{
		IBlockState state = getBrimstone(world, pos);
		BlockHelper.setWithoutUpdate(world, pos, state);
		if (state.getValue(BrimstoneBlock.ACTIVATED))
		{
			makeShards(world, pos, random);
		}
	}
	
	private IBlockState getBrimstone(World world, BlockPos pos)
	{
		for (EnumFacing dir: BlockHelper.DIRECTIONS)
		{
			if (world.getBlockState(pos.offset(dir)).getBlock()==(Blocks.WATER))
			{
				return ModBlocks.BRIMSTONE.getDefaultState().withProperty(BrimstoneBlock.ACTIVATED, true);
			}
		}
		return ModBlocks.BRIMSTONE.getDefaultState();
	}
	
	private void makeShards(World world, BlockPos pos, Random random)
	{
		for (EnumFacing dir: BlockHelper.DIRECTIONS)
		{
			BlockPos side;
			if (random.nextInt(16) == 0 && world.getBlockState((side = pos.offset(dir))).getBlock()==(Blocks.WATER))
			{
				IBlockState state = ModBlocks.SULPHUR_CRYSTAL.getDefaultState()
						//.withProperty(SulphurCrystalBlock.WATERLOGGED, true)
						.withProperty(SulphurCrystalBlock.FACING, dir)
						.withProperty(SulphurCrystalBlock.AGE, random.nextInt(3));
				BlockHelper.setWithoutUpdate(world, side, state);
			}
		}
	}
}
