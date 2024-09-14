package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.HydraluxShape;
import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HydraluxSaplingBlock extends PlantBlock {
	public static final PropertyEnum<HydraluxShape> SHAPE = PropertyEnum.create("shape", HydraluxShape.class);

	public HydraluxSaplingBlock() {
		super(Material.WATER);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, HydraluxShape.ROOTS));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHAPE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(SHAPE, HydraluxShape.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(SHAPE).ordinal();
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			this.doGrow(worldIn, rand, pos);
		}
	}

	public void doGrow(World world, Random random, BlockPos pos) {
		int h = ModMathHelper.randRange(4, 8, random);
		MutableBlockPos mut = new MutableBlockPos(pos);

		for (int i = 1; i < h; i++) {
			mut.setY(pos.getY() + i);
			if (!world.getBlockState(mut).getBlock().equals(Blocks.WATER)) {
				return;
			}
		}

		mut.setY(pos.getY());
		IBlockState state = ModBlocks.HYDRALUX.getDefaultState();
		BlockHelper.setWithoutUpdate(world, pos, state.withProperty(SHAPE, HydraluxShape.ROOTS));
		for (int i = 1; i < h - 2; i++) {
			mut.setY(pos.getY() + i);
			BlockHelper.setWithoutUpdate(world, mut, state.withProperty(SHAPE, HydraluxShape.VINE));
		}

		mut.setY(mut.getY() + 1);
		boolean big = random.nextBoolean();
		BlockHelper.setWithoutUpdate(world, mut, state.withProperty(SHAPE, big ? HydraluxShape.FLOWER_BIG_BOTTOM : HydraluxShape.FLOWER_SMALL_BOTTOM));

		mut.setY(mut.getY() + 1);
		BlockHelper.setWithoutUpdate(world, mut, state.withProperty(SHAPE, big ? HydraluxShape.FLOWER_BIG_TOP : HydraluxShape.FLOWER_SMALL_TOP));
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == ModBlocks.SULPHURIC_ROCK.stone;
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
