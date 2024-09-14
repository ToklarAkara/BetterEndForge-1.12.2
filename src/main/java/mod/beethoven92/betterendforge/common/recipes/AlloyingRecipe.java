package mod.beethoven92.betterendforge.common.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AlloyingRecipe implements IRecipe {
	public final static String GROUP = "alloying";

	protected ResourceLocation id;
	protected final Ingredient primaryInput;
	protected final Ingredient secondaryInput;
	protected final ItemStack output;
	protected final String group;
	protected final float experience;
	protected final int smeltTime;

	public AlloyingRecipe(ResourceLocation id, String group, Ingredient primary, Ingredient secondary, ItemStack output,
						  float experience, int smeltTime) {
		this.group = group;
		this.id = id;
		this.primaryInput = primary;
		this.secondaryInput = secondary;
		this.output = output;
		this.experience = experience;
		this.smeltTime = smeltTime;
	}

	public float getExperience() {
		return this.experience;
	}

	public int getSmeltTime() {
		return this.smeltTime;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();
		defaultedList.add(primaryInput);
		defaultedList.add(secondaryInput);

		return defaultedList;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return this.primaryInput.apply(inv.getStackInSlot(0)) && this.secondaryInput.apply(inv.getStackInSlot(1)) ||
				this.primaryInput.apply(inv.getStackInSlot(1)) && this.secondaryInput.apply(inv.getStackInSlot(0));
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return this.output.copy();
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
	public String getGroup() {
		return this.group;
	}
}
