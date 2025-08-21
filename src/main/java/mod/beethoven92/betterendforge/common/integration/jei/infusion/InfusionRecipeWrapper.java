package mod.beethoven92.betterendforge.common.integration.jei.infusion;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mod.beethoven92.betterendforge.common.recipes.InfusionRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class InfusionRecipeWrapper implements IRecipeWrapper {
    private final InfusionRecipe recipe;

    public InfusionRecipeWrapper(InfusionRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        for(Ingredient i : recipe.getIngredients()) {
            builder.add(Lists.newArrayList(i.getMatchingStacks()));
        }
        ingredients.setInputLists(VanillaTypes.ITEM, builder.build());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int infusionTime = recipe.time;
        if (infusionTime > 0) {
            String timeString = I18n.format("gui.jei.category.infusion.time", infusionTime);
            FontRenderer fontRenderer = minecraft.fontRenderer;
            int stringWidth = fontRenderer.getStringWidth(timeString);
            fontRenderer.drawString(timeString, recipeWidth - stringWidth, 0, Color.GRAY.getRGB());
        }
    }
}