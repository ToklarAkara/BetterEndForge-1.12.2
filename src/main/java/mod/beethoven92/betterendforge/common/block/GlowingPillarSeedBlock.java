package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.FurBlock;
import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GlowingPillarSeedBlock extends PlantBlock {
	public static final PropertyEnum<TripleShape> SHAPE = PropertyEnum.create("shape", TripleShape.class);

	public GlowingPillarSeedBlock() {
		super(Material.PLANTS);
		setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, TripleShape.BOTTOM));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHAPE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(SHAPE, TripleShape.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(SHAPE).ordinal();
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			this.growAdult(worldIn, rand, pos);
		}
	}

	public void growAdult(World world, Random random, BlockPos pos) {
		int height = ModMathHelper.randRange(1, 2, random);
		int h = BlockHelper.upRay(world, pos, height + 2);
		if (h < height) {
			return;
		}

		MutableBlockPos mut = new MutableBlockPos(pos);
		IBlockState roots = ModBlocks.GLOWING_PILLAR_ROOTS.getDefaultState();
		if (height < 2) {
			BlockHelper.setWithUpdate(world, mut, roots.withProperty(SHAPE, TripleShape.MIDDLE));
			mut.move(EnumFacing.UP);
		} else {
			BlockHelper.setWithUpdate(world, mut, roots.withProperty(SHAPE, TripleShape.BOTTOM));
			mut.move(EnumFacing.UP);
			BlockHelper.setWithUpdate(world, mut, roots.withProperty(SHAPE, TripleShape.TOP));
			mut.move(EnumFacing.UP);
		}
		BlockHelper.setWithUpdate(world, mut, ModBlocks.GLOWING_PILLAR_LUMINOPHOR.getDefaultState().withProperty(BlockProperties.NATURAL, true));
		for (EnumFacing dir : EnumFacing.values()) {
			BlockPos offsetPos = mut.offset(dir);
			if (world.isAirBlock(offsetPos)) {
				BlockHelper.setWithUpdate(world, offsetPos, ModBlocks.GLOWING_PILLAR_LEAVES.getDefaultState().withProperty(FurBlock.FACING, dir));
			}
		}
		mut.move(EnumFacing.UP);
		if (world.isAirBlock(mut)) {
			BlockHelper.setWithUpdate(world, mut, ModBlocks.GLOWING_PILLAR_LEAVES.getDefaultState().withProperty(FurBlock.FACING, EnumFacing.UP));
		}
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == ModBlocks.AMBER_MOSS;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	public int quantityDropped(Random random)
	{
		return 0;
	}

	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
	{
		if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
		{
			spawnAsEntity(worldIn, pos, new ItemStack(state.getBlock(), 1, 0));
		}
		else
		{
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
	}
}
