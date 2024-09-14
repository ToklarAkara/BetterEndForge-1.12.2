package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class FullHeightScatterFeature extends WorldGenerator {
	private static final MutableBlockPos POS = new MutableBlockPos();
	private final int radius;

	public FullHeightScatterFeature(int radius) {
		this.radius = radius;
	}

	public abstract boolean canGenerate(World world, Random random, BlockPos center, BlockPos blockPos, float radius);

	public abstract void generateInner(World world, Random random, BlockPos blockPos);

	@Override
	public boolean generate(World world, Random random, BlockPos center) {
		int maxY = world.getHeight(center.getX(), center.getZ());
		int minY = BlockHelper.upRay(world, new BlockPos(center.getX(), 0, center.getZ()), maxY);
		for (int y = maxY; y > minY; y--) {
			POS.setPos(center.getX(), y, center.getZ());
			if (world.isAirBlock(POS) && !world.isAirBlock(POS.down())) {
				float r = ModMathHelper.randRange(radius * 0.5F, radius, random);
				int count = ModMathHelper.floor(r * r * ModMathHelper.randRange(1.5F, 3F, random));
				for (int i = 0; i < count; i++) {
					float pr = r * (float) Math.sqrt(random.nextFloat());
					float theta = random.nextFloat() * ModMathHelper.PI2;
					float x = pr * (float) Math.cos(theta);
					float z = pr * (float) Math.sin(theta);

					POS.setPos(center.getX() + x, y + 5, center.getZ() + z);
					int down = BlockHelper.downRay(world, POS, 16);
					if (down > 10) continue;
					POS.setY(POS.getY() - down);

					if (canGenerate(world, random, center, POS, r)) {
						generateInner(world, random, POS);
					}
				}
			}
		}
		return true;
	}
}
