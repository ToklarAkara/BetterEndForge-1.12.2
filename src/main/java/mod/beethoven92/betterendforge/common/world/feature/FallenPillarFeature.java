package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFRotation;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFCappedCone;
import mod.beethoven92.betterendforge.common.util.sdf.vector.Vector3f;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class FallenPillarFeature extends WorldGenerator {

	public FallenPillarFeature() {
		super();
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		pos = world.getHeight(pos.add(random.nextInt(16), 0, random.nextInt(16)));
		if (!ModTags.GEN_TERRAIN.contains(world.getBlockState(pos.down(5)).getBlock())) {
			return false;
		}

		float height = ModMathHelper.randRange(20F, 40F, random);
		float radius = ModMathHelper.randRange(2F, 4F, random);
		SDF pillar = new SDFCappedCone().setRadius1(radius).setRadius2(radius).setHeight(height * 0.5F)
				.setBlock(Blocks.OBSIDIAN.getDefaultState());
		pillar = new SDFTranslate().setTranslate(0, radius * 0.5F - 2, 0).setSource(pillar);
		OpenSimplexNoise noise = new OpenSimplexNoise(random.nextLong());
		pillar = new SDFDisplacement().setFunction((vec) -> {
			return (float) (noise.eval(vec.x * 0.3, vec.y * 0.3, vec.z * 0.3) * 0.5F);
		}).setSource(pillar);
		Vector3f vec = ModMathHelper.randomHorizontal(random);
		float angle = (float) random.nextGaussian() * 0.05F + (float) Math.PI;
		pillar = new SDFRotation().setRotation(vec, angle).setSource(pillar);

		IBlockState mossy = ModBlocks.MOSSY_OBSIDIAN.getDefaultState();
		pillar.addPostProcess((info) -> {
			if (info.getStateUp().getBlock()==Blocks.AIR && random.nextFloat() > 0.1F) {
				return mossy;
			}
			return info.getState();
		}).setReplaceFunction((state) -> {
			return state.getMaterial().isReplaceable() || ModTags.GEN_TERRAIN.contains(state.getBlock())
					|| state.getMaterial() == Material.PLANTS;
		}).fillRecursive(world, pos);

		return true;
	}
}
