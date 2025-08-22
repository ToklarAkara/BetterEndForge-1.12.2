package mod.beethoven92.betterendforge.common.integration.vanilla;

import mod.beethoven92.betterendforge.common.block.material.MetalMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictRegistry {
    public static void registerOres() {
        registerWoodMaterial(ModBlocks.MOSSY_GLOWSHROOM);
        registerWoodMaterial(ModBlocks.LACUGROVE);
        registerWoodMaterial(ModBlocks.END_LOTUS);
        registerWoodMaterial(ModBlocks.PYTHADENDRON);
        registerWoodMaterial(ModBlocks.DRAGON_TREE);
        registerWoodMaterial(ModBlocks.TENANEA);
        registerWoodMaterial(ModBlocks.HELIX_TREE);
        registerWoodMaterial(ModBlocks.UMBRELLA_TREE);
        registerWoodMaterial(ModBlocks.JELLYSHROOM);
        registerWoodMaterial(ModBlocks.LUCERNIA);

        registerMetalMaterial(ModBlocks.TERMINITE);
        registerMetalMaterial(ModBlocks.THALLASIUM);

        register("ingotAethernim", ModItems.AETERNIUM_INGOT);
        register("blockAeternium", ModBlocks.AETERNIUM_BLOCK);
    }

    private static void register(String name, net.minecraft.item.Item item) {
        OreDictionary.registerOre(name, new ItemStack(item));
    }

    private static void register(String name, net.minecraft.block.Block block) {
        if (net.minecraft.item.Item.getItemFromBlock(block) != net.minecraft.init.Items.AIR) {
            OreDictionary.registerOre(name, new ItemStack(block));
        }
    }
    
    private static void registerWoodMaterial(WoodenMaterial material){
        register("logWood", material.log);
        register("logWood", material.bark);
        register("logWood", material.log_stripped);
        register("logWood", material.bark_stripped);
        //register("plankWood", material.planks);
        register("slabWood", material.slab);
        register("stairWood", material.stairs);
        register("bookshelf", material.shelf);
        register(snakeToCamel("bookshelf_"+material.name), material.shelf);
    }

    private static void registerMetalMaterial(MetalMaterial material){
        register(snakeToCamel("ore_"+material.name), material.ore);
        register(snakeToCamel("ingot_"+material.name), material.ingot);
        register(snakeToCamel("block_"+material.name), material.block);
        register(snakeToCamel("nugget_"+material.name), material.nugget);
    }

    public static String snakeToCamel(String snakeCase) {
        if (snakeCase == null || snakeCase.isEmpty()) {
            return snakeCase;
        }

        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = false;

        for (int i = 0; i < snakeCase.length(); i++) {
            char currentChar = snakeCase.charAt(i);

            if (currentChar == '_') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    result.append(Character.toUpperCase(currentChar));
                    capitalizeNext = false;
                } else {
                    result.append(currentChar);
                }
            }
        }

        return result.toString();
    }
}
