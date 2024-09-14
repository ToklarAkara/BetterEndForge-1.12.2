package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.EndSaplingBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import mod.beethoven92.betterendforge.common.world.feature.UmbrellaTreeFeature;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class UmbrellaTreeSaplingBlock extends EndSaplingBlock {

	public UmbrellaTreeSaplingBlock() {
		super(Material.PLANTS);
	}

	@Override
	protected WorldGenerator getFeature() {
		return new UmbrellaTreeFeature(true);
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		IBlockState soil = worldIn.getBlockState(pos.down());
		return soil.getBlock() == ModBlocks.JUNGLE_MOSS;
	}
}
