package mod.beethoven92.betterendforge.common.integration.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import mod.beethoven92.betterendforge.data.AlloyingRecipes;
import mod.beethoven92.betterendforge.data.InfusionRecipes;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import mod.beethoven92.betterendforge.common.recipes.InfusionRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.betterendforge.BetterEndCraftTweaker")
@ZenRegister
public class BetterEndCraftTweaker {

    @ZenClass("mods.betterendforge.Alloying")
    @ZenRegister
    public static class Alloying {

        @ZenMethod
        public static void addRecipe(String name, IItemStack output, IItemStack input1, IItemStack input2, @Optional(valueDouble = 1.0D) double experience, @Optional(valueLong = 400) int smeltTime) {
            AlloyingRecipe recipe = new AlloyingRecipe(
                    new ResourceLocation("crafttweaker", name),
                    "",
                    CraftTweakerHelper.toIngredient(input1),
                    CraftTweakerHelper.toIngredient(input2),
                    CraftTweakerHelper.toItemStack(output),
                    (float) experience,
                    smeltTime
            );
            CraftTweakerAPI.apply(new ActionAddAlloying(recipe));
        }

        @ZenMethod
        public static void removeRecipe(IItemStack output) {
            CraftTweakerAPI.apply(new ActionRemoveAlloying(CraftTweakerHelper.toItemStack(output)));
        }

        private static class ActionAddAlloying implements IAction {
            private final AlloyingRecipe recipe;

            ActionAddAlloying(AlloyingRecipe recipe) {
                this.recipe = recipe;
            }

            @Override
            public void apply() {
                AlloyingRecipes.getRecipes().add(recipe);
            }

            @Override
            public String describe() {
                return "Adding Alloying recipe for " + recipe.getRecipeOutput().getDisplayName();
            }
        }

        private static class ActionRemoveAlloying implements IAction {
            private final ItemStack output;

            ActionRemoveAlloying(ItemStack output) {
                this.output = output;
            }

            @Override
            public void apply() {
                AlloyingRecipes.getRecipes().removeIf(recipe -> OreDictionary.itemMatches(recipe.getRecipeOutput(), output, false));
            }

            @Override
            public String describe() {
                return "Removing Alloying recipe(s) that output " + output.getDisplayName();
            }
        }
    }

    @ZenClass("mods.betterendforge.Infusion")
    @ZenRegister
    public static class Infusion {

        @ZenMethod
        public static void addRecipe(String name, IItemStack output, IItemStack input, IItemStack[] catalysts, int time) {
            if (catalysts.length != 8) {
                CraftTweakerAPI.logError("Infusion recipe requires exactly 8 catalysts, but got " + catalysts.length);
                return;
            }

            InfusionRecipe.Builder builder = InfusionRecipe.Builder.create()
                    .setInput(CraftTweakerHelper.toIngredient(input))
                    .setOutput(CraftTweakerHelper.toItemStack(output))
                    .setTime(time);

            for (int i = 0; i < catalysts.length; i++) {
                IItemStack catalyst = catalysts[i];
                if (catalyst != null) {
                    builder.addCatalyst(i, CraftTweakerHelper.toIngredient(catalyst));
                }
            }

            List<InfusionRecipe> createdRecipes = new ArrayList<>();
            builder.build(createdRecipes::add, new ResourceLocation("crafttweaker", name));

            if (!createdRecipes.isEmpty()) {
                CraftTweakerAPI.apply(new ActionAddInfusion(createdRecipes.get(0)));
            } else {
                CraftTweakerAPI.logError("Failed to create Infusion recipe: " + name);
            }
        }

        @ZenMethod
        public static void removeRecipe(IItemStack output) {
            CraftTweakerAPI.apply(new ActionRemoveInfusion(CraftTweakerHelper.toItemStack(output)));
        }

        private static class ActionAddInfusion implements IAction {
            private final InfusionRecipe recipe;

            ActionAddInfusion(InfusionRecipe recipe) {
                this.recipe = recipe;
            }

            @Override
            public void apply() {
                InfusionRecipes.getRecipes().add(recipe);
            }

            @Override
            public String describe() {
                return "Adding Infusion recipe for " + recipe.getRecipeOutput().getDisplayName();
            }
        }

        private static class ActionRemoveInfusion implements IAction {
            private final ItemStack output;

            ActionRemoveInfusion(ItemStack output) {
                this.output = output;
            }

            @Override
            public void apply() {
                InfusionRecipes.getRecipes().removeIf(recipe -> OreDictionary.itemMatches(recipe.getRecipeOutput(), output, false));
            }

            @Override
            public String describe() {
                return "Removing Infusion recipe(s) that output " + output.getDisplayName();
            }
        }
    }
}