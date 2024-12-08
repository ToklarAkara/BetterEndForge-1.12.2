package mod.beethoven92.betterendforge.common.world.feature.caves;

import com.google.common.collect.Sets;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndCaveBiome;
import mod.beethoven92.betterendforge.common.world.biome.ExtendedBiome;
import mod.beethoven92.betterendforge.common.world.feature.Mutable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

public class CaveChunkPopulatorFeature extends WorldGenerator
{
	private Supplier<BetterEndCaveBiome> supplier;

	


	public boolean generate(World world,Random random, BlockPos pos)
	{

		Set<BlockPos> floorPositions = Sets.newHashSet();
		Set<BlockPos> ceilPositions = Sets.newHashSet();
		int sx = (pos.getX() >> 4) << 4;
		int sz = (pos.getZ() >> 4) << 4;
		BlockPos.MutableBlockPos min = new Mutable().setPos(pos);
		BlockPos.MutableBlockPos max = new Mutable().setPos(pos);
		fillSets(sx, sz, world.getChunk(pos), floorPositions, ceilPositions, min, max);
		BetterEndCaveBiome biome = supplier.get();
		IBlockState surfaceBlock = (biome.getBiome() instanceof ExtendedBiome)?((ExtendedBiome)biome.getBiome()).getSurface().config.getTop() :biome.getBiome().topBlock;
		placeFloor(world, biome, floorPositions, random, surfaceBlock);
		placeCeil(world, biome, ceilPositions, random);
		BlockHelper.fixBlocks(world, min, max);
		return true;
	}
	
	protected void fillSets(int sx, int sz, Chunk chunk, Set<BlockPos> floorPositions, Set<BlockPos> ceilPositions, BlockPos.MutableBlockPos min, BlockPos.MutableBlockPos max) {
		Mutable mut = new Mutable();
		Mutable mut2 = new Mutable();
		Mutable mut3 = new Mutable();
		for (int x = 0; x < 16; x++) {
			mut.setX(x);
			mut2.setX(x);
			for (int z = 0; z < 16; z++) {
				mut.setZ(z);
				mut2.setZ(z);
				mut2.setY(0);
				for (int y = 1; y < 256; y++) { //TODO CHECK
					mut.setY(y);
					IBlockState top = chunk.getBlockState(mut);
					IBlockState bottom = chunk.getBlockState(mut2);
					if (top.getBlock()==Blocks.AIR && (ModTags.GEN_TERRAIN.contains(bottom.getBlock()) || bottom.getBlock()==(Blocks.STONE))) {
						mut3.setPos(mut2).add(sx, 0, sz);
						floorPositions.add(mut3.toImmutable());
						updateMin(mut3, min);
						updateMax(mut3, max);
					}
					else if (bottom.getBlock()==Blocks.AIR && (ModTags.GEN_TERRAIN.contains(top.getBlock()) || top.getBlock()==(Blocks.STONE))) {
						mut3.setPos(mut).add(sx, 0, sz);
						ceilPositions.add(mut3.toImmutable());
						updateMin(mut3, min);
						updateMax(mut3, max);
					}
					mut2.setY(y);
				}
			}
		}
	}
	
	private void updateMin(BlockPos pos, BlockPos.MutableBlockPos min) {
		if (pos.getX() < min.getX()) {
			min.setPos(pos.getX(), min.getY(), min.getZ());
		}
		if (pos.getY() < min.getY()) {
			min.setY(pos.getY());
		}
		if (pos.getZ() < min.getZ()) {
			min.setPos(min.getX(), min.getY(), pos.getZ());
		}
	}
	
	private void updateMax(BlockPos pos, BlockPos.MutableBlockPos max) {
		if (pos.getX() > max.getX()) {
			max.setPos(pos.getX(), pos.getY(), pos.getZ());
		}
		if (pos.getY() > max.getY()) {
			max.setY(pos.getY());
		}
		if (pos.getZ() > max.getZ()) {
			max.setPos(pos.getX(), pos.getY(), pos.getZ());
		}
	}
	
	protected void placeFloor(World world, BetterEndCaveBiome biome, Set<BlockPos> floorPositions, Random random, IBlockState surfaceBlock) {
		float density = biome.getFloorDensity();
		floorPositions.forEach((pos) -> {
			BlockHelper.setWithoutUpdate(world, pos, surfaceBlock);
			if (density > 0 && random.nextFloat() <= density) {
				WorldGenerator feature = biome.getFloorFeature(random);
				if (feature != null) {
					feature.generate(world, random, pos.up());
				}
			}
		});
	}
	
	protected void placeCeil(World world, BetterEndCaveBiome biome, Set<BlockPos> ceilPositions, Random random) {
		float density = biome.getCeilDensity();
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
}
