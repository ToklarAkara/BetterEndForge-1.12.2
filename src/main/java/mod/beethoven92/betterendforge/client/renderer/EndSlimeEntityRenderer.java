package mod.beethoven92.betterendforge.client.renderer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.model.EndSlimeEntityModel;
import mod.beethoven92.betterendforge.common.entity.EndSlimeEntity;
import mod.beethoven92.betterendforge.common.util.AdvMathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EndSlimeEntityRenderer extends RenderLiving<EndSlimeEntity> {
	private static final ResourceLocation[] TEXTURE = new ResourceLocation[4];
	private static final ResourceLocation[] GLOW = new ResourceLocation[4];

	public EndSlimeEntityRenderer(RenderManager renderManager) {
		super(renderManager, new EndSlimeEntityModel(false), 0.25F);
		this.addLayer(new OverlayFeatureRenderer<EndSlimeEntity>());
		this.addLayer(new EyesLayer<EndSlimeEntity>());
	}

	@Override
	protected ResourceLocation getEntityTexture(EndSlimeEntity entity) {
		return TEXTURE[entity.getSlimeType()];
	}

	@Override
	protected void preRenderCallback(EndSlimeEntity entity, float partialTickTime) {
		GlStateManager.scale(0.999F, 0.999F, 0.999F);
		GlStateManager.translate(0.0D, 0.0010000000474974513D, 0.0D);
		float h = (float) entity.getSlimeSize();
		float i = AdvMathHelper.lerp(partialTickTime, entity.prevSquishFactor, entity.squishFactor) / (h * 0.5F + 1.0F);
		float j = 1.0F / (i + 1.0F);
		GlStateManager.scale(j * h, 1.0F / j * h, j * h);
	}

	@Override
	public void doRender(EndSlimeEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
		this.shadowSize = 0.25F * (float) entity.getSlimeSize();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	private final class OverlayFeatureRenderer<T extends EndSlimeEntity> implements LayerRenderer<T> {
		private final EndSlimeEntityModel modelOrdinal = new EndSlimeEntityModel(true);
		private final EndSlimeEntityModel modelLake = new EndSlimeEntityModel(true);

		public OverlayFeatureRenderer() {
			super();
		}

		@Override
		public void doRenderLayer(T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
			if (!entity.isInvisible()) {
				if (entity.isLake()) {
					Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE[2]);
					this.modelLake.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
				} else if (entity.isAmber() || entity.isChorus()) {
					Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE[3]);
					this.modelOrdinal.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
				}
			}
		}

		@Override
		public boolean shouldCombineTextures() {
			return false;
		}
	}

	private final class EyesLayer<T extends EndSlimeEntity> implements LayerRenderer<T> {
		@Override
		public void doRenderLayer(T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(GLOW[entity.getSlimeType()]);
			GlStateManager.enableBlend();
			GlStateManager.disableAlpha();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
			GlStateManager.depthMask(!entity.isInvisible());
			GlStateManager.disableLighting();
			GlStateManager.enableLighting();
			GlStateManager.depthMask(true);
			GlStateManager.disableBlend();
			GlStateManager.enableAlpha();
		}

		@Override
		public boolean shouldCombineTextures() {
			return false;
		}
	}

	static {
		TEXTURE[0] = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_slime/end_slime.png");
		TEXTURE[1] = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_slime/end_slime_mossy.png");
		TEXTURE[2] = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_slime/end_slime_lake.png");
		TEXTURE[3] = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_slime/end_slime_amber.png");
		GLOW[0] = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_slime/end_slime_glow.png");
		GLOW[1] = GLOW[0];
		GLOW[2] = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_slime/end_slime_lake_glow.png");
		GLOW[3] = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_slime/end_slime_amber_glow.png");
	}
}
