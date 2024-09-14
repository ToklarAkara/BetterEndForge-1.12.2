package mod.beethoven92.betterendforge.common.world.moderngen.placement;

import java.util.Objects;
import java.util.Random;

public class FeatureSpread {

    private final int baseValue;
    private final int spread;



    private FeatureSpread(int p_i241900_1_, int p_i241900_2_) {
        this.baseValue = p_i241900_1_;
        this.spread = p_i241900_2_;
    }

    public static FeatureSpread fixed(int p_242252_0_) {
        return new FeatureSpread(p_242252_0_, 0);
    }

    public static FeatureSpread of(int p_242253_0_, int p_242253_1_) {
        return new FeatureSpread(p_242253_0_, p_242253_1_);
    }

    public int sample(Random p_242259_1_) {
        return this.spread == 0 ? this.baseValue : this.baseValue + p_242259_1_.nextInt(this.spread + 1);
    }

    public boolean equals(Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
            FeatureSpread featurespread = (FeatureSpread)p_equals_1_;
            return this.baseValue == featurespread.baseValue && this.spread == featurespread.spread;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.baseValue, this.spread);
    }

    public String toString() {
        return "[" + this.baseValue + '-' + (this.baseValue + this.spread) + ']';
    }
}
