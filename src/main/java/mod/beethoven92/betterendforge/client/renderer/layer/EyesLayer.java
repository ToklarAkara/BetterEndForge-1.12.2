package mod.beethoven92.betterendforge.client.renderer.layer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class EyesLayer<T extends EntityLivingBase> implements LayerRenderer<T> {
	private static final ResourceLocation EYES_TEXTURE = new ResourceLocation("textures/entity/eyes.png");
	private final RenderLivingBase<T> renderer;
	private final ModelBase model;

	public EyesLayer(RenderLivingBase<T> renderer, ModelBase model) {
		this.renderer = renderer;
		this.model = model;
	}

	@Override
	public void doRenderLayer(T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.renderer.bindTexture(EYES_TEXTURE);
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
		GlStateManager.depthMask(!entity.isInvisible());
		GlStateManager.disableLighting();
		GlStateManager.enableLighting();
		this.model.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
		this.model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
		this.model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.depthMask(true);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}
