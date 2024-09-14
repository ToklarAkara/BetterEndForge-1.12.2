package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.FeatureHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class ScatterFeature extends WorldGenerator {
	private static final MutableBlockPos POS = new MutableBlockPos();
	private final int radius;

	public ScatterFeature(int radius) {
		super(false);
		this.radius = radius;
	}

	public abstract boolean canGenerate(World world, Random random, BlockPos center, BlockPos blockPos, float radius);

	public abstract void generateInner(World world, Random random, BlockPos blockPos);

	protected BlockPos getCenterGround(World world, BlockPos pos) {
		return FeatureHelper.getPosOnSurfaceWG(world, pos);
	}

	protected boolean canSpawn(World world, BlockPos pos) {
		if (pos.getY() < 5) {
			return false;
		} else if (!ModTags.END_GROUND.contains(world.getBlockState(pos.down()).getBlock())) {
			return false;
		}
		return true;
	}

	protected boolean getGroundPlant(World world, MutableBlockPos pos) {
		int down = BlockHelper.downRay(world, pos, 16);

		if (down > Math.abs(getYOffset() * 2)) {
			return false;
		}
		pos.setY(pos.getY() - down);
		return true;
	}

	protected int getYOffset() {
		return 5;
	}

	protected int getChance() {
		return 1;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		pos = getCenterGround(world, pos);

		if (!canSpawn(world, pos)) {
			return false;
		}

		float r = ModMathHelper.randRange(radius * 0.5F, radius, rand);
		int count = (int) Math.floor(r * r * ModMathHelper.randRange(1.5F, 3F, rand));
		for (int i = 0; i < count; i++) {
			float pr = r * (float) Math.sqrt(rand.nextFloat());
			float theta = rand.nextFloat() * (float) (Math.PI * 2);
			float x = pr * (float) Math.cos(theta);
			float z = pr * (float) Math.sin(theta);

			POS.setPos(pos.getX() + x, pos.getY() + getYOffset(), pos.getZ() + z);
			if (getGroundPlant(world, POS) && canGenerate(world, rand, pos, POS, r) && (getChance() < 2 || rand.nextInt(getChance()) == 0)) {
				generateInner(world, rand, POS);
			}
		}

		return true;
	}
}
