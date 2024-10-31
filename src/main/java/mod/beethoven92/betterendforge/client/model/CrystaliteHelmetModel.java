package mod.beethoven92.betterendforge.client.model;

import com.google.common.collect.Lists;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class CrystaliteHelmetModel extends ModelBiped {

	public CrystaliteHelmetModel(float scale) {
		super(scale, 0, 64, 48);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale + 0.5F);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

//	@Override
//	protected Iterable<ModelRenderer> getHeadParts() {
//		return Lists.newArrayList(this.bipedHead);
//	}
//
//	@Override
//	protected Iterable<ModelRenderer> getBodyParts() {
//		return Lists.newArrayList();
//	}
}
