package mod.beethoven92.betterendforge.client.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class EndCrystalRenderer {
	private static final ResourceLocation CRYSTAL_TEXTURE = new ResourceLocation("textures/entity/end_crystal/end_crystal.png");
	private static final ResourceLocation CRYSTAL_BEAM_TEXTURE = new ResourceLocation("textures/entity/end_crystal/end_crystal_beam.png");
	private static final float SINE_45_DEGREES = (float) Math.sin(0.7853981633974483D);
	private static final int AGE_CYCLE = 240;

	public static void render(int age, int maxAge, float tickDelta) {
		float k = (float) AGE_CYCLE / maxAge;
		float rotation = (age * k + tickDelta) * 3.0F;
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.8F, 0.8F, 0.8F);
		GlStateManager.translate(0.0D, -0.5D, 0.0D);
		GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(0.0D, 0.8F, 0.0D);
		GlStateManager.rotate(60.0F, SINE_45_DEGREES, 0.0F, SINE_45_DEGREES);
		renderModel();
		GlStateManager.scale(0.875F, 0.875F, 0.875F);
		GlStateManager.rotate(60.0F, SINE_45_DEGREES, 0.0F, SINE_45_DEGREES);
		GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
		renderModel();
		GlStateManager.scale(0.875F, 0.875F, 0.875F);
		GlStateManager.rotate(60.0F, SINE_45_DEGREES, 0.0F, SINE_45_DEGREES);
		GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
		renderCore();
		GlStateManager.popMatrix();
	}

	private static void renderModel() {
		// Render the model here
	}

	private static void renderCore() {
		// Render the core here
	}

	public static void renderBeam(BlockPos start, BlockPos end, float tickDelta, int age) {
		float dx = start.getX() - end.getX() + 1.0F;
		float dy = start.getY() - end.getY() + 1.0F;
		float dz = start.getZ() - end.getZ() + 1.0F;
		float f = MathHelper.sqrt(dx * dx + dz * dz);
		float g = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.0D, 2.0D, 0.0D);
		GlStateManager.rotate((float) (-Math.atan2(dz, dx)) - 1.5707964F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate((float) (-Math.atan2(f, dy)) - 1.5707964F, 1.0F, 0.0F, 0.0F);
		Tessellator tessellator = Tessellator.getInstance();
		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		float h = 0.0F - ((float) age + tickDelta) * 0.01F;
		float i = MathHelper.sqrt(dx * dx + dy * dy + dz * dz) / 32.0F - ((float) age + tickDelta) * 0.01F;
		float k = 0.0F;
		float l = 0.75F;
		float m = 0.0F;

		for (int n = 1; n <= 8; ++n) {
			float o = MathHelper.sin((float) n * 6.2831855F / 8.0F) * 0.75F;
			float p = MathHelper.cos((float) n * 6.2831855F / 8.0F) * 0.75F;
			float q = (float) n / 8.0F;
			tessellator.getBuffer().pos(k * 0.2F, l * 0.2F, 0.0F).tex(m, h).color(0, 0, 0, 255).endVertex();
			tessellator.getBuffer().pos(k, l, g).tex(m, i).color(255, 255, 255, 255).endVertex();
			tessellator.getBuffer().pos(o, p, g).tex(q, i).color(255, 255, 255, 255).endVertex();
			tessellator.getBuffer().pos(o * 0.2F, p * 0.2F, 0.0F).tex(q, h).color(0, 0, 0, 255).endVertex();
			k = o;
			l = p;
			m = q;
		}

		tessellator.draw();
		GlStateManager.popMatrix();
	}
}
