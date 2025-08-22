package mod.beethoven92.betterendforge.common.integration.crafttweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class CraftTweakerHelper {

    public static ItemStack toItemStack(IItemStack iStack) {
        return CraftTweakerMC.getItemStack(iStack);
    }

    public static Ingredient toIngredient(IIngredient iIngredient) {
        return CraftTweakerMC.getIngredient(iIngredient);
    }
}