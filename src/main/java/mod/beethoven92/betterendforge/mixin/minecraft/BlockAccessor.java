package mod.beethoven92.betterendforge.mixin.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Block.class)
public interface BlockAccessor {
    @Invoker("setSoundType")
    Block invokeSetSoundType(SoundType p_149672_1_);
}
