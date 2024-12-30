package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.EndSaplingBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import mod.beethoven92.betterendforge.common.world.feature.HelixTreeFeature;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class HelixTreeSaplingBlock extends EndSaplingBlock {
	public HelixTreeSaplingBlock(Material material) {
		super(material);
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		return worldIn.getBlockState(pos.down()).getBlock() == ModBlocks.AMBER_MOSS;
	}

	@Override
	protected WorldGenerator getFeature() {
		return new HelixTreeFeature(true);
	}
}
