package mod.beethoven92.betterendforge.client.renderer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.AuroraCrystalBlock;
import mod.beethoven92.betterendforge.common.util.AdvMathHelper;
import mod.beethoven92.betterendforge.common.util.ColorHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.opengl.GL11;

public class EternalCrystalRenderer extends ModelBase
{
	private static final ResourceLocation CRYSTAL_TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/eternal_crystal.png");
	private static final ModelRenderer[] SHARDS;
	private static final ModelRenderer CORE;

	static
	{
		SHARDS = new ModelRenderer[4];
		SHARDS[0] = new ModelRenderer(new EternalCrystalRenderer(), 2, 4).setTextureSize(16,16).addBox(-5.0F, 1.0F, -3.0F, 2, 8, 2);
		SHARDS[1] = new ModelRenderer(new EternalCrystalRenderer(), 2, 4).setTextureSize(16,16).addBox(3.0F, -1.0F, -1.0F, 2, 8, 2);
		SHARDS[2] = new ModelRenderer(new EternalCrystalRenderer(), 2, 4).setTextureSize(16,16).addBox(-1.0F, 0.0F, -5.0F, 2, 4, 2);
		SHARDS[3] = new ModelRenderer(new EternalCrystalRenderer(), 2, 4).setTextureSize(16,16).addBox(0.0F, 3.0F, 4.0F, 2, 6, 2);
		CORE = new ModelRenderer(new EternalCrystalRenderer(), 0, 0).setTextureSize(16,16);
		CORE.addBox(-2.0F, -2.0F, -2.0F,  4, 12, 4);
	}

	public static void render(int age, float tickDelta, float x, float y, float z)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x+0.5, y, z+0.5);
		GlStateManager.scale(1F, 1F, 1F);
		GlStateManager.rotate((float) (((age + tickDelta) / 25.0F + 6.0F)/Math.PI/2*180), 0.0F, 1.0F, 0.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture (CRYSTAL_TEXTURE);
		GlStateManager.disableDepth();

		float[] colors = colors(age);
		GlStateManager.color(colors[0], colors[1], colors[2], colors[3]);
		CORE.render(0.0625F);

		for (int i = 0; i < 4; i++)
		{
			GlStateManager.pushMatrix();
			float offset = MathHelper.sin((age + tickDelta) / 25.0F * 2 + i) * 0.15F;
			GlStateManager.translate(0, offset, 0);
			SHARDS[i].render(0.0625F);
			GlStateManager.popMatrix();
		}
		GlStateManager.enableDepth();
		GlStateManager.popMatrix();
	}

	public static float[] colors(int age)
	{
		double delta = age * 0.01;
		int index = ModMathHelper.floor(delta);
		int index2 = (index + 1) & 3;
		delta -= index;
		index &= 3;

		Vec3i color1 = AuroraCrystalBlock.COLORS[index];
		Vec3i color2 = AuroraCrystalBlock.COLORS[index2];

		int r = ModMathHelper.floor(AdvMathHelper.lerp(delta, color1.getX(), color2.getX()));
		int g = ModMathHelper.floor(AdvMathHelper.lerp(delta, color1.getY(), color2.getY()));
		int b = ModMathHelper.floor(AdvMathHelper.lerp(delta, color1.getZ(), color2.getZ()));

		return ColorHelper.toFloatArray(ModMathHelper.color(r, g, b));
	}
}
