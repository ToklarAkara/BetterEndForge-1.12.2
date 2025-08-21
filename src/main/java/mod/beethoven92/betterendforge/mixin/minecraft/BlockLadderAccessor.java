package mod.beethoven92.betterendforge.mixin.minecraft;

import net.minecraft.block.BlockLadder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockLadder.class)
public interface BlockLadderAccessor {
    @Invoker("<init>") static BlockLadder constructNew(){return null;}
}
