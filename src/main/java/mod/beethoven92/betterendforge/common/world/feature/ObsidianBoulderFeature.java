package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale3D;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class ObsidianBoulderFeature extends WorldGenerator {

	@Override
	public boolean generate(World world,  Random random,
							BlockPos pos) {
		pos = world.getHeight(new BlockPos(pos.getX() + random.nextInt(16), pos.getY(), pos.getZ() + random.nextInt(16)));
		if (!ModTags.END_GROUND.contains(world.getBlockState(pos.down()))) {
			return false;
		}
		
		int count = ModMathHelper.randRange(1, 5, random);
		for (int i = 0; i < count; i++) {
			BlockPos p = world.getHeight(new BlockPos(pos.getX() + random.nextInt(16) - 8, pos.getY(), pos.getZ() + random.nextInt(16) - 8));
			makeBoulder(world, p, random);
		}
		
		return true;
	}
	
	private void makeBoulder(World world, BlockPos pos, Random random) {
		if (!ModTags.END_GROUND.contains(world.getBlockState(pos.down()))) {
			return;
		}
		
		float radius = ModMathHelper.randRange(1F, 5F, random);
		SDF sphere = new SDFSphere().setRadius(radius).setBlock(Blocks.OBSIDIAN);
		float sx = ModMathHelper.randRange(0.7F, 1.3F, random);
		float sy = ModMathHelper.randRange(0.7F, 1.3F, random);
		float sz = ModMathHelper.randRange(0.7F, 1.3F, random);
		sphere = new SDFScale3D().setScale(sx, sy, sz).setSource(sphere);
		OpenSimplexNoise noise = new OpenSimplexNoise(random.nextLong());
		sphere = new SDFDisplacement().setFunction((vec) -> {
			return (float) (noise.eval(vec.getX() * 0.2, vec.getY() * 0.2, vec.getZ() * 0.2) * 1.5F);
		}).setSource(sphere);
		
		IBlockState mossy = ModBlocks.MOSSY_OBSIDIAN.getDefaultState();
		sphere.addPostProcess((info) -> {
			if (info.getStateUp().getBlock()==Blocks.AIR && random.nextFloat() > 0.1F) {
				return mossy;
			}
			return info.getState();
		}).setReplaceFunction((state) -> {
			return state.getMaterial().isReplaceable() || ModTags.GEN_TERRAIN.contains(state.getBlock()) || state.getMaterial().equals(Material.PLANTS);
		}).fillRecursive(world, pos);
	}
}
