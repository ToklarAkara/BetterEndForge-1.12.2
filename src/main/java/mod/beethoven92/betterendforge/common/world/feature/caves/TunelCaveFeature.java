package mod.beethoven92.betterendforge.common.world.feature.caves;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.AdvMathHelper;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndCaveBiome;
import mod.beethoven92.betterendforge.common.world.biome.ExtendedBiome;
import mod.beethoven92.betterendforge.common.world.feature.Mutable;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenerator;


import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

public class TunelCaveFeature extends EndCaveFeature {

	private Set<BlockPos> generate(World world, BlockPos center, Random random) {
		int cx = center.getX() >> 4;
		int cz = center.getZ() >> 4;
		if ((long) cx * (long) cx + (long) cz + (long) cz < 256) {
			return Sets.newHashSet();
		}

		int x1 = cx << 4;
		int z1 = cz << 4;
		int x2 = x1 + 16;
		int z2 = z1 + 16;

		Random rand = new Random(world.getSeed());
		OpenSimplexNoise noiseH = new OpenSimplexNoise(rand.nextInt());
		OpenSimplexNoise noiseV = new OpenSimplexNoise(rand.nextInt());
		OpenSimplexNoise noiseD = new OpenSimplexNoise(rand.nextInt());

		Set<BlockPos> positions = Sets.newConcurrentHashSet();

		float a = hasCaves(world, new BlockPos(x1, 0, z1)) ? 1F : 0F;
		float b = hasCaves(world, new BlockPos(x2, 0, z1)) ? 1F : 0F;
		float c = hasCaves(world, new BlockPos(x1, 0, z2)) ? 1F : 0F;
		float d = hasCaves(world, new BlockPos(x2, 0, z2)) ? 1F : 0F;

		Chunk chunk = world.getChunk(cx, cz);
		IntStream.range(0, 256).forEach(index -> {
			Mutable pos = new Mutable();
			int x = index & 15;
			int z = index >> 4;
			int wheight = chunk.getHeightValue(x, z);
			float dx = x / 16F;
			float dz = z / 16F;
			pos.setX(x + x1);
			pos.setZ(z + z1);
			float da = AdvMathHelper.lerp(dx, a, b);
			float db = AdvMathHelper.lerp(dx, c, d);
			float density = 1 - AdvMathHelper.lerp(dz, da, db);
			if (density < 0.5) {
				for (int y = 0; y < wheight; y++) {
					pos.setY(y);
					float gradient = 1 - MathHelper.clamp((wheight - y) * 0.1F, 0F, 1F);
					if (gradient > 0.5) {
						break;
					}
					float val = MathHelper.abs((float) noiseH.eval(pos.getX() * 0.02, y * 0.01, pos.getZ() * 0.02));
					float vert = MathHelper.sin((y + (float) noiseV.eval(
						pos.getX() * 0.01,
						pos.getZ() * 0.01
					) * 20) * 0.1F) * 0.9F;
					float dist = (float) noiseD.eval(pos.getX() * 0.1, y * 0.1, pos.getZ() * 0.1) * 0.12F;
					val = (val + vert * vert + dist) + density + gradient;
					if (val < 0.15 && ModTags.GEN_TERRAIN.contains(world.getBlockState(pos).getBlock()) && noWaterNear(world, pos)) {
						positions.add(pos.toImmutable());
					}
				}
			}
		});
		positions.forEach(bpos -> BlockHelper.setWithoutUpdate(world, bpos, Blocks.AIR));

		return positions;
	}

	private boolean noWaterNear(World world, BlockPos pos) {
		BlockPos above1 = pos.up();
		BlockPos above2 = pos.up(2);
		if (!noWater(world, above1) || !noWater(world, above2)) {
			return false;
		}
		for (EnumFacing dir : BlockHelper.HORIZONTAL_DIRECTIONS) {
			if (!noWater(world, above1.offset(dir))) {
				return false;
			}
			if (!noWater(world, above2.offset(dir))) {
				return false;
			}
		}
		return true;
	}

	boolean noWater(World world, BlockPos pos){
		Block block = world.getBlockState(pos).getBlock();
		return block!=Blocks.WATER && block!=Blocks.FLOWING_WATER;
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		if (pos.getX() * pos.getX() + pos.getZ() * pos.getZ() <= 2500) {
			return false;
		}

		if (biomeMissingCaves(world, pos)) {
			return false;
		}

		Set<BlockPos> caveBlocks = generate(world, pos, random);
		if (caveBlocks.isEmpty()) {
			return false;
		}

		Map<BetterEndCaveBiome, Set<BlockPos>> floorSets = Maps.newHashMap();
		Map<BetterEndCaveBiome, Set<BlockPos>> ceilSets = Maps.newHashMap();
		Mutable mut = new Mutable();
		Set<BlockPos> remove = Sets.newHashSet();
		caveBlocks.forEach((bpos) -> {
			mut.setPos(bpos);
			BetterEndCaveBiome bio = ModBiomes.getCaveBiome(random);
			int height = world.getHeight(bpos.getX(), bpos.getZ());
			if (mut.getY() >= height) {
				remove.add(bpos);
			}
			else if (world.getBlockState(mut).getMaterial().isReplaceable()) {
				mut.setY(bpos.getY() - 1);
				if (ModTags.GEN_TERRAIN.contains(world.getBlockState(mut).getBlock())) {
					Set<BlockPos> floorPositions = floorSets.get(bio);
					if (floorPositions == null) {
						floorPositions = Sets.newHashSet();
						floorSets.put(bio, floorPositions);
					}
					floorPositions.add(mut.toImmutable());
				}
				mut.setY(bpos.getY() + 1);
				if (ModTags.GEN_TERRAIN.contains(world.getBlockState(mut).getBlock())) {
					Set<BlockPos> ceilPositions = ceilSets.get(bio);
					if (ceilPositions == null) {
						ceilPositions = Sets.newHashSet();
						ceilSets.put(bio, ceilPositions);
					}
					ceilPositions.add(mut.toImmutable());
				}
				setBiome(world, bpos, bio);
			}
		});
		caveBlocks.removeAll(remove);

		if (caveBlocks.isEmpty()) {
			return true;
		}

		floorSets.forEach((biome, floorPositions) -> {
			IBlockState surfaceBlock = biome.getBiome().topBlock.getBlock()==Blocks.GRASS?Blocks.END_STONE.getDefaultState():biome.getBiome().topBlock;
			if(biome.getBiome() instanceof ExtendedBiome && ((ExtendedBiome)biome.getBiome()).getSurface()!=null){
				surfaceBlock = ((ExtendedBiome)biome.getBiome()).getSurface().config.getTop();
			}
			placeFloor(world, biome, floorPositions, random, surfaceBlock);
		});
		ceilSets.forEach((biome, ceilPositions) -> {
			placeCeil(world, biome, ceilPositions, random);
		});
		BetterEndCaveBiome biome = ModBiomes.getCaveBiome(random);
		placeWalls(world, biome, caveBlocks, random);
		fixBlocks(world, caveBlocks);

		return true;
	}
	@Override
	protected Set<BlockPos> generateCaveBlocks(World world, BlockPos center, int radius, Random random) {
		return null;
	}


	@Override
	protected void placeFloor(World world, BetterEndCaveBiome biome, Set<BlockPos> floorPositions, Random random, IBlockState surfaceBlock) {
		float density = biome.getFloorDensity() * 0.2F;
		floorPositions.forEach((pos) -> {
			if (surfaceBlock.getBlock()!=(Blocks.END_STONE)) {
				BlockHelper.setWithoutUpdate(world, pos, surfaceBlock);
			}
			if (density > 0 && random.nextFloat() <= density) {
				WorldGenerator feature = biome.getFloorFeature(random);
				if (feature != null) {
					feature.generate(world, random, pos.up());
				}
			}
		});
	}

	@Override
	protected void placeCeil(World world, BetterEndCaveBiome biome, Set<BlockPos> ceilPositions, Random random) {
		float density = biome.getCeilDensity() * 0.2F;
		ceilPositions.forEach((pos) -> {
			IBlockState ceilBlock = biome.getCeil(pos);
			if (ceilBlock != null) {
				BlockHelper.setWithoutUpdate(world, pos, ceilBlock);
			}
			if (density > 0 && random.nextFloat() <= density) {
				WorldGenerator feature = biome.getCeilFeature(random);
				if (feature != null) {
					feature.generate(world, random, pos.down());
				}
			}
		});
	}

	protected boolean hasCaves(World world, BlockPos pos) {
		return hasCavesInBiome(world, pos.add(-8, 0, -8)) &&
				hasCavesInBiome(world, pos.add(8, 0, -8)) &&
				hasCavesInBiome(world, pos.add(-8, 0, 8)) &&
				hasCavesInBiome(world, pos.add(8, 0, 8));
	}

	protected boolean hasCavesInBiome(World world, BlockPos pos) {
		Biome biome = world.getBiome(pos);
		BetterEndBiome endBiome = ModBiomes.getFromBiome(biome);
		return endBiome.hasCaves();
	}
}


