package mod.beethoven92.betterendforge.common.util;

import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import mod.beethoven92.betterendforge.common.block.BlueVineBlock;
import mod.beethoven92.betterendforge.common.block.StalactiteBlock;
import mod.beethoven92.betterendforge.common.block.template.DoublePlantBlock;
import mod.beethoven92.betterendforge.common.block.template.EndVineBlock;
import mod.beethoven92.betterendforge.common.block.template.FurBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class  BlockHelper {
	public static final PropertyBool ROOTS = PropertyBool.create("roots");

	public static final int FLAG_UPDATE_BLOCK = 1;
	public static final int FLAG_SEND_CLIENT_CHANGES = 2;
	public static final int FLAG_NO_RERENDER = 4;
	public static final int FORSE_RERENDER = 8;
	public static final int FLAG_IGNORE_OBSERVERS = 16;

	public static final int SET_SILENT = FLAG_UPDATE_BLOCK | FLAG_IGNORE_OBSERVERS | FLAG_SEND_CLIENT_CHANGES;
	public static final int SET_OBSERV = FLAG_UPDATE_BLOCK | FLAG_SEND_CLIENT_CHANGES;

	public static final EnumFacing[] HORIZONTAL_DIRECTIONS = makeHorizontal();
	public static final EnumFacing[] DIRECTIONS = EnumFacing.values();

	private static final MutableBlockPos POS = new MutableBlockPos();
	protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
	protected static final IBlockState WATER = Blocks.WATER.getDefaultState();

	public static int upRay(IBlockAccess world, BlockPos pos, int maxDist) {
		int length = 0;
		for (int j = 1; j < maxDist && (world.isAirBlock(pos.up(j))); j++)
			length++;
		return length;
	}

	public static int downRay(IBlockAccess world, BlockPos pos, int maxDist) {
		int length = 0;
		for (int j = 1; j < maxDist && (world.isAirBlock(pos.down(j))); j++)
			length++;
		return length;
	}

	public static int downRayRep(IBlockAccess world, BlockPos pos, int maxDist) {
		POS.setPos(pos);
		for (int j = 1; j < maxDist && (world.getBlockState(POS)).getMaterial().isReplaceable(); j++) {
			POS.setY(POS.getY() - 1);
		}
		return pos.getY() - POS.getY();
	}

	public static EnumFacing[] makeHorizontal() {
		return new EnumFacing[]{EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST};
	}

	public static EnumFacing randomHorizontal(Random random) {
		return HORIZONTAL_DIRECTIONS[random.nextInt(4)];
	}

	public static EnumFacing randomDirection(Random random) {
		return DIRECTIONS[random.nextInt(6)];
	}

	public static IBlockState rotateHorizontal(IBlockState state, Rotation rotation, PropertyEnum<EnumFacing> facing) {
		return state.withProperty(facing, rotation.rotate(state.getValue(facing)));
	}

	public static IBlockState mirrorHorizontal(IBlockState state, Mirror mirror, PropertyEnum<EnumFacing> facing) {
		return state.withProperty(facing, mirror.toRotation(state.getValue(facing)).rotate(state.getValue(facing)));
	}

	public static EnumFacing getRandomHorizontalDirection(Random rand) {
		return HORIZONTAL_DIRECTIONS[rand.nextInt(4)];
	}

	public static void setWithoutUpdate(World world, BlockPos pos, IBlockState state) {
		world.setBlockState(pos, state, SET_SILENT);
	}

	public static void setWithUpdate(World world, BlockPos pos, IBlockState state) {
		world.setBlockState(pos, state, SET_OBSERV);
	}

	public static void setWithUpdate(World world, BlockPos pos, Block block) {
		world.setBlockState(pos, block.getDefaultState(), SET_OBSERV);
	}

	public static void setWithoutUpdate(World world, BlockPos pos, Block block) {
		world.setBlockState(pos, block.getDefaultState(), SET_SILENT);
	}

	public static void fixBlocks(World world, BlockPos start, BlockPos end) {
		if(1==1)return;
		IBlockState state;
		Set<BlockPos> doubleCheck = Sets.newHashSet();
		for (int x = start.getX(); x <= end.getX(); x++) {
			POS.setPos(x, 0, 0);
			for (int z = start.getZ(); z <= end.getZ(); z++) {
				POS.setPos(x, 0, z);
				for (int y = start.getY(); y <= end.getY(); y++) {
					POS.setPos(x, y, z);
					state = world.getBlockState(POS);

					if (state.getBlock() instanceof FurBlock) {
						doubleCheck.add(POS.toImmutable());
					}
					// Liquids
					else if ((state.getBlock()==Blocks.WATER || state.getBlock()==Blocks.FLOWING_WATER)) {
						if (!state.getBlock().canPlaceBlockAt(world, POS)) {
							setWithoutUpdate(world, POS, WATER);
							POS.setY(POS.getY() - 1);
							state = world.getBlockState(POS);
							while (!state.getBlock().canPlaceBlockAt(world, POS)) {
								state = !(state.getBlock()==Blocks.WATER || state.getBlock()==Blocks.FLOWING_WATER) ? AIR : WATER;
								setWithoutUpdate(world, POS, state);
								POS.setY(POS.getY() - 1);
								state = world.getBlockState(POS);
							}
						}
						POS.setY(y - 1);
						if (world.isAirBlock(POS)) {
							POS.setY(y);
							while ((world.getBlockState(POS).getBlock()==Blocks.WATER || world.getBlockState(POS).getBlock()==Blocks.FLOWING_WATER)) {
								setWithoutUpdate(world, POS, AIR);
								POS.setY(POS.getY() + 1);
							}
							continue;
						}
						for (EnumFacing dir : HORIZONTAL_DIRECTIONS) {
							if (world.isAirBlock(POS.offset(dir))) {
								world.scheduleUpdate(POS, state.getBlock(), 0);
								break;
							}
						}
					} else if (state.getBlock() == ModBlocks.SMARAGDANT_CRYSTAL) {
						POS.setY(POS.getY() - 1);
						if (world.isAirBlock(POS)) {
							POS.setY(POS.getY() + 1);
							while (state.getBlock() == ModBlocks.SMARAGDANT_CRYSTAL) {
								setWithoutUpdate(world, POS, AIR);
								POS.setY(POS.getY() + 1);
								state = world.getBlockState(POS);
							}
						}
					} else if (state.getBlock() instanceof StalactiteBlock) {
						if (!state.getBlock().canPlaceBlockAt(world, POS)) {
							if (world.getBlockState(POS.up()).getBlock() instanceof StalactiteBlock) {
								while (state.getBlock() instanceof StalactiteBlock) {
									setWithoutUpdate(world, POS, AIR);
									POS.setY(POS.getY() + 1);
									state = world.getBlockState(POS);
								}
							} else {
								while (state.getBlock() instanceof StalactiteBlock) {
									setWithoutUpdate(world, POS, AIR);
									POS.setY(POS.getY() - 1);
									state = world.getBlockState(POS);
								}
							}
						}
					} else if (!state.getBlock().canPlaceBlockAt(world, POS)) {
						// Chorus
						if (state.getBlock() == Blocks.CHORUS_PLANT) {
							Set<BlockPos> ends = Sets.newHashSet();
							Set<BlockPos> add = Sets.newHashSet();
							ends.add(POS.toImmutable());

							for (int i = 0; i < 64 && !ends.isEmpty(); i++) {
								ends.forEach((pos) -> {
									setWithoutUpdate(world, pos, AIR);
									for (EnumFacing dir : HORIZONTAL_DIRECTIONS) {
										BlockPos p = pos.offset(dir);
										IBlockState st = world.getBlockState(p);
										if ((st.getBlock() == Blocks.CHORUS_PLANT || st.getBlock() == Blocks.CHORUS_FLOWER) && !st.getBlock().canPlaceBlockAt(world, p)) {
											add.add(p);
										}
									}
									BlockPos p = pos.up();
									IBlockState st = world.getBlockState(p);
									if ((st.getBlock() == Blocks.CHORUS_PLANT || st.getBlock() == Blocks.CHORUS_FLOWER) && !st.getBlock().canPlaceBlockAt(world, p)) {
										add.add(p);
									}
								});
								ends.clear();
								ends.addAll(add);
								add.clear();
							}
						}
						// Vines
						else if (state.getBlock() instanceof EndVineBlock) {
							while (world.getBlockState(POS).getBlock() instanceof EndVineBlock) {
								setWithoutUpdate(world, POS, AIR);
								POS.setY(POS.getY() - 1);
							}
						}
						// Falling blocks
						else if (state.getBlock() instanceof BlockFalling) {
							IBlockState falling = state;

							POS.setY(POS.getY() - 1);
							state = world.getBlockState(POS);

							int ray = downRayRep(world, POS.toImmutable(), 64);
							if (ray > 32) {
								BlockHelper.setWithoutUpdate(world, POS, Blocks.END_STONE.getDefaultState());
								if (world.rand.nextBoolean()) {
									POS.setY(POS.getY() - 1);
									state = world.getBlockState(POS);
									BlockHelper.setWithoutUpdate(world, POS, Blocks.END_STONE.getDefaultState());
								}
							} else {
								POS.setY(y);
								IBlockState replacement = AIR;
								for (EnumFacing dir : HORIZONTAL_DIRECTIONS) {
									state = world.getBlockState(POS.offset(dir));
									if ((state.getBlock()==Blocks.WATER || state.getBlock()==Blocks.FLOWING_WATER)) {
										replacement = state;
										break;
									}
								}
								BlockHelper.setWithoutUpdate(world, POS, replacement);
								POS.setY(y - ray);
								BlockHelper.setWithoutUpdate(world, POS, falling);
							}
						}
						// Blocks without support
						else {
							// Blue Vine
							if (state.getBlock() instanceof BlueVineBlock) {
								while (state.getBlock() == ModBlocks.BLUE_VINE || state.getBlock() == ModBlocks.BLUE_VINE_LANTERN || state.getBlock() == ModBlocks.BLUE_VINE_FUR) {
									BlockHelper.setWithoutUpdate(world, POS, AIR);
									POS.setY(POS.getY() + 1);
									state = world.getBlockState(POS);
								}
							}
							// Double plants
							if (state.getBlock() instanceof DoublePlantBlock) {
								BlockHelper.setWithoutUpdate(world, POS, AIR);
								POS.setY(POS.getY() + 1);
								BlockHelper.setWithoutUpdate(world, POS, AIR);
							}
							// Other blocks
							else {
								BlockHelper.setWithoutUpdate(world, POS, getAirOrFluid(state));
							}
						}
					}
				}
			}
		}

		doubleCheck.forEach((pos) -> {
			if (!world.getBlockState(pos).getBlock().canPlaceBlockAt(world, pos)) {
				BlockHelper.setWithoutUpdate(world, pos, AIR);
			}
		});
	}

	private static IBlockState getAirOrFluid(IBlockState state) {
		return !(state.getBlock()==Blocks.WATER || state.getBlock()==Blocks.FLOWING_WATER) ? AIR : state;
	}

	public static boolean isEndNylium(Block block) {
		return block==ModBlocks.CHORUS_NYLIUM || block==ModBlocks.CHORUS_NYLIUM_PATH;
		//return block.isIn(BlockTags.NYLIUM) && ModTags.END_GROUND.contains(block);
	}

	public static boolean isEndNylium(IBlockState state) {
		return isEndNylium(state.getBlock());
	}
}
