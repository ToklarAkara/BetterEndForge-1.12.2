package mod.beethoven92.betterendforge.client.renderer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.model.EndFishEntityModel;
import mod.beethoven92.betterendforge.client.renderer.layer.EyesLayer;
import mod.beethoven92.betterendforge.common.entity.EndFishEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EndFishEntityRenderer extends RenderLiving<EndFishEntity> {
	private static final ResourceLocation[] TEXTURE = new ResourceLocation[EndFishEntity.VARIANTS];
	private static final ResourceLocation[] GLOW = new ResourceLocation[EndFishEntity.VARIANTS];

	public EndFishEntityRenderer(RenderManager renderManager) {
		super(renderManager, new EndFishEntityModel(), 0.5F);
		this.addLayer(new EyesLayer<>(this, new EndFishEntityModel()));
	}

	@Override
	protected ResourceLocation getEntityTexture(EndFishEntity entity) {
		return TEXTURE[entity.getVariant()];
	}

	@Override
	protected void preRenderCallback(EndFishEntity entity, float partialTickTime) {
		float scale = entity.getScale();
		GlStateManager.scale(scale, scale, scale);
	}

	@Override
	protected void renderModel(EndFishEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		super.renderModel(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		this.bindTexture(GLOW[entity.getVariant()]);
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

	static {
		for (int i = 0; i < EndFishEntity.VARIANTS; i++) {
			TEXTURE[i] = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_fish/end_fish_" + i + ".png");
			GLOW[i] = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_fish/end_fish_" + i + "_glow.png");
		}
	}
}
