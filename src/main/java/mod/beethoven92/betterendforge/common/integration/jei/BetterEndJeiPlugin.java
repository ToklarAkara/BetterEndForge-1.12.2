package mod.beethoven92.betterendforge.common.integration.jei;

import mezz.jei.api.*;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.integration.jei.alloying.AlloyingRecipeCategory;
import mod.beethoven92.betterendforge.common.integration.jei.alloying.AlloyingRecipeWrapper;
import mod.beethoven92.betterendforge.common.integration.jei.infusion.InfusionRecipeCategory;
import mod.beethoven92.betterendforge.common.integration.jei.infusion.InfusionRecipeWrapper;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import mod.beethoven92.betterendforge.common.recipes.InfusionRecipe;
import mod.beethoven92.betterendforge.data.AlloyingRecipes;
import mod.beethoven92.betterendforge.data.InfusionRecipes;
import net.minecraft.item.ItemStack;

import java.util.List;

@JEIPlugin
public class BetterEndJeiPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

        AlloyingRecipeCategory alloyingCategory = new AlloyingRecipeCategory(guiHelper);
        registry.addRecipeCategories(
                alloyingCategory,
                new InfusionRecipeCategory(guiHelper)
        );

        registry.handleRecipes(AlloyingRecipe.class, recipe -> new AlloyingRecipeWrapper(alloyingCategory, recipe), AlloyingRecipeCategory.UID);
        registry.handleRecipes(InfusionRecipe.class, InfusionRecipeWrapper::new, InfusionRecipeCategory.UID);

        List<AlloyingRecipe> alloyingRecipes = AlloyingRecipes.getRecipes();
        registry.addRecipes(alloyingRecipes, AlloyingRecipeCategory.UID);

        List<InfusionRecipe> infusionRecipes = InfusionRecipes.getRecipes();
        registry.addRecipes(infusionRecipes, InfusionRecipeCategory.UID);

        registry.addRecipeCatalyst(new ItemStack(ModBlocks.END_STONE_SMELTER), AlloyingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.INFUSION_PEDESTAL), InfusionRecipeCategory.UID);

        registry.addRecipeCatalyst(new ItemStack(ModBlocks.END_STONE_FURNACE), VanillaRecipeCategoryUid.SMELTING);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.END_STONE_FURNACE), VanillaRecipeCategoryUid.FUEL);

        for (StoneMaterial material : ModBlocks.getStoneMaterials()) {
            registry.addRecipeCatalyst(new ItemStack(material.furnace), VanillaRecipeCategoryUid.FUEL);
            registry.addRecipeCatalyst(new ItemStack(material.furnace), VanillaRecipeCategoryUid.SMELTING);
        }

        for (WoodenMaterial material : ModBlocks.getWoodenMaterials()) {
            registry.addRecipeCatalyst(new ItemStack(material.craftingTable), VanillaRecipeCategoryUid.CRAFTING);
        }
    }
}