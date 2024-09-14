package mod.beethoven92.betterendforge.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BlockBenchModel<T extends Entity> extends ModelBase
{
	private final ModelRenderer modelRenderer;

	public BlockBenchModel() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.modelRenderer = new ModelRenderer(this);
		this.modelRenderer.setRotationPoint(0.0F, 24.0F, 0.0F);
		// Add your model parts here
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.modelRenderer.render(scale);
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		// Set rotation angles here
	}

	protected void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
