package mod.beethoven92.betterendforge.common.particles;

import mod.beethoven92.betterendforge.common.util.AdvMathHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleSimpleAnimated;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;

public class FireflyParticle extends ParticleSimpleAnimated {
	private double preVX;
	private double preVY;
	private double preVZ;
	private double nextVX;
	private double nextVY;
	private double nextVZ;

	protected FireflyParticle(World world, double x, double y, double z, TextureAtlasSprite sprite) {
		super(world, x, y, z, 0, 0, 0);
		this.setParticleTexture(sprite);
		particleTextureIndexY = 2;
		this.particleMaxAge = ModMathHelper.randRange(150, 300, rand);
		this.particleScale = ModMathHelper.randRange(0.05F, 0.15F, rand);
		this.setColorFade(15916745);
		this.setAlphaF(0);

		preVX = rand.nextGaussian() * 0.02;
		preVY = rand.nextGaussian() * 0.02;
		preVZ = rand.nextGaussian() * 0.02;

		nextVX = rand.nextGaussian() * 0.02;
		nextVY = rand.nextGaussian() * 0.02;
		nextVZ = rand.nextGaussian() * 0.02;
	}

	@Override
	public void onUpdate() {
		int ticks = this.particleAge & 31;
		if (ticks == 0) {
			preVX = nextVX;
			preVY = nextVY;
			preVZ = nextVZ;
			nextVX = rand.nextGaussian() * 0.02;
			nextVY = rand.nextGaussian() * 0.02;
			nextVZ = rand.nextGaussian() * 0.02;
		}
		double delta = (double) ticks / 31.0;

		this.motionX = AdvMathHelper.lerp(delta, preVX, nextVX);
		this.motionY = AdvMathHelper.lerp(delta, preVY, nextVY);
		this.motionZ = AdvMathHelper.lerp(delta, preVZ, nextVZ);

		if (this.particleAge <= 60) {
			this.setAlphaF(this.particleAge / 60F);
		} else if (this.particleAge > particleMaxAge - 60) {
			this.setAlphaF((particleMaxAge - this.particleAge) / 60F);
		}

		super.onUpdate();
	}

	public static class FireflyParticleFactory implements IParticleFactory {
		private final TextureAtlasSprite sprite;

		public FireflyParticleFactory(TextureAtlasSprite sprite) {
			this.sprite = sprite;
		}

		@Override
		public Particle createParticle(int particleID, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
			return new FireflyParticle(world, x, y, z, sprite);
		}
	}
}
