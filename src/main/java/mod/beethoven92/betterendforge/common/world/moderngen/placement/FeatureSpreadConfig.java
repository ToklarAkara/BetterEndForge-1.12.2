package mod.beethoven92.betterendforge.common.world.moderngen.placement;

public class FeatureSpreadConfig implements IPlacementConfig {
      private final FeatureSpread count;

    public FeatureSpreadConfig(int p_i241982_1_) {
        this.count = FeatureSpread.fixed(p_i241982_1_);
    }

    public FeatureSpreadConfig(FeatureSpread p_i241983_1_) {
        this.count = p_i241983_1_;
    }

    public FeatureSpread count() {
        return this.count;
    }
}