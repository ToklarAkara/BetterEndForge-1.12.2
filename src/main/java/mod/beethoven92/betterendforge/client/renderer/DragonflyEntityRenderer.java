package mod.beethoven92.betterendforge.client.renderer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.model.DragonflyEntityModel;
import mod.beethoven92.betterendforge.client.renderer.layer.EyesLayer;
import mod.beethoven92.betterendforge.common.entity.DragonflyEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class DragonflyEntityRenderer extends RenderLiving<DragonflyEntity> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/dragonfly.png");
	private static final ResourceLocation GLOW = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/dragonfly_glow.png");

	public DragonflyEntityRenderer(RenderManager renderManager) {
		super(renderManager, new DragonflyEntityModel(), 0.5F);
		this.addLayer(new EyesLayer<>(this, new DragonflyEntityModel()));
	}

	@Override
	protected ResourceLocation getEntityTexture(DragonflyEntity entity) {
		return TEXTURE;
	}

	@Override
	protected void preRenderCallback(DragonflyEntity entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		this.bindTexture(GLOW);
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
}
