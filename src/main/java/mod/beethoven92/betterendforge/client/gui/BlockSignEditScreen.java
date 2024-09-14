package mod.beethoven92.betterendforge.client.gui;

import java.util.Arrays;

import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.input.Keyboard;

import mod.beethoven92.betterendforge.common.block.EndSignBlock;
import mod.beethoven92.betterendforge.common.tileentity.ESignTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public class BlockSignEditScreen extends GuiScreen {
	private static final ResourceLocation SIGN_TEXTURE = new ResourceLocation("textures/entity/sign.png");
	private final ESignTileEntity sign;
	private int updateCounter;
	private int editLine;
	private GuiButton doneBtn;
	private final String[] text = new String[4];

	public BlockSignEditScreen(ESignTileEntity sign) {
		this.sign = sign;
		for (int i = 0; i < 4; ++i) {
			this.text[i] = sign.text[i].getUnformattedText();
		}
	}

	@Override
	public void initGui() {
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		this.doneBtn = this.addButton(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, I18n.translateToLocal("gui.done")));
		this.sign.setEditable(false);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		NetHandlerPlayClient connection = this.mc.getConnection();
		if (connection != null) {
			ITextComponent[] arr = new ITextComponent[text.length];
			for(int i=0;i<arr.length;i++){
				arr[i] = new TextComponentString(text[i]);
			}
			connection.sendPacket(new CPacketUpdateSign(this.sign.getPos(), arr));
		}
		this.sign.setEditable(true);
	}

	@Override
	public void updateScreen() {
		++this.updateCounter;
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.enabled && button.id == 0) {
			this.sign.markDirty();
			this.mc.displayGuiScreen(null);
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			this.actionPerformed(this.doneBtn);
		} else if (keyCode == Keyboard.KEY_RETURN) {
			this.editLine = (this.editLine + 1) % 4;
		} else {
			String s = this.text[this.editLine];
			if (keyCode == Keyboard.KEY_BACK && !s.isEmpty()) {
				s = s.substring(0, s.length() - 1);
			} else if (ChatAllowedCharacters.isAllowedCharacter(typedChar) && this.fontRenderer.getStringWidth(s + typedChar) <= 90) {
				s = s + typedChar;
			}
			this.text[this.editLine] = s;
			this.sign.text[this.editLine] = new TextComponentString(s);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, I18n.translateToLocal("sign.edit"), this.width / 2, 40, 16777215);
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)(this.width / 2), 0.0F, 50.0F);
		float scale = 93.75F;
		GlStateManager.scale(scale, -scale, scale);
		GlStateManager.translate(0.0F, -1.3125F, 0.0F);
		IBlockState blockState = this.sign.getWorld().getBlockState(sign.getPos());
		boolean isStanding = sign.getBlockType() instanceof EndSignBlock && blockState.getValue(EndSignBlock.FLOOR);

		if (!isStanding) {
			GlStateManager.translate(0.0F, -0.3125F, 0.0F);
		}

		boolean cursorBlink = this.updateCounter / 6 % 2 == 0;
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.6666667F, -0.6666667F, -0.6666667F);
		this.mc.getTextureManager().bindTexture(SIGN_TEXTURE);
		GlStateManager.enableRescaleNormal();
		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.5F, -0.5F, -0.5F);
//		this.model.signBoard.render(0.0625F);
//		if (isStanding) {
//			this.model.signStick.render(0.0625F);
//		} TODO SIGN RENDERER
		GlStateManager.popMatrix();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.translate(0.0F, 0.33333334F, 0.046666667F);
		GlStateManager.scale(0.010416667F, -0.010416667F, 0.010416667F);
		int color = this.sign.getTextColor().getColorValue();
		int cursorPos = this.fontRenderer.getStringWidth(this.text[this.editLine]) / 2;
		int yOffset = this.editLine * 10 - this.text.length * 5;

		for (int i = 0; i < this.text.length; ++i) {
			String line = this.text[i];
			if (line != null) {
//				if (this.fontRenderer.getBidiFlag()) {
//					line = this.fontRenderer.bidiReorder(line);
//				}
				float xOffset = (float)(-this.fontRenderer.getStringWidth(line) / 2);
				this.fontRenderer.drawString(line, (int) xOffset, i * 10 - this.text.length * 5, color);
				if (i == this.editLine && cursorPos >= 0 && cursorBlink) {
					this.fontRenderer.drawString("_", (int) (xOffset + cursorPos), yOffset, color);
				}
			}
		}

		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
