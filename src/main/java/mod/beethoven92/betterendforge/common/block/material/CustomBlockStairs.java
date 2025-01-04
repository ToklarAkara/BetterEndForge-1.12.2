package mod.beethoven92.betterendforge.common.block.material;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;

public class CustomBlockStairs extends BlockStairs {
    public CustomBlockStairs(IBlockState state){
        super(state);
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }
}
