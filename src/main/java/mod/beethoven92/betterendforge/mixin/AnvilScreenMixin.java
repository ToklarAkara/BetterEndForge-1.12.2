package mod.beethoven92.betterendforge.mixin;

import java.io.IOException;
import java.util.List;

import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.Lists;

import mod.beethoven92.betterendforge.common.interfaces.ExtendedRepairContainer;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

@Mixin(GuiRepair.class)
public abstract class AnvilScreenMixin extends GuiContainer
{
	@Shadow
	private GuiTextField nameField;

	private final List<GuiButton> be_buttons = Lists.newArrayList();
	private ExtendedRepairContainer anvilHandler;

	public AnvilScreenMixin(InventoryPlayer playerInventory, World worldIn, BlockPos blockPosition)
	{
		super(null);
	}

	@Inject(method = "initGui", at = @At("TAIL"))
	protected void be_setup(CallbackInfo info)
	{
		this.be_buttons.clear();
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.anvilHandler = (ExtendedRepairContainer) this.inventorySlots;
		this.be_buttons.add(new GuiButton(0, x + 8, y + 45, 15, 20, "<"));
		this.be_buttons.add(new GuiButton(1, x + 154, y + 45, 15, 20, ">"));
		this.buttonList.addAll(be_buttons);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		this.be_buttons.forEach(button -> {
			button.drawButton(this.mc, mouseX, mouseY, partialTicks);
		});
	}

	@Inject(method = "sendSlotContents", at = @At("HEAD"), cancellable = true)
	public void be_onSlotUpdate(Container container, int slotInd, ItemStack stack, CallbackInfo info)
	{
		ExtendedRepairContainer anvilHandler = (ExtendedRepairContainer) container;
		if (anvilHandler.be_getCurrentRecipe() != null)
		{
			if (anvilHandler.be_getRecipes().size() > 1)
			{
				this.be_buttons.forEach(button -> button.visible = true);
			}
			else
			{
				this.be_buttons.forEach(button -> button.visible = false);
			}
			this.nameField.setText("");
			info.cancel();
		}
		else
		{
			this.be_buttons.forEach(button -> button.visible = false);
		}
	}

	private void be_nextRecipe()
	{
		this.anvilHandler.be_nextRecipe();
	}

	private void be_previousRecipe()
	{
		this.anvilHandler.be_previousRecipe();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (this.mc != null)
		{
			for (GuiButton elem : be_buttons)
			{
				if (elem.visible && elem.mousePressed(this.mc, mouseX, mouseY))
				{
					if (this.mc.playerController != null)
					{
						int i = be_buttons.indexOf(elem);
						this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, i);
					}
				}
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
}
