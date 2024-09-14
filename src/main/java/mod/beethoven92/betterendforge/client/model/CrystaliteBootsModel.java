package mod.beethoven92.betterendforge.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class CrystaliteBootsModel extends ModelBiped {

	public ModelRenderer leftBoot;
	public ModelRenderer rightBoot;

	public CrystaliteBootsModel(float scale) {
		super(scale, 0, 64, 48);
		this.leftBoot = new ModelRenderer(this, 0, 32);
		this.leftBoot.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale + 0.25F);
		this.leftBoot.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightBoot = new ModelRenderer(this, 0, 16);
		this.rightBoot.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale + 0.25F);
		this.rightBoot.setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.leftBoot.render(scale);
		this.rightBoot.render(scale);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		this.leftBoot.rotateAngleX = this.bipedLeftLeg.rotateAngleX;
		this.leftBoot.rotateAngleY = this.bipedLeftLeg.rotateAngleY;
		this.leftBoot.rotateAngleZ = this.bipedLeftLeg.rotateAngleZ;
		this.rightBoot.rotateAngleX = this.bipedRightLeg.rotateAngleX;
		this.rightBoot.rotateAngleY = this.bipedRightLeg.rotateAngleY;
		this.rightBoot.rotateAngleZ = this.bipedRightLeg.rotateAngleZ;
	}
}
