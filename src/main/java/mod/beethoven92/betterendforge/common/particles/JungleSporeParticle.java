package mod.beethoven92.betterendforge.common.particles;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleSimpleAnimated;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.World;

public class JungleSporeParticle extends ParticleSimpleAnimated
{
	protected static final TextureAtlasSprite PARTICLE_ATLAS = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("betterendforge:particle/spritesheet");

	protected JungleSporeParticle(World world, double x, double y, double z)
	{
		super(world, x, y, z, 0, 0, 0);
		setParticleTexture(PARTICLE_ATLAS);
		particleTextureIndexY = 4;
		this.particleMaxAge = ModMathHelper.randRange(150, 300, rand);
		this.particleScale = ModMathHelper.randRange(0.05F, 0.15F, rand);
		this.setColorFade(15916745);
		this.setAlphaF(0);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		int ticks = this.particleAge % 30;
		if (ticks == 0)
		{
			this.motionX = rand.nextGaussian() * 0.02;
			this.motionY = rand.nextFloat() * 0.02 + 0.02;
			this.motionZ = rand.nextGaussian() * 0.02;
			ticks = 0;
		}

		if (this.particleAge <= 30)
		{
			float delta = ticks / 30F;
			this.setAlphaF(delta);
		}
		else if (this.particleAge >= this.particleMaxAge)
		{
			this.setAlphaF(0);
		}
		else if (this.particleAge >= this.particleMaxAge - 30)
		{
			this.setAlphaF((this.particleMaxAge - this.particleAge) / 30F);
		}
		else
		{
			this.setAlphaF(1);
		}

		this.motionY -= 0.001F;
		this.motionX *= 0.99F;
		this.motionZ *= 0.99F;
	}

	public static class Factory implements IParticleFactory
	{

		@Override
		public Particle createParticle(int particleID, World world, double x, double y, double z,
									   double xSpeed, double ySpeed, double zSpeed, int... args)
		{
			return new JungleSporeParticle(world, x, y, z);
		}
	}
}
