package mod.beethoven92.betterendforge.common.world.moderngen.decorator;

import mod.beethoven92.betterendforge.common.world.moderngen.placement.ConfiguredPlacement;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class DecoratedFeatureConfig implements IFeatureConfig {
    public final Supplier<ConfiguredFeature<?, ?>> feature;
    public final ConfiguredPlacement<?> decorator;

    public DecoratedFeatureConfig(Supplier<ConfiguredFeature<?, ?>> p_i241984_1_, ConfiguredPlacement<?> p_i241984_2_) {
        this.feature = p_i241984_1_;
        this.decorator = p_i241984_2_;
    }

    public Stream<ConfiguredFeature<?, ?>> getFeatures() {
        return this.feature.get().getFeatures();
    }
}