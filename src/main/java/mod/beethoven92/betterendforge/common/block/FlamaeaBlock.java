package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class FlamaeaBlock extends BlockLilyPad {
	private static final AxisAlignedBB SHAPE = new AxisAlignedBB(0D/16D, 0D/16D, 0D/16D, 16D/16D, 1D/16D, 16D/16D);

	public FlamaeaBlock() {
		setHardness(0.0F);
		setSoundType(SoundType.PLANT);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SHAPE;
	}

	@Override
	public EnumOffsetType getOffsetType() {
		return EnumOffsetType.NONE;
	}
}
