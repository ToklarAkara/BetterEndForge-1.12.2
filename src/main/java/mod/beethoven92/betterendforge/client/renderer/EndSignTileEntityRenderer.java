package mod.beethoven92.betterendforge.client.renderer;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.EndSignBlock;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.tileentity.ESignTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

public class EndSignTileEntityRenderer extends TileEntitySpecialRenderer<ESignTileEntity> {
	private static final HashMap<Block, ResourceLocation> TEXTURES = Maps.newHashMap();
	private static ResourceLocation defaultTexture;
	private final ModelSign model = new ModelSign();

	public EndSignTileEntityRenderer() {
		defaultTexture = new ResourceLocation("textures/entity/signs/oak.png");

		ModItems.ITEMS.forEach((item) -> {
			if (item instanceof ItemBlock) {
				Block block = ((ItemBlock) item).getBlock();
				if (block instanceof EndSignBlock) {
					String name = block.getRegistryName().getPath();
					ResourceLocation texture = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/signs/" + name + ".png");
					TEXTURES.put(block, texture);
				}
			}
		});
	}

	@Override
	public void render(ESignTileEntity entity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		IBlockState state = entity.getWorld().getBlockState(new BlockPos(x,y,z));
		GlStateManager.pushMatrix();
		float angle = state.getBlock() instanceof BlockStandingSign ? (float)(state.getValue(BlockStandingSign.ROTATION) * 360) / 16.0F : state.getValue(BlockWallSign.FACING).getHorizontalAngle();
		GlStateManager.translate(x + 0.5D, y + 0.5D, z + 0.5D);
		GlStateManager.rotate(-angle, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(-0.5D, -0.5D, -0.5D);
		this.bindTexture(TEXTURES.getOrDefault(state.getBlock(), defaultTexture));
		GlStateManager.enableRescaleNormal();
		GlStateManager.pushMatrix();
		GlStateManager.scale(1.0F, -1.0F, -1.0F);
		this.model.renderSign();
		GlStateManager.popMatrix();
		FontRenderer fontRenderer = this.getFontRenderer();
		GlStateManager.translate(0.0D, 0.33333334D, 0.046666667D);
		GlStateManager.scale(0.010416667F, -0.010416667F, 0.010416667F);
		int color = entity.getTextColor().getColorValue();
		int red = (int)(color >> 16 & 255) * 4 / 10;
		int green = (int)(color >> 8 & 255) * 4 / 10;
		int blue = (int)(color & 255) * 4 / 10;
		int textColor = (red << 16) + (green << 8) + blue;

		for (int i = 0; i < 4; ++i) {
			ITextComponent text = entity.text[i];
			if (text != null) {
				float textWidth = (float)(-fontRenderer.getStringWidth(text.getFormattedText()) / 2);
				fontRenderer.drawString(text.getFormattedText(), (int) textWidth, i * 10 - 20, textColor);
			}
		}

		GlStateManager.popMatrix();
	}
}
