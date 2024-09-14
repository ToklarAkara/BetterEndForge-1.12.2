package mod.beethoven92.betterendforge.common.world.moderngen.placement;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.stream.Stream;

public class SquarePlacement extends SimplePlacement<NoPlacementConfig> {

    public Stream<BlockPos> place(World world, Random p_212852_1_, NoPlacementConfig p_212852_2_, BlockPos p_212852_3_) {
        int i = p_212852_1_.nextInt(16) + p_212852_3_.getX();
        int j = p_212852_1_.nextInt(16) + p_212852_3_.getZ();
        int k = p_212852_3_.getY();
        return Stream.of(new BlockPos(i, k, j));
    }
}