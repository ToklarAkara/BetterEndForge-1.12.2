package mod.beethoven92.betterendforge.common.world.moderngen.placement;


public interface IDecoratable<R> {
    R decorated(ConfiguredPlacement<?> p_227228_1_);

    default R chance(int p_242729_1_) {
        return this.decorated(Placement.CHANCE.configure(new ChanceConfig(p_242729_1_)));
    }

    default R count(FeatureSpread p_242730_1_) {
        return this.decorated(Placement.COUNT.configure(new FeatureSpreadConfig(p_242730_1_)));
    }

    default R count(int p_242731_1_) {
        return this.count(FeatureSpread.fixed(p_242731_1_));
    }

    default R countRandom(int p_242732_1_) {
        return this.count(FeatureSpread.of(0, p_242732_1_));
    }
    default R range(int p_242733_1_) {
        return (R)this.decorated(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, p_242733_1_)));
    }
    default R squared() {
        return this.decorated(Placement.SQUARE.configure(NoPlacementConfig.INSTANCE));
    }
}