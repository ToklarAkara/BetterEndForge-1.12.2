package mod.beethoven92.betterendforge.mixin.minecraft;

import net.minecraft.block.BlockWorkbench;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockWorkbench.class)
public interface BlockWorkbenchAccessor {
    @Invoker("<init>") static BlockWorkbench constructNew(){return null;}
}
