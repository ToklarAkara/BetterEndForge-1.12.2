package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;
import java.util.function.Function;

import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale3D;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSubtraction;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BushWithOuterFeature extends WorldGenerator {
	private static final EnumFacing[] DIRECTIONS = EnumFacing.values();
	private static final Function<IBlockState, Boolean> REPLACE;
	private final Block outerLeaves;
	private final Block leaves;
	private final Block stem;

	public BushWithOuterFeature(Block leaves, Block outerLeaves, Block stem) {
		this.outerLeaves = outerLeaves;
		this.leaves = leaves;
		this.stem = stem;
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		if (!ModTags.END_GROUND.contains(world.getBlockState(pos.down()).getBlock()) && !ModTags.END_GROUND.contains(world.getBlockState(pos.up()).getBlock())) {
			return false;
		}

		float radius = ModMathHelper.randRange(1.8F, 3.5F, random);
		OpenSimplexNoise noise = new OpenSimplexNoise(random.nextInt());
		SDF sphere = new SDFSphere().setRadius(radius).setBlock(this.leaves.getDefaultState());
		sphere = new SDFScale3D().setScale(1, 0.5F, 1).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> (float) noise.eval(vec.x * 0.2, vec.y * 0.2, vec.z * 0.2) * 3).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> ModMathHelper.randRange(-2F, 2F, random)).setSource(sphere);
		sphere = new SDFSubtraction().setSourceA(sphere).setSourceB(new SDFTranslate().setTranslate(0, -radius, 0).setSource(sphere));
		sphere.setReplaceFunction(REPLACE);
		sphere.addPostProcess((info) -> {
//			if (info.getState().getBlock() instanceof BlockLeaves) {
//				int distance = info.getPos().manhattanDistance(pos);
//				if (distance < 7) {
//					return info.getState().withProperty(BlockLeaves.DISTANCE, distance);
//				} else {
//					return Blocks.AIR.getDefaultState();
//				}
//			}
			return info.getState();
		}).addPostProcess((info) -> {
			if (info.getState().getBlock() instanceof BlockLeaves) {
				ModMathHelper.shuffle(DIRECTIONS, random);
				for (EnumFacing dir : DIRECTIONS) {
					if (info.getState(dir).getBlock()== Blocks.AIR) {
						info.setBlockPos(info.getPos().offset(dir), outerLeaves.getDefaultState());
					}
				}
			}
			return info.getState();
		});
		sphere.fillRecursive(world, pos);
		BlockHelper.setWithoutUpdate(world, pos, stem.getDefaultState());
		for (EnumFacing d : EnumFacing.values()) {
			BlockPos p = pos.offset(d);
			if (world.isAirBlock(p)) {
//				if (leaves instanceof BlockLeaves) {
//					BlockHelper.setWithoutUpdate(world, p, leaves.getDefaultState().with(BlockLeaves.DISTANCE, 1));
//				} else {
					BlockHelper.setWithoutUpdate(world, p, leaves.getDefaultState());
//				}
			}
		}

		return true;
	}

	static {
		REPLACE = (state) -> {
			if (state.getMaterial() == Material.PLANTS) {
				return true;
			}
			return state.getMaterial().isReplaceable();
		};
	}
}
