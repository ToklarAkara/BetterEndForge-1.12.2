package mod.beethoven92.betterendforge.common.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.block.EndStoneSmelter;
import mod.beethoven92.betterendforge.common.inventory.EndStoneSmelterContainer;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import mod.beethoven92.betterendforge.data.AlloyingRecipes;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EndStoneSmelterTileEntity extends TileEntityLockable implements ITickable, ISidedInventory
{
	private static final int[] SLOTS_TOP = new int[] {0, 1};
	private static final int[] SLOTS_BOTTOM = new int[] {2, 3};
	private static final int[] SLOTS_SIDES = new int[] {2};
	private static final Map<Item, Integer> AVAILABLE_FUELS = Maps.newHashMap();

	private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
	protected NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
	private int smeltTimeTotal;
	private int smeltTime;
	private int burnTime;
	private int fuelTime;

	public EndStoneSmelterTileEntity()
	{
		super();
		this.registerFuels();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setShort("BurnTime", (short)this.burnTime);
		compound.setShort("FuelTime", (short)this.fuelTime);
		compound.setShort("SmeltTime", (short)this.smeltTime);
		compound.setShort("SmeltTimeTotal", (short)this.smeltTimeTotal);
		ItemStackHelper.saveAllItems(compound, this.items);
		NBTTagCompound usedRecipes = new NBTTagCompound();
		this.recipesUsed.forEach((identifier, integer) -> {
			usedRecipes.setInteger(identifier.toString(), integer);
		});
		compound.setTag("RecipesUsed", usedRecipes);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.items);
		this.burnTime = compound.getShort("BurnTime");
		this.fuelTime = compound.getShort("FuelTime");
		this.smeltTime = compound.getShort("SmeltTime");
		this.smeltTimeTotal = compound.getShort("SmeltTimeTotal");
		NBTTagCompound usedRecipes = compound.getCompoundTag("RecipesUsed");
		for (String key : usedRecipes.getKeySet())
		{
			this.recipesUsed.put(new ResourceLocation(key), usedRecipes.getInteger(key));
		}
	}

	private void registerFuels()
	{
		registerFuel(Items.LAVA_BUCKET, 16000);
		registerFuel(Item.getItemFromBlock(Blocks.COAL_BLOCK), 12000);
		registerFuel(Items.BLAZE_ROD, 2000);
	}

	private boolean isBurning()
	{
		return this.burnTime > 0;
	}

	public void dropExperience(EntityPlayer player)
	{
//		List<IRecipe<?>> list = Lists.newArrayList();
//		ObjectIterator<Entry<ResourceLocation>> usedRecipes = this.recipesUsed.object2IntEntrySet().iterator();
//		while (usedRecipes.hasNext())
//		{
//			Entry<ResourceLocation> entry = usedRecipes.next();
//			IRecipe<?> recipe = FurnaceRecipes.instance().getRecipe(entry.getKey());
//			if (recipe != null)
//			{
//				list.add(recipe);
//				this.dropExperience(player.world, player.getPositionVector(), entry.getIntValue(), recipe.getExperience());
//			}
//		}
//		player.unlockRecipes(list);
		this.recipesUsed.clear();
	}

	private void dropExperience(World world, Vec3d vec3d, int i, float f)
	{
		int j = MathHelper.floor(i * f);
		float g = (float) MathHelper.frac(i * f);
		if (g != 0.0F && Math.random() < g)
		{
			j++;
		}

		while (j > 0)
		{
			int k = EntityXPOrb.getXPSplit(j);
			j -= k;
			world.spawnEntity(new EntityXPOrb(world, vec3d.x, vec3d.y, vec3d.z, k));
		}
	}


	@Override
	public void update()
	{
		boolean initialBurning = this.isBurning();
		if (initialBurning)
		{
			this.burnTime--;
		}

		boolean burning = this.isBurning();
		if (!this.world.isRemote)
		{
			ItemStack fuel = this.items.get(2);
			if (!burning && (fuel.isEmpty() || items.get(0).isEmpty() && items.get(1).isEmpty()))
			{
				if (!burning && smeltTime > 0)
				{
					this.smeltTime = MathHelper.clamp(smeltTime - 2, 0, smeltTimeTotal);
				}
			}
			else
			{
				AlloyingRecipe recipe = AlloyingRecipes.findRecipe(this.items.get(0), this.items.get(1), world);
				//ItemStack recipe = FurnaceRecipes.instance().getSmeltingResult(this.items.get(0));
//				if (recipe == null)
//				{
//					recipe = FurnaceRecipes.instance().getSmeltingResult(this.items.get(1));
//				}
				boolean accepted = this.canAcceptRecipeOutput(recipe);
				if (!burning && accepted)
				{
					this.burnTime = this.getBurnTime(fuel);
					this.fuelTime = this.burnTime;
					burning = this.isBurning();
					if (burning)
					{
						if (!fuel.isEmpty())
						{
							Item item = fuel.getItem();
							fuel.shrink(1);
							if (fuel.isEmpty())
							{
								Item remainFuel = item.getContainerItem();
								this.items.set(2, remainFuel == null ? ItemStack.EMPTY : new ItemStack(remainFuel));
							}
						}
						this.markDirty();
					}
				}

				if (burning && accepted)
				{
					this.smeltTime++;
					if (smeltTime == smeltTimeTotal)
					{
						this.smeltTime = 0;
						this.smeltTimeTotal = this.getSmeltTime();
						this.craftRecipe(recipe);
						this.markDirty();
					}
				}
				else
				{
					this.smeltTime = 0;
				}
			}

			if (initialBurning != burning)
			{
				this.world.setBlockState(pos, world.getBlockState(pos).withProperty(EndStoneSmelter.LIT, burning), 3);
				this.validate();
				world.setTileEntity(this.pos, this);
				this.markDirty();
			}
		}
	}


	private void craftRecipe(AlloyingRecipe recipe)
	{
		if (recipe == null || !canAcceptRecipeOutput(recipe)) return;

		ItemStack result = recipe.getRecipeOutput();
		ItemStack output = this.items.get(3);
		if (output.isEmpty())
		{
			this.items.set(3, result.copy());
		}
		else if (output.getItem() == result.getItem())
		{
			output.grow(result.getCount());
		}

		if (!this.world.isRemote)
		{
			this.setRecipeUsed(recipe);
		}

		if (recipe instanceof AlloyingRecipe)
		{
			this.items.get(0).shrink(1);
			this.items.get(1).shrink(1);
		}
		else
		{
			if (!this.items.get(0).isEmpty())
			{
				this.items.get(0).shrink(1);
			}
			else
			{
				this.items.get(1).shrink(1);
			}
		}
	}


	public void setRecipeUsed(AlloyingRecipe recipe)
	{
		if (recipe != null)
		{
			ResourceLocation recipeId = recipe.getRegistryName();
			this.recipesUsed.addTo(recipeId, 1);
		}
	}


	@Override
	public int getSizeInventory()
	{
		return this.items.size();
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.items)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.items.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.items, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.items, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack itemStack = this.items.get(index);
		boolean stackValid = !stack.isEmpty() && stack.isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(stack, itemStack);
		this.items.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}
		if ((index == 0 || index == 1) && !stackValid)
		{
			this.smeltTimeTotal = this.getSmeltTime();
			this.smeltTime = 0;
			this.markDirty();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	protected int getSmeltTime()
	{
		int smeltTime = 200; // Default smelt time for FurnaceRecipes
		return smeltTime;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		if (side == EnumFacing.DOWN)
		{
			return SLOTS_BOTTOM;
		}
		else
		{
			return side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES;
		}
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		if (direction == EnumFacing.DOWN && index == 2)
		{
			if (stack.getItem() != Items.BUCKET && stack.getItem() != Items.WATER_BUCKET)
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		if (this.world.getTileEntity(this.pos) != this)
		{
			return false;
		}
		else
		{
			return player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	public int getField(int id)
	{
		switch (id)
		{
			case 0:
				return this.burnTime;
			case 1:
				return this.fuelTime;
			case 2:
				return this.smeltTime;
			case 3:
				return this.smeltTimeTotal;
			default:
				return 0;
		}
	}

	public void setField(int id, int value)
	{
		switch (id)
		{
			case 0:
				this.burnTime = value;
				break;
			case 1:
				this.fuelTime = value;
				break;
			case 2:
				this.smeltTime = value;
				break;
			case 3:
				this.smeltTimeTotal = value;
		}
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear()
	{
		this.items.clear();
	}

	@Override
	public String getName() {
		return "container.betterendforge.end_stone_smelter";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentTranslation("container.betterendforge.end_stone_smelter");
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new EndStoneSmelterContainer(playerInventory, this);
	}

	@Override
	public String getGuiID() {
		return null;
	}

	public static boolean isFuel(ItemStack stack)
	{
		return AVAILABLE_FUELS.containsKey(stack.getItem());
	}

	protected int getBurnTime(ItemStack fuel)
	{
		if (fuel.isEmpty())
		{
			return 0;
		}
		else
		{
			Item item = fuel.getItem();
			return AVAILABLE_FUELS.getOrDefault(item, 0);
		}
	}

	public static void registerFuel(Item fuel, int time)
	{
		if (AVAILABLE_FUELS.containsKey(fuel))
		{
			AVAILABLE_FUELS.replace(fuel, time);
		}
		else
		{
			AVAILABLE_FUELS.put(fuel, time);
		}
	}

	protected boolean canAcceptRecipeOutput(AlloyingRecipe recipe)
	{
		if (recipe == null) return false;
		boolean validInput = false;
		if (recipe instanceof AlloyingRecipe)
		{
			validInput = !items.get(0).isEmpty() &&
					!items.get(1).isEmpty();
		}
		else
		{
			validInput = !items.get(0).isEmpty() ||
					!items.get(1).isEmpty();
		}
		if (validInput)
		{
			ItemStack result = recipe.getRecipeOutput();
			if (result.isEmpty())
			{
				return false;
			}
			else
			{
				ItemStack output = this.items.get(3);
				int outCount = output.getCount();
				int total = outCount + result.getCount();
				if (output.isEmpty())
				{
					return true;
				}
				else if (!output.isItemEqualIgnoreDurability(result))
				{
					return false;
				}
				else if (outCount < this.getInventoryStackLimit() && outCount < output.getMaxStackSize())
				{
					return this.getInventoryStackLimit() >= total;
				}
				else
				{
					return output.getCount() < result.getMaxStackSize();
				}
			}
		}

		return false;
	}

	public static Map<Item, Integer> getAvailableFuels()
	{
		return AVAILABLE_FUELS;
	}
}