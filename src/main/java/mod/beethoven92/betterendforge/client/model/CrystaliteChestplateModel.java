package mod.beethoven92.betterendforge.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;

public class CrystaliteChestplateModel extends ModelBiped {

	public ModelRenderer leftShoulder;
	public ModelRenderer rightShoulder;
	private boolean thinArms;

	public CrystaliteChestplateModel(float scale, boolean thinArms) {
		super(scale, 0, 64, 48);
		this.thinArms = thinArms;
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, scale + 0.25F);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		if (thinArms) {
			this.leftShoulder = new ModelRenderer(this, 41, 32);
			this.leftShoulder.addBox(-1.0F, -2.5F, -2.0F, 3, 12, 4, scale + 0.35F);
			this.leftShoulder.setRotationPoint(5.0F, 2.5F, 0.0F);
			this.leftShoulder.mirror = true;
			this.rightShoulder = new ModelRenderer(this, 41, 16);
			this.rightShoulder.addBox(-2.0F, -2.5F, -2.0F, 3, 12, 4, scale + 0.35F);
			this.rightShoulder.setRotationPoint(-5.0F, 2.5F, 0.0F);
		} else {
			this.leftShoulder = new ModelRenderer(this, 40, 32);
			this.leftShoulder.addBox(-1.0F, -2.5F, -2.0F, 4, 12, 4, scale + 0.45F);
			this.leftShoulder.setRotationPoint(5.0F, 2.0F, 0.0F);
			this.leftShoulder.mirror = true;
			this.rightShoulder = new ModelRenderer(this, 40, 16);
			this.rightShoulder.addBox(-3.0F, -2.5F, -2.0F, 4, 12, 4, scale + 0.45F);
			this.rightShoulder.setRotationPoint(-5.0F, 2.0F, 0.0F);
		}
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.leftShoulder.render(scale);
		this.rightShoulder.render(scale);
		this.bipedBody.render(scale);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		this.leftShoulder.rotateAngleX = this.bipedLeftArm.rotateAngleX;
		this.leftShoulder.rotateAngleY = this.bipedLeftArm.rotateAngleY;
		this.leftShoulder.rotateAngleZ = this.bipedLeftArm.rotateAngleZ;
		this.rightShoulder.rotateAngleX = this.bipedRightArm.rotateAngleX;
		this.rightShoulder.rotateAngleY = this.bipedRightArm.rotateAngleY;
		this.rightShoulder.rotateAngleZ = this.bipedRightArm.rotateAngleZ;
	}

	@Override
	public void postRenderArm(float scale, EnumHandSide side) {
		ModelRenderer modelPart = this.getArmForSide(side);
		if (this.thinArms) {
			float f = 0.5F * (side == EnumHandSide.RIGHT ? 1 : -1);
			modelPart.rotationPointX += f;
			modelPart.postRender(scale);
			modelPart.rotationPointX -= f;
		} else {
			modelPart.postRender(scale);
		}
	}
}
