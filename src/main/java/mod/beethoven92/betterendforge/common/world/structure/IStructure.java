package mod.beethoven92.betterendforge.common.world.structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public interface IStructure {
	public void generate(World world, BlockPos pos, Random random);
}