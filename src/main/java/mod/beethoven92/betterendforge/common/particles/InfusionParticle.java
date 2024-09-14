package mod.beethoven92.betterendforge.common.particles;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.World;

public class InfusionParticle extends Particle
{
	private final TextureAtlasSprite spriteWithAge;

	public InfusionParticle(World world, double x, double y, double z, double velocityX,
							double velocityY, double velocityZ, float[] palette, TextureAtlasSprite spriteWithAge)
	{
		super(world, x, y, z, 0.0, 0.0, 0.0);
		this.spriteWithAge = spriteWithAge;
		this.setParticleTexture(spriteWithAge);
		this.setRBGColorF(palette[0], palette[1], palette[2]);
		this.particleAlpha = palette[3];
		this.motionX = velocityX * 0.1D;
		this.motionY = velocityY * 0.1D;
		this.motionZ = velocityZ * 0.1D;
		this.particleMaxAge = (int) (3.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
		this.particleScale *= 0.9F;
	}

	@Override
	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.particleAge++ >= this.particleMaxAge)
		{
			this.setExpired();
		}
		else
		{
			this.setParticleTexture(this.spriteWithAge);
			double velocityX = 2.0D * this.motionX * this.rand.nextDouble();
			double velocityY = 3.0D * this.motionY * this.rand.nextDouble();
			double velocityZ = 2.0D * this.motionZ * this.rand.nextDouble();
			this.move(velocityX, velocityY, velocityZ);
		}
	}

	public static class Factory implements IParticleFactory
	{
		private final TextureAtlasSprite sprite;

		public Factory(TextureAtlasSprite sprite)
		{
			this.sprite = sprite;
		}

		@Override
		public Particle createParticle(int particleID, World world, double x, double y, double z,
									   double xSpeed, double ySpeed, double zSpeed, int... args)
		{
			float[] palette = new float[] {1.0F, 1.0F, 1.0F, 1.0F}; // Default palette, adjust as needed
			return new InfusionParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, palette, this.sprite);
		}
	}
}
