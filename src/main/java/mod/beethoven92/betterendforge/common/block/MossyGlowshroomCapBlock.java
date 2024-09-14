package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MossyGlowshroomCapBlock extends Block {
	public static final PropertyBool TRANSITION = PropertyBool.create("transition");

	public MossyGlowshroomCapBlock() {
		super(Material.WOOD);
		setSoundType(SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TRANSITION, false));
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(TRANSITION, ModBlocks.MOSSY_GLOWSHROOM.isTreeLog(worldIn.getBlockState(pos.down())));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TRANSITION);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TRANSITION) ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TRANSITION, meta == 1);
	}
}
