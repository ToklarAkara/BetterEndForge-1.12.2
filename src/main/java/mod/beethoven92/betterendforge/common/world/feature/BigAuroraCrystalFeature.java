package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFRotation;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFHexPrism;
import mod.beethoven92.betterendforge.common.util.sdf.vector.Vector3f;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BigAuroraCrystalFeature extends WorldGenerator {

	public BigAuroraCrystalFeature() {
		super();
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		int maxY = pos.getY() + BlockHelper.upRay(world, pos, 16);
		int minY = pos.getY() - BlockHelper.downRay(world, pos, 16);

		if (maxY - minY < 10) {
			return false;
		}

		int y = ModMathHelper.randRange(minY, maxY, rand);
		pos = new BlockPos(pos.getX(), y, pos.getZ());

		int height = ModMathHelper.randRange(5, 25, rand);
		SDF prism = new SDFHexPrism().setHeight(height).setRadius(ModMathHelper.randRange(1.7F, 3F, rand)).setBlock(ModBlocks.AURORA_CRYSTAL.getDefaultState());
		Vector3f vec = ModMathHelper.randomHorizontal(rand);
		prism = new SDFRotation().setRotation(vec, rand.nextFloat()).setSource(prism);
		prism.setReplaceFunction((bState) -> {
			return bState.getMaterial().isReplaceable()
					|| ModTags.GEN_TERRAIN.contains(bState.getBlock())
					|| bState.getMaterial() == Material.PLANTS
					|| bState.getMaterial() == Material.LEAVES;
		});
		prism.fillRecursive(world, pos);
		BlockHelper.setWithoutUpdate(world, pos, ModBlocks.AURORA_CRYSTAL.getDefaultState());

		return true;
	}
}
