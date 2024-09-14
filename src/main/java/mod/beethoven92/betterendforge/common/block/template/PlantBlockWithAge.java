package mod.beethoven92.betterendforge.common.block.template;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class PlantBlockWithAge extends PlantBlock {
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);

	public PlantBlockWithAge(Material material) {
		super(material);
		setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, AGE);
	}

	public abstract void growAdult(World world, Random random, BlockPos pos);

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		if (rand.nextInt(4) == 0) {
			int age = state.getValue(AGE);
			if (age < 3) {
				worldIn.setBlockState(pos, state.withProperty(AGE, age + 1));
			} else {
				growAdult(worldIn, rand, pos);
			}
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		grow(worldIn, rand, pos, state);
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
