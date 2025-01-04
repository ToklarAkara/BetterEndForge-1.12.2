package mod.beethoven92.betterendforge.common.world.feature;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Lists;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.FurBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.SplineHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale3D;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSubtraction;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.util.sdf.vector.Vector3f;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LucerniaFeature extends WorldGenerator {
	private static final EnumFacing[] EnumFacingS = EnumFacing.values();
	private static final Function<IBlockState, Boolean> REPLACE;
	private static final Function<IBlockState, Boolean> IGNORE;
	private static final List<Vector3f> SPLINE;
	private static final List<Vector3f> ROOT;

	public LucerniaFeature(){
		super();
	}

	public LucerniaFeature(boolean notify){
		super(notify);
	}
	

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		if (!ModTags.END_GROUND.contains(world.getBlockState(pos.down()).getBlock()))
			return false;

		float size = ModMathHelper.randRange(12, 20, random);
		int count = (int) (size * 0.3F);
		float var = ModMathHelper.PI2 / (float) (count * 3);
		float start = ModMathHelper.randRange(0, ModMathHelper.PI2, random);
		for (int i = 0; i < count; i++) {
			float angle = (float) i / (float) count * ModMathHelper.PI2 + ModMathHelper.randRange(0, var, random) + start;
			List<Vector3f> spline = SplineHelper.copySpline(SPLINE);
			SplineHelper.rotateSpline(spline, angle);
			SplineHelper.scale(spline, size * ModMathHelper.randRange(0.5F, 1F, random));
			SplineHelper.offsetParts(spline, random, 1F, 0, 1F);
			SplineHelper.fillSpline(spline, world, ModBlocks.LUCERNIA.bark.getDefaultState(), pos, REPLACE);
			Vector3f last = spline.get(spline.size() - 1);
			float leavesRadius = (size * 0.13F + ModMathHelper.randRange(0.8F, 1.5F, random)) * 1.4F;
			OpenSimplexNoise noise = new OpenSimplexNoise(random.nextLong());
			leavesBall(world, pos.add(last.getX(), last.getY(), last.getZ()), leavesRadius, random, noise,
					true);
		}

		makeRoots(world, pos.add(0, ModMathHelper.randRange(3, 5, random), 0), size * 0.35F, random);

		return true;
	}

	private void leavesBall(World world, BlockPos pos, float radius, Random random,
			OpenSimplexNoise noise, boolean natural) {
		SDF sphere = new SDFSphere().setRadius(radius)
				.setBlock(ModBlocks.LUCERNIA_LEAVES.getDefaultState());
		SDF sub = new SDFScale().setScale(5).setSource(sphere);
		sub = new SDFTranslate().setTranslate(0, -radius * 5, 0).setSource(sub);
		sphere = new SDFSubtraction().setSourceA(sphere).setSourceB(sub);
		sphere = new SDFScale3D().setScale(1, 0.75F, 1).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> {
			return (float) noise.eval(vec.getX() * 0.2, vec.getY() * 0.2, vec.getZ() * 0.2) * 2F;
		}).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> {
			return ModMathHelper.randRange(-1.5F, 1.5F, random);
		}).setSource(sphere);

		Mutable mut = new Mutable();
		for (EnumFacing d1 : BlockHelper.HORIZONTAL_DIRECTIONS) {
			BlockPos p = mut.setPos(pos).move(EnumFacing.UP).move(d1).toImmutable();
			BlockHelper.setWithoutUpdate(world, p, ModBlocks.LUCERNIA.bark.getDefaultState());
			for (EnumFacing d2 : BlockHelper.HORIZONTAL_DIRECTIONS) {
				mut.setPos(p).move(EnumFacing.UP).move(d2);
				BlockHelper.setWithoutUpdate(world, p, ModBlocks.LUCERNIA.bark.getDefaultState());
			}
		}

		IBlockState top = ModBlocks.FILALUX.getDefaultState().withProperty(BlockProperties.TRIPLE_SHAPE, TripleShape.TOP);
		IBlockState middle = ModBlocks.FILALUX.getDefaultState().withProperty(BlockProperties.TRIPLE_SHAPE, TripleShape.MIDDLE);
		IBlockState bottom = ModBlocks.FILALUX.getDefaultState().withProperty(BlockProperties.TRIPLE_SHAPE, TripleShape.BOTTOM);
		IBlockState outer = ModBlocks.LUCERNIA_OUTER_LEAVES.getDefaultState();

		List<BlockPos> support = Lists.newArrayList();
		sphere.addPostProcess((info) -> {
			if (natural && random.nextInt(6) == 0 && info.getStateDown().getBlock()==Blocks.AIR) {
				BlockPos d = info.getPos().down();
				support.add(d);
			}
			if (random.nextInt(15) == 0) {
				for (EnumFacing dir : EnumFacing.values()) {
					IBlockState state = info.getState(dir, 2);
					if (state.getBlock()== Blocks.AIR) {
						return info.getState();
					}
				}
				info.setState(ModBlocks.LUCERNIA.bark.getDefaultState());
			}

			ModMathHelper.shuffle(EnumFacingS, random);
			for (EnumFacing d : EnumFacingS) {
				if (info.getState(d).getBlock()== Blocks.AIR && GeneratorOptions.getGenerateOuterLeaves()) {
					info.setBlockPos(info.getPos().offset(d), outer.withProperty(FurBlock.FACING, d));
				}
			}

			if (ModBlocks.LUCERNIA.isTreeLog(info.getState())) {
				for (int x = -6; x < 7; x++) {
					int ax = Math.abs(x);
					mut.setX(x + info.getPos().getX());
					for (int z = -6; z < 7; z++) {
						int az = Math.abs(z);
						mut.setZ(z + info.getPos().getZ());
						for (int y = -6; y < 7; y++) {
							int ay = Math.abs(y);
							int d = ax + ay + az;
							if (d < 7) {
								mut.setY(y + info.getPos().getY());
							}
						}
					}
				}
			}
			return info.getState();
		});
		sphere.fillRecursiveIgnore(world, pos, IGNORE);
		BlockHelper.setWithoutUpdate(world, pos, ModBlocks.LUCERNIA.bark);

		support.forEach((bpos) -> {
			IBlockState state = world.getBlockState(bpos);
			if (state.getBlock()==Blocks.AIR || state.getBlock()==(ModBlocks.LUCERNIA_OUTER_LEAVES)) {
				int count = ModMathHelper.randRange(3, 8, random);
				mut.setPos(bpos);
				if (world.getBlockState(mut.up()).getBlock()==(ModBlocks.LUCERNIA_LEAVES)) {
					BlockHelper.setWithoutUpdate(world, mut, top);
					for (int i = 1; i < count; i++) {
						mut.setY(mut.getY() - 1);
						if (world.isAirBlock(mut.down())) {
							BlockHelper.setWithoutUpdate(world, mut, middle);
						} else {
							break;
						}
					}
					BlockHelper.setWithoutUpdate(world, mut, bottom);
				}
			}
		});
	}

	private void makeRoots(World world, BlockPos pos, float radius, Random random) {
		int count = (int) (radius * 1.5F);
		for (int i = 0; i < count; i++) {
			float angle = (float) i / (float) count * ModMathHelper.PI2;
			float scale = radius * ModMathHelper.randRange(0.85F, 1.15F, random);

			List<Vector3f> branch = SplineHelper.copySpline(ROOT);
			SplineHelper.rotateSpline(branch, angle);
			SplineHelper.scale(branch, scale);
			Vector3f last = branch.get(branch.size() - 1);
			if (ModTags.GEN_TERRAIN.contains(world.getBlockState(pos.add(last.getX(), last.getY(), last.getZ())).getBlock())) {
				SplineHelper.fillSplineForce(branch, world, ModBlocks.LUCERNIA.bark.getDefaultState(), pos, REPLACE);
			}
		}
	}

	static {
		REPLACE = (state) -> {
			if (ModTags.END_GROUND.contains(state.getBlock())) {
				return true;
			}
			if (state.getBlock() == ModBlocks.LUCERNIA_LEAVES) {
				return true;
			}
			if (state.getMaterial().equals(Material.PLANTS)) {
				return true;
			}
			return state.getMaterial().isReplaceable();
		};

		IGNORE = (state) -> {
			return ModBlocks.LUCERNIA.isTreeLog(state);
		};

		SPLINE = Lists.newArrayList(new Vector3f(0.00F, 0.00F, 0.00F), new Vector3f(0.10F, 0.35F, 0.00F),
				new Vector3f(0.20F, 0.50F, 0.00F), new Vector3f(0.30F, 0.55F, 0.00F), new Vector3f(0.42F, 0.70F, 0.00F),
				new Vector3f(0.50F, 1.00F, 0.00F));

		ROOT = Lists.newArrayList(new Vector3f(0.1F, 0.70F, 0), new Vector3f(0.3F, 0.30F, 0),
				new Vector3f(0.7F, 0.05F, 0), new Vector3f(0.8F, -0.20F, 0));
		SplineHelper.offset(ROOT, new Vector3f(0, -0.45F, 0));
	}
}
