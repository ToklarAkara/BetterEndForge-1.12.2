package mod.beethoven92.betterendforge.common.world.feature;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Lists;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.SplineHelper;
import mod.beethoven92.betterendforge.common.util.sdf.PosInfo;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale3D;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSubtraction;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.util.sdf.vector.Vector3f;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class DragonTreeFeature extends WorldGenerator
{
	private static final Function<IBlockState, Boolean> REPLACE;
	private static final Function<IBlockState, Boolean> IGNORE;
	private static final Function<PosInfo, IBlockState> POST;
	private static final List<Vector3f> BRANCH;
	private static final List<Vector3f> SIDE1;
	private static final List<Vector3f> SIDE2;
	private static final List<Vector3f> ROOT;

	public DragonTreeFeature(){
		super();
	}

	public DragonTreeFeature(boolean notify){
		super(notify);
	}
	
	static 
	{
		REPLACE = (state) -> {
			if (ModTags.END_GROUND.contains(state.getBlock()))
			{
				return true;
			}
			if (state.getBlock() == ModBlocks.DRAGON_TREE_LEAVES) 
			{
				return true;
			}
			if (state.getMaterial().equals(Material.PLANTS)) 
			{
				return true;
			}
			return state.getMaterial().isReplaceable();
		};
		
		IGNORE = (state) -> {
			return ModBlocks.DRAGON_TREE.isTreeLog(state);
		};
		
		POST = (info) -> {
			if (ModBlocks.DRAGON_TREE.isTreeLog(info.getStateUp()) && ModBlocks.DRAGON_TREE.isTreeLog(info.getStateDown())) 
			{
				return ModBlocks.DRAGON_TREE.log.getDefaultState();
			}
			return info.getState();
		};
		
		BRANCH = Lists.newArrayList(new Vector3f(0, 0, 0),
				new Vector3f(0.1F, 0.3F, 0),
				new Vector3f(0.4F, 0.6F, 0),
				new Vector3f(0.8F, 0.8F, 0),
				new Vector3f(1, 1, 0));
		SIDE1 = Lists.newArrayList(new Vector3f(0.4F, 0.6F, 0),
				new Vector3f(0.8F, 0.8F, 0),
				new Vector3f(1, 1, 0));
		SIDE2 = SplineHelper.copySpline(SIDE1);
		
		Vector3f offset1 = new Vector3f(-0.4F, -0.6F, 0);
		Vector3f offset2 = new Vector3f(0.4F, 0.6F, 0);
		
		SplineHelper.offset(SIDE1, offset1);
		SplineHelper.offset(SIDE2, offset1);
		SplineHelper.rotateSpline(SIDE1, 0.5F);
		SplineHelper.rotateSpline(SIDE2, -0.5F);
		SplineHelper.offset(SIDE1, offset2);
		SplineHelper.offset(SIDE2, offset2);
		
		ROOT = Lists.newArrayList(new Vector3f(0F, 1F, 0),
				new Vector3f(0.1F, 0.7F, 0),
				new Vector3f(0.3F, 0.3F, 0),
				new Vector3f(0.7F, 0.05F, 0),
				new Vector3f(0.8F, -0.2F, 0));
		SplineHelper.offset(ROOT, new Vector3f(0, -0.45F, 0));
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		if (!ModTags.END_GROUND.contains(world.getBlockState(pos.down()).getBlock())) return false;
		
		float size = ModMathHelper.randRange(10, 25, rand);
		List<Vector3f> spline = SplineHelper.makeSpline(0, 0, 0, 0, size, 0, 6);
		SplineHelper.offsetParts(spline, rand, 1F, 0, 1F);
		
		if (!SplineHelper.canGenerate(spline, pos, world, REPLACE)) 
		{
			return false;
		}
		
		BlockHelper.setWithoutUpdate(world, pos, Blocks.AIR);
		
		Vector3f last = SplineHelper.getPos(spline, 3.5F);
		OpenSimplexNoise noise = new OpenSimplexNoise(rand.nextLong());
		float radius = size * ModMathHelper.randRange(0.5F, 0.7F, rand);
		makeCap(world, pos.add(last.getX(), last.getY(), last.getZ()), radius, rand, noise);
		
		last = spline.get(0);
		makeRoots(world, pos.add(last.getX(), last.getY(), last.getZ()), radius, rand);
		
		radius = ModMathHelper.randRange(1.2F, 2.3F, rand);
		SDF function = SplineHelper.buildSDF(spline, radius, 1.2F, (bpos) -> {
			return ModBlocks.DRAGON_TREE.bark.getDefaultState();
		});
		
		function.setReplaceFunction(REPLACE);
		function.addPostProcess(POST);
		function.fillRecursiveIgnore(world, pos, IGNORE);
		
		return true;
	}

	private void makeCap(World world, BlockPos pos, float radius, Random random, OpenSimplexNoise noise) 
	{
		int count = (int) radius;
		int offset = (int) (BRANCH.get(BRANCH.size() - 1).getY() * radius);
		for (int i = 0; i < count; i++) 
		{
			float angle = (float) i / (float) count * ModMathHelper.PI2;
			float scale = radius * ModMathHelper.randRange(0.85F, 1.15F, random);
			
			List<Vector3f> branch = SplineHelper.copySpline(BRANCH);
			SplineHelper.rotateSpline(branch, angle);
			SplineHelper.scale(branch, scale);
			SplineHelper.fillSpline(branch, world, ModBlocks.DRAGON_TREE.bark.getDefaultState(), pos, REPLACE);
			
			branch = SplineHelper.copySpline(SIDE1);
			SplineHelper.rotateSpline(branch, angle);
			SplineHelper.scale(branch, scale);
			SplineHelper.fillSpline(branch, world, ModBlocks.DRAGON_TREE.bark.getDefaultState(), pos, REPLACE);
			
			branch = SplineHelper.copySpline(SIDE2);
			SplineHelper.rotateSpline(branch, angle);
			SplineHelper.scale(branch, scale);
			SplineHelper.fillSpline(branch, world, ModBlocks.DRAGON_TREE.bark.getDefaultState(), pos, REPLACE);
		}
		leavesBall(world, pos.up(offset), radius * 1.15F + 2, random, noise);
	}
	
	private void makeRoots(World world, BlockPos pos, float radius, Random random) 
	{
		int count = (int) (radius * 1.5F);
		for (int i = 0; i < count; i++) 
		{
			float angle = (float) i / (float) count * ModMathHelper.PI2;
			float scale = radius * ModMathHelper.randRange(0.85F, 1.15F, random);
			
			List<Vector3f> branch = SplineHelper.copySpline(ROOT);
			SplineHelper.rotateSpline(branch, angle);
			SplineHelper.scale(branch, scale);
			Vector3f last = branch.get(branch.size() - 1);
			if (ModTags.GEN_TERRAIN.contains(world.getBlockState(pos.add(last.getX(), last.getY(), last.getZ())).getBlock()))
			{
				SplineHelper.fillSpline(branch, world, ModBlocks.DRAGON_TREE.bark.getDefaultState(), pos, REPLACE);
			}
		}
	}
	
	private void leavesBall(World world, BlockPos pos, float radius, Random random, OpenSimplexNoise noise) 
	{
		SDF sphere = new SDFSphere().setRadius(radius).setBlock(ModBlocks.DRAGON_TREE_LEAVES.getDefaultState());
		SDF sub = new SDFScale().setScale(5).setSource(sphere);
		sub = new SDFTranslate().setTranslate(0, -radius * 5, 0).setSource(sub);
		sphere = new SDFSubtraction().setSourceA(sphere).setSourceB(sub);
		sphere = new SDFScale3D().setScale(1, 0.5F, 1).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> { return (float) noise.eval(vec.getX() * 0.2, vec.getY() * 0.2, vec.getZ() * 0.2) * 1.5F; }).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> { return random.nextFloat() * 3F - 1.5F; }).setSource(sphere);
		BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
		sphere.addPostProcess((info) -> {
			if (random.nextInt(5) == 0) 
			{
				for (EnumFacing dir: EnumFacing.values())
				{
					IBlockState state = info.getState(dir, 2);
					if (state.getBlock()==Blocks.AIR)
					{
						return info.getState();
					}
				}
				info.setState(ModBlocks.DRAGON_TREE.bark.getDefaultState());
				for (int x = -6; x < 7; x++) 
				{
					int ax = Math.abs(x);
					mut.setPos(x + info.getPos().getX(), mut.getY(), mut.getZ());
					for (int z = -6; z < 7; z++) 
					{
						int az = Math.abs(z);
						mut.setPos(mut.getX(), mut.getY(), z + info.getPos().getZ());
						for (int y = -6; y < 7; y++) 
						{
							int ay = Math.abs(y);
							int d = ax + ay + az;
							if (d < 7) 
							{
								mut.setY(y + info.getPos().getY());
								IBlockState state = info.getState(mut);
//								if (state.getBlock() instanceof LeavesBlock)
//								{
//									int distance = state.get(LeavesBlock.DISTANCE);
//									if (d < distance)
//									{
//										info.setState(mut, state.with(LeavesBlock.DISTANCE, d));
//									}
//								}
							}
						}
					}
				}
			}
			return info.getState();
		});
		sphere.fillRecursiveIgnore(world, pos, IGNORE);
		
		
		if (radius > 5) {
			int count = (int) (radius * 2.5F);
			for (int i = 0; i < count; i++) {
				BlockPos p = pos.add(random.nextGaussian() * 1, random.nextGaussian() * 1, random.nextGaussian() * 1);
				boolean place = true;
				for (EnumFacing d: EnumFacing.values()) {
					IBlockState state = world.getBlockState(p.offset(d));
					if (!ModBlocks.DRAGON_TREE.isTreeLog(state) && state.getBlock()!=(ModBlocks.DRAGON_TREE_LEAVES))
					{
						place = false;
						break;
					}
				}
				if (place) {
					setBlockAndNotifyAdequately(world, p, ModBlocks.DRAGON_TREE.bark.getDefaultState());
				}
			}
		}

		setBlockAndNotifyAdequately(world, pos, ModBlocks.DRAGON_TREE.bark.getDefaultState());
	}
}
