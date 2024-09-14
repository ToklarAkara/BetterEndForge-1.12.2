package mod.beethoven92.betterendforge.client.gui;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.inventory.EndStoneSmelterContainer;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class EndStoneSmelterScreen extends GuiContainer implements IRecipeShownListener
{
	private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/gui/smelter_gui.png");

	public final GuiRecipeBook recipeBook;
	private boolean narrow;

	public EndStoneSmelterScreen(InventoryPlayer playerInv, EndStoneSmelterContainer container)
	{
		super(container);
		this.recipeBook = new GuiRecipeBook();
		this.ySize = 166;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		this.narrow = this.width < 379;
		//this.recipeBook.initVisuals(narrow, ((EndStoneSmelterContainer) this.inventorySlots));
		this.guiLeft = this.recipeBook.updateScreenPosition(this.narrow, this.width, this.xSize);
		this.addButton(new GuiButtonImage(10, this.guiLeft + 20, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE));
		//this.titleX = (this.xSize - this.fontRenderer.getStringWidth("")) / 2;
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		this.recipeBook.tick();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		if (this.recipeBook.isVisible() && this.narrow)
		{
			this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
			this.recipeBook.render(mouseX, mouseY, partialTicks);
		}
		else
		{
			this.recipeBook.render(mouseX, mouseY, partialTicks);
			super.drawScreen(mouseX, mouseY, partialTicks);
			this.recipeBook.renderGhostRecipe(this.guiLeft, this.guiTop, true, partialTicks);
		}

		this.renderHoveredToolTip(mouseX, mouseY);
		this.recipeBook.renderTooltip(this.guiLeft, this.guiTop, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
//		this.mc.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
//		int i = this.guiLeft;
//		int j = this.guiTop;
//		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
//		int k;
//		if (((EndStoneSmelterContainer) this.inventorySlots).isBurning())
//		{
//			k = ((EndStoneSmelterContainer) this.inventorySlots).getFuelProgress();
//			this.drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
//		}
//		k = ((EndStoneSmelterContainer) this.inventorySlots).getSmeltProgress();
//		this.drawTexturedModalRect(i + 92, j + 34, 176, 14, k + 1, 16);
	}

//	@Override
//	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
//		if (this.recipeBook.mouseClicked(mouseX, mouseY, mouseButton))
//		{
//			return true;
//		}
//		else
//		{
//			return this.narrow && this.recipeBook.isVisible() ? true : super.mouseClicked(mouseX, mouseY, button);
//		}
//	}
//
//	@Override
//	public boolean mouseClicked(double mouseX, double mouseY, int button)
//	{
//
//	}

	@Override
	protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type)
	{
		super.handleMouseClick(slotIn, slotId, mouseButton, type);
		this.recipeBook.slotClicked(slotIn);
	}

//	@Override
//	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
//	{
//		return this.recipeBook.keyPressed(keyCode, scanCode, modifiers) ? false : super.keyPressed(keyCode, scanCode, modifiers);
//	}
//
//	@Override
//	protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton)
//	{
//		boolean isMouseOut = mouseX < (double)guiLeftIn || mouseY < (double)guiTopIn || mouseX >= (double)(guiLeftIn + this.xSize) || mouseY >= (double)(guiTopIn + this.ySize);
//		return this.recipeBook.hasClickedOutside(mouseX, mouseY, this.guiLeft, this.guiTop, this.xSize, this.ySize, mouseButton) && isMouseOut;
//	}
//
//	@Override
//	public boolean charTyped(char codePoint, int modifiers)
//	{
//		return this.recipeBook.charTyped(codePoint, modifiers) ? true : super.charTyped(codePoint, modifiers);
//	}

	@Override
	public void recipesUpdated()
	{
		this.recipeBook.recipesUpdated();
	}

	@Override
	public GuiRecipeBook func_194310_f() {
		return null;
	}

//	@Override
//	public GuiRecipeBook getRecipeGui()
//	{
//		return this.recipeBook;
//	}
//
//	@Override
//	public void onGuiClosed()
//	{
//		this.recipeBook.onGuiClosed();
//		super.onGuiClosed();
//	}
}
