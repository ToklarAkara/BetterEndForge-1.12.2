package mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders;

import net.minecraft.block.state.IBlockState;

public interface ISurfaceBuilderConfig {
    IBlockState getTop();

    IBlockState getUnder();
}