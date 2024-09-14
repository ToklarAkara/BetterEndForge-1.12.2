package mod.beethoven92.betterendforge.common.world.moderngen.decorator;

import java.util.Random;
import java.util.stream.Stream;

import mod.beethoven92.betterendforge.common.world.moderngen.placement.ConfiguredPlacement;
import mod.beethoven92.betterendforge.common.world.moderngen.placement.IDecoratable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfiguredFeature<FC extends IFeatureConfig, F extends WorldGenerator> implements IDecoratable<ConfiguredFeature<?, ?>> {

    public static final Logger LOGGER = LogManager.getLogger();
    public final F feature;
    public final FC config;

    public ConfiguredFeature(F p_i49900_1_, FC p_i49900_2_) {
        this.feature = p_i49900_1_;
        this.config = p_i49900_2_;
    }

    public F feature() {
        return this.feature;
    }

    public FC config() {
        return this.config;
    }

    public ConfiguredFeature<?, ?> decorated(ConfiguredPlacement<?> p_227228_1_) {
        DecoratedFeatureConfig config = new DecoratedFeatureConfig(() -> this, p_227228_1_);
        return new ConfiguredFeature<>(new DecoratedFeature(config), config);
    }

//    public ConfiguredRandomFeatureList weighted(float p_227227_1_) {
//        return new ConfiguredRandomFeatureList(this, p_227227_1_);
//    }

    public boolean place(World p_242765_1_, Random p_242765_3_, BlockPos p_242765_4_) {
        return this.feature.generate(p_242765_1_, p_242765_3_, p_242765_4_);
    }

    public Stream<ConfiguredFeature<?, ?>> getFeatures() {
        return Stream.concat(Stream.of(this), this.config.getFeatures());
    }
}