package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.EndVineBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class FilaluxBlock extends EndVineBlock {
	public FilaluxBlock() {
		super(Material.PLANTS);
		setHardness(0.0F);
		setLightLevel(1);
		setSoundType(SoundType.PLANT);
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		if(state.getValue(SHAPE)==TripleShape.BOTTOM)
			return 15;
		return 0;
	}

	@Override
	public Block.EnumOffsetType getOffsetType() {
		return Block.EnumOffsetType.NONE;
	}
}
