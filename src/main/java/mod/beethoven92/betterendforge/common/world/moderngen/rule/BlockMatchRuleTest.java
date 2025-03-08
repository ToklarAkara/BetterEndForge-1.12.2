package mod.beethoven92.betterendforge.common.world.moderngen.rule;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import java.util.Random;

public class BlockMatchRuleTest extends RuleTest {
    private final Block block;

    public BlockMatchRuleTest(Block block) {
        this.block = block;
    }

    public boolean test(IBlockState p_215181_1_, Random p_215181_2_) {
        return p_215181_1_.getBlock()==this.block;
    }
}