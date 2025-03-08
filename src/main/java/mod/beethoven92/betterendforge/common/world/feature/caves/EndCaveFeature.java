package mod.beethoven92.betterendforge.common.world.feature.caves;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.interfaces.IBiomeArray;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndCaveBiome;
import mod.beethoven92.betterendforge.common.world.biome.ExtendedBiome;
import mod.beethoven92.betterendforge.common.world.feature.Mutable;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class EndCaveFeature extends WorldGenerator
{
	protected static final IBlockState END_STONE = Blocks.END_STONE.getDefaultState();
	protected static final IBlockState WATER = Blocks.WATER.getDefaultState();
	private static final Vec3i[] SPHERE;


	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		/*if (!(CommonConfig.isNewGeneratorEnabled() && GeneratorOptions.noRingVoid()) || pos.getX() * pos.getX() + pos.getZ() * pos.getZ() <= 22500)
		{
			return false;
		}*/
		if (!(GeneratorOptions.useNewGenerator() && GeneratorOptions.noRingVoid()))
		{
			if (pos.getX() * pos.getX() + pos.getZ() * pos.getZ() <= 22500)
			{
				return false;
			}
		}

		if (biomeMissingCaves(world, pos))
		{
			return false;
		}

		int radius = ModMathHelper.randRange(10, 30, rand);
		BlockPos center = findPos(world, pos, radius, rand);

		if (center == null)
		{
			return false;
		}

		BetterEndCaveBiome biome = ModBiomes.getCaveBiome(rand);
		Set<BlockPos> caveBlocks = generateCaveBlocks(world, center, radius, rand);
		if (!caveBlocks.isEmpty())
		{
			if (biome != null)
			{
				setBiomes(world, biome, caveBlocks);
				Set<BlockPos> floorPositions = Sets.newHashSet();
				Set<BlockPos> ceilPositions = Sets.newHashSet();
				Mutable mut = new Mutable();
				caveBlocks.forEach((bpos) -> {
					mut.setPos(bpos);
					if (world.getBlockState(mut).getMaterial().isReplaceable())
					{
						mut.setY(bpos.getY() - 1);
						if (ModTags.GEN_TERRAIN.contains(world.getBlockState(mut).getBlock()))
						{
							floorPositions.add(mut.toImmutable());
						}
						mut.setY(bpos.getY() + 1);
						if (ModTags.GEN_TERRAIN.contains(world.getBlockState(mut).getBlock()))
						{
							ceilPositions.add(mut.toImmutable());
						}
					}
				});
				IBlockState surfaceBlock = (biome.getBiome() instanceof ExtendedBiome)?((ExtendedBiome)biome.getBiome()).getSurface().config.getTop() :biome.getBiome().topBlock;
				if(surfaceBlock.getBlock()==Blocks.GRASS || surfaceBlock.getBlock()==Blocks.DIRT){
					surfaceBlock = Blocks.END_STONE.getDefaultState();
				}
				placeFloor(world, biome, floorPositions, rand, surfaceBlock);
				placeCeil(world, biome, ceilPositions, rand);
				placeWalls(world, biome, caveBlocks, rand);
			}
			fixBlocks(world, caveBlocks);
		}

		return true;
	}

	protected abstract Set<BlockPos> generateCaveBlocks(World world, BlockPos center, int radius, Random random);

	protected void placeFloor(World world, BetterEndCaveBiome biome, Set<BlockPos> floorPositions,
			Random random, IBlockState surfaceBlock)
	{
		float density = biome.getFloorDensity();
		floorPositions.forEach((pos) -> {
			BlockHelper.setWithoutUpdate(world, pos, surfaceBlock);
			if (density > 0 && random.nextFloat() <= density) 
			{
				WorldGenerator feature = biome.getFloorFeature(random);
				if (feature != null) 
				{
					feature.generate(world, random, pos.up());
				}
			}
		});
	}
	
	protected void placeCeil(World world, BetterEndCaveBiome biome, Set<BlockPos> ceilPositions, Random random)
	{
		float density = biome.getCeilDensity();
		ceilPositions.forEach((pos) -> {
			IBlockState ceilBlock = biome.getCeil(pos);
			if (ceilBlock != null) {
				BlockHelper.setWithoutUpdate(world, pos, ceilBlock);
			}
			if (density > 0 && random.nextFloat() <= density) 
			{
				WorldGenerator feature = biome.getCeilFeature(random);
				if (feature != null) 
				{
					feature.generate(world, random, pos.down());
				}
			}
		});
	}


	protected void placeWalls(World world, BetterEndCaveBiome biome, Set<BlockPos> positions, Random random) {
		Set<BlockPos> placed = Sets.newHashSet();
		positions.forEach(pos -> {
			if (random.nextInt(4) == 0 && hasOpenSide(pos, positions)) {
				IBlockState wallBlock = biome.getWall(pos);
				if (wallBlock != null) {
					for (Vec3i offset : SPHERE) {
						BlockPos wallPos = pos.add(offset);
						if (!positions.contains(wallPos) && !placed.contains(wallPos) && ModTags.GEN_TERRAIN.contains(world.getBlockState(wallPos).getBlock())) {
							wallBlock = biome.getWall(wallPos);
							BlockHelper.setWithoutUpdate(world, wallPos, wallBlock);
							placed.add(wallPos);
						}
					}
				}
			}
		});
	}



	private boolean hasOpenSide(BlockPos pos, Set<BlockPos> positions) {
		for (EnumFacing dir : BlockHelper.DIRECTIONS) {
			if (!positions.contains(pos.offset(dir))) {
				return true;
			}
		}
		return false;
	}

	
	protected void setBiomes(World world, BetterEndCaveBiome biome, Set<BlockPos> blocks)
	{
		blocks.forEach((pos) -> setBiome(world, pos, biome));
	}
	
	public void setBiome(World world, BlockPos pos, BetterEndCaveBiome biome)
	{
//		IBiomeArray array = (IBiomeArray) world.getChunk(pos).getBiomeArray().getBiomes();
//		if (array != null)
//		{
//			array.setBiome(biome.getActualBiome(), pos);
//		} TODO BIOMES
	}
	
	private BlockPos findPos(World world, BlockPos pos, int radius, Random random)
	{
		int top = world.getHeight(pos.getX(), pos.getZ());
		Mutable bpos = new Mutable();
		bpos.setX(pos.getX());
		bpos.setZ(pos.getZ());
		bpos.setY(top - 1);
		
		IBlockState state = world.getBlockState(bpos);
		while (!ModTags.GEN_TERRAIN.contains(state) && bpos.getY() > 5)
		{
			bpos.setY(bpos.getY() - 1);
			state = world.getBlockState(bpos);
		}
		if (bpos.getY() < 10) 
		{
			return null;
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
			return null;
		}
		
		return new BlockPos(pos.getX(), ModMathHelper.randRange(bottom, top, random), pos.getZ());
	}
	
	protected void fixBlocks(World world, Set<BlockPos> caveBlocks)
	{
		BlockPos pos = caveBlocks.iterator().next();
		BlockPos.MutableBlockPos start = new Mutable().setPos(pos);
		BlockPos.MutableBlockPos end = new Mutable().setPos(pos);
		caveBlocks.forEach((bpos) -> {
			if (bpos.getX() < start.getX())
			{
				start.setPos(bpos.getX(), start.getY(), start.getZ());
			}
			if (bpos.getX() > end.getX())
			{
				end.setPos(bpos.getX(), start.getY(), start.getZ());
			}

			if (bpos.getY() < start.getY())
			{
				start.setY(bpos.getY());
			}
			if (bpos.getY() > end.getY())
			{
				end.setY(bpos.getY());
			}

			if (bpos.getZ() < start.getZ())
			{
				start.setPos(start.getX(), start.getY(), bpos.getZ());
			}
			if (bpos.getZ() > end.getZ())
			{
				end.setPos(start.getX(), start.getY(), bpos.getZ());
			}
		});
		//BlockHelper.fixBlocks(world, start.add(-5, -5, -5), end.add(5, 5, 5));
	}
	
	protected boolean isWaterNear(World world, BlockPos pos)
	{
		for (EnumFacing dir: BlockHelper.DIRECTIONS)
		{
			if (world.getBlockState(pos.offset(dir, 5)).getBlock()==Blocks.WATER ||
					world.getBlockState(pos.offset(dir, 5)).getBlock()==Blocks.FLOWING_WATER)
			{
				return true;
			}
		}
		return false;
	}



	protected boolean biomeMissingCaves(World world, BlockPos pos)
	{
		for (int x = -2; x < 3; x++) 
		{
			for (int z = -2; z < 3; z++)
			{
				Biome biome = world.getBiome(pos.add(x << 4, 0, z << 4));
				BetterEndBiome endBiome = ModBiomes.getFromBiome(biome);
				if (!endBiome.hasCaves()) 
				{
					return true;
				}
			}
		}
		return false;
	}

	static {
		List<Vec3i> prePos = Lists.newArrayList();
		int radius = 5;
		int r2 = radius * radius;
		for (int x = -radius; x <= radius; x++) {
			int x2 = x * x;
			for (int y = -radius; y <= radius; y++) {
				int y2 = y * y;
				for (int z = -radius; z <= radius; z++) {
					int z2 = z * z;
					if (x2 + y2 + z2 < r2) {
						prePos.add(new Vec3i(x, y, z));
					}
				}
			}
		}
		SPHERE = prePos.toArray(new Vec3i[] {});
	}
}
