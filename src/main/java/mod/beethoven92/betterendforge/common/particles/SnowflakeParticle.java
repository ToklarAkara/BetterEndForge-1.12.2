package mod.beethoven92.betterendforge.common.particles;

import mod.beethoven92.betterendforge.common.util.AdvMathHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleSimpleAnimated;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SnowflakeParticle extends Particle
{
	protected static final TextureAtlasSprite PARTICLE_ATLAS = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("betterendforge:particle/spritesheet");
	private int ticks;
	private double preVX;
	private double preVY;
	private double preVZ;
	private double nextVX;
	private double nextVY;
	private double nextVZ;

	protected SnowflakeParticle(World world, double x, double y, double z, double vx, double vy, double vz)
	{
		super(world, x, y, z, vx, vy, vz);

		setParticleTexture(PARTICLE_ATLAS);
		particleTextureIndexY = 5;

		this.particleMaxAge = ModMathHelper.randRange(150, 300, rand);
		this.particleScale = ModMathHelper.randRange(0.05F, 0.2F, rand);
		this.particleAlpha = 0F;

		preVX = rand.nextGaussian() * 0.015;
		preVY = rand.nextGaussian() * 0.015;
		preVZ = rand.nextGaussian() * 0.015;

		nextVX = rand.nextGaussian() * 0.015;
		nextVY = rand.nextGaussian() * 0.015;
		nextVZ = rand.nextGaussian() * 0.015;
	}

	@Override
	public void onUpdate()
	{
		ticks++;
		if (ticks > 200)
		{
			preVX = nextVX;
			preVY = nextVY;
			preVZ = nextVZ;
			nextVX = rand.nextGaussian() * 0.015;
			nextVY = rand.nextGaussian() * 0.015;
			nextVZ = rand.nextGaussian() * 0.015;
			if (rand.nextInt(4) == 0)
			{
				nextVY = Math.abs(nextVY);
			}
			ticks = 0;
		}
		double delta = (double) ticks / 200.0;

		if (this.particleAge <= 40) {
			this.setAlphaF(this.particleAge / 40F);
		}
		else if (this.particleAge >= this.particleMaxAge - 40) {
			this.setAlphaF((this.particleMaxAge - this.particleAge) / 40F);
		}

		if (this.particleAge >= this.particleMaxAge) {
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
			return new SnowflakeParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
		}
	}
}
