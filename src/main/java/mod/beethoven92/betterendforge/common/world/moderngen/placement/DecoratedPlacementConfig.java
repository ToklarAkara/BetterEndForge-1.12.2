package mod.beethoven92.betterendforge.common.world.moderngen.placement;


public class DecoratedPlacementConfig implements IPlacementConfig {
    private final ConfiguredPlacement<?> outer;
    private final ConfiguredPlacement<?> inner;

    public DecoratedPlacementConfig(ConfiguredPlacement<?> p_i242020_1_, ConfiguredPlacement<?> p_i242020_2_) {
        this.outer = p_i242020_1_;
        this.inner = p_i242020_2_;
    }

    public ConfiguredPlacement<?> outer() {
        return this.outer;
    }

    public ConfiguredPlacement<?> inner() {
        return this.inner;
    }
}