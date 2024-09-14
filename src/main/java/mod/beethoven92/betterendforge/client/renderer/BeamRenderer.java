package mod.beethoven92.betterendforge.client.renderer;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BeamRenderer
{
	private static final ResourceLocation BEAM_TEXTURE = new ResourceLocation("textures/entity/end_gateway_beam.png");

	public static void renderLightBeam(float tick, int minY, int maxY, float[] colors, float alpha, float beamIn, float beamOut)
	{
		float red = colors[0];
		float green = colors[1];
		float blue = colors[2];

		int maxBY = minY + maxY;
		float delta = maxY < 0 ? tick : -tick;
		float fractDelta = delta * 0.2F - (float) Math.floor(delta * 0.1F);
		float xIn = -beamIn;
		float minV = Math.max(fractDelta - 1.0F, 0.0F);
		float maxV = (float) maxY * (0.5F / beamIn) + minV;
		float rotation = (tick) / 25.0F + 6.0F;

		GlStateManager.pushMatrix();
		GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
		renderBeam(red, green, blue, alpha, minY, maxBY, beamIn, xIn, minV, maxV);

		float xOut = -beamOut;
		maxV = (float) maxY + minV;
		renderBeam(red, green, blue, alpha, minY, maxBY, xOut, beamOut, minV, maxV);
		GlStateManager.popMatrix();
	}

	private static void renderBeam(float red, float green, float blue, float alpha, int minY, int maxY, float x1, float x2, float minV, float maxV)
	{
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

		buffer.pos(x1, minY, 0.0D).tex(0.0D, minV).color(red, green, blue, alpha).endVertex();
		buffer.pos(x1, maxY, 0.0D).tex(0.0D, maxV).color(red, green, blue, alpha).endVertex();
		buffer.pos(x2, maxY, 0.0D).tex(1.0D, maxV).color(red, green, blue, alpha).endVertex();
		buffer.pos(x2, minY, 0.0D).tex(1.0D, minV).color(red, green, blue, alpha).endVertex();

		tessellator.draw();
	}
}
