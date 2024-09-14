package mod.beethoven92.betterendforge.common.block.template;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EndCropBlock extends PlantBlock {
	private static final AxisAlignedBB SHAPE = new AxisAlignedBB(2D/16D, 0D/16D, 2D/16D, 14D/16D, 14D/16D, 14D/16D);
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);

	private final Block[] terrain;

	public EndCropBlock(Material material, Block... terrain) {
		super(material);
		setSoundType(SoundType.PLANT);
		this.terrain = terrain;
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SHAPE;
	}

	@Override
	public EnumOffsetType getOffsetType() {
		return EnumOffsetType.NONE;
	}

	protected boolean isTerrain(IBlockState state) {
		for (Block block : terrain) {
			if (state.getBlock() == block) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return state.getValue(AGE) < 3;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return state.getValue(AGE) < 3;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		if (rand.nextInt(8) == 0) {
			int age = state.getValue(AGE);
			if (age < 3) {
				BlockHelper.setWithUpdate(worldIn, pos, state.withProperty(AGE, age + 1));
			}
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		grow(worldIn, rand, pos, state);
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
