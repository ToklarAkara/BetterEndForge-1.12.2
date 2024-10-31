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
}
