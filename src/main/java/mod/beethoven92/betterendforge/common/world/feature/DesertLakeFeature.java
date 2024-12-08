package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.biome.ExtendedBiome;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class DesertLakeFeature extends WorldGenerator {
	private static final IBlockState END_STONE = Blocks.END_STONE.getDefaultState();
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(15152);
	private static final BlockPos.MutableBlockPos POS = new BlockPos.MutableBlockPos();
	private final int radius;

	public DesertLakeFeature() {
		this.radius = 6;
	}

	public static BlockPos getPosOnSurfaceWG(World world, BlockPos pos) {
		return world.getHeight(pos);
	}

	public static BlockPos getPosOnSurfaceRaycast(World world, BlockPos pos) {
		return getPosOnSurfaceRaycast(world, pos, 256);
	}

	public static BlockPos getPosOnSurfaceRaycast(World world, BlockPos pos, int dist) {
		int h = BlockHelper.downRay(world, pos, dist);
		return pos.down(h);
	}

	@Override
	public boolean generate(World world, Random random, BlockPos blockPos) {
		double radius = ModMathHelper.randRange(8.0, 15.0, random);
		double depth = radius * 0.5 * ModMathHelper.randRange(0.8, 1.2, random);
		int dist = ModMathHelper.floor(radius);
		int dist2 = ModMathHelper.floor(radius * 1.5);
		int bott = ModMathHelper.floor(depth);
		blockPos = getPosOnSurfaceWG(world, blockPos);

		if (blockPos.getY() < 10)
			return false;

		int waterLevel = blockPos.getY();

		BlockPos pos = getPosOnSurfaceRaycast(world, blockPos.north(dist).up(10), 20);
		if (Math.abs(blockPos.getY() - pos.getY()) > 5)
			return false;
		waterLevel = ModMathHelper.min(pos.getY(), waterLevel);

		pos = getPosOnSurfaceRaycast(world, blockPos.south(dist).up(10), 20);
		if (Math.abs(blockPos.getY() - pos.getY()) > 5)
			return false;
		waterLevel = ModMathHelper.min(pos.getY(), waterLevel);

		pos = getPosOnSurfaceRaycast(world, blockPos.east(dist).up(10), 20);
		if (Math.abs(blockPos.getY() - pos.getY()) > 5)
			return false;
		waterLevel = ModMathHelper.min(pos.getY(), waterLevel);

		pos = getPosOnSurfaceRaycast(world, blockPos.west(dist).up(10), 20);
		if (Math.abs(blockPos.getY() - pos.getY()) > 5)
			return false;
		waterLevel = ModMathHelper.min(pos.getY(), waterLevel);
		IBlockState state;

		int minX = blockPos.getX() - dist2;
		int maxX = blockPos.getX() + dist2;
		int minZ = blockPos.getZ() - dist2;
		int maxZ = blockPos.getZ() + dist2;
		int maskMinX = minX - 1;
		int maskMinZ = minZ - 1;

		boolean[][] mask = new boolean[maxX - minX + 3][maxZ - minZ + 3];
		for (int x = minX; x <= maxX; x++) {
			POS.setPos(x, POS.getY(), pos.getZ());
			int mx = x - maskMinX;
			for (int z = minZ; z <= maxZ; z++) {
				POS.setPos(POS.getX(), pos.getY(), z);
				int mz = z - maskMinZ;
				if (!mask[mx][mz]) {
					for (int y = waterLevel + 1; y <= waterLevel + 20; y++) {
						POS.setY(y);
						IBlockState fluid = world.getBlockState(POS);
						if ((fluid.getBlock()==Blocks.WATER || fluid.getBlock()==Blocks.FLOWING_WATER)) {
							for (int i = -1; i < 2; i++) {
								int px = mx + i;
								for (int j = -1; j < 2; j++) {
									int pz = mz + j;
									mask[px][pz] = true;
								}
							}
							break;
						}
					}
				}
			}
		}

		for (int x = minX; x <= maxX; x++) {
			POS.setPos(x, POS.getY(), pos.getZ());
			int x2 = x - blockPos.getX();
			x2 *= x2;
			int mx = x - maskMinX;
			for (int z = minZ; z <= maxZ; z++) {
				POS.setPos(POS.getX(), pos.getY(), z);
				int z2 = z - blockPos.getZ();
				z2 *= z2;
				int mz = z - maskMinZ;
				if (!mask[mx][mz]) {
					double size = 1;
					for (int y = blockPos.getY(); y <= blockPos.getY() + 20; y++) {
						POS.setY(y);
						double add = y - blockPos.getY();
						if (add > 5) {
							size *= 0.8;
							add = 5;
						}
						double r = (add * 1.8 + radius * (NOISE.eval(x * 0.2, y * 0.2, z * 0.2) * 0.25 + 0.75))
								- 1.0 / size;
						if (r > 0) {
							r *= r;
							if (x2 + z2 <= r) {
								state = world.getBlockState(POS);
								if (ModTags.GEN_TERRAIN.contains(state.getBlock())) {
									BlockHelper.setWithoutUpdate(world, POS, Blocks.AIR.getDefaultState());
								}
								pos = POS.down();
								if (ModTags.GEN_TERRAIN.contains(world.getBlockState(pos).getBlock())) {
									state = (world.getBiome(pos) instanceof ExtendedBiome)?((ExtendedBiome)world.getBiome(pos)).getSurface().config.getTop() :world.getBiome(pos).topBlock;
									if (y > waterLevel + 1)
										BlockHelper.setWithoutUpdate(world, pos, state);
									else if (y > waterLevel)
										BlockHelper.setWithoutUpdate(world, pos, random.nextBoolean() ? state
												: ModBlocks.ENDSTONE_DUST.getDefaultState());
									else
										BlockHelper.setWithoutUpdate(world, pos,
												ModBlocks.ENDSTONE_DUST.getDefaultState());
								}
							}
						} else {
							break;
						}
					}
				}
			}
		}

		double aspect = ((double) radius / (double) depth);

		for (int x = blockPos.getX() - dist; x <= blockPos.getX() + dist; x++) {
			POS.setPos(x, POS.getY(), pos.getZ());
			int x2 = x - blockPos.getX();
			x2 *= x2;
			int mx = x - maskMinX;
			for (int z = blockPos.getZ() - dist; z <= blockPos.getZ() + dist; z++) {
				POS.setPos(POS.getX(), pos.getY(), z);
				int z2 = z - blockPos.getZ();
				z2 *= z2;
				int mz = z - maskMinZ;
				if (!mask[mx][mz]) {
					for (int y = blockPos.getY() - bott; y < blockPos.getY(); y++) {
						POS.setY(y);
						double y2 = (double) (y - blockPos.getY()) * aspect;
						y2 *= y2;
						double r = radius * (NOISE.eval(x * 0.2, y * 0.2, z * 0.2) * 0.25 + 0.75);
						double rb = r * 1.2;
						r *= r;
						rb *= rb;
						if (y2 + x2 + z2 <= r) {
							state = world.getBlockState(POS);
							if (canReplace(state)) {
								state = world.getBlockState(POS.up());
								state = canReplace(state) ? (y < waterLevel ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState()) : state;
								BlockHelper.setWithoutUpdate(world, POS, state);
							}
							pos = POS.down();
							if (ModTags.GEN_TERRAIN.contains(world.getBlockState(pos).getBlock())) {
								BlockHelper.setWithoutUpdate(world, pos, ModBlocks.ENDSTONE_DUST.getDefaultState());
							}
							pos = POS.up();
							while (canReplace(state = world.getBlockState(pos)) && state.getBlock()!=Blocks.AIR
									&& !(state.getBlock()==Blocks.WATER || state.getBlock()==Blocks.FLOWING_WATER)) {
								BlockHelper.setWithoutUpdate(world, pos, pos.getY() < waterLevel ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState());
								pos = pos.up();
							}
						}
						// Make border
						else if (y2 + x2 + z2 <= rb) {
							state = world.getBlockState(POS);
							if (ModTags.GEN_TERRAIN.contains(state.getBlock()) && world.isAirBlock(POS.up())) {
								BlockHelper.setWithoutUpdate(world, POS, ModBlocks.END_MOSS);
							} else if (y < waterLevel) {
								if (world.isAirBlock(POS.up())) {
									state = (world.getBiome(POS) instanceof ExtendedBiome)?((ExtendedBiome)world.getBiome(POS)).getSurface().config.getTop() :world.getBiome(POS).topBlock;
									BlockHelper.setWithoutUpdate(world, POS,
											random.nextBoolean() ? state : ModBlocks.ENDSTONE_DUST.getDefaultState());
									BlockHelper.setWithoutUpdate(world, POS.down(), END_STONE);
								} else {
									BlockHelper.setWithoutUpdate(world, POS,
											ModBlocks.ENDSTONE_DUST.getDefaultState());
									BlockHelper.setWithoutUpdate(world, POS.down(), END_STONE);
								}
							}
						}
					}
				}
			}
		}

		BlockHelper.fixBlocks(world, new BlockPos(minX - 2, waterLevel - 2, minZ - 2),
				new BlockPos(maxX + 2, blockPos.getY() + 20, maxZ + 2));

		return true;
	}

	private boolean canReplace(IBlockState state) {
		return state.getMaterial().isReplaceable() || ModTags.GEN_TERRAIN.contains(state.getBlock()) || state.getBlock() == ModBlocks.ENDSTONE_DUST
				|| state.getMaterial().equals(Material.PLANTS) //|| state.getMaterial().equals(Material.OCEAN_PLANT)
				|| state.getMaterial().equals(Material.CORAL);
	}
}
