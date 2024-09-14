package mod.beethoven92.betterendforge.client.renderer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.model.SilkMothEntityModel;
import mod.beethoven92.betterendforge.common.entity.SilkMothEntity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class SilkMothEntityRenderer extends RenderLiving<SilkMothEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/silk_moth.png");

    public SilkMothEntityRenderer(RenderManager renderManager) {
        super(renderManager, new SilkMothEntityModel(), 0.5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(SilkMothEntity entity) {
        return TEXTURE;
    }
}
