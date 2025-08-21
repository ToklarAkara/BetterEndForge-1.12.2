package mod.beethoven92.betterendforge.mixin.minecraft;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TileEntityFurnace.class)
public interface TileEntityFurnaceAccessor {
    @Accessor("furnaceBurnTime") int getFurnaceBurnTime();
    @Accessor("furnaceBurnTime") void setFurnaceBurnTime(int newValue);
    @Accessor("currentItemBurnTime") void setCurrentItemBurnTime(int newValue);
    @Accessor("cookTime") int getCookTime();
    @Accessor("cookTime") void setCookTime(int newValue);
    @Accessor("totalCookTime") int getTotalCookTime();
    @Accessor("totalCookTime") void setTotalCookTime(int newValue);
    @Accessor("furnaceItemStacks") NonNullList<ItemStack> getFurnaceItemStacks();
    @Invoker("canSmelt") boolean invokeCanSmelt();
}
