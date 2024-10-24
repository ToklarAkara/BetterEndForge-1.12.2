package mod.beethoven92.betterendforge.client.renderer;

import net.minecraft.client.Minecraft;
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
		float rotation = (float) (((tick) / 25.0F + 6.0F)/Math.PI/2*180);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

		GlStateManager.pushMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(BEAM_TEXTURE);
		GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
		renderBeam1(buffer, red, green, blue, alpha, minY, maxBY, beamIn, 0.0F, 0.0F, beamIn, 0.0F, xIn, xIn, 0.0F, 0.0F, 1.0F, minV, maxV);

		float xOut = -beamOut;
		maxV = (float) maxY + minV;
		renderBeam1(buffer, red, green, blue, alpha, minY, maxBY, xOut, xOut, beamOut, xOut, xOut, beamOut, beamOut, beamOut, 0.0F, 1.0F, minV, maxV);
		tessellator.draw();

		GlStateManager.popMatrix();
	}

	private static void renderBeam1(BufferBuilder vertexBuilder, float red, float green, float blue, float alpha, int minY, int maxY, float x1, float d1, float x2, float d2, float x3, float d3, float x4, float d4, float minU, float maxU, float minV, float maxV)
	{
		renderBeam(vertexBuilder, red, green, blue, alpha, maxY, minY, x1, d1, x2, d2, minU, maxU, minV, maxV);
		renderBeam(vertexBuilder, red, green, blue, alpha, maxY, minY, x4, d4, x3, d3, minU, maxU, minV, maxV);
		renderBeam(vertexBuilder, red, green, blue, alpha, maxY, minY, x2, d2, x4, d4, minU, maxU, minV, maxV);
		renderBeam(vertexBuilder, red, green, blue, alpha, maxY, minY, x3, d3, x1, d1, minU, maxU, minV, maxV);
	}

	private static void renderBeam(BufferBuilder vertexBuilder, float red, float green, float blue, float alpha, int minY, int maxY, float minX, float minD, float maxX, float maxD, float minU, float maxU, float minV, float maxV)
	{
		addVertex(vertexBuilder, red, green, blue, alpha, maxX, minY, maxD, maxU, minV);
		addVertex(vertexBuilder, red, green, blue, alpha, maxX, maxY, maxD, maxU, maxV);
		addVertex(vertexBuilder, red, green, blue, alpha, minX, maxY, minD, minU, maxV);
		addVertex(vertexBuilder, red, green, blue, alpha, minX, minY, minD, minU, minV);
	}

	private static void addVertex(BufferBuilder vertexBuilder, float red, float green, float blue, float alpha, float x, float y, float d, float u, float v)
	{
		vertexBuilder.pos(x, y, d).color(red, green, blue, alpha).tex(u, v).lightmap(255,255).normal( 0.0F, 1.0F, 0.0F).endVertex();
	}

//	private static void renderBeam(float red, float green, float blue, float alpha, int minY, int maxY, float x1, float x2, float minV, float maxV)
//	{
//		Tessellator tessellator = Tessellator.getInstance();
//		BufferBuilder buffer = tessellator.getBuffer();
//		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
//
//		buffer.pos(x1, minY, 0.0D).tex(0.0D, minV).color(red, green, blue, alpha).endVertex();
//		buffer.pos(x1, maxY, 0.0D).tex(0.0D, maxV).color(red, green, blue, alpha).endVertex();
//		buffer.pos(x2, maxY, 0.0D).tex(1.0D, maxV).color(red, green, blue, alpha).endVertex();
//		buffer.pos(x2, minY, 0.0D).tex(1.0D, minV).color(red, green, blue, alpha).endVertex();
//
//		tessellator.draw();
//	}
}
