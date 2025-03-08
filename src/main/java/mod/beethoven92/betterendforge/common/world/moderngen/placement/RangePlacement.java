package mod.beethoven92.betterendforge.common.world.moderngen.placement;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.stream.Stream;

public class RangePlacement extends SimplePlacement<TopSolidRangeConfig> {
    public Stream<BlockPos> place(World world, Random random, TopSolidRangeConfig config, BlockPos pos) {
        int i = pos.getX();
        int j = pos.getZ();
        int k = random.nextInt(config.maximum - config.topOffset) + config.bottomOffset;
        return Stream.of(new BlockPos(i, k, j));
    }
}