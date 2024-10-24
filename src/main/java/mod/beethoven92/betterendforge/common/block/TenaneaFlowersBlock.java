package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.EndVineBlock;
import mod.beethoven92.betterendforge.common.util.AdvMathHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TenaneaFlowersBlock extends EndVineBlock {
	public static final Vec3i[] COLORS;

	static {
		COLORS = new Vec3i[] {
				new Vec3i(250, 111, 222),
				new Vec3i(167, 89, 255),
				new Vec3i(120, 207, 239),
				new Vec3i(255, 87, 182)
		};
	}

	public TenaneaFlowersBlock() {
		super(Material.PLANTS);
		this.setTickRandomly(true);
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
		if (rand.nextInt(32) == 0) {
			double x = (double) pos.getX() + rand.nextGaussian() + 0.5;
			double z = (double) pos.getZ() + rand.nextGaussian() + 0.5;
			double y = (double) pos.getY() + rand.nextDouble();
			//worldIn.spawnParticle(ModParticleTypes.TENANEA_PETAL, x, y, z, 0, 0, 0); TODO PARTICLE
		}
	}

	public static int getBlockColor(BlockPos pos) {
		long i = (ModMathHelper.getRandom(pos.getX(), pos.getZ()) & 63) + pos.getY();

		double delta = i * 0.1;
		int index = ModMathHelper.floor(delta);
		int index2 = (index + 1) & 3;
		delta -= index;
		index &= 3;

		Vec3i color1 = COLORS[index];
		Vec3i color2 = COLORS[index2];

		int r = ModMathHelper.floor(AdvMathHelper.lerp(delta, color1.getX(), color2.getX()));
		int g = ModMathHelper.floor(AdvMathHelper.lerp(delta, color1.getY(), color2.getY()));
		int b = ModMathHelper.floor(AdvMathHelper.lerp(delta, color1.getZ(), color2.getZ()));
		float[] hsb = ModMathHelper.fromRGBtoHSB(r, g, b);

		return ModMathHelper.fromHSBtoRGB(hsb[0], Math.max(0.5F, hsb[1]), hsb[2]);
	}

	public static int getItemColor() {
		return ModMathHelper.color(255, 255, 255);
	}

	@Override
	public boolean isPassable(IBlockAccess p_176205_1_, BlockPos p_176205_2_) {
		return true;
	}
}
