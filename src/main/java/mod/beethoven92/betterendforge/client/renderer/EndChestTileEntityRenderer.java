package mod.beethoven92.betterendforge.client.renderer;

import java.util.HashMap;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.tileentity.EChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EndChestTileEntityRenderer extends TileEntitySpecialRenderer<EChestTileEntity> {
	private static final HashMap<Block, ResourceLocation[]> TEXTURES = Maps.newHashMap();
	private static ResourceLocation[] defaultTextures;

	private final ModelChest simpleChest = new ModelChest();
	private final ModelChest largeChest = new ModelChest();

	public EndChestTileEntityRenderer() {
		defaultTextures = new ResourceLocation[] {
				new ResourceLocation("textures/entity/chest/normal.png"),
				new ResourceLocation("textures/entity/chest/normal_left.png"),
				new ResourceLocation("textures/entity/chest/normal_right.png")
		};

		ModItems.ITEMS.forEach((item) -> {
			if (item instanceof ItemBlock) {
				Block block = ((ItemBlock) item).getBlock();
				if (block instanceof BlockChest) {
					String name = block.getRegistryName().getPath();
					TEXTURES.put(block, new ResourceLocation[] {
							new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/chest/" + name + ".png"),
							new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/chest/" + name + "_left.png"),
							new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/chest/" + name + "_right.png")
					});
				}
			}
		});
	}

	@Override
	public void render(EChestTileEntity entity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		World world = entity.getWorld();
		boolean hasWorld = world != null;
		IBlockState state = hasWorld ? entity.getWorld().getBlockState(new BlockPos(x,y,z)) : Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.SOUTH);
		Block block = state.getBlock();
		if (entity.hasChest()) {
			block = entity.getChest();
		}

		if (block instanceof BlockChest) {
			BlockChest chest = (BlockChest) block;
			boolean isDouble = false;// chest.isDoubleChest(state);
			ModelChest model = isDouble ? this.largeChest : this.simpleChest;

			GlStateManager.pushMatrix();
			GlStateManager.enableRescaleNormal();
			GlStateManager.translate(x, y + 1.0F, z + 1.0F);
			GlStateManager.scale(1.0F, -1.0F, -1.0F);
			GlStateManager.translate(0.5F, 0.5F, 0.5F);
			GlStateManager.rotate(state.getValue(BlockChest.FACING).getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(-0.5F, -0.5F, -0.5F);

			float lidAngle = entity.prevLidAngle + (entity.lidAngle - entity.prevLidAngle) * partialTicks;
			lidAngle = 1.0F - lidAngle;
			lidAngle = 1.0F - lidAngle * lidAngle * lidAngle;
			model.chestLid.rotateAngleX = -(lidAngle * ((float) Math.PI / 2F));

			ResourceLocation[] textures = TEXTURES.getOrDefault(block, defaultTextures);
			this.bindTexture(textures[0]);
			model.renderAll();

			GlStateManager.disableRescaleNormal();
			GlStateManager.popMatrix();
		}
	}
}
