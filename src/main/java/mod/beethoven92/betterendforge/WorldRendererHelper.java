package mod.beethoven92.betterendforge;

import mod.beethoven92.betterendforge.client.ClientOptions;
import mod.beethoven92.betterendforge.common.util.BackgroundInfo;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.vector.Quaternion;
import mod.beethoven92.betterendforge.common.util.sdf.vector.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class WorldRendererHelper {
    private static final ResourceLocation NEBULA_1 = new ResourceLocation(BetterEnd.MOD_ID, "textures/sky/nebula_2.png");
    private static final ResourceLocation NEBULA_2 = new ResourceLocation(BetterEnd.MOD_ID, "textures/sky/nebula_3.png");
    private static final ResourceLocation HORIZON = new ResourceLocation(BetterEnd.MOD_ID, "textures/sky/nebula_1.png");
    private static final ResourceLocation FOG = new ResourceLocation(BetterEnd.MOD_ID, "textures/sky/fog.png");
    private static final ResourceLocation STARS = new ResourceLocation(BetterEnd.MOD_ID, "textures/sky/stars.png");

    private static VertexBuffer stars1;
    private static VertexBuffer stars2;
    private static VertexBuffer stars3;
    private static VertexBuffer stars4;
    private static VertexBuffer nebulas1;
    private static VertexBuffer nebulas2;
    private static VertexBuffer horizon;
    private static VertexBuffer fog;
    private static Vector3f axis1;
    private static Vector3f axis2;
    private static Vector3f axis3;
    private static Vector3f axis4;
    private static float time;
    private static float time2;
    private static float time3;
    private static float blind02;
    private static float blind06;
    private static boolean directOpenGL = false; // Unused

    public static void init(){
        initStars();
        Random random = new Random(131);
        axis1 = new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
        axis2 = new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
        axis3 = new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
        axis4 = new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
        axis1.normalize();
        axis2.normalize();
        axis3.normalize();
        axis4.normalize();
    }

    public static void renderSkyEnd(int cloudTickCounter){

        if (ClientOptions.isCustomSky())
        {
            TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;
            time = (cloudTickCounter % 360000) * 0.000017453292F;
            time2 = time * 2;
            time3 = time * 3;

            GlStateManager.disableFog();
            GlStateManager.enableTexture2D();

            //if (directOpenGL)
            {
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glAlphaFunc(516, 0.0F);
                GL11.glEnable(GL11.GL_BLEND);
                GlStateManager.depthMask(false);
            }
			/*else
			{
				RenderSystem.enableAlphaTest();
				RenderSystem.alphaFunc(516, 0.0F);
				RenderSystem.enableBlend();
			}*/

            float blindA = 1F - BackgroundInfo.blindness;
            blind02 = blindA * 0.2F;
            blind06 = blindA * 0.6F;

            if (blindA > 0)
            {
                GlStateManager.pushMatrix();
                GlStateManager.rotate(new Quaternion(0, time, 0, false).getLWJGL());
                renderEngine.bindTexture(HORIZON);
                WorldRendererHelper.renderBuffer(horizon, DefaultVertexFormats.POSITION_TEX, 0.77F, 0.31F, 0.73F, 0.7F * blindA);
                GlStateManager.popMatrix();

                GlStateManager.pushMatrix();
                GlStateManager.rotate(new Quaternion(0, -time, 0, false).getLWJGL());
                renderEngine.bindTexture(NEBULA_1);
                WorldRendererHelper.renderBuffer(nebulas1, DefaultVertexFormats.POSITION_TEX, 0.77F, 0.31F, 0.73F, blind02);
                GlStateManager.popMatrix();

                GlStateManager.pushMatrix();
                GlStateManager.rotate(new Quaternion(0, time2, 0, false).getLWJGL());
                renderEngine.bindTexture(NEBULA_2);
                WorldRendererHelper.renderBuffer(nebulas2, DefaultVertexFormats.POSITION_TEX, 0.77F, 0.31F, 0.73F, blind02);
                GlStateManager.popMatrix();
//
                renderEngine.bindTexture(STARS);

                GlStateManager.pushMatrix();
                GlStateManager.rotate(axis3.rotation(time).getLWJGL());
                WorldRendererHelper.renderBuffer(stars3, DefaultVertexFormats.POSITION_TEX, 0.77F, 0.31F, 0.73F, blind06);
                GlStateManager.popMatrix();

                GlStateManager.pushMatrix();
                GlStateManager.rotate(axis4.rotation(time2).getLWJGL());
                WorldRendererHelper.renderBuffer(stars4, DefaultVertexFormats.POSITION_TEX, 1F, 1F, 1F, blind06);
                GlStateManager.popMatrix();
            }

//            float a = (BackgroundInfo.fog - 1F);
//            if (a > 0)
//            {
//                if (a > 1) a = 1;
//                renderEngine.bindTexture(FOG);
//                WorldRendererHelper.renderBuffer(fog, DefaultVertexFormats.POSITION_TEX, BackgroundInfo.red, BackgroundInfo.green, BackgroundInfo.blue, a);
//            }

            GlStateManager.disableTexture2D();

            if (blindA > 0)
            {
                GlStateManager.pushMatrix();
                GlStateManager.rotate(axis1.rotation(time3).getLWJGL());
                WorldRendererHelper.renderBuffer(stars1, DefaultVertexFormats.POSITION, 1, 1, 1, blind06);
                GlStateManager.popMatrix();

                GlStateManager.pushMatrix();
                GlStateManager.rotate(axis2.rotation(time2).getLWJGL());
                WorldRendererHelper.renderBuffer(stars2, DefaultVertexFormats.POSITION, 0.95F, 0.64F, 0.93F, blind06);
                GlStateManager.popMatrix();
            }

            GlStateManager.enableTexture2D();
            GlStateManager.depthMask(true);

            //ci.cancel();
        }
    }

    public static void renderBuffer(VertexBuffer buffer, VertexFormat format, float r, float g, float b, float a)
    {
        GL11.glColor4f(r, g, b, a);
        buffer.bindBuffer();
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

        GL11.glVertexPointer(3, 5126, 12, 0);
        if(format== DefaultVertexFormats.POSITION_TEX) {
            GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
            GL11.glVertexPointer(3, 5126, 20, 0);
            GL11.glTexCoordPointer(2, 5126, 20, 12);
        }

        buffer.drawArrays(7);
        buffer.unbindBuffer();
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        if(format== DefaultVertexFormats.POSITION_TEX) {
            GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        }
    }

    public static void initStars()
    {
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        stars1 = WorldRendererHelper.buildBufferStars(buffer, stars1, 0.1, 0.30, 3500, 41315);
        stars2 = WorldRendererHelper.buildBufferStars(buffer, stars2, 0.1, 0.35, 2000, 35151);
        stars3 = WorldRendererHelper.buildBufferUVStars(buffer, stars3, 0.4, 1.2, 1000, 61354);
        stars4 = WorldRendererHelper.buildBufferUVStars(buffer, stars4, 0.4, 1.2, 1000, 61355);
        nebulas1 = WorldRendererHelper.buildBufferFarFog(buffer, nebulas1, 40, 60, 30, 11515);
        nebulas2 = WorldRendererHelper.buildBufferFarFog(buffer, nebulas2, 40, 60, 10, 14151);
        horizon = WorldRendererHelper.buildBufferHorizon(buffer, horizon);
        fog = WorldRendererHelper.buildBufferFog(buffer, fog);
    }

    public static VertexBuffer buildBufferStars(BufferBuilder bufferBuilder, VertexBuffer buffer, double minSize, double maxSize, int count, long seed)
    {
        if (buffer != null)
        {
            buffer.unbindBuffer();
        }

        buffer = new VertexBuffer(DefaultVertexFormats.POSITION);
        makeStars(bufferBuilder, minSize, maxSize, count, seed);
        bufferBuilder.finishDrawing();
        bufferBuilder.reset();
        buffer.bufferData(bufferBuilder.getByteBuffer());

        return buffer;
    }

    public static VertexBuffer buildBufferUVStars(BufferBuilder bufferBuilder, VertexBuffer buffer, double minSize, double maxSize, int count, long seed)
    {
        if (buffer != null)
        {
            buffer.unbindBuffer();
        }

        buffer = new VertexBuffer(DefaultVertexFormats.POSITION_TEX);
        makeUVStars(bufferBuilder, minSize, maxSize, count, seed);
        bufferBuilder.finishDrawing();
        bufferBuilder.reset();
        buffer.bufferData(bufferBuilder.getByteBuffer());

        return buffer;
    }

    public static VertexBuffer buildBufferFarFog(BufferBuilder bufferBuilder, VertexBuffer buffer, double minSize, double maxSize, int count, long seed)
    {
        if (buffer != null)
        {
            buffer.unbindBuffer();
        }

        buffer = new VertexBuffer(DefaultVertexFormats.POSITION_TEX);
        makeFarFog(bufferBuilder, minSize, maxSize, count, seed);
        bufferBuilder.finishDrawing();
        bufferBuilder.reset();
        buffer.bufferData(bufferBuilder.getByteBuffer());

        return buffer;
    }

    public static VertexBuffer buildBufferHorizon(BufferBuilder bufferBuilder, VertexBuffer buffer)
    {
        if (buffer != null)
        {
            buffer.unbindBuffer();
        }

        buffer = new VertexBuffer(DefaultVertexFormats.POSITION_TEX);
        makeCylinder(bufferBuilder, 16, 50, 100);
        bufferBuilder.finishDrawing();
        bufferBuilder.reset();
        buffer.bufferData(bufferBuilder.getByteBuffer());

        return buffer;
    }

    public static VertexBuffer buildBufferFog(BufferBuilder bufferBuilder, VertexBuffer buffer)
    {
        if (buffer != null) {
            buffer.unbindBuffer();
        }

        buffer = new VertexBuffer(DefaultVertexFormats.POSITION_TEX);
        makeCylinder(bufferBuilder, 16, 50, 70);
        bufferBuilder.finishDrawing();
        bufferBuilder.reset();
        buffer.bufferData(bufferBuilder.getByteBuffer());

        return buffer;
    }

    public static void makeStars(BufferBuilder buffer, double minSize, double maxSize, int count, long seed)
    {
        Random random = new Random(seed);
        buffer.begin(7, DefaultVertexFormats.POSITION);

        for (int i = 0; i < count; ++i) {
            double posX = random.nextDouble() * 2.0 - 1.0;
            double posY = random.nextDouble() * 2.0 - 1.0;
            double posZ = random.nextDouble() * 2.0 - 1.0;
            double size = MathHelper.nextDouble(random, minSize, maxSize);
            double length = posX * posX + posY * posY + posZ * posZ;
            if (length < 1.0 && length > 0.001) {
                length = 1.0 / Math.sqrt(length);
                posX *= length;
                posY *= length;
                posZ *= length;
                double j = posX * 100.0;
                double k = posY * 100.0;
                double l = posZ * 100.0;
                double m = Math.atan2(posX, posZ);
                double n = Math.sin(m);
                double o = Math.cos(m);
                double p = Math.atan2(Math.sqrt(posX * posX + posZ * posZ), posY);
                double q = Math.sin(p);
                double r = Math.cos(p);
                double s = random.nextDouble() * Math.PI * 2.0;
                double t = Math.sin(s);
                double u = Math.cos(s);

                for (int v = 0; v < 4; ++v) {
                    double x = (double) ((v & 2) - 1) * size;
                    double y = (double) ((v + 1 & 2) - 1) * size;
                    double aa = x * u - y * t;
                    double ab = y * u + x * t;
                    double ad = aa * q + 0.0 * r;
                    double ae = 0.0 * q - aa * r;
                    double af = ae * n - ab * o;
                    double ah = ab * n + ae * o;
                    buffer.pos(j + af, k + ad, l + ah).endVertex();
                }
            }
        }
    }

    public static void makeUVStars(BufferBuilder buffer, double minSize, double maxSize, int count, long seed)
    {
        Random random = new Random(seed);
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);

        for (int i = 0; i < count; ++i)
        {
            double posX = random.nextDouble() * 2.0 - 1.0;
            double posY = random.nextDouble() * 2.0 - 1.0;
            double posZ = random.nextDouble() * 2.0 - 1.0;
            double size = ModMathHelper.randRange(minSize, maxSize, random);
            double length = posX * posX + posY * posY + posZ * posZ;
            if (length < 1.0 && length > 0.001)
            {
                length = 1.0 / Math.sqrt(length);
                posX *= length;
                posY *= length;
                posZ *= length;
                double j = posX * 100.0;
                double k = posY * 100.0;
                double l = posZ * 100.0;
                double m = Math.atan2(posX, posZ);
                double n = Math.sin(m);
                double o = Math.cos(m);
                double p = Math.atan2(Math.sqrt(posX * posX + posZ * posZ), posY);
                double q = Math.sin(p);
                double r = Math.cos(p);
                double s = random.nextDouble() * Math.PI * 2.0;
                double t = Math.sin(s);
                double u = Math.cos(s);

                int pos = 0;
                float minV = random.nextInt(4) / 4F;
                for (int v = 0; v < 4; ++v)
                {
                    double x = (double) ((v & 2) - 1) * size;
                    double y = (double) ((v + 1 & 2) - 1) * size;
                    double aa = x * u - y * t;
                    double ab = y * u + x * t;
                    double ad = aa * q + 0.0 * r;
                    double ae = 0.0 * q - aa * r;
                    double af = ae * n - ab * o;
                    double ah = ab * n + ae * o;
                    float texU = (pos >> 1) & 1;
                    float texV = (((pos + 1) >> 1) & 1) / 4F + minV;
                    pos ++;
                    buffer.pos(j + af, k + ad, l + ah).tex(texU, texV).endVertex();
                }
            }
        }
    }

    public static void makeFarFog(BufferBuilder buffer, double minSize, double maxSize, int count, long seed)
    {
        Random random = new Random(seed);
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);

        for (int i = 0; i < count; ++i) {
            double posX = random.nextDouble() * 2.0 - 1.0;
            double posY = random.nextDouble() - 0.5;
            double posZ = random.nextDouble() * 2.0 - 1.0;
            double size = MathHelper.nextDouble(random, minSize, maxSize);
            double length = posX * posX + posY * posY + posZ * posZ;
            double distance = 2.0;
            double delta = 1.0 / count;
            if (length < 1.0 && length > 0.001) {
                length = distance / Math.sqrt(length);
                size *= distance;
                distance -= delta;
                posX *= length;
                posY *= length;
                posZ *= length;
                double j = posX * 100.0;
                double k = posY * 100.0;
                double l = posZ * 100.0;
                double m = Math.atan2(posX, posZ);
                double n = Math.sin(m);
                double o = Math.cos(m);
                double p = Math.atan2(Math.sqrt(posX * posX + posZ * posZ), posY);
                double q = Math.sin(p);
                double r = Math.cos(p);
                double s = random.nextDouble() * Math.PI * 2.0;
                double t = Math.sin(s);
                double u = Math.cos(s);

                int pos = 0;
                for (int v = 0; v < 4; ++v) {
                    double x = (double) ((v & 2) - 1) * size;
                    double y = (double) ((v + 1 & 2) - 1) * size;
                    double aa = x * u - y * t;
                    double ab = y * u + x * t;
                    double ad = aa * q + 0.0 * r;
                    double ae = 0.0 * q - aa * r;
                    double af = ae * n - ab * o;
                    double ah = ab * n + ae * o;
                    float texU = (pos >> 1) & 1;
                    float texV = ((pos + 1) >> 1) & 1;
                    pos ++;
                    buffer.pos(j + af, k + ad, l + ah).tex(texU, texV).endVertex();
                }
            }
        }
    }

    public static void makeCylinder(BufferBuilder buffer, int segments, double height, double radius)
    {
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        for (int i = 0; i < segments; i ++)
        {
            double a1 = (double) i * Math.PI * 2.0 / (double) segments;
            double a2 = (double) (i + 1) * Math.PI * 2.0 / (double) segments;
            double px1 = Math.sin(a1) * radius;
            double pz1 = Math.cos(a1) * radius;
            double px2 = Math.sin(a2) * radius;
            double pz2 = Math.cos(a2) * radius;

            float u0 = (float) i / (float) segments;
            float u1 = (float) (i + 1) / (float) segments;

            buffer.pos(px1, -height, pz1).tex(u0, 0).endVertex();
            buffer.pos(px1, height, pz1).tex(u0, 1).endVertex();
            buffer.pos(px2, height, pz2).tex(u1, 1).endVertex();
            buffer.pos(px2, -height, pz2).tex(u1, 0).endVertex();
        }
    }
}
