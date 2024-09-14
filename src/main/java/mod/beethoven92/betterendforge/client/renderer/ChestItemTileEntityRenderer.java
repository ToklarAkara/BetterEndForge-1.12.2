package mod.beethoven92.betterendforge.client.renderer;

import mod.beethoven92.betterendforge.common.tileentity.EChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class ChestItemTileEntityRenderer extends TileEntityItemStackRenderer {
	private EChestTileEntity chest;

	@Override
	public void renderByItem(ItemStack stack) {
		if (chest == null) {
			chest = new EChestTileEntity();
		}
		chest.setChest(Block.getBlockFromItem(stack.getItem()));
		GlStateManager.pushMatrix();
		TileEntityRendererDispatcher.instance.render(chest, 0.0D, 0.0D, 0.0D, 0.0F);
		GlStateManager.popMatrix();
	}
}
