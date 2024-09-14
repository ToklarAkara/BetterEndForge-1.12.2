package mod.beethoven92.betterendforge.common.world.moderngen.placement;

import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SimpleHeightmapBasedPlacement extends Placement<NoPlacementConfig> {

    public Stream<BlockPos> getPositions(World p_241857_1_, Random p_241857_2_, NoPlacementConfig p_241857_3_, BlockPos p_241857_4_) {
        int i = p_241857_4_.getX();
        int j = p_241857_4_.getZ();
        int k = p_241857_1_.getHeight(i, j);
        return k > 0 ? Stream.of(new BlockPos(i, k, j)) : Stream.of();
    }
}