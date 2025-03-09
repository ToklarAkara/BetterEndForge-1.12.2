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
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class EndStoneSmelterScreen extends GuiContainer// implements IRecipeShownListener
{
	private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/gui/smelter_gui.png");

	//public final GuiRecipeBook recipeBook;
	private boolean narrow;
	private InventoryPlayer player;
	public EndStoneSmelterScreen(InventoryPlayer playerInv, EndStoneSmelterContainer container)
	{
		super(container);
		//this.recipeBook = new GuiRecipeBook();
		this.ySize = 166;
		this.player = playerInv;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		this.narrow = this.width < 379;
		//this.recipeBook.initVisuals(narrow, ((EndStoneSmelterContainer) this.inventorySlots));
		//this.guiLeft = this.recipeBook.updateScreenPosition(this.narrow, this.width, this.xSize);
		//this.addButton(new GuiButtonImage(10, this.guiLeft + 20, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE));
		//this.titleX = (this.xSize - this.fontRenderer.getStringWidth("")) / 2;
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		//this.recipeBook.tick();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
//		if (this.recipeBook.isVisible() && this.narrow)
//		{
//			this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
//			this.recipeBook.render(mouseX, mouseY, partialTicks);
//		}
//		else
//		{
//			this.recipeBook.render(mouseX, mouseY, partialTicks);
//			super.drawScreen(mouseX, mouseY, partialTicks);
//			this.recipeBook.renderGhostRecipe(this.guiLeft, this.guiTop, true, partialTicks);
//		}
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
		//this.recipeBook.renderTooltip(this.guiLeft, this.guiTop, mouseX, mouseY);
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = "End Stone Smelter";
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		this.mc.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		int k;
		if (TileEntityFurnace.isBurning(((EndStoneSmelterContainer)this.inventorySlots).inventory))
		{
			k = this.getBurnLeftScaled(13);
			this.drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
		}
		k = this.getCookProgressScaled(24);
		this.drawTexturedModalRect(i + 92, j + 34, 176, 14, k + 1, 16);
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
		//this.recipeBook.slotClicked(slotIn);
	}

	private int getCookProgressScaled(int p_175381_1_) {
		int lvt_2_1_ = ((EndStoneSmelterContainer)this.inventorySlots).inventory.getField(2);
		int lvt_3_1_ = ((EndStoneSmelterContainer)this.inventorySlots).inventory.getField(3);
		return lvt_3_1_ != 0 && lvt_2_1_ != 0 ? lvt_2_1_ * p_175381_1_ / lvt_3_1_ : 0;
	}

	private int getBurnLeftScaled(int p_175382_1_) {
		int lvt_2_1_ = ((EndStoneSmelterContainer)this.inventorySlots).inventory.getField(1);
		if (lvt_2_1_ == 0) {
			lvt_2_1_ = 200;
		}

		return ((EndStoneSmelterContainer)this.inventorySlots).inventory.getField(0) * p_175382_1_ / lvt_2_1_;
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

//	@Override
//	public void recipesUpdated()
//	{
//		//this.recipeBook.recipesUpdated();
//	}
//
//	@Override
//	public GuiRecipeBook func_194310_f() {
//		return null;
//	}

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
