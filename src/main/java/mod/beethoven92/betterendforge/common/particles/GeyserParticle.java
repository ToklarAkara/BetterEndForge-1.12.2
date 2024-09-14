package mod.beethoven92.betterendforge.common.particles;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleSimpleAnimated;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class GeyserParticle extends ParticleSimpleAnimated {
	private BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
	private boolean changeDir = false;
	private boolean check = true;

	protected GeyserParticle(World world, double x, double y, double z, double vx, double vy, double vz, TextureAtlasSprite sprite) {
		super(world, x, y, z, 0, 0, 0);
		this.setParticleTexture(sprite);
		this.particleMaxAge = ModMathHelper.randRange(400, 800, rand);
		this.particleScale = ModMathHelper.randRange(0.5F, 1.0F, rand);

		this.motionX = vx;
		this.motionZ = vz;
		this.prevPosY = y - 0.125;
	}

	@Override
	public void onUpdate() {
		if (this.prevPosY == this.posY || this.particleAge > this.particleMaxAge) {
			this.setExpired();
		} else {
			if (this.particleAge >= this.particleMaxAge - 200) {
				this.setAlphaF((this.particleMaxAge - this.particleAge) / 200F);
			}

			this.particleScale += 0.005F;
			this.motionY = 0.125;

			if (changeDir) {
				changeDir = false;
				check = false;
				this.motionX += ModMathHelper.randRange(-0.2, 0.2, rand);
				this.motionZ += ModMathHelper.randRange(-0.2, 0.2, rand);
			} else if (check) {
				changeDir = world.getBlockState(mut.setPos(posX, posY, posZ)).getMaterial().isLiquid();
				this.motionX = 0;
				this.motionZ = 0;
			}
		}
		super.onUpdate();
	}

	public static class Factory implements IParticleFactory {
		private final TextureAtlasSprite sprite;

		public Factory(TextureAtlasSprite sprite) {
			this.sprite = sprite;
		}

		@Override
		public Particle createParticle(int particleID, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
			return new GeyserParticle(world, x, y, z, 0, 0.125, 0, sprite);
		}
	}
}
