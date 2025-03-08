package mod.beethoven92.betterendforge.common.world.moderngen.decorator;

import mod.beethoven92.betterendforge.common.world.moderngen.rule.RuleTest;
import net.minecraft.block.state.IBlockState;

public class OreFeatureConfig implements IFeatureConfig {
    public final RuleTest target;
    public final int size;
    public final IBlockState state;

    public OreFeatureConfig(RuleTest p_i241989_1_, IBlockState state, int size) {
        this.size = size;
        this.state = state;
        this.target = p_i241989_1_;
    }
}
