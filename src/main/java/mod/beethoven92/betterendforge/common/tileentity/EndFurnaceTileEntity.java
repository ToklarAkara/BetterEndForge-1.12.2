package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.block.template.EndFurnaceBlock;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.mixin.minecraft.TileEntityFurnaceAccessor;
import net.minecraft.block.BlockFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class EndFurnaceTileEntity extends TileEntityFurnace implements ITickable {

	public EndFurnaceTileEntity() {
		super();
	}

	public void update()
	{
		//TODO: comment by nischi, instead of all the private accessors this could probably easily be done with a small mixin instead
		TileEntityFurnaceAccessor thisFurnace = (TileEntityFurnaceAccessor) this;
		boolean flag = this.isBurning();
		boolean flag1 = false;

		if (this.isBurning())
		{
			thisFurnace.setFurnaceBurnTime(thisFurnace.getFurnaceBurnTime()-1);
		}

		if (!this.world.isRemote)
		{
			ItemStack itemstack = thisFurnace.getFurnaceItemStacks().get(1);

			if (this.isBurning() || !itemstack.isEmpty() && !((ItemStack)thisFurnace.getFurnaceItemStacks().get(0)).isEmpty())
			{
				if (!this.isBurning() && thisFurnace.invokeCanSmelt())
				{
					thisFurnace.setFurnaceBurnTime(getItemBurnTime(itemstack));
					thisFurnace.setCurrentItemBurnTime(thisFurnace.getFurnaceBurnTime());

					if (this.isBurning())
					{
						flag1 = true;

						if (!itemstack.isEmpty())
						{
							Item item = itemstack.getItem();
							itemstack.shrink(1);

							if (itemstack.isEmpty())
							{
								ItemStack item1 = item.getContainerItem(itemstack);
								thisFurnace.getFurnaceItemStacks().set(1, item1);
							}
						}
					}
				}

				if (this.isBurning() && thisFurnace.invokeCanSmelt())
				{
					thisFurnace.setCookTime(thisFurnace.getCookTime()+1);

					if (thisFurnace.getCookTime() == thisFurnace.getTotalCookTime())
					{
						thisFurnace.setCookTime(0);
						thisFurnace.setTotalCookTime(this.getCookTime(thisFurnace.getFurnaceItemStacks().get(0)));
						this.smeltItem();
						flag1 = true;
					}
				}
				else
				{
					thisFurnace.setCookTime(0);
				}
			}
			else if (!this.isBurning() && thisFurnace.getCookTime() > 0)
			{
				thisFurnace.setCookTime(MathHelper.clamp(thisFurnace.getCookTime() - 2, 0, thisFurnace.getTotalCookTime()));
			}

			if (flag != this.isBurning())
			{
				flag1 = true;
				EndFurnaceBlock.setState(this.isBurning(), this.world, this.pos);
			}
		}

		if (flag1)
		{
			this.markDirty();
		}
	}

}
