package mod.beethoven92.betterendforge.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class EndFishEntityModel extends ModelBase {
	private final ModelRenderer model;
	private final ModelRenderer fin_top;
	private final ModelRenderer fin_bottom;
	private final ModelRenderer flipper;
	private final ModelRenderer fin_right;
	private final ModelRenderer fin_left;

	public EndFishEntityModel() {
		textureWidth = 32;
		textureHeight = 32;

		model = new ModelRenderer(this);
		model.setRotationPoint(0.0F, 20.0F, 0.0F);
		model.setTextureOffset(0, 0).addBox(-1.0F, -2.0F, -4.0F, 2, 4, 8, 0.0F);

		fin_top = new ModelRenderer(this);
		fin_top.setRotationPoint(0.0F, -2.0F, -4.0F);
		model.addChild(fin_top);
		fin_top.rotateAngleX = -0.6981F;
		fin_top.setTextureOffset(0, 6).addBox(0.0F, -8.0F, 0.0F, 0, 8, 6, 0.0F);

		fin_bottom = new ModelRenderer(this);
		fin_bottom.setRotationPoint(0.0F, 2.0F, -4.0F);
		model.addChild(fin_bottom);
		fin_bottom.rotateAngleX = 0.6981F;
		fin_bottom.setTextureOffset(0, 6).addBox(0.0F, 0.0F, 0.0F, 0, 8, 6, 0.0F);

		flipper = new ModelRenderer(this);
		flipper.setRotationPoint(0.0F, 0.0F, 2.0F);
		model.addChild(flipper);
		flipper.rotateAngleX = -0.7854F;
		flipper.setTextureOffset(0, 15).addBox(0.0F, -5.0F, 0.0F, 0, 5, 5, 0.0F);

		fin_right = new ModelRenderer(this);
		fin_right.setRotationPoint(-1.0F, 0.0F, -1.0F);
		model.addChild(fin_right);
		fin_right.rotateAngleX = 1.5708F;
		fin_right.rotateAngleY = 0.7854F;
		fin_right.setTextureOffset(0, 25).addBox(-3.7071F, 0.7071F, -1.5F, 3, 0, 3, 0.0F);

		fin_left = new ModelRenderer(this);
		fin_left.setRotationPoint(1.0F, 0.0F, -1.0F);
		model.addChild(fin_left);
		fin_left.rotateAngleX = 1.5708F;
		fin_left.rotateAngleY = -0.7854F;
		fin_left.setTextureOffset(0, 25).addBox(0.7071F, 0.7071F, -1.5F, 3, 0, 3, true);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		model.render(scale);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
		float s1 = (float) Math.sin(ageInTicks * 0.1);
		float s2 = (float) Math.sin(ageInTicks * 0.05);
		flipper.rotateAngleY = s1 * 0.3F;
		fin_top.rotateAngleX = s2 * 0.02F - 0.6981F;
		fin_bottom.rotateAngleX = 0.6981F - s2 * 0.02F;
		fin_left.rotateAngleY = s1 * 0.3F - 0.7854F;
		fin_right.rotateAngleY = 0.7854F - s1 * 0.3F;
	}
}
