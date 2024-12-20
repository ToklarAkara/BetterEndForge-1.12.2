package mod.beethoven92.betterendforge.data;

import java.util.ArrayList;
import java.util.function.Consumer;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.material.ColoredMaterial;
import mod.beethoven92.betterendforge.common.block.material.MetalMaterial;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes
{

	private static ResourceLocation rl(String s) {
		return new ResourceLocation(BetterEnd.MOD_ID, s);
	}

	public static class ShapedRecipeBuilder{
		ItemStack res;

		ArrayList<Pair<Character, Object>> keys = new ArrayList<>();
		ArrayList<String> lines = new ArrayList<>();

		String group = null;
		public static ShapedRecipeBuilder shapedRecipe(Block res, int count){
			ShapedRecipeBuilder recipeBuilder = new ShapedRecipeBuilder();
			recipeBuilder.res = new ItemStack(res, count, 0);
			return recipeBuilder;
		}

		public static ShapedRecipeBuilder shapedRecipe(Block res){
			return shapedRecipe(res, 1);
		}

		public static ShapedRecipeBuilder shapedRecipe(Item res, int count){
			ShapedRecipeBuilder recipeBuilder = new ShapedRecipeBuilder();
			recipeBuilder.res = new ItemStack(res, count, 0);
			return recipeBuilder;
		}

		public static ShapedRecipeBuilder shapedRecipe(Item res){
			return shapedRecipe(res, 1);
		}

		public ShapedRecipeBuilder key(Character cha, Object val){
			keys.add(new Pair<>(cha, val));
			return this;
		}

		public ShapedRecipeBuilder patternLine(String line){
			lines.add(line);
			return this;
		}

		public ShapedRecipeBuilder setGroup(String group){
			this.group = group;
			return this;
		}

		public ShapedRecipeBuilder addCriterion(String str, Object dummy){
			return this;
		}

		public void build(Object dummy){
			ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(res.getItem());
			build(dummy, resourcelocation);
		}

		public void build(Object dummy, ResourceLocation name){
			Object[] components = new Object[lines.size()+keys.size()*2];
			for(int i=0;i<lines.size();i++){
				components[i]=lines.get(i);
			}
			for(int i = 0;i<keys.size();i++){
				components[lines.size()+2*i]=keys.get(i).first;
				components[lines.size()+2*i+1]=keys.get(i).second;
			}
			if(group==null){
				GameRegistry.addShapedRecipe(name, null, res, components);
			}else{
				GameRegistry.addShapedRecipe(name, new ResourceLocation(group), res, components);
			}
		}
	}

	public static class ShapelessRecipeBuilder{
		ItemStack res;

		ArrayList<Ingredient> ingrs = new ArrayList<>();

		String group;
		public static ShapelessRecipeBuilder shapelessRecipe(Block res, int count){
			ShapelessRecipeBuilder recipeBuilder = new ShapelessRecipeBuilder();
			recipeBuilder.res = new ItemStack(res, count, 0);
			return recipeBuilder;
		}

		public static ShapelessRecipeBuilder shapelessRecipe(Block res){
			return shapelessRecipe(res, 1);
		}

		public static ShapelessRecipeBuilder shapelessRecipe(Item res, int count){
			ShapelessRecipeBuilder recipeBuilder = new ShapelessRecipeBuilder();
			recipeBuilder.res = new ItemStack(res, count, 0);
			return recipeBuilder;
		}

		public static ShapelessRecipeBuilder shapelessRecipe(ItemStack res){
			ShapelessRecipeBuilder recipeBuilder = new ShapelessRecipeBuilder();
			recipeBuilder.res = res;
			return recipeBuilder;
		}

		public static ShapelessRecipeBuilder shapelessRecipe(Item res){
			return shapelessRecipe(res, 1);
		}

		public ShapelessRecipeBuilder addIngredient(Item val){
			ingrs.add(Ingredient.fromItem(val));
			return this;
		}

		public ShapelessRecipeBuilder addIngredient(Block val){
			ingrs.add(Ingredient.fromItem(ItemBlock.getItemFromBlock(val)));
			return this;
		}

		public ShapelessRecipeBuilder addIngredient(ItemStack val){
			ingrs.add(Ingredient.fromStacks(val));
			return this;
		}

		public ShapelessRecipeBuilder setGroup(String group){
			this.group = group;
			return this;
		}

		public ShapelessRecipeBuilder addCriterion(String str, Object dummy){
			return this;
		}

		public void build(Object dummy){
			ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(res.getItem());
			build(dummy, resourcelocation);
		}

		public void build(Object dummy, ResourceLocation name){
			if(group==null){
				GameRegistry.addShapelessRecipe(name, null, res, ingrs.toArray(new Ingredient[0]));
			}else{
				GameRegistry.addShapelessRecipe(name, new ResourceLocation(group), res, ingrs.toArray(new Ingredient[0]));
			}

		}
	}

	public static Object hasItem(Object dummy){
		return null;
	}

	public static void registerRecipes()
	{
		Object consumer = null;
		// BLOCKS
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.ENDER_BLOCK).key('#', Items.ENDER_PEARL).patternLine("##").patternLine("##").addCriterion("has_ender_pearl", hasItem(Items.ENDER_PEARL)).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(Items.ENDER_PEARL, 4).addIngredient(ModBlocks.ENDER_BLOCK).addCriterion("has_ender_block", hasItem(ModBlocks.ENDER_BLOCK)).build(consumer, rl("ender_pearl_from_ender_block"));

	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.HYDRALUX_PETAL_BLOCK).key('#', ModItems.HYDRALUX_PETAL).patternLine("##").patternLine("##").addCriterion("has_hydralux_petal", hasItem(ModItems.HYDRALUX_PETAL)).build(consumer);

	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.AMBER_BLOCK).key('#', ModItems.AMBER_GEM).patternLine("##").patternLine("##").addCriterion("has_amber_gem", hasItem(ModItems.AMBER_GEM)).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(ModItems.AMBER_GEM, 4).addIngredient(ModBlocks.AMBER_BLOCK).addCriterion("has_amber_block", hasItem(ModBlocks.AMBER_BLOCK)).build(consumer, rl("amber_gem_from_amber_block"));

	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.AURORA_CRYSTAL).key('#', ModItems.CRYSTAL_SHARDS).patternLine("##").patternLine("##").addCriterion("has_crystal_shard", hasItem(ModItems.CRYSTAL_SHARDS)).build(consumer);

	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.END_LOTUS.log).key('#', ModBlocks.END_LOTUS_STEM).patternLine("##").patternLine("##").addCriterion("has_end_lotus_stem", hasItem(ModBlocks.END_LOTUS_STEM)).build(consumer);

	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.END_STONE_FURNACE).key('#', Blocks.END_STONE).patternLine("###").patternLine("# #").patternLine("###").setGroup("end_stone_furnaces").addCriterion("has_end_stone", hasItem(Blocks.END_STONE)).build(consumer);

	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.END_STONE_SMELTER).key('#', Blocks.END_BRICKS).key('V', Blocks.FURNACE).key('T', ModBlocks.THALLASIUM.ingot).patternLine("T#T").patternLine("V V").patternLine("T#T").addCriterion("has_end_stone_bricks", hasItem(Blocks.END_BRICKS)).build(consumer);

	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.RESPAWN_OBELISK).key('C', ModBlocks.AURORA_CRYSTAL).key('S', ModItems.ETERNAL_CRYSTAL).key('A', ModBlocks.AMBER_BLOCK).patternLine("CSC").patternLine("CSC").patternLine("AAA").addCriterion("has_amber_block", hasItem(ModBlocks.AMBER_BLOCK)).build(consumer);

	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.DENSE_EMERALD_ICE).key('#', ModBlocks.EMERALD_ICE).patternLine("##").patternLine("##").addCriterion("has_emerald_ice", hasItem(ModBlocks.EMERALD_ICE)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.ANCIENT_EMERALD_ICE).key('#', ModBlocks.DENSE_EMERALD_ICE).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_dense_emerald_ice", hasItem(ModBlocks.DENSE_EMERALD_ICE)).build(consumer);

	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.IRON_BULB_LANTERN).key('C', Blocks.IRON_BARS).key('I', Items.IRON_INGOT).key('#', ModItems.GLOWING_BULB).patternLine("C").patternLine("I").patternLine("#").addCriterion("has_glowing_bulb", hasItem(ModItems.GLOWING_BULB)).build(consumer);

	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.IRON_CHANDELIER).key('I', ModItems.LUMECORN_ROD).key('#', Items.IRON_INGOT).patternLine("I#I").patternLine(" # ").setGroup("end_metal_chandelier").addCriterion("has_iron_ingot", hasItem(Items.IRON_INGOT)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.GOLD_CHANDELIER).key('I', ModItems.LUMECORN_ROD).key('#', Items.GOLD_INGOT).patternLine("I#I").patternLine(" # ").setGroup("end_metal_chandelier").addCriterion("has_gold_ingot", hasItem(Items.GOLD_INGOT)).build(consumer);

	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.MISSING_TILE, 4).key('#', ModBlocks.VIOLECITE.stone).key('P', Blocks.PURPUR_BLOCK).patternLine("#P").patternLine("P#").addCriterion("has_violecite", hasItem(ModBlocks.VIOLECITE.stone)).build(consumer);

	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.FILALUX_LANTERN).key('#', ModBlocks.FILALUX).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_filalux", hasItem(ModBlocks.FILALUX)).build(consumer);

	    // DYES
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage())).addIngredient(ModBlocks.BLUE_VINE_SEED).setGroup("blue_dye").addCriterion("has_blue_vine_seed", hasItem(ModBlocks.BLUE_VINE_SEED)).build(consumer, rl("blue_dye_from_blue_vine_seed"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.CYAN.getDyeDamage())).addIngredient(ModBlocks.CREEPING_MOSS).setGroup("cyan_dye").addCriterion("has_creeping_moss", hasItem(ModBlocks.CREEPING_MOSS)).build(consumer, rl("cyan_dye_from_creeping_moss"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.CYAN.getDyeDamage())).addIngredient(ModBlocks.CYAN_MOSS).setGroup("cyan_dye").addCriterion("has_cyan_moss", hasItem(ModBlocks.CYAN_MOSS)).build(consumer, rl("cyan_dye_from_cyan_moss"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.YELLOW.getDyeDamage())).addIngredient(ModBlocks.UMBRELLA_MOSS).setGroup("yellow_dye").addCriterion("has_umbrella_moss", hasItem(ModBlocks.UMBRELLA_MOSS)).build(consumer, rl("yellow_dye_from_umbrella_moss"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).addIngredient(ModBlocks.UMBRELLA_MOSS_TALL).setGroup("yellow_dye").addCriterion("has_umbrella_moss_tall", hasItem(ModBlocks.UMBRELLA_MOSS_TALL)).build(consumer, rl("yellow_dye_from_umbrella_moss_tall"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.BLACK.getDyeDamage())).addIngredient(ModBlocks.SHADOW_PLANT).setGroup("black_dye").addCriterion("has_shadow_plant", hasItem(ModBlocks.SHADOW_PLANT)).build(consumer, rl("black_dye_from_shadow_plant"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.PURPLE.getDyeDamage())).addIngredient(ModBlocks.PURPLE_POLYPORE).setGroup("purple_dye").addCriterion("has_purple_polypore", hasItem(ModBlocks.PURPLE_POLYPORE)).build(consumer, rl("purple_dye_from_purple_polypore"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.GRAY.getDyeDamage())).addIngredient(ModBlocks.TAIL_MOSS).setGroup("gray_dye").addCriterion("has_tail_moss", hasItem(ModBlocks.TAIL_MOSS)).build(consumer, rl("gray_dye_from_tail_moss"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.MAGENTA.getDyeDamage())).addIngredient(ModBlocks.BUSHY_GRASS).setGroup("magenta_dye").addCriterion("has_bushy_grass", hasItem(ModBlocks.BUSHY_GRASS)).build(consumer, rl("magenta_dye_from_bushy_grass"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.PINK.getDyeDamage())).addIngredient(ModBlocks.TWISTED_MOSS).setGroup("pink_dye").addCriterion("has_twisted_moss", hasItem(ModBlocks.TWISTED_MOSS)).build(consumer, rl("pink_dye_from_twisted_moss"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage())).addIngredient(ModItems.HYDRALUX_PETAL).setGroup("white_dye").addCriterion("has_hydralux_petal", hasItem(ModItems.HYDRALUX_PETAL)).build(consumer, rl("white_dye_from_hydralux_petal"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.PURPLE.getDyeDamage())).addIngredient(ModBlocks.TWISTED_UMBRELLA_MOSS).setGroup("purple_dye").addCriterion("has_twisted_umbrella_moss", hasItem(ModBlocks.TWISTED_UMBRELLA_MOSS)).build(consumer, rl("purple_dye_from_twisted_umbrella_moss"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.PURPLE.getDyeDamage())).addIngredient(ModBlocks.TWISTED_UMBRELLA_MOSS_TALL).setGroup("purple_dye").addCriterion("has_twisted_umbrella_moss_tall", hasItem(ModBlocks.TWISTED_UMBRELLA_MOSS_TALL)).build(consumer, rl("purple_dye_from_twisted_umbrella_moss_tall"));

		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.RED.getDyeDamage())).addIngredient(ModBlocks.CHARNIA_RED).setGroup("red_dye").addCriterion("has_red_charnia", hasItem(ModBlocks.CHARNIA_RED)).build(consumer, rl("red_dye_from_red_charnia"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.PURPLE.getDyeDamage())).addIngredient(ModBlocks.CHARNIA_PURPLE).setGroup("purple_dye").addCriterion("has_purple_charnia", hasItem(ModBlocks.CHARNIA_PURPLE)).build(consumer, rl("purple_dye_from_purple_charnia"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.ORANGE.getDyeDamage())).addIngredient(ModBlocks.CHARNIA_ORANGE).setGroup("orange_dye").addCriterion("has_orange_charnia", hasItem(ModBlocks.CHARNIA_ORANGE)).build(consumer, rl("orange_dye_from_orange_charnia"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.LIGHT_BLUE.getDyeDamage())).addIngredient(ModBlocks.CHARNIA_LIGHT_BLUE).setGroup("light_blue_dye").addCriterion("has_light_blue_charnia", hasItem(ModBlocks.CHARNIA_LIGHT_BLUE)).build(consumer, rl("light_blue_dye_from_light_blue_charnia"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.CYAN.getDyeDamage())).addIngredient(ModBlocks.CHARNIA_CYAN).setGroup("cyan_dye").addCriterion("has_cyan_charnia", hasItem(ModBlocks.CHARNIA_CYAN)).build(consumer, rl("cyan_dye_from_cyan_charnia"));
		ShapelessRecipeBuilder.shapelessRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.GREEN.getDyeDamage())).addIngredient(ModBlocks.CHARNIA_GREEN).setGroup("green_dye").addCriterion("has_green_charnia", hasItem(ModBlocks.CHARNIA_GREEN)).build(consumer, rl("green_dye_from_green_charnia"));

		// ITEMS
		ShapedRecipeBuilder.shapedRecipe(Items.PAPER, 3).key('#', ModItems.END_LILY_LEAF_DRIED).patternLine("###").addCriterion("has_end_lily_leaf_dried", hasItem(ModItems.END_LILY_LEAF_DRIED)).build(consumer, rl("paper_from_end_lily_leaf_dried"));
		ShapelessRecipeBuilder.shapelessRecipe(Items.STICK, 2).addIngredient(ModBlocks.NEEDLEGRASS).addCriterion("has_needlegrass", hasItem(ModBlocks.NEEDLEGRASS)).build(consumer, rl("stick_from_needlegrass"));
		ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.SHADOW_BERRY, 4).addIngredient(ModItems.SHADOW_BERRY_RAW).addCriterion("has_shadow_berry_raw", hasItem(ModItems.SHADOW_BERRY_RAW)).build(consumer, rl("shadow_berry_from_shadow_berry_raw"));
//		ShapelessRecipeBuilder.shapelessRecipe(ModItems.SWEET_BERRY_JELLY).addIngredient(ModItems.GELATINE).addIngredient(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER).getItem()).addIngredient(Items.SUGAR).addIngredient(Items.SWEET_BERRIES).addCriterion("has_gelatine", hasItem(ModItems.GELATINE)).build(consumer);
//		ShapelessRecipeBuilder.shapelessRecipe(ModItems.SHADOW_BERRY_JELLY).addIngredient(ModItems.GELATINE).addIngredient(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER).getItem()).addIngredient(Items.SUGAR).addIngredient(ModItems.SHADOW_BERRY_COOKED).addCriterion("has_gelatine", hasItem(ModItems.GELATINE)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(ModItems.AMBER_GEM).key('#', ModItems.RAW_AMBER).patternLine("##").patternLine("##").addCriterion("has_raw_amber", hasItem(ModItems.RAW_AMBER)).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(Items.GUNPOWDER).addIngredient(ModItems.CRYSTALLINE_SULPHUR).addIngredient(Items.COAL).addIngredient(new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage())).addCriterion("has_crystalline_sulphur", hasItem(ModItems.CRYSTALLINE_SULPHUR)).build(consumer, rl("gunpowder_from_sulphur"));
	    ShapedRecipeBuilder.shapedRecipe(ModItems.GUIDE_BOOK).key('D', ModItems.ENDER_DUST).key('B', Items.BOOK).key('C', ModItems.CRYSTAL_SHARDS).patternLine("D").patternLine("B").patternLine("C").addCriterion("has_crystal_shards", hasItem(ModItems.CRYSTAL_SHARDS)).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(ModItems.LEATHER_STRIPE, 3).addIngredient(Items.LEATHER).addCriterion("has_leather", hasItem(Items.LEATHER)).build(consumer, rl("leather_to_stripes"));
	    ShapelessRecipeBuilder.shapelessRecipe(Items.LEATHER).addIngredient(ModItems.LEATHER_STRIPE).addIngredient(ModItems.LEATHER_STRIPE).addIngredient(ModItems.LEATHER_STRIPE).addCriterion("has_leather_stripe", hasItem(ModItems.LEATHER_STRIPE)).build(consumer, rl("stripes_to_leather"));
	    ShapelessRecipeBuilder.shapelessRecipe(ModItems.LEATHER_WRAPPED_STICK).addIngredient(Items.STICK).addIngredient(ModItems.LEATHER_STRIPE).addCriterion("has_leather_stripe", hasItem(ModItems.LEATHER_STRIPE)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(Items.ENDER_EYE).key('S', ModItems.CRYSTAL_SHARDS).key('A', ModItems.AMBER_GEM).key('P', Items.ENDER_PEARL).patternLine("SAS").patternLine("APA").patternLine("SAS").addCriterion("has_amber_gem", hasItem(ModItems.AMBER_GEM)).build(consumer, rl("ender_eye_from_amber_gem"));
	    ShapedRecipeBuilder.shapedRecipe(Items.STRING, 6).key('#', ModItems.SILK_FIBER).patternLine("#").patternLine("#").patternLine("#").addCriterion("has_silk_fiber", hasItem(ModItems.SILK_FIBER)).build(consumer, rl("fiber_string"));

//	    // LANTERNS
//	    registerLantern(ModBlocks.ANDESITE_LANTERN, Blocks.ANDESITE_SLAB, "andesite");
//	    registerLantern(ModBlocks.DIORITE_LANTERN, Blocks.DIORITE_SLAB, "diorite");
//	    registerLantern(ModBlocks.GRANITE_LANTERN, Blocks.GRANITE_SLAB, "granite");
//	    registerLantern(ModBlocks.QUARTZ_LANTERN, Blocks.QUARTZ_SLAB, "quartz");
//	    registerLantern(ModBlocks.PURPUR_LANTERN, Blocks.PURPUR_SLAB, "purpur");
//	    registerLantern(ModBlocks.END_STONE_LANTERN, Blocks.END_STONE_BRICK_SLAB, "end_stone");
//	    registerLantern(ModBlocks.BLACKSTONE_LANTERN, Blocks.BLACKSTONE_SLAB, "blackstone");

//	    // PEDESTALS
//		registerPedestal(ModBlocks.QUARTZ_PEDESTAL, Blocks.QUARTZ_SLAB, Blocks.QUARTZ_PILLAR, "quartz");
//		registerPedestal(ModBlocks.PURPUR_PEDESTAL, Blocks.PURPUR_SLAB, Blocks.PURPUR_PILLAR, "purpur");
//		registerPedestal(ModBlocks.ANDESITE_PEDESTAL, Blocks.POLISHED_ANDESITE_SLAB, Blocks.POLISHED_ANDESITE, "andesite");
//		registerPedestal(ModBlocks.DIORITE_PEDESTAL, Blocks.POLISHED_DIORITE_SLAB, Blocks.POLISHED_DIORITE, "diorite");
//		registerPedestal(ModBlocks.GRANITE_PEDESTAL, Blocks.POLISHED_GRANITE_SLAB, Blocks.POLISHED_GRANITE, "granite");

		ShapedRecipeBuilder.shapedRecipe(ModBlocks.INFUSION_PEDESTAL).key('O', Items.ENDER_PEARL).key('Y', Items.ENDER_EYE).key('#', Blocks.OBSIDIAN).patternLine(" Y ").patternLine("O#O").patternLine(" # ").addCriterion("has_ender_pearl", hasItem(Items.ENDER_PEARL)).addCriterion("has_ender_eye", hasItem(Items.ENDER_EYE)).addCriterion("has_obsidian", hasItem(Blocks.OBSIDIAN)).build(consumer);

		// FURNACE
		cookFood(ModItems.END_FISH_RAW, ModItems.END_FISH_COOKED, 0.35F, 200);
		cookFood(ModItems.END_LILY_LEAF, ModItems.END_LILY_LEAF_DRIED, 0.35F, 200);
		cookFood(ModItems.SHADOW_BERRY_RAW, ModItems.SHADOW_BERRY_COOKED, 0.35F, 200);
		cookFood(ModItems.CHORUS_MUSHROOM_RAW, ModItems.CHORUS_MUSHROOM_COOKED, 0.35F, 200);
		GameRegistry.addSmelting(ModBlocks.ENDSTONE_DUST, new ItemStack(Blocks.GLASS, 1, 0), 0.35F);
		GameRegistry.addSmelting(ModBlocks.JELLYSHROOM_CAP_PURPLE, new ItemStack(Items.SLIME_BALL, 1, 0), 0.35F);
		GameRegistry.addSmelting(ModBlocks.MENGER_SPONGE_WET, new ItemStack(ModBlocks.MENGER_SPONGE, 1, 0), 0.35F);
		// ARMORS AND TOOLS
		makeIngotAndBlockRecipes(ModBlocks.AETERNIUM_BLOCK, ModItems.AETERNIUM_INGOT, "aeternium");

		makeHammerRecipe(ModItems.GOLDEN_HAMMER, Items.GOLD_INGOT, "gold");
		makeHammerRecipe(ModItems.IRON_HAMMER, Items.IRON_INGOT, "iron");
		makeHammerRecipe(ModItems.DIAMOND_HAMMER, Items.DIAMOND, "diamond");

//		// SMITHING TABLE
//		makeSmithingRecipe(ModItems.DIAMOND_HAMMER, Items., ModItems.NETHERITE_HAMMER);
//
//		makeSmithingRecipe(ItemBlock.getItemFromBlock(ModBlocks.THALLASIUM.anvil), ItemBlock.getItemFromBlock(ModBlocks.TERMINITE.block), ItemBlock.getItemFromBlock(ModBlocks.TERMINITE.anvil));
//		makeSmithingRecipe(ItemBlock.getItemFromBlock(ModBlocks.TERMINITE.anvil), ItemBlock.getItemFromBlock(ModBlocks.AETERNIUM_BLOCK), ItemBlock.getItemFromBlock(ModBlocks.AETERNIUM_ANVIL));
//
//		makeSmithingRecipe(ModBlocks.TERMINITE.ingot, ModItems.LEATHER_WRAPPED_STICK, ModItems.AETERNIUM_SWORD_HANDLE);
//		makeSmithingRecipe(ModItems.AETERNIUM_SWORD_BLADE, ModItems.AETERNIUM_SWORD_HANDLE, ModItems.AETERNIUM_SWORD);
//		makeSmithingRecipe(ModItems.AETERNIUM_PICKAXE_HEAD, ModItems.LEATHER_WRAPPED_STICK, ModItems.AETERNIUM_PICKAXE);
//		makeSmithingRecipe(ModItems.AETERNIUM_AXE_HEAD, ModItems.LEATHER_WRAPPED_STICK, ModItems.AETERNIUM_AXE);
//		makeSmithingRecipe(ModItems.AETERNIUM_SHOVEL_HEAD, ModItems.LEATHER_WRAPPED_STICK, ModItems.AETERNIUM_SHOVEL);
//		makeSmithingRecipe(ModItems.AETERNIUM_HOE_HEAD, ModItems.LEATHER_WRAPPED_STICK, ModItems.AETERNIUM_HOE);
//		makeSmithingRecipe(ModItems.AETERNIUM_HAMMER_HEAD, ModItems.LEATHER_WRAPPED_STICK, ModItems.AETERNIUM_HAMMER);
//		makeSmithingRecipe(ModBlocks.TERMINITE.helmet, ModItems.AETERNIUM_INGOT, ModItems.AETERNIUM_HELMET);
//		makeSmithingRecipe(ModBlocks.TERMINITE.chestplate, ModItems.AETERNIUM_INGOT, ModItems.AETERNIUM_CHESTPLATE);
//		makeSmithingRecipe(ModBlocks.TERMINITE.leggings, ModItems.AETERNIUM_INGOT, ModItems.AETERNIUM_LEGGINGS);
//		makeSmithingRecipe(ModBlocks.TERMINITE.boots, ModItems.AETERNIUM_INGOT, ModItems.AETERNIUM_BOOTS);


		// NEON CACTUS
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.NEON_CACTUS_BLOCK).key('#', ModBlocks.NEON_CACTUS).patternLine("##").patternLine("##").addCriterion("has_neon_cactus", hasItem(ModBlocks.NEON_CACTUS)).build(consumer);
	    //ShapedRecipeBuilder.shapedRecipe(ModBlocks.NEON_CACTUS_BLOCK_SLAB, 6).key('#', ModBlocks.NEON_CACTUS_BLOCK).patternLine("###").addCriterion("has_neon_cactus_block", hasItem(ModBlocks.NEON_CACTUS_BLOCK)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(ModBlocks.NEON_CACTUS_BLOCK_STAIRS, 4).key('#', ModBlocks.NEON_CACTUS_BLOCK).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_neon_cactus_block", hasItem(ModBlocks.NEON_CACTUS_BLOCK)).build(consumer);

		// WOODEN MATERIALS
		makeWoodenMaterialRecipes(ModBlocks.MOSSY_GLOWSHROOM);
		makeWoodenMaterialRecipes(ModBlocks.LACUGROVE);
		makeWoodenMaterialRecipes(ModBlocks.END_LOTUS);
		makeWoodenMaterialRecipes(ModBlocks.PYTHADENDRON);
		makeWoodenMaterialRecipes(ModBlocks.DRAGON_TREE);
		makeWoodenMaterialRecipes(ModBlocks.TENANEA);
		makeWoodenMaterialRecipes(ModBlocks.HELIX_TREE);
		makeWoodenMaterialRecipes(ModBlocks.UMBRELLA_TREE);
		makeWoodenMaterialRecipes(ModBlocks.JELLYSHROOM);
		makeWoodenMaterialRecipes(ModBlocks.LUCERNIA);

		// STONE MATERIALS
		makeStoneMaterialRecipes(ModBlocks.FLAVOLITE);
		makeStoneMaterialRecipes(ModBlocks.VIOLECITE);
		makeStoneMaterialRecipes(ModBlocks.SULPHURIC_ROCK);
		makeStoneMaterialRecipes(ModBlocks.VIRID_JADESTONE);
		makeStoneMaterialRecipes(ModBlocks.AZURE_JADESTONE);
		makeStoneMaterialRecipes(ModBlocks.SANDY_JADESTONE);
		makeStoneMaterialRecipes(ModBlocks.UMBRALITH);

		// METAL MATERIALS
		makeMetalMaterialRecipes(ModBlocks.THALLASIUM);
		makeMetalMaterialRecipes(ModBlocks.TERMINITE);

		// COLORED MATERIALS
		makeColoredMaterialRecipes(ModBlocks.HYDRALUX_PETAL_BLOCK_COLORED);
		makeColoredMaterialRecipes(ModBlocks.IRON_BULB_LANTERN_COLORED);
	}

	private static void makeWoodenMaterialRecipes(WoodenMaterial material)
	{
		Object consumer = null;
		ShapelessRecipeBuilder.shapelessRecipe(material.planks, 4).addIngredient(material.log).setGroup("end_planks").addCriterion("has_logs", hasItem(material.logItemTag)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(material.stairs, 4).key('#', material.planks).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("end_planks__stairs").addCriterion("has_planks", hasItem(material.planks)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(material.slab, 6).key('#', material.planks).patternLine("###").setGroup("end_planks_slabs").addCriterion("has_planks", hasItem(material.planks)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.fence, 3).key('#', Items.STICK).key('W', material.planks).patternLine("W#W").patternLine("W#W").setGroup("end_planks_fences").addCriterion("has_planks", hasItem(material.planks)).build(consumer);
	    //ShapedRecipeBuilder.shapedRecipe(material.gate).key('#', Items.STICK).key('W', material.planks).patternLine("#W#").patternLine("#W#").setGroup("end_planks_gates").addCriterion("has_planks", hasItem(material.planks)).build(consumer);
	    //ShapelessRecipeBuilder.shapelessRecipe(material.button).addIngredient(material.planks).setGroup("end_planks_buttons").addCriterion("has_planks", hasItem(material.planks)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.pressurePlate).key('#', material.planks).patternLine("##").setGroup("end_planks_plates").addCriterion("has_planks", hasItem(material.planks)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.trapdoor, 2).key('#', material.planks).patternLine("###").patternLine("###").setGroup("end_trapdoors").addCriterion("has_planks", hasItem(material.planks)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.door, 3).key('#', material.planks).patternLine("##").patternLine("##").patternLine("##").setGroup("end_doors").addCriterion("has_planks", hasItem(material.planks)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.bark, 3).key('#', material.log).patternLine("##").patternLine("##").addCriterion("has_log", hasItem(material.log)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.bark_stripped, 3).key('#', material.log_stripped).patternLine("##").patternLine("##").addCriterion("has_log_stripped", hasItem(material.log_stripped)).build(consumer);

	    //ShapedRecipeBuilder.shapedRecipe(material.composter, 1).key('#', material.slab).patternLine("# #").patternLine("# #").patternLine("###").setGroup("end_composters").addCriterion("has_slabs", hasItem(material.slab)).build(consumer);
//	    ShapedRecipeBuilder.shapedRecipe(material.craftingTable, 1).key('#', material.planks).patternLine("##").patternLine("##").setGroup("end_crafting_tables").addCriterion("has_planks", hasItem(material.planks)).build(consumer);
//	    //ShapedRecipeBuilder.shapedRecipe(material.ladder, 3).key('#', material.planks).key('I', Items.STICK).patternLine("I I").patternLine("I#I").patternLine("I I").setGroup("end_ladders").addCriterion("has_planks", hasItem(material.planks)).build(consumer);
//	    ShapedRecipeBuilder.shapedRecipe(material.chest, 1).key('#', material.planks).patternLine("###").patternLine("# #").patternLine("###").setGroup("end_chests").addCriterion("has_planks", hasItem(material.planks)).build(consumer);
//	    ShapedRecipeBuilder.shapedRecipe(material.sign, 3).key('#', material.planks).key('I', Items.STICK).patternLine("###").patternLine("###").patternLine(" I ").setGroup("end_signs").addCriterion("has_planks", hasItem(material.planks)).build(consumer);
//	    ShapedRecipeBuilder.shapedRecipe(material.barrel, 1).key('#', material.planks).key('S', material.slab).patternLine("#S#").patternLine("# #").patternLine("#S#").setGroup("end_barrels").addCriterion("has_planks", hasItem(material.planks)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.shelf, 1).key('#', material.planks).key('P', Items.BOOK).patternLine("###").patternLine("PPP").patternLine("###").setGroup("end_bookshelves").addCriterion("has_planks", hasItem(material.planks)).build(consumer);
	}

	private static void makeStoneMaterialRecipes(StoneMaterial material)
	{
		Object consumer = null;
		// Crafting
		ShapedRecipeBuilder.shapedRecipe(material.bricks, 4).key('#', material.stone).patternLine("##").patternLine("##").setGroup("end_bricks").addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(material.polished, 4).key('#', material.bricks).patternLine("##").patternLine("##").setGroup("end_tile").addCriterion("has_" + material.bricks.getRegistryName().getPath(), hasItem(material.bricks)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(material.tiles, 4).key('#', material.polished).patternLine("##").patternLine("##").setGroup("end_small_tile").addCriterion("has_" + material.polished.getRegistryName().getPath(), hasItem(material.polished)).build(consumer);
	    //ShapedRecipeBuilder.shapedRecipe(material.pillar).key('#', material.slab).patternLine("#").patternLine("#").setGroup("end_pillar").addCriterion("has_" + material.slab.getRegistryName().getPath(), hasItem(material.slab)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.stairs, 4).key('#', material.stone).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("end_stone_stairs").addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer);
	    //ShapedRecipeBuilder.shapedRecipe(material.slab, 6).key('#', material.stone).patternLine("###").setGroup("end_stone_slabs").addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.brick_stairs, 4).key('#', material.bricks).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("end_stone_stairs").addCriterion("has_" + material.bricks.getRegistryName().getPath(), hasItem(material.bricks)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.brick_slab, 6).key('#', material.bricks).patternLine("###").setGroup("end_stone_slabs").addCriterion("has_" + material.bricks.getRegistryName().getPath(), hasItem(material.bricks)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.wall, 6).key('#', material.stone).patternLine("###").patternLine("###").setGroup("end_wall").addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.brick_wall, 6).key('#', material.bricks).patternLine("###").patternLine("###").setGroup("end_wall").addCriterion("has_" + material.bricks.getRegistryName().getPath(), hasItem(material.bricks)).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(material.button).addIngredient(material.stone).setGroup("end_stone_buttons").addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.pressure_plate).key('#', material.stone).patternLine("##").setGroup("end_stone_plates").addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer);
	    //registerLantern(material.lantern, material.slab, material.name);
	    //registerPedestal(material.pedestal, material.slab, material.pillar, material.name);
	    ShapedRecipeBuilder.shapedRecipe(material.furnace).key('#', material.stone).patternLine("###").patternLine("# #").patternLine("###").setGroup("end_stone_furnaces").addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer);

//		// Stonecutting
//		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone), material.bricks).addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer, rl(material.name + "_bricks_from_" + material.name + "_stonecutting"));
//		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone), material.polished).addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer, rl(material.name + "_polished_from_" + material.name + "_stonecutting"));
//		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone), material.tiles).addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer, rl(material.name + "_tiles_from_" + material.name + "_stonecutting"));
//		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone), material.pillar).addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer, rl(material.name + "_pillar_from_" + material.name + "_stonecutting"));
//		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone), material.stairs).addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer, rl(material.name + "_stairs_from_" + material.name + "_stonecutting"));
//		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone), material.slab, 2).addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer, rl(material.name + "_slab_from_" + material.name + "_stonecutting"));
//		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone), material.brick_stairs).addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer, rl(material.name + "_bricks_stairs_from_" + material.name + "_stonecutting"));
//		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone), material.brick_slab, 2).addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer, rl(material.name + "_bricks_slab_from_" + material.name + "_stonecutting"));
//		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone), material.wall).addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer, rl(material.name + "_wall_from_" + material.name + "_stonecutting"));
//		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone), material.brick_wall).addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer, rl(material.name + "_bricks_wall_from_" + material.name + "_stonecutting"));
//		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone), material.button).addCriterion("has_" + material.stone.getRegistryName().getPath(), hasItem(material.stone)).build(consumer, rl(material.name + "_button_from_" + material.name + "_stonecutting"));
//		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.bricks), material.brick_stairs).addCriterion("has_" + material.bricks.getRegistryName().getPath(), hasItem(material.bricks)).build(consumer, rl(material.name + "_bricks_stairs_from_" + material.name + "_bricks_stonecutting"));
//		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.bricks), material.brick_slab, 2).addCriterion("has_" + material.bricks.getRegistryName().getPath(), hasItem(material.bricks)).build(consumer, rl(material.name + "_bricks_slab_from_" + material.name + "_bricks_stonecutting"));
//		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.bricks), material.brick_wall).addCriterion("has_" + material.bricks.getRegistryName().getPath(), hasItem(material.bricks)).build(consumer, rl(material.name + "_bricks_wall_from_" + material.name + "_bricks_stonecutting"));
	}

	private static void makeMetalMaterialRecipes(MetalMaterial material)
	{
		Object consumer = null;
		// Base
	    makeIngotAndBlockRecipes(material.block, material.ingot, material.name);
	    ShapedRecipeBuilder.shapedRecipe(material.ingot).key('#', material.nugget).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_" + material.name + "_nugget", hasItem(material.nugget)).build(consumer, rl(material.name + "_ingot_from_" + material.name + "_nuggets"));

	    // Blocks
	    ShapedRecipeBuilder.shapedRecipe(material.tile, 4).key('#', material.block).patternLine("##").patternLine("##").setGroup("end_metal_tile").addCriterion("has_" + material.name + "_block", hasItem(material.block)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.stairs, 4).key('#', material.block).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("end_metal_stairs").addCriterion("has_" + material.name + "_block", hasItem(material.block)).build(consumer);
//ShapedRecipeBuilder.shapedRecipe(material.slab, 6).key('#', material.block).patternLine("###").setGroup("end_metal_slabs").addCriterion("has_" + material.block + "_block", hasItem(material.block)).build(consumer);
	    //ShapedRecipeBuilder.shapedRecipe(material.door, 3).key('#', material.ingot).patternLine("##").patternLine("##").patternLine("##").setGroup("end_metal_doors").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.trapdoor).key('#', material.ingot).patternLine("##").patternLine("##").setGroup("end_metal_trapdoors").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.pressure_plate).key('#', material.ingot).patternLine("##").setGroup("end_metal_plates").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.bars, 16).key('#', material.ingot).patternLine("###").patternLine("###").setGroup("end_metal_bars").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.chain).key('N', material.nugget).key('#', material.ingot).patternLine("N").patternLine("#").patternLine("N").setGroup("end_metal_chain").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.anvil).key('#', material.block).key('I', material.ingot).patternLine("###").patternLine(" I ").patternLine("III").setGroup("end_metal_anvil").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.chandelier).key('I', ModItems.LUMECORN_ROD).key('#', material.ingot).patternLine("I#I").patternLine(" # ").setGroup("end_metal_chandelier").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot)).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.bulb_lantern).key('C', Blocks.IRON_BARS).key('I', material.ingot).key('#', ModItems.GLOWING_BULB).patternLine("C").patternLine("I").patternLine("#").addCriterion("has_glowing_bulb", hasItem(ModItems.GLOWING_BULB)).build(consumer);
	    makeColoredMaterialRecipes(material.bulb_lantern_colored);
	    //ShapedRecipeBuilder.shapedRecipe(Blocks.SMITHING_TABLE).key('I', material.ingot).key('#', ItemTags.PLANKS).patternLine("II").patternLine("##").patternLine("##").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot)).build(consumer, rl("smithing_table_from_" + material.name + "_ingot"));

	    // Furnace & blast furnace
	    float exp = 0.35f;
	    int smeltTime = 200;
	    int blastTime = smeltTime / 2;
	    if (material.hasOre)
	    {
			GameRegistry.addSmelting(material.ore, new ItemStack( material.ingot, 1, 0) ,exp);
		}

	    // Smithing table
	    makeSmithingRecipe(ItemBlock.getItemFromBlock(material.block), Items.STICK, material.hammer);
		makeSmithingRecipe(material.shovelHead, Items.STICK, material.shovel);
		makeSmithingRecipe(material.axeHead, Items.STICK, material.axe);
		makeSmithingRecipe(material.pickaxeHead, Items.STICK, material.pickaxe);
		makeSmithingRecipe(material.hoeHead, Items.STICK, material.hoe);
		makeSmithingRecipe(material.ingot, Items.STICK, material.swordHandle);
		makeSmithingRecipe(material.swordBlade, material.swordHandle, material.sword);

		// Armor
	    makeHelmetRecipe(material.helmet, material.ingot, material.name);
		makeChestplateRecipe(material.chestplate, material.ingot, material.name);
		makeLeggingsRecipe(material.leggings, material.ingot, material.name);
		makeBootsRecipe(material.boots, material.ingot, material.name);
	}

	private static void makeColoredMaterialRecipes(ColoredMaterial material)
	{
		if (material.craftEight)
		{
			for (EnumDyeColor color : EnumDyeColor.values())
			{
				ShapedRecipeBuilder.shapedRecipe(material.getByColor(color), 8).key('#', material.craftMaterial).key('D', new ItemStack(Items.DYE, 1, color.getDyeDamage())).patternLine("###").patternLine("#D#").patternLine("###").addCriterion("has_" + material.name, hasItem(material.craftMaterial)).build(null);
			}
		}
		else
		{
			for (EnumDyeColor color : EnumDyeColor.values())
			{
				ShapelessRecipeBuilder.shapelessRecipe(material.getByColor(color)).addIngredient(material.craftMaterial).addIngredient(new ItemStack(Items.DYE, 1, color.getDyeDamage())).addCriterion("has_" + material.name, hasItem(material.craftMaterial)).build(null);
			}
		}

	}

	private static void cookFood(Item in, Item out, float exp, int time) {
		GameRegistry.addSmelting(in, new ItemStack(out, 1, 0), exp);
	    }

	private static void makeSmithingRecipe(Item base, Item addition, Item output)
	{
		//SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(base), Ingredient.fromItems(addition), output).addCriterion("has_" + addition.getRegistryName().getPath(), hasItem(addition)).build(null, rl(output.getRegistryName().getPath() + "_smithing"));
	}

	private static void makeIngotAndBlockRecipes(Block block, Item ingot, String material)
	{
	    ShapedRecipeBuilder.shapedRecipe(block).key('#', ingot).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_" + material + "_ingot", hasItem(ingot)).build(null);
	    ShapelessRecipeBuilder.shapelessRecipe(ingot, 9).addIngredient(block).setGroup(material + "_ingot").addCriterion("has_" + material + "_block", hasItem(block)).build(null, rl(material + "_ingot_from_" + material + "_block"));
	}

	private static void makeHelmetRecipe(Item result, Item ingredient, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(result).key('X', ingredient).patternLine("XXX").patternLine("X X").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(null);
	}

	private static void makeChestplateRecipe(Item result, Item ingredient, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(result).key('X', ingredient).patternLine("X X").patternLine("XXX").patternLine("XXX").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(null);
	}

	private static void makeLeggingsRecipe(Item result, Item ingredient, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(result).key('X', ingredient).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(null);
	}

	private static void makeBootsRecipe(Item result, Item ingredient, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(result).key('X', ingredient).patternLine("X X").patternLine("X X").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(null);
	}

	private static void makeSwordRecipe(Item sword, Item ingredient, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(sword).key('#', Items.STICK).key('X', ingredient).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(null);
	}

	private static void makePickaxeRecipe(Item pickaxe, Item ingredient, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(pickaxe).key('#', Items.STICK).key('X', ingredient).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(null);
	}

	private static void makeAxeRecipe(Item axe, Item ingredient, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(axe).key('#', Items.STICK).key('X', ingredient).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(null);
	}

	private static void makeShovelRecipe(Item shovel, Item ingredient, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(shovel).key('#', Items.STICK).key('X', ingredient).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(null);
	}

	private static void makeHoeRecipe(Item hoe, Item ingredient, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(hoe).key('#', Items.STICK).key('X', ingredient).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(null);
	}

	private static void makeHammerRecipe(Item hammer, Item ingredient, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(hammer).key('#', Items.STICK).key('X', ingredient).patternLine("X X").patternLine("X#X").patternLine(" # ").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(null);
	}

	private static void registerPedestal(Block pedestal, Block slab, Block pillar, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(pedestal, 2).key('S', slab).key('#', pillar).patternLine("S").patternLine("#").patternLine("S").addCriterion("has_" + material + "_slab", hasItem(slab)).addCriterion("has_" + material + "_pillar", hasItem(pillar)).build(null);
	}

	private static void registerLantern(Block lantern, Block slab, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(lantern).key('S', slab).key('#', ModItems.CRYSTAL_SHARDS).patternLine("S").patternLine("#").patternLine("S").addCriterion("has_" + material + "_slab", hasItem(slab)).addCriterion("has_crystal_shard", hasItem(ModItems.CRYSTAL_SHARDS)).build(null);
	}
}
