package mod.beethoven92.betterendforge.common.block.template;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class EndSaplingBlock extends BlockBush implements IGrowable {
	private static final AxisAlignedBB SHAPE = new AxisAlignedBB(4D/16D, 0D/16D, 4D/16D, 12D/16D, 14D/16D, 12D/16D);
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);

	public EndSaplingBlock(Material material) {
		super(material);
		setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
	}

	protected abstract WorldGenerator getFeature();

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return ModTags.END_GROUND.contains(worldIn.getBlockState(pos.down()).getBlock()) && canBlockStay(worldIn, pos, this.getDefaultState());
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos currentPos, IBlockState stateIn, EntityLivingBase placer, ItemStack stack){
		if (!this.canPlaceBlockAt(worldIn, currentPos)) {
			worldIn.setBlockState(currentPos, Blocks.AIR.getDefaultState());
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		grow(worldIn, rand, pos, state);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SHAPE;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		if (rand.nextInt(16) == 0) {
			getFeature().generate(worldIn, rand, pos);
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, AGE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(AGE);
	}
}
