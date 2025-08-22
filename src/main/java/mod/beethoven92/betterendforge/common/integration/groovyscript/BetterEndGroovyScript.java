package mod.beethoven92.betterendforge.common.integration.groovyscript;

import com.cleanroommc.groovyscript.api.IIngredient;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.data.AlloyingRecipes;
import mod.beethoven92.betterendforge.data.InfusionRecipes;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import mod.beethoven92.betterendforge.common.recipes.InfusionRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BetterEndGroovyScript {
    public static final Logger LOGGER = LogManager.getLogger(BetterEnd.MOD_ID + "/GroovyScript");

    public static class Alloying {

        public static void addRecipe(String name, ItemStack output, IIngredient input1, IIngredient input2) {
            addRecipe(name, output, input1, input2, 1.0F, 400);
        }

        public static void addRecipe(String name, ItemStack output, IIngredient input1, IIngredient input2, float experience) {
            addRecipe(name, output, input1, input2, experience, 400);
        }

        public static void addRecipe(String name, ItemStack output, IIngredient input1, IIngredient input2, float experience, int smeltTime) {
            if (Objects.isNull(name) || Objects.isNull(output) || Objects.isNull(input1) || Objects.isNull(input2)) {
                LOGGER.error("Failed to add Alloying recipe: one of the required arguments is null.");
                return;
            }
            AlloyingRecipe recipe = new AlloyingRecipe(
                    new ResourceLocation("groovyscript", name),
                    "",
                    input1.toMcIngredient(),
                    input2.toMcIngredient(),
                    output,
                    experience,
                    smeltTime
            );
            AlloyingRecipes.getRecipes().add(recipe);
            LOGGER.info("Successfully added Alloying recipe: " + name);
        }

        public static void removeRecipe(ItemStack output) {
            if (Objects.isNull(output) || output.isEmpty()) return;

            List<AlloyingRecipe> toRemove = AlloyingRecipes.getRecipes().stream()
                    .filter(recipe -> ItemStack.areItemStacksEqual(recipe.getRecipeOutput(), output))
                    .collect(Collectors.toList());

            if (!toRemove.isEmpty()) {
                AlloyingRecipes.getRecipes().removeAll(toRemove);
                LOGGER.info("Removed " + toRemove.size() + " Alloying recipe(s) for output: " + output.getDisplayName());
            }
        }
    }

    public static class Infusion {

        public static void addRecipe(String name, ItemStack output, IIngredient input, List<IIngredient> catalysts, int time) {
            if (Objects.isNull(name) || Objects.isNull(output) || Objects.isNull(input) || Objects.isNull(catalysts)) {
                LOGGER.error("Failed to add Infusion recipe: one of the required arguments is null.");
                return;
            }
            if (catalysts.size() != 8) {
                LOGGER.error("Failed to add Infusion recipe '" + name + "': catalyst list must contain exactly 8 elements (use null for empty slots).");
                return;
            }

            InfusionRecipe.Builder builder = InfusionRecipe.Builder.create()
                    .setInput(input.toMcIngredient())
                    .setOutput(output)
                    .setTime(time);

            for (int i = 0; i < catalysts.size(); i++) {
                IIngredient catalyst = catalysts.get(i);
                if (catalyst != null) {
                    builder.addCatalyst(i, catalyst.toMcIngredient());
                }
            }

            builder.build(recipe -> InfusionRecipes.getRecipes().add(recipe), new ResourceLocation("groovyscript", name));
            LOGGER.info("Successfully added Infusion recipe: " + name);
        }

        public static void removeRecipe(ItemStack output) {
            if (Objects.isNull(output) || output.isEmpty()) return;

            List<InfusionRecipe> toRemove = InfusionRecipes.getRecipes().stream()
                    .filter(recipe -> ItemStack.areItemStacksEqual(recipe.getRecipeOutput(), output))
                    .collect(Collectors.toList());

            if (!toRemove.isEmpty()) {
                InfusionRecipes.getRecipes().removeAll(toRemove);
                LOGGER.info("Removed " + toRemove.size() + " Infusion recipe(s) for output: " + output.getDisplayName());
            }
        }
    }
}