package mod.beethoven92.betterendforge.data;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.function.Consumer;

public class AlloyingRecipes {
    private static ArrayList<AlloyingRecipe> recipes;

    public static void init() {
        recipes = new ArrayList<>();
        registerRecipes(recipes::add);
    }

    public static AlloyingRecipe findRecipe(ItemStack in1, ItemStack in2, World world) {
        for (AlloyingRecipe recipe : recipes) {
            InventoryCrafting inventoryCrafting = new InventoryCrafting(new Container()
            {
                public boolean canInteractWith(EntityPlayer playerIn)
                {
                    return false;
                }
            }, 9, 1);
            inventoryCrafting.setInventorySlotContents(0, in1);
            inventoryCrafting.setInventorySlotContents(1, in2);
            if (recipe.matches(inventoryCrafting, world)) {
                return recipe;
            }
        }
        return null;
    }

    private static ResourceLocation rl(String s) {
        return new ResourceLocation(BetterEnd.MOD_ID, s);
    }

    public static void registerRecipes(Consumer<AlloyingRecipe> consumer) {
        consumer.accept(
                new AlloyingRecipe(
                        rl("additional_gold"), "",
                        Ingredient.fromItem(Item.getItemFromBlock(Blocks.GOLD_ORE)),
                        Ingredient.fromItem(Item.getItemFromBlock(Blocks.GOLD_ORE)),
                        new ItemStack(Items.GOLD_INGOT, 3),
                        3.0f, 350
                )
        );
        consumer.accept(
                new AlloyingRecipe(
                        rl("additional_iron"), "",
                        Ingredient.fromItem(Item.getItemFromBlock(Blocks.IRON_ORE)),
                        Ingredient.fromItem(Item.getItemFromBlock(Blocks.IRON_ORE)),
                        new ItemStack(Items.IRON_INGOT, 3),
                        2.1f, 350
                )
        );
        consumer.accept(
                new AlloyingRecipe(
                        rl("additional_thallasium"), "",
                        Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.THALLASIUM.ore)),
                        Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.THALLASIUM.ore)),
                        new ItemStack(ModBlocks.THALLASIUM.ingot, 3),
                        2.1f, 350
                )
        );
        consumer.accept(
                new AlloyingRecipe(
                        rl("terminite_ingot"), "",
                        Ingredient.fromItem(ModItems.ENDER_DUST),
                        Ingredient.fromItem(Items.IRON_INGOT),
                        new ItemStack(ModBlocks.TERMINITE.ingot, 1),
                        4.5f, 600
                )
        );
        consumer.accept(
                new AlloyingRecipe(
                        rl("aeternium_ingot"), "",
                        Ingredient.fromItem(ModBlocks.TERMINITE.ingot),
                        Ingredient.fromItem(Items.DIAMOND),
                        new ItemStack(ModItems.AETERNIUM_INGOT, 1),
                        2.5f, 450
                )
        );

        consumer.accept(
                new AlloyingRecipe(
                        rl("thallasium_ingot"), "",
                        Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.THALLASIUM.ore)),
                        Ingredient.fromStacks(ItemStack.EMPTY),
                        new ItemStack(ModBlocks.THALLASIUM.ingot, 1),
                        0.0f, 100
                )
        );
    }

    public static ArrayList<AlloyingRecipe> getRecipes() {
        return recipes;
    }
}
