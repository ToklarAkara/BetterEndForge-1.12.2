package mod.beethoven92.betterendforge.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class EndSlimeEntityModel extends ModelBase {
	private final ModelRenderer flower;
	private final ModelRenderer crop;
	private final ModelRenderer innerCube;
	private final ModelRenderer rightEye;
	private final ModelRenderer leftEye;
	private final ModelRenderer mouth;

	public EndSlimeEntityModel(boolean onlyShell) {
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.innerCube = new ModelRenderer(this, 0, 16);
		this.rightEye = new ModelRenderer(this, 32, 0);
		this.leftEye = new ModelRenderer(this, 32, 4);
		this.mouth = new ModelRenderer(this, 32, 8);
		this.flower = new ModelRenderer(this);
		this.crop = new ModelRenderer(this);

		if (onlyShell) {
			this.innerCube.setTextureOffset(0, 0);
			this.innerCube.addBox(-4.0F, 16.0F, -4.0F, 8, 8, 8);
		} else {
			this.innerCube.addBox(-3.0F, 17.0F, -3.0F, 6, 6, 6);
			this.rightEye.addBox(-3.25F, 18.0F, -3.5F, 2, 2, 2);
			this.leftEye.addBox(1.25F, 18.0F, -3.5F, 2, 2, 2);
			this.mouth.addBox(0.0F, 21.0F, -3.5F, 1, 1, 1);

			for (int i = 0; i < 4; i++) {
				ModelRenderer petalRot = new ModelRenderer(this);
				petalRot.rotateAngleY = (float) Math.toRadians(i * 45F);

				ModelRenderer petal = new ModelRenderer(this, 40, 0);
				petal.setRotationPoint(-4, 8, 0);
				petal.addBox(0.0F, 0.0F, 0.0F, 8, 8, 0);

				this.flower.addChild(petalRot);
				petalRot.addChild(petal);
			}

			for (int i = 0; i < 2; i++) {
				ModelRenderer petalRot = new ModelRenderer(this);
				petalRot.rotateAngleY = (float) Math.toRadians(i * 90F + 45F);

				ModelRenderer petal = new ModelRenderer(this, 40, 0);
				petal.setRotationPoint(-4, 8, 0);
				petal.addBox(0.0F, 0.0F, 0.0F, 8, 8, 0);

				this.crop.addChild(petalRot);
				petalRot.addChild(petal);
			}
		}
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
		// Implement rotation logic if needed
	}

	public void renderFlower(float scale) {
		flower.render(scale);
	}

	public void renderCrop(float scale) {
		crop.render(scale);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		innerCube.render(scale);
		rightEye.render(scale);
		leftEye.render(scale);
		mouth.render(scale);
	}
}
