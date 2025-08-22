package mod.beethoven92.betterendforge.common.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class InfusionRecipe implements IRecipe {
	public final static String GROUP = "infusion";

	public ResourceLocation id;
	public Ingredient input;
	public ItemStack output;
	public int time = 1;
	public Ingredient[] catalysts = new Ingredient[8];
	public Map<Integer, Ingredient> ingredientPositions = Maps.newHashMap();

	public InfusionRecipe(ResourceLocation id) {
		this(id, null, null);
	}

	public InfusionRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
		this.id = id;
		this.input = input;
		this.output = output;
		Arrays.fill(catalysts, Ingredient.EMPTY);
	}

	public int getInfusionTime() {
		return this.time;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = NonNullList.create();
		list.add(input);
		for (Ingredient catalyst : catalysts) {
			list.add(catalyst);
		}
		return list;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		boolean valid = this.input.apply(inv.getStackInSlot(0));
		if (!valid)
			return false;

		ArrayList<Ingredient> ingredients = Lists.newArrayList(this.catalysts);

		for(int i=1;i<=8;i++){
			ItemStack pedestalContents = inv.getStackInSlot(i);
			for (int j = 0; j < ingredients.size(); j++) {
				if(ingredients.get(j).apply(pedestalContents)){
					ingredients.remove(j);
					break;
				}
			}
		}

		return ingredients.isEmpty();
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

	public static class Builder {

		public static Builder create() {
			return new Builder();
		}

		private Ingredient input;
		private ItemStack output;
		private int time = 1;
		private Ingredient[] catalysts = new Ingredient[8];

		private Builder() {
			Arrays.fill(catalysts, Ingredient.EMPTY);
		}

		public Builder setInput(Item input) {
			this.input = Ingredient.fromItem(input);
			return this;
		}

		public Builder setInput(Ingredient input) {
			this.input = input;
			return this;
		}

		public Builder setOutput(Item output) {
			this.output = new ItemStack(output, 1);
			return this;
		}

		public Builder setOutput(ItemStack output) {
			this.output = output;
			this.output.setCount(1);
			return this;
		}

		public Builder setTime(int time) {
			this.time = time;
			return this;
		}

		public Builder setGroup(String group) {
			return this;
		}

		public Builder addCatalyst(int slot, Block block, int meta) {
			if (slot > 7)
				return this;

			this.catalysts[slot] = Ingredient.fromStacks(new ItemStack(block, 1, meta));
			return this;
		}

		public Builder addCatalyst(int slot, Block block) {
			if (slot > 7)
				return this;

			this.catalysts[slot] = Ingredient.fromStacks(new ItemStack(block, 1));
			return this;
		}

		public Builder addCatalyst(int slot, Item item) {
			if (slot > 7)
				return this;

			this.catalysts[slot] = Ingredient.fromStacks(new ItemStack(item, 1));
			return this;
		}

		public Builder addCatalyst(int slot, Ingredient item) {
			if (slot > 7)
				return this;

			this.catalysts[slot] = item;
			return this;
		}

		public Builder addCatalyst(int slot, Item item, int meta) {
			if (slot > 7)
				return this;

			this.catalysts[slot] = Ingredient.fromStacks(new ItemStack(item, 1, meta));
			return this;
		}

		public Builder addCatalyst(int slot, ItemStack... items) {
			if (slot > 7)
				return this;
			this.catalysts[slot] = Ingredient.fromStacks(items);
			return this;
		}

		public void build(Consumer<InfusionRecipe> consumerIn, ResourceLocation id) {
			validate(id);
			InfusionRecipe recipe = new InfusionRecipe(id, input, output);
			recipe.catalysts = catalysts;
			recipe.time = time;
			consumerIn.accept(recipe);
		}

		private void validate(ResourceLocation id) {
			if (input == null)
				Illegal("Input for Infusion recipe can't be null, recipe %s", id);
			if (output == null)
				Illegal("Output for Infusion recipe can't be null, recipe %s", id);
			int empty = 0;
			for (int i = 0; i < catalysts.length; i++) {
				if (catalysts[i].getMatchingStacks().length==0)
					empty++;
			}
			if (empty == catalysts.length) {
				Illegal("At least one catalyst must be non empty, recipe %s", id);
			}
		}

		private void Illegal(String s, ResourceLocation id) {
			throw new IllegalArgumentException(String.format(s, id.toString()));
		}
	}
}
