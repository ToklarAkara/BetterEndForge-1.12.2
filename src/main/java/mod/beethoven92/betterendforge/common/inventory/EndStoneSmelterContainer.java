package mod.beethoven92.betterendforge.common.inventory;

import mod.beethoven92.betterendforge.common.inventory.slot.SmelterFuelSlot;
import mod.beethoven92.betterendforge.common.inventory.slot.SmelterOutputSlot;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import mod.beethoven92.betterendforge.common.recipes.ModRecipeManager;
import mod.beethoven92.betterendforge.common.tileentity.EndStoneSmelterTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EndStoneSmelterContainer extends Container
{
	private int cookTime;
	private int totalCookTime;
	private int furnaceBurnTime;
	private int currentItemBurnTime;
	private final IInventory inventory;
	protected final World world;
	
	public EndStoneSmelterContainer(InventoryPlayer playerInventory, IInventory inventory)
	{
		super();
		this.inventory = inventory;
		this.world = playerInventory.player.world;

		this.addSlotToContainer(new Slot(inventory, 0, 45, 17));
		this.addSlotToContainer(new Slot(inventory, 1, 67, 17));
		this.addSlotToContainer(new SmelterFuelSlot(this, inventory, 2, 56, 53));
		this.addSlotToContainer(new SmelterOutputSlot(playerInventory.player, inventory, 3, 129, 35));

		for(int i = 0; i < 3; ++i) 
		{
			for(int j = 0; j < 9; ++j) 
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for(int i = 0; i < 9; ++i) 
		{
			this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}

	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.inventory.isUsableByPlayer(playerIn);
	}

	protected boolean isSmeltable(ItemStack itemStack)
	{
		return true;//ModRecipeManager.RECIPES.getRecipe(AlloyingRecipe.class, new Inventory(new ItemStack[]{itemStack}), this.world).isPresent();
	}

	public boolean isFuel(ItemStack itemStack) 
	{
		return EndStoneSmelterTileEntity.isFuel(itemStack);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) 
		{
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();
			if (index == 3) 
			{
				if (!this.mergeItemStack(itemStack2, 4, 40, true)) 
				{
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(itemStack2, itemStack);
			} 
			else if (index != 2 && index != 1 && index != 0) 
			{
				if (isSmeltable(itemStack2)) 
				{
					if (!this.mergeItemStack(itemStack2, 0, 2, false)) 
					{
						return ItemStack.EMPTY;
					}
				} 
				else if (isFuel(itemStack2)) 
				{
					if (!this.mergeItemStack(itemStack2, 2, 3, false)) 
					{
						return ItemStack.EMPTY;
					}
				} 
				else if (index >= 4 && index < 31) 
				{
					if (!this.mergeItemStack(itemStack2, 31, 40, false)) 
					{
						return ItemStack.EMPTY;
					}
				} 
				else if (index >= 31 && index < 40 && !this.mergeItemStack(itemStack2, 4, 31, false)) 
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemStack2, 4, 40, false)) 
			{
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) 
			{
				slot.putStack(ItemStack.EMPTY);
			} 
			else 
			{
				slot.onSlotChanged();
			}

			if (itemStack2.getCount() == itemStack.getCount()) 
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemStack2);
		}

		return itemStack;
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i)
		{
			IContainerListener icontainerlistener = this.listeners.get(i);

			if (this.cookTime != this.inventory.getField(2))
			{
				icontainerlistener.sendWindowProperty(this, 2, this.inventory.getField(2));
			}

			if (this.furnaceBurnTime != this.inventory.getField(0))
			{
				icontainerlistener.sendWindowProperty(this, 0, this.inventory.getField(0));
			}

			if (this.currentItemBurnTime != this.inventory.getField(1))
			{
				icontainerlistener.sendWindowProperty(this, 1, this.inventory.getField(1));
			}

			if (this.totalCookTime != this.inventory.getField(3))
			{
				icontainerlistener.sendWindowProperty(this, 3, this.inventory.getField(3));
			}
		}

		this.cookTime = this.inventory.getField(2);
		this.furnaceBurnTime = this.inventory.getField(0);
		this.currentItemBurnTime = this.inventory.getField(1);
		this.totalCookTime = this.inventory.getField(3);
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		this.inventory.setField(id, data);
	}
}
