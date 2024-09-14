package mod.beethoven92.betterendforge.common.world.moderngen.placement;

import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DecoratedPlacement extends Placement<DecoratedPlacementConfig> {

    public Stream<BlockPos> getPositions(World p_241857_1_, Random p_241857_2_, DecoratedPlacementConfig p_241857_3_, BlockPos p_241857_4_) {
        return p_241857_3_.outer().getPositions(p_241857_1_, p_241857_2_, p_241857_4_).flatMap((p_242882_3_) -> {
            return p_241857_3_.inner().getPositions(p_241857_1_, p_241857_2_, p_242882_3_);
        });
    }
}