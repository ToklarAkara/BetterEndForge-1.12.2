package mod.beethoven92.betterendforge.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import java.util.Collections;

public class CrystaliteLeggingsModel extends ModelBiped {

	public CrystaliteLeggingsModel(float scale) {
		super(scale, 0, 64, 48);
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, scale);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 32);
		this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

//	@Override
//	protected Iterable<ModelRenderer> getHeadParts() {
//		return Collections::emptyIterator;
//	}
//
//	@Override
//	protected Iterable<ModelRenderer> getBodyParts() {
//		return java.util.Arrays.asList(bipedBody, bipedLeftLeg, bipedRightLeg);
//	}
}
