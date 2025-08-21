package mod.beethoven92.betterendforge.mixin.minecraft;

import net.minecraft.block.BlockFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockFurnace.class)
public interface BlockFurnaceAccessor {
    @Accessor("keepInventory") static void setKeepInventory(boolean newValue){}
}
