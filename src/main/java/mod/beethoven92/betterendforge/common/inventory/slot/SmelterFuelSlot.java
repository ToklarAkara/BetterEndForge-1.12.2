package mod.beethoven92.betterendforge.common.inventory.slot;

import mod.beethoven92.betterendforge.common.inventory.EndStoneSmelterContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;

public class SmelterFuelSlot extends Slot
{
	private final EndStoneSmelterContainer handler;
	
	public SmelterFuelSlot(EndStoneSmelterContainer handler, IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
		
		this.handler = handler;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return this.handler.isFuel(stack) || SlotFurnaceFuel.isBucket(stack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack)
	{

		return SlotFurnaceFuel.isBucket(stack) ? 1 : super.getItemStackLimit(stack);
	}
}
