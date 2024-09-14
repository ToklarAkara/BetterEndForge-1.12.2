package mod.beethoven92.betterendforge.common.world.moderngen.placement;

import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ConfiguredPlacement<DC extends IPlacementConfig> implements IDecoratable<ConfiguredPlacement<?>> {
    private final Placement<DC> decorator;
    private final DC config;

    public ConfiguredPlacement(Placement<DC> p_i51390_1_, DC p_i51390_2_) {
        this.decorator = p_i51390_1_;
        this.config = p_i51390_2_;
    }

    public Stream<BlockPos> getPositions(World p_242876_1_, Random p_242876_2_, BlockPos p_242876_3_) {
        return this.decorator.getPositions(p_242876_1_, p_242876_2_, this.config, p_242876_3_);
    }

    public ConfiguredPlacement<?> decorated(ConfiguredPlacement<?> p_227228_1_) {
        return new ConfiguredPlacement<>(Placement.DECORATED, new DecoratedPlacementConfig(p_227228_1_, this));
    }

    public DC config() {
        return this.config;
    }
}