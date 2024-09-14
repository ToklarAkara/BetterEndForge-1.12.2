package mod.beethoven92.betterendforge.common.world.moderngen.placement;

import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChancePlacement extends SimplePlacement<ChanceConfig> {

    public Stream<BlockPos> place(World world, Random p_212852_1_, ChanceConfig p_212852_2_, BlockPos p_212852_3_) {
        return p_212852_1_.nextFloat() < 1.0F / (float)p_212852_2_.chance ? Stream.of(p_212852_3_) : Stream.empty();
    }
}