package mod.beethoven92.betterendforge.client.renderer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.entity.ShadowWalkerEntity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class ShadowWalkerEntityRenderer extends RenderBiped<ShadowWalkerEntity> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/shadow_walker.png");

	public ShadowWalkerEntityRenderer(RenderManager renderManager) {
		super(renderManager, new ModelBiped(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(ShadowWalkerEntity entity) {
		return TEXTURE;
	}
}
