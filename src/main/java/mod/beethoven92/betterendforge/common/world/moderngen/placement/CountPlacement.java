package mod.beethoven92.betterendforge.common.world.moderngen.placement;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CountPlacement extends SimplePlacement<FeatureSpreadConfig> {

    public Stream<BlockPos> place(World world, Random p_212852_1_, FeatureSpreadConfig p_212852_2_, BlockPos p_212852_3_) {
        return IntStream.range(0, p_212852_2_.count().sample(p_212852_1_)).mapToObj((p_242878_1_) -> {
            return p_212852_3_;
        });
    }
}