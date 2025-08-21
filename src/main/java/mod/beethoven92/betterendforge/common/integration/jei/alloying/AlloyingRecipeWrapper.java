package mod.beethoven92.betterendforge.common.integration.jei.alloying;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.awt.*;
import java.util.List;

public class AlloyingRecipeWrapper implements IRecipeWrapper {
    private final AlloyingRecipe recipe;
    private final AlloyingRecipeCategory category;

    public AlloyingRecipeWrapper(AlloyingRecipeCategory category, AlloyingRecipe recipe) {
        this.recipe = recipe;
        this.category = category;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        for(Ingredient i : recipe.getIngredients()) {
            builder.add(Lists.newArrayList(i.getMatchingStacks()));
        }
        builder.add(Lists.newArrayList(new ItemStack(Blocks.COAL_BLOCK), new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.LAVA_BUCKET)));

        ingredients.setInputLists(VanillaTypes.ITEM, builder.build());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        // Отрисовка стрелки прогресса
        category.getArrow(recipe).draw(minecraft, 47, 18);

        // Отрисовка опыта
        float experience = recipe.getExperience();
        if (experience > 0) {
            String experienceString = I18n.format("gui.jei.category.alloying.experience", experience);
            FontRenderer fontRenderer = minecraft.fontRenderer;
            int stringWidth = fontRenderer.getStringWidth(experienceString);
            fontRenderer.drawString(experienceString, recipeWidth - stringWidth, 0, Color.GRAY.getRGB());
        }

        // Отрисовка времени плавки
        int smeltTime = recipe.getSmeltTime();
        if (smeltTime > 0) {
            int smeltTimeSeconds = smeltTime / 20;
            String timeString = I18n.format("gui.jei.category.alloying.time.seconds", smeltTimeSeconds);
            FontRenderer fontRenderer = minecraft.fontRenderer;
            int stringWidth = fontRenderer.getStringWidth(timeString);
            fontRenderer.drawString(timeString, recipeWidth - stringWidth, 45, Color.GRAY.getRGB());
        }
    }
}