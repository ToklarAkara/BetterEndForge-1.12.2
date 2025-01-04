package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShadowGrassBlock extends Block {
	public ShadowGrassBlock() {
		super(Material.GRASS);
		this.setTickRandomly(true);
		setSoundType(SoundType.GROUND);
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(32) == 0) {
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double) pos.getX() + rand.nextDouble(), (double) pos.getY() + 1.1D, (double) pos.getZ() + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}
}
