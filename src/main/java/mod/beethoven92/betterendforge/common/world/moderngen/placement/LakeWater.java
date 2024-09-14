package mod.beethoven92.betterendforge.common.world.moderngen.placement;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.stream.Stream;

public class LakeWater extends Placement<ChanceConfig> {

    public Stream<BlockPos> getPositions(World world, Random p_241857_2_, ChanceConfig p_241857_3_, BlockPos p_241857_4_) {
        if (p_241857_2_.nextInt(p_241857_3_.chance) == 0) {
            int i = p_241857_2_.nextInt(16) + p_241857_4_.getX();
            int j = p_241857_2_.nextInt(16) + p_241857_4_.getZ();
            int k = p_241857_2_.nextInt(p_241857_4_.getY()); //p_241857_1_.getGenDepth());
            return Stream.of(new BlockPos(i, k, j));
        } else {
            return Stream.empty();
        }
    }
}