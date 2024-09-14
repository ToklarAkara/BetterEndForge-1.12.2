package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFRotation;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSubtraction;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFCappedCone;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFFlatland;
import mod.beethoven92.betterendforge.common.util.sdf.vector.Vector3f;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class ObsidianPillarBasementFeature extends WorldGenerator {
	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		pos = world.getHeight(new BlockPos(pos.getX() + random.nextInt(16), pos.getY(), pos.getZ() + random.nextInt(16)));
		if (!ModTags.GEN_TERRAIN.contains(world.getBlockState(pos.down(5)).getBlock())) {
			return false;
		}
		
		float height = ModMathHelper.randRange(10F, 35F, random);
		float radius = ModMathHelper.randRange(2F, 5F, random);
		SDF pillar = new SDFCappedCone().setRadius1(radius).setRadius2(radius).setHeight(height * 0.5F).setBlock(Blocks.OBSIDIAN);
		pillar = new SDFTranslate().setTranslate(0, height * 0.5F - 3, 0).setSource(pillar);
		SDF cut = new SDFFlatland().setBlock(Blocks.OBSIDIAN);
		OpenSimplexNoise noise = new OpenSimplexNoise(random.nextLong());
		cut = new SDFDisplacement().setFunction((vec) -> {
			return (float) (noise.eval(vec.getX() * 0.2, vec.getZ() * 0.2) * 3);
		}).setSource(cut);
		Vector3f vec = ModMathHelper.randomHorizontal(random);
		float angle = random.nextFloat() * 0.5F + (float) Math.PI;
		cut = new SDFRotation().setRotation(vec, angle).setSource(cut);
		cut = new SDFTranslate().setTranslate(0, height * 0.7F - 3, 0).setSource(cut);
		pillar = new SDFSubtraction().setSourceA(pillar).setSourceB(cut);
		vec = ModMathHelper.randomHorizontal(random);
		 angle = random.nextFloat() * 0.2F;
		pillar = new SDFRotation().setRotation(vec, angle).setSource(pillar);
		
		IBlockState mossy = ModBlocks.MOSSY_OBSIDIAN.getDefaultState();
		pillar.addPostProcess((info) -> {
			if (info.getStateUp().getBlock()== Blocks.AIR && random.nextFloat() > 0.1F) {
				return mossy;
			}
			return info.getState();
		}).setReplaceFunction((state) -> {
			return state.getMaterial().isReplaceable() || ModTags.GEN_TERRAIN.contains(state.getBlock()) || state.getMaterial().equals(Material.PLANTS);
		}).fillRecursive(world, pos);
		
		return true;
	}
}
