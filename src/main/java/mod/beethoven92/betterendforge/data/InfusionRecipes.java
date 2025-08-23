package mod.beethoven92.betterendforge.data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableMap;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.recipes.InfusionRecipe;
import mod.beethoven92.betterendforge.common.rituals.InfusionRitual;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class InfusionRecipes {

	private static ArrayList<InfusionRecipe> recipes;

	public static void init(){
		recipes = new ArrayList<>();
		registerRecipes(recipes::add);
	}

	public static InfusionRecipe findRecipe(InfusionRitual ritual, World world){
		for(InfusionRecipe recipe: recipes){
			if(recipe.matches(ritual.getInvCrafting(), world)){
				return recipe;
			}
		}
		return null;
	}

	private static ResourceLocation rl(String s) {
		return new ResourceLocation(BetterEnd.MOD_ID, s);
	}

	public static void registerRecipes(Consumer<InfusionRecipe> consumer) {

		InfusionRecipe.Builder.create().
				setInput(Items.END_CRYSTAL).
				setOutput(ModItems.ETERNAL_CRYSTAL).
				setTime(250).
				addCatalyst(0, ModItems.ENDER_SHARD).
				addCatalyst(2, ModItems.ENDER_SHARD).
				addCatalyst(4, ModItems.ENDER_SHARD).
				addCatalyst(6, ModItems.ENDER_SHARD).
				addCatalyst(1, ModItems.CRYSTAL_SHARDS).
				addCatalyst(3, ModItems.CRYSTAL_SHARDS).
				addCatalyst(5, ModItems.CRYSTAL_SHARDS).
				addCatalyst(7, ModItems.CRYSTAL_SHARDS).

				build(consumer, rl("eternal_crystal"));

		InfusionRecipe.Builder.create().
				setInput(ModItems.HYDRALUX_PETAL).
				setOutput(ModItems.ENCHANTED_PETAL).
				setTime(300).
				addCatalyst(0, ModItems.CRYSTAL_SHARDS).
				addCatalyst(2, ModItems.CRYSTAL_SHARDS).
				addCatalyst(4, ModItems.CRYSTAL_SHARDS).
				addCatalyst(6, ModItems.CRYSTAL_SHARDS).
				build(consumer, rl("enchanted_petal"));

		InfusionRecipe.Builder.create().
				setInput(Item.getItemFromBlock(ModBlocks.FLAVOLITE.polished)).
				setOutput(Item.getItemFromBlock(ModBlocks.FLAVOLITE_RUNED)).
				setTime(100).
				addCatalyst(0, ModItems.CRYSTAL_SHARDS).
				addCatalyst(2, ModItems.CRYSTAL_SHARDS).
				addCatalyst(4, ModItems.CRYSTAL_SHARDS).
				addCatalyst(6, ModItems.CRYSTAL_SHARDS).
				build(consumer, rl("runed_flavolite"));

		InfusionRecipe.Builder.create().
				setInput(ModBlocks.TERMINITE.chestplate).
				setOutput(ModItems.CRYSTALITE_CHESTPLATE).
				setTime(300).
				addCatalyst(1, ModItems.CRYSTAL_SHARDS).
				addCatalyst(3, ModItems.CRYSTAL_SHARDS).
				addCatalyst(5, ModItems.CRYSTAL_SHARDS).
				addCatalyst(7, ModItems.CRYSTAL_SHARDS).
				addCatalyst(0, ModItems.AMBER_GEM).
				build(consumer, rl("crystal_chestplate"));

		InfusionRecipe.Builder.create().
				setInput(ModBlocks.TERMINITE.leggings).
				setOutput(ModItems.CRYSTALITE_LEGGINGS).
				setTime(225).
				addCatalyst(2, ModItems.CRYSTAL_SHARDS).
				addCatalyst(4, ModItems.CRYSTAL_SHARDS).
				addCatalyst(6, ModItems.CRYSTAL_SHARDS).
				addCatalyst(0, ModItems.AMBER_GEM).
				build(consumer, rl("crystal_leggings"));

		InfusionRecipe.Builder.create().
				setInput(ModBlocks.TERMINITE.helmet).
				setOutput(ModItems.CRYSTALITE_HELMET).
				setTime(150).
				addCatalyst(2, ModItems.CRYSTAL_SHARDS).
				addCatalyst(6, ModItems.CRYSTAL_SHARDS).
				addCatalyst(0, ModItems.AMBER_GEM).
				build(consumer, rl("crystal_helmet"));

		InfusionRecipe.Builder.create().
				setInput(ModBlocks.TERMINITE.boots).
				setOutput(ModItems.CRYSTALITE_BOOTS).
				setTime(150).
				addCatalyst(2, ModItems.CRYSTAL_SHARDS).
				addCatalyst(6, ModItems.CRYSTAL_SHARDS).
				addCatalyst(0, ModItems.AMBER_GEM).
				build(consumer, rl("crystal_boots"));

		InfusionRecipe.Builder.create().
        setInput(Items.BOOK).
        setOutput(enchBook(Enchantments.PROTECTION, 1)).
        setTime(300).
        addCatalyst(0, ModItems.ENCHANTED_PETAL).
        addCatalyst(4, Items.IRON_CHESTPLATE).
        addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage()).
        addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage()).
        addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage()).
        addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage()).
        build(consumer, rl("protection_book"));
		
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.FIRE_PROTECTION, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.BLAZE_ROD)
		.addCatalyst(4, Items.BLAZE_ROD)
		.addCatalyst(6, Items.BLAZE_ROD)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("fire_protection_book"));
	     
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.FEATHER_FALLING, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.FEATHER)
		.addCatalyst(4, Items.FEATHER)
		.addCatalyst(6, Items.FEATHER)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("feather_falling_book"));
	     
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.BLAST_PROTECTION, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Blocks.OBSIDIAN)
		.addCatalyst(4, Blocks.OBSIDIAN)
		.addCatalyst(6, Blocks.OBSIDIAN)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("blast_protection_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.PROJECTILE_PROTECTION, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.IRON_INGOT)
		.addCatalyst(4, Items.SHIELD)
		.addCatalyst(6, Items.IRON_INGOT)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("projectile_protection_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.RESPIRATION, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.WATER_BUCKET)
		.addCatalyst(4, Items.WATER_BUCKET)
		.addCatalyst(6, Items.WATER_BUCKET)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("respiration_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.AQUA_AFFINITY, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.PRISMARINE_CRYSTALS)
		.addCatalyst(4, Items.PRISMARINE_CRYSTALS)
		.addCatalyst(6, Items.PRISMARINE_CRYSTALS)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("aqua_affinity_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.THORNS, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Blocks.CACTUS)
		.addCatalyst(4, Blocks.CACTUS)
		.addCatalyst(6, Blocks.CACTUS)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("thorns_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.DEPTH_STRIDER, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Blocks.WATERLILY)
		.addCatalyst(4, ModBlocks.END_LILY_SEED)
		.addCatalyst(6, Blocks.WATERLILY)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("depth_strider_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.FROST_WALKER, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(4, ModBlocks.ANCIENT_EMERALD_ICE)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("frost_walker_book"));
	    
//		InfusionRecipe.Builder.create()
//		.setInput(Items.BOOK)
//		.setOutput(enchBook(Enchantments.SOUL_SPEED, 1))
//		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
//		.addCatalyst(2, Blocks.SOUL_SAND, Blocks.SOUL_SOIL)
//		.addCatalyst(4, Blocks.SOUL_SAND, Blocks.SOUL_SOIL)
//		.addCatalyst(6, Blocks.SOUL_SAND, Blocks.SOUL_SOIL)
//		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.setGroup("enchantment")
//		.setTime(300)
//		.build(consumer, rl("soul_speed_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.SHARPNESS, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(4, Items.IRON_SWORD)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("sharpness_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.SMITE, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Blocks.YELLOW_FLOWER)
		.addCatalyst(4, Items.GOLD_INGOT)
		.addCatalyst(6, Blocks.YELLOW_FLOWER)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("smite_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.BANE_OF_ARTHROPODS, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.FERMENTED_SPIDER_EYE)
		.addCatalyst(4, Items.IRON_INGOT)
		.addCatalyst(6, Items.FERMENTED_SPIDER_EYE)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("bane_of_arthropods_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.KNOCKBACK, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.REDSTONE)
		.addCatalyst(4, Blocks.PISTON)
		.addCatalyst(6, Items.REDSTONE)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("knockback_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.FIRE_ASPECT, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.BLAZE_POWDER)
		.addCatalyst(4, Items.MAGMA_CREAM)
		.addCatalyst(6, Items.BLAZE_POWDER)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("fire_aspect_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.LOOTING, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.EMERALD)
		.addCatalyst(4, Items.GOLD_INGOT)
		.addCatalyst(6, Items.EMERALD)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("looting_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.SWEEPING, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.GOLDEN_SWORD)
		.addCatalyst(4, Items.IRON_SWORD)
		.addCatalyst(6, Items.GOLDEN_SWORD)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("sweeping_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.EFFICIENCY, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, ModItems.AMBER_GEM)
		.addCatalyst(4, ModItems.AMBER_GEM)
		.addCatalyst(6, ModItems.AMBER_GEM)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("efficiency_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.SILK_TOUCH, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Blocks.WEB)
		.addCatalyst(4, ModItems.ETERNAL_CRYSTAL)
		.addCatalyst(6, Blocks.WEB)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(375)
		.build(consumer, rl("silk_touch_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.UNBREAKING, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.DIAMOND)
		.addCatalyst(4, Items.DIAMOND)
		.addCatalyst(6, Items.DIAMOND)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("unbreaking_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.FORTUNE, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.EMERALD)
		.addCatalyst(4, Items.RABBIT_FOOT)
		.addCatalyst(6, Items.EMERALD)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("fortune_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.POWER, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, ModItems.AMBER_GEM)
		.addCatalyst(4, Items.DIAMOND_SWORD)
		.addCatalyst(6, ModItems.AMBER_GEM)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("power_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.PUNCH, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.CHORUS_FRUIT_POPPED)
		.addCatalyst(4, Items.SPECTRAL_ARROW)
		.addCatalyst(6, Items.CHORUS_FRUIT_POPPED)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("punch_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.FLAME, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.BLAZE_POWDER)
		.addCatalyst(4, Items.SPECTRAL_ARROW)
		.addCatalyst(6, Items.BLAZE_POWDER)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("flame_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.INFINITY, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.SPECTRAL_ARROW)
		.addCatalyst(4, ModItems.ETERNAL_CRYSTAL)
		.addCatalyst(6, Items.SPECTRAL_ARROW)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(375)
		.build(consumer, rl("infinity_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.LUCK_OF_THE_SEA, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.EMERALD)
		.addCatalyst(4, Items.FISHING_ROD)
		.addCatalyst(6, Items.EMERALD)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("luck_of_sea_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.LURE, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.GOLD_NUGGET)
		.addCatalyst(4, Items.FISHING_ROD)
		.addCatalyst(6, Items.GOLD_NUGGET)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(300)
		.build(consumer, rl("lure_book"));
	    
//		InfusionRecipe.Builder.create()
//		.setInput(Items.BOOK)
//		.setOutput(enchBook(Enchantments.LOYALTY, 1))
//		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
//		.addCatalyst(2, Items.ENDER_EYE)
//		.addCatalyst(4, Items.HEART_OF_THE_SEA)
//		.addCatalyst(6, Items.ENDER_EYE)
//		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.setGroup("enchantment")
//		.setTime(375)
//		.build(consumer, rl("loyalty_book"));
//
//		InfusionRecipe.Builder.create()
//		.setInput(Items.BOOK)
//		.setOutput(enchBook(Enchantments.IMPALING, 1))
//		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
//		.addCatalyst(2, Items.PRISMARINE_SHARD)
//		.addCatalyst(4, Items.IRON_SWORD)
//		.addCatalyst(6, Items.PRISMARINE_SHARD)
//		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.setGroup("enchantment")
//		.setTime(300)
//		.build(consumer, rl("impaling_book"));
//
//		InfusionRecipe.Builder.create()
//		.setInput(Items.BOOK)
//		.setOutput(enchBook(Enchantments.RIPTIDE, 1))
//		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
//		.addCatalyst(2, Items.LEAD)
//		.addCatalyst(4, Items.HEART_OF_THE_SEA)
//		.addCatalyst(6, Items.LEAD)
//		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.setGroup("enchantment")
//		.setTime(375)
//		.build(consumer, rl("riptide_book"));
//
//		InfusionRecipe.Builder.create()
//		.setInput(Items.BOOK)
//		.setOutput(enchBook(Enchantments.CHANNELING, 1))
//		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
//		.addCatalyst(2, Items.CHAIN)
//		.addCatalyst(4, Items.HEART_OF_THE_SEA)
//		.addCatalyst(6, Items.CHAIN)
//		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.setGroup("enchantment")
//		.setTime(375)
//		.build(consumer, rl("channeling_book"));
//
//		InfusionRecipe.Builder.create()
//		.setInput(Items.BOOK)
//		.setOutput(enchBook(Enchantments.MULTISHOT, 1))
//		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
//		.addCatalyst(2, Items.ARROW)
//		.addCatalyst(4, Items.SPECTRAL_ARROW)
//		.addCatalyst(6, Items.ARROW)
//		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.setGroup("enchantment")
//		.setTime(300)
//		.build(consumer, rl("multishot_book"));
//
//		InfusionRecipe.Builder.create()
//		.setInput(Items.BOOK)
//		.setOutput(enchBook(Enchantments.QUICK_CHARGE, 1))
//		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
//		.addCatalyst(2, Items.QUARTZ)
//		.addCatalyst(4, Items.GLOWSTONE_DUST)
//		.addCatalyst(6, Items.QUARTZ)
//		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.setGroup("enchantment")
//		.setTime(300)
//		.build(consumer, rl("quick_charge_book"));
//
//		InfusionRecipe.Builder.create()
//		.setInput(Items.BOOK)
//		.setOutput(enchBook(Enchantments.PIERCING, 1))
//		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
//		.addCatalyst(2, Items.GLOWSTONE_DUST)
//		.addCatalyst(4, Items.SPECTRAL_ARROW)
//		.addCatalyst(6, Items.GLOWSTONE_DUST)
//		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
//		.setGroup("enchantment")
//		.setTime(300)
//		.build(consumer, rl("piercing_book"));
	    
		InfusionRecipe.Builder.create()
		.setInput(Items.BOOK)
		.setOutput(enchBook(Enchantments.MENDING, 1))
		.addCatalyst(0, ModItems.ENCHANTED_PETAL)
		.addCatalyst(2, Items.EXPERIENCE_BOTTLE)
		.addCatalyst(4, Blocks.ANVIL)
		.addCatalyst(6, Items.EXPERIENCE_BOTTLE)
		.addCatalyst(1, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(3, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(5, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.addCatalyst(7, Items.DYE, EnumDyeColor.BLUE.getDyeDamage())
		.setGroup("enchantment")
		.setTime(375)
		.build(consumer, rl("mending_book"));
	}
	
	private static ItemStack enchBook(Enchantment enchantment, int level) {
		ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantmentHelper.setEnchantments(ImmutableMap.of(enchantment, level), book);
		return book;
	}

    public static List<InfusionRecipe> getRecipes() {
		return recipes;
    }
}
