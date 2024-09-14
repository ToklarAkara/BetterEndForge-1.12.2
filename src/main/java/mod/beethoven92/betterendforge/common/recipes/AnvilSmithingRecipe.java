package mod.beethoven92.betterendforge.common.recipes;

import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.item.HammerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AnvilSmithingRecipe implements IRecipe {
	public final static String GROUP = "anvil_smithing";

	private ResourceLocation id;
	public final Ingredient input;
	public final ItemStack output;
	public final int damage;
	public final int inputCount;
	public final int level;
	public final int anvilLevel;

	public AnvilSmithingRecipe(ResourceLocation identifier, Ingredient input, ItemStack output, int inputCount,
							   int level, int damage, int anvilLevel) {
		this.id = identifier;
		this.input = input;
		this.output = output;
		this.inputCount = inputCount;
		this.level = level;
		this.damage = damage;
		this.anvilLevel = anvilLevel;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		ItemStack hammer = inv.getStackInSlot(0);
		if (hammer.isEmpty() || !ModTags.HAMMERS.contains(hammer.getItem())) {
			return false;
		}
		ItemStack material = inv.getStackInSlot(1);
		int materialCount = material.getCount();

		int level = ((HammerItem) hammer.getItem()).getHarvestLevel();
		return level >= this.level && this.input.apply(inv.getStackInSlot(1)) && materialCount >= this.inputCount;
	}

	public boolean checkHammerDurability(InventoryCrafting craftingInventory, EntityPlayer player) {
		if (player.capabilities.isCreativeMode) return true;
		ItemStack hammer = craftingInventory.getStackInSlot(0);
		int damage = hammer.getItemDamage() + this.damage;
		return damage < hammer.getMaxDamage();
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();

		defaultedList.add(Ingredient.fromStacks(ModTags.HAMMERS.stream().filter(hammer -> {
			return ((HammerItem) hammer).getHarvestLevel() >= level;
		}).toArray(ItemStack[]::new)));

		// Needed for JEI to display the amount of input items required by this recipe
		ItemStack amount = new ItemStack(input.getMatchingStacks()[0].getItem(), inputCount);
		defaultedList.add(Ingredient.fromStacks(amount));

		return defaultedList;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return this.output.copy();
	}

	public ItemStack craft(InventoryCrafting craftingInventory, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			if (!checkHammerDurability(craftingInventory, player)) return ItemStack.EMPTY;

			ItemStack hammer = craftingInventory.getStackInSlot(0);
			hammer.damageItem(this.damage, player);
		}
		return this.getCraftingResult(craftingInventory);
	}

	@Override
	public boolean canFit(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.output;
	}

	@Override
	public IRecipe setRegistryName(ResourceLocation name) {
		this.id = name;
		return this;
	}

	@Override
	public ResourceLocation getRegistryName() {
		return this.id;
	}

	@Override
	public Class<IRecipe> getRegistryType() {
		return IRecipe.class;
	}

	@Override
	public boolean isDynamic() {
		return true;
	}
}
