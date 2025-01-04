package mod.beethoven92.betterendforge.common.world.feature;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import mod.beethoven92.betterendforge.common.block.template.AttachedBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.SplineHelper;
import mod.beethoven92.betterendforge.common.util.sdf.PosInfo;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.vector.Vector3f;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GiganticAmaranitaFeature extends WorldGenerator {

	private static final Function<IBlockState, Boolean> REPLACE;
	private static final Function<IBlockState, Boolean> IGNORE;
	private static final Function<PosInfo, IBlockState> POST;

	public GiganticAmaranitaFeature(){
		super();
	}

	public GiganticAmaranitaFeature(boolean notify){
		super(notify);
	}
	
	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		if (!ModTags.END_GROUND.contains(world.getBlockState(pos.down()).getBlock())) return false;
		
		float size = ModMathHelper.randRange(5, 10, random);
		List<Vector3f> spline = SplineHelper.makeSpline(0, 0, 0, 0, size, 0, 5);
		SplineHelper.offsetParts(spline, random, 0.7F, 0, 0.7F);
		
		if (!SplineHelper.canGenerate(spline, pos, world, REPLACE)) {
			return false;
		}
		BlockHelper.setWithoutUpdate(world, pos, Blocks.AIR.getDefaultState());
		
		float radius = size * 0.17F;//MHelper.randRange(0.8F, 1.2F, random);
		SDF function = SplineHelper.buildSDF(spline, radius, 0.2F, (bpos) -> {
			return ModBlocks.AMARANITA_STEM.getDefaultState();
		});
		
		Vector3f capPos = spline.get(spline.size() - 1);
		makeHead(world, pos.add(capPos.getX() + 0.5F, capPos.getY() + 1.5F ,capPos.getZ() + 0.5F), MathHelper.floor(size / 1.6F));
		
		function.setReplaceFunction(REPLACE);
		function.addPostProcess(POST);
		function.fillRecursiveIgnore(world, pos, IGNORE);
		
		for (int i = 0; i < 3; i++) {
			List<Vector3f> copy = SplineHelper.copySpline(spline);
			SplineHelper.offsetParts(copy, random, 0.2F, 0, 0.2F);
			SplineHelper.fillSplineForce(copy, world, ModBlocks.AMARANITA_HYPHAE.getDefaultState(), pos, REPLACE);
		}
		
		return true;
	}
	
	private void makeHead(World world, BlockPos pos, int radius) {
		Mutable mut = new Mutable();
		if (radius < 2) {
			for (int i = -1; i < 2; i++) {
				mut.setPos(pos).move(EnumFacing.NORTH, 2).move(EnumFacing.EAST, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				mut.setPos(pos).move(EnumFacing.SOUTH, 2).move(EnumFacing.EAST, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				mut.setPos(pos).move(EnumFacing.EAST, 2).move(EnumFacing.NORTH, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				mut.setPos(pos).move(EnumFacing.WEST, 2).move(EnumFacing.NORTH, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
			}
			for (int x = -1; x < 2; x++) {
				for (int z = -1; z < 2; z++) {
					mut = new Mutable(mut.setPos(pos).add(x, 0, z));
					if (world.getBlockState(mut).getMaterial().isReplaceable()) {
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_LANTERN);
						mut.move(EnumFacing.DOWN);
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_LANTERN);
						mut.move(EnumFacing.DOWN);
						if (world.getBlockState(mut).getMaterial().isReplaceable()) {
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_FUR.getDefaultState().withProperty(AttachedBlock.FACING, EnumFacing.DOWN));
						}
					}
				}
			}
			
			int h = radius + 1;
			for (int y = 0; y < h; y++) {
				mut.setY(pos.getY() + y + 1);
				for (int x = -1; x < 2; x++) {
					mut.setX(pos.getX() + x);
					for (int z = -1; z < 2; z++) {
						mut.setZ(pos.getZ() + z);
						if (world.getBlockState(mut).getMaterial().isReplaceable()) {
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_CAP);
						}
					}
				}
			}
			
			mut.setY(pos.getY() + h + 1);
			for (int x = -1; x < 2; x++) {
				mut.setX(pos.getX() + x);
				for (int z = -1; z < 2; z++) {
					mut.setZ(pos.getZ() + z);
					if ((x == 0 || z == 0) && world.getBlockState(mut).getMaterial().isReplaceable()) {
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_CAP);
					}
				}
			}
		}
		else if (radius < 4) {
			pos = pos.add(-1, 0, -1);
			for (int i = -2; i < 2; i++) {
				mut.setPos(pos).move(EnumFacing.NORTH, 2).move(EnumFacing.WEST, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				mut.setPos(pos).move(EnumFacing.SOUTH, 3).move(EnumFacing.WEST, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				mut.setPos(pos).move(EnumFacing.EAST, 3).move(EnumFacing.NORTH, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				mut.setPos(pos).move(EnumFacing.WEST, 2).move(EnumFacing.NORTH, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
			}
			for (int x = -1; x < 3; x++) {
				for (int z = -1; z < 3; z++) {
					mut = new Mutable(mut.setPos(pos).add(x, 0, z));
					if (world.getBlockState(mut).getMaterial().isReplaceable()) {
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_LANTERN);
						mut.move(EnumFacing.DOWN);
						if ((x >> 1) == 0 || (z >> 1) == 0) {
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_LANTERN);
							EnumFacing.Axis axis = x < 0 || x > 1 ? EnumFacing.Axis.X : EnumFacing.Axis.Z;
							int distance = axis == EnumFacing.Axis.X ? x < 0 ? -1 : 1 : z < 0 ? -1 : 1;
							BlockPos offseted = mut.offset(EnumFacing.getFacingFromAxis(EnumFacing.AxisDirection.POSITIVE, axis), distance);
							if (world.getBlockState(offseted).getMaterial().isReplaceable()) {
								EnumFacing dir = EnumFacing.getFacingFromAxis(distance < 0 ? EnumFacing.AxisDirection.NEGATIVE : EnumFacing.AxisDirection.POSITIVE, axis);
								BlockHelper.setWithoutUpdate(world, offseted, ModBlocks.AMARANITA_FUR.getDefaultState().withProperty(AttachedBlock.FACING, dir));
							}
							mut.move(EnumFacing.DOWN);
						}
						if (world.getBlockState(mut).getMaterial().isReplaceable()) {
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_FUR.getDefaultState().withProperty(AttachedBlock.FACING, EnumFacing.DOWN));
						}
					}
				}
			}
			
			int h = radius - 1;
			for (int y = 0; y < h; y++) {
				mut.setY(pos.getY() + y + 1);
				for (int x = -1; x < 3; x++) {
					mut.setX(pos.getX() + x);
					for (int z = -1; z < 3; z++) {
						mut.setZ(pos.getZ() + z);
						if (world.getBlockState(mut).getMaterial().isReplaceable()) {
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_CAP);
						}
					}
				}
			}
			
			mut.setY(pos.getY() + h + 1);
			for (int x = -1; x < 3; x++) {
				mut.setX(pos.getX() + x);
				for (int z = -1; z < 3; z++) {
					mut.setZ(pos.getZ() + z);
					if (((x >> 1) == 0 || (z >> 1) == 0) && world.getBlockState(mut).getMaterial().isReplaceable()) {
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_CAP);
					}
				}
			}
		}
		else {
			for (int i = -2; i < 3; i++) {
				mut.setPos(pos).move(EnumFacing.NORTH, 3).move(EnumFacing.EAST, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				mut.move(EnumFacing.UP);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				mut.move(EnumFacing.NORTH);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				
				mut.setPos(pos).move(EnumFacing.SOUTH, 3).move(EnumFacing.EAST, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				mut.move(EnumFacing.UP);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				mut.move(EnumFacing.SOUTH);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				
				mut.setPos(pos).move(EnumFacing.EAST, 3).move(EnumFacing.NORTH, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				mut.move(EnumFacing.UP);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				mut.move(EnumFacing.EAST);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				
				mut.setPos(pos).move(EnumFacing.WEST, 3).move(EnumFacing.NORTH, i);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				mut.move(EnumFacing.UP);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
				mut.move(EnumFacing.WEST);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
			}
			
			for (int i = 0; i < 4; i++) {
				mut.setPos(pos).move(EnumFacing.UP).move(BlockHelper.HORIZONTAL_DIRECTIONS[i], 3).move(BlockHelper.HORIZONTAL_DIRECTIONS[(i + 1) & 3], 3);
				if (world.getBlockState(mut).getMaterial().isReplaceable()) {
					BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_HYMENOPHORE);
				}
			}
			
			for (int x = -2; x < 3; x++) {
				for (int z = -2; z < 3; z++) {
					mut = new Mutable(mut.setPos(pos).add(x, 0, z));
					if (world.getBlockState(mut).getMaterial().isReplaceable()) {
						BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_LANTERN);
						mut.move(EnumFacing.DOWN);
						if ((x / 2) == 0 || (z / 2) == 0) {
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_LANTERN);
							EnumFacing.Axis axis = x < 0 || x > 1 ? EnumFacing.Axis.X : EnumFacing.Axis.Z;
							int distance = axis == EnumFacing.Axis.X ? x < 0 ? -1 : 1 : z < 0 ? -1 : 1;
							BlockPos offseted = mut.offset(EnumFacing.getFacingFromAxis(EnumFacing.AxisDirection.POSITIVE, axis), distance);
							if (world.getBlockState(offseted).getMaterial().isReplaceable()) {
								EnumFacing dir = EnumFacing.getFacingFromAxis(distance < 0 ? EnumFacing.AxisDirection.NEGATIVE : EnumFacing.AxisDirection.POSITIVE, axis);
								BlockHelper.setWithoutUpdate(world, offseted, ModBlocks.AMARANITA_FUR.getDefaultState().withProperty(AttachedBlock.FACING, dir));
							}
							mut.move(EnumFacing.DOWN);
						}
						if (world.getBlockState(mut).getMaterial().isReplaceable()) {
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_FUR.getDefaultState().withProperty(AttachedBlock.FACING, EnumFacing.DOWN));
						}
					}
				}
			}
			
			for (int y = 0; y < 3; y++) {
				mut.setY(pos.getY() + y + 1);
				for (int x = -2; x < 3; x++) {
					mut.setX(pos.getX() + x);
					for (int z = -2; z < 3; z++) {
						mut.setZ(pos.getZ() + z);
						if (world.getBlockState(mut).getMaterial().isReplaceable()) {
							BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_CAP);
						}
					}
				}
			}
			
			int h = radius + 1;
			for (int y = 4; y < h; y++) {
				mut.setY(pos.getY() + y);
				for (int x = -2; x < 3; x++) {
					mut.setX(pos.getX() + x);
					for (int z = -2; z < 3; z++) {
						mut.setZ(pos.getZ() + z);
						if (y < 6) {
							if (((x / 2) == 0 || (z / 2) == 0) && world.getBlockState(mut).getMaterial().isReplaceable()) {
								BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_CAP);
							}
						}
						else {
							if ((x == 0 || z == 0) && (Math.abs(x) < 2 && Math.abs(z) < 2) && world.getBlockState(mut).getMaterial().isReplaceable()) {
								BlockHelper.setWithoutUpdate(world, mut, ModBlocks.AMARANITA_CAP);
							}
						}
					}
				}
			}
		}
	}
	
	static {
		REPLACE = (state) -> {
			if (ModTags.END_GROUND.contains(state.getBlock()) || state.getMaterial().equals(Material.PLANTS)) {
				return true;
			}
			return state.getMaterial().isReplaceable();
		};
		
		IGNORE = (state) -> {
			return ModBlocks.DRAGON_TREE.isTreeLog(state);
		};
		
		POST = (info) -> {
			if (info.getStateUp().getBlock()!=(ModBlocks.AMARANITA_STEM) || info.getStateDown().getBlock()!=(ModBlocks.AMARANITA_STEM)) {
				return ModBlocks.AMARANITA_HYPHAE.getDefaultState();
			}
			return info.getState();
		};
	}
}
