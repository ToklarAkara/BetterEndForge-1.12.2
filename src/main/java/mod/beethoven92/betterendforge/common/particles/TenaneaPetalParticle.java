package mod.beethoven92.betterendforge.common.particles;

import mod.beethoven92.betterendforge.common.block.TenaneaFlowersBlock;
import mod.beethoven92.betterendforge.common.util.AdvMathHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class TenaneaPetalParticle extends Particle
{
	protected static final TextureAtlasSprite PARTICLE_ATLAS = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("betterendforge:particle/spritesheet");
	private double preVX;
	private double preVY;
	private double preVZ;
	private double nextVX;
	private double nextVY;
	private double nextVZ;

	protected TenaneaPetalParticle(World world, double x, double y, double z, double r,
								   double g, double b)
	{
		super(world, x, y, z, r, g, b);

		setParticleTexture(PARTICLE_ATLAS);
		particleTextureIndexY = 7;

		int color = TenaneaFlowersBlock.getBlockColor(new BlockPos(x, y, z));

		this.particleRed = ((color >> 16) & 255) / 255F;
		this.particleGreen = ((color >> 8) & 255) / 255F;
		this.particleBlue = ((color) & 255) / 255F;

		this.particleMaxAge = ModMathHelper.randRange(120, 200, rand);
		this.particleScale = ModMathHelper.randRange(0.05F, 0.15F, rand);
		this.particleAlpha = 0;

		preVX = 0;
		preVY = 0;
		preVZ = 0;

		nextVX = rand.nextGaussian() * 0.02;
		nextVY = -rand.nextDouble() * 0.02 - 0.02;
		nextVZ = rand.nextGaussian() * 0.02;
	}

	@Override
	public void onUpdate()
	{
		int ticks = this.particleAge & 63;
		if (ticks == 0)
		{
			preVX = nextVX;
			preVY = nextVY;
			preVZ = nextVZ;
			nextVX = rand.nextGaussian() * 0.02;
			nextVY = -rand.nextDouble() * 0.02 - 0.02;
			nextVZ = rand.nextGaussian() * 0.02;
		}

		double delta = (double) ticks / 63.0;

		if (this.particleAge <= 40)
		{
			this.setAlphaF(this.particleAge / 40F);
		}
		else if (this.particleAge >= this.particleMaxAge - 40)
		{
			this.setAlphaF((this.particleMaxAge - this.particleAge) / 40F);
		}

		if (this.particleAge >= this.particleMaxAge)
		{
			this.setExpired();
		}

		this.motionX = AdvMathHelper.lerp(delta, preVX, nextVX);
		this.motionY = AdvMathHelper.lerp(delta, preVY, nextVY);
		this.motionZ = AdvMathHelper.lerp(delta, preVZ, nextVZ);

		super.onUpdate();
	}

	public static class Factory implements IParticleFactory
	{
		@Override
		public Particle createParticle(int particleID, World world, double x, double y, double z,
									   double xSpeed, double ySpeed, double zSpeed, int... args)
		{
			return new TenaneaPetalParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
		}
	}
}
