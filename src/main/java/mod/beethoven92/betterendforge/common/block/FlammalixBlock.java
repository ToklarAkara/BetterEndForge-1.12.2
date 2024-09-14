package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class FlammalixBlock extends BlockBush {
	private static final AxisAlignedBB SHAPE = new AxisAlignedBB(2D/16D, 0D/16D, 2D/16D, 14D/16D, 14D/16D, 14D/16D);

	public FlammalixBlock() {
		super(Material.ROCK);
		this.setSoundType(SoundType.STONE);
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == ModBlocks.PALLIDIUM_FULL ||
				state.getBlock() == ModBlocks.PALLIDIUM_HEAVY ||
				state.getBlock() == ModBlocks.PALLIDIUM_THIN ||
				state.getBlock() == ModBlocks.PALLIDIUM_TINY;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SHAPE;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
}
