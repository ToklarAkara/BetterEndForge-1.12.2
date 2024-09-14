package mod.beethoven92.betterendforge.client.gui;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mod.beethoven92.betterendforge.common.tileentity.EndStoneSmelterTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class EndStoneSmelterRecipeBookScreen extends GuiContainer {
	private static final ResourceLocation RECIPE_BOOK_GUI_TEXTURE = new ResourceLocation("textures/gui/recipe_book.png");
	private Iterator<Item> fuelIterator;
	private Set<Item> fuels;
	private Slot fuelSlot;
	private Item currentItem;
	private float frameTime;

	public EndStoneSmelterRecipeBookScreen() {
		super(null);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(RECIPE_BOOK_GUI_TEXTURE);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}

	protected Set<Item> getAvailableFuels() {
		return EndStoneSmelterTileEntity.getAvailableFuels().keySet();
	}

	@Override
	protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
		super.handleMouseClick(slotIn, slotId, mouseButton, type);
		if (slotIn != null && slotIn.slotNumber < this.inventorySlots.inventorySlots.size()) {
			this.fuelSlot = null;
		}
	}


//	public void setupGhostRecipe(IRecipe<?> recipe, List<Slot> slots) {
//		this.ghostRecipe.clear();
//		ItemStack result = recipe.getRecipeOutput();
//		this.ghostRecipe.setRecipe(recipe);
//		this.ghostRecipe.addIngredient(Ingredient.fromStacks(result), slots.get(3).xPos, slots.get(3).yPos);
//		NonNullList<Ingredient> inputs = recipe.getIngredients();
//		Iterator<Ingredient> iterator = inputs.iterator();
//		for (int i = 0; i < 2; i++) {
//			if (!iterator.hasNext()) {
//				return;
//			}
//			Ingredient ingredient = iterator.next();
//			if (!ingredient.hasNoMatchingItems()) {
//				Slot slot = slots.get(i);
//				this.ghostRecipe.addIngredient(ingredient, slot.xPos, slot.yPos);
//			}
//		}
//		this.fuelSlot = slots.get(2);
//		if (this.fuels == null) {
//			this.fuels = this.getAvailableFuels();
//		}
//
//		this.fuelIterator = this.fuels.iterator();
//		this.currentItem = null;
//	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
		if (fuelSlot != null) {
			if (!isCtrlKeyDown()) {
				this.frameTime += partialTicks;
			}

			int slotX = this.fuelSlot.xPos + this.guiLeft;
			int slotY = this.fuelSlot.yPos + this.guiTop;
			drawRect(slotX, slotY, slotX + 16, slotY + 16, 822018048);
			this.itemRender.renderItemAndEffectIntoGUI(this.mc.player, this.getItem().getDefaultInstance(), slotX, slotY);
			GlStateManager.depthFunc(516);
			drawRect(slotX, slotY, slotX + 16, slotY + 16, 822083583);
			GlStateManager.depthFunc(515);
		}
	}

	private Item getItem() {
		if (this.currentItem == null || this.frameTime > 30.0F) {
			this.frameTime = 0.0F;
			if (this.fuelIterator == null || !this.fuelIterator.hasNext()) {
				if (this.fuels == null) {
					this.fuels = this.getAvailableFuels();
				}
				this.fuelIterator = this.fuels.iterator();
			}
			this.currentItem = this.fuelIterator.next();
		}
		return this.currentItem;
	}
}
