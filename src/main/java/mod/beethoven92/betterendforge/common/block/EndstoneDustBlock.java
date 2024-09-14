package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class EndstoneDustBlock extends BlockFalling
{
	private static final int COLOR = ModMathHelper.color(226, 239, 168);

	public EndstoneDustBlock(Material materialIn)
	{
		super(materialIn);
		setSoundType(SoundType.SAND);
	}

	@Override
	public int getDustColor(IBlockState state)
	{
		return COLOR;
	}
}
