package mod.beethoven92.betterendforge.common.world.moderngen.rule;

import net.minecraft.block.state.IBlockState;

import java.util.Random;

public abstract class RuleTest {

    public RuleTest() {
    }

    public abstract boolean test(IBlockState var1, Random var2);

}