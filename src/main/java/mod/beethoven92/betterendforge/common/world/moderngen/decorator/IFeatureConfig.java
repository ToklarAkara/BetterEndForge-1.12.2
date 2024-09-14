package mod.beethoven92.betterendforge.common.world.moderngen.decorator;

import java.util.stream.Stream;

public interface IFeatureConfig {
    NoFeatureConfig NO_FEATURE_CONFIG = NoFeatureConfig.INSTANCE;

    default Stream<ConfiguredFeature<?, ?>> getFeatures() {
        return Stream.empty();
    }
}