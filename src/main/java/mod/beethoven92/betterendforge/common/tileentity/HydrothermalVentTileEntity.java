package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.block.HydrothermalVentBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.state.IBlockState;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

// Is this tile entity necessary?
public class HydrothermalVentTileEntity extends TileEntity implements ITickable
{

	@Override
	public void update()
	{
		if (world.rand.nextInt(20) == 0)
		{
			double x = pos.getX() + world.rand.nextDouble();
			double y = pos.getY() + 0.9 + world.rand.nextDouble() * 0.3;
			double z = pos.getZ() + world.rand.nextDouble();
			IBlockState state = this.getWorld().getBlockState(pos);
			if (state.getBlock() == ModBlocks.HYDROTHERMAL_VENT && state.getValue(HydrothermalVentBlock.ACTIVATED))
			{
//				if (state.getValue(HydrothermalVentBlock.WATERLOGGED))
//				{
//					world.spawnParticle(ModParticleTypes.GEYSER_PARTICLE, x, y, z, 0, 0, 0);
//				}
//				else
//				{
//					world.spawnParticle(Particles.BUBBLE, x, y, z, 0, 0, 0);
//				} TODO PARTICLES
			}
		}
	}
}
