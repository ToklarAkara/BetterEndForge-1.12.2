package mod.beethoven92.betterendforge.common.world.moderngen.placement;

import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class SimplePlacement<DC extends IPlacementConfig> extends Placement<DC> {

    public final Stream<BlockPos> getPositions(World world, Random p_241857_2_, DC p_241857_3_, BlockPos p_241857_4_) {
        return this.place(world, p_241857_2_, p_241857_3_, p_241857_4_);
    }

    protected abstract Stream<BlockPos> place(World world, Random p_212852_1_, DC p_212852_2_, BlockPos p_212852_3_);
}