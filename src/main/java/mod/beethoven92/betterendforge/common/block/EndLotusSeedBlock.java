package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;

public class EndLotusSeedBlock extends BlockBush implements IGrowable, IPlantable, IShearable
{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
	private static final AxisAlignedBB SHAPE = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.875D, 0.75D);

	public EndLotusSeedBlock()
	{
		super(Material.PLANTS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return SHAPE;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, AGE);
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
	{
		IBlockState down = worldIn.getBlockState(pos.down());
		return isValidTerrain(down) && worldIn.getBlockState(pos).getMaterial() == Material.WATER;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos currentPos, IBlockState stateIn, EntityLivingBase placer, ItemStack stack){
		if (!this.canPlaceBlockAt(worldIn, currentPos)) {
			worldIn.setBlockState(currentPos, Blocks.WATER.getDefaultState());
		}
	}

	protected boolean isValidTerrain(IBlockState state)
	{
		return ModTags.END_GROUND.contains(state.getBlock()) || state.getBlock() == ModBlocks.ENDSTONE_DUST;
	}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
	{
		grow(worldIn, random, pos, state);
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		if (rand.nextInt(4) == 0)
		{
			int age = state.getValue(AGE);
			if (age < 3)
			{
				worldIn.setBlockState(pos, state.withProperty(AGE, age + 1));
			}
			else
			{
				generate(worldIn, rand, pos);
			}
		}
	}

	public boolean searchForAirAbove(World worldIn, BlockPos pos)
	{
		MutableBlockPos bpos = new MutableBlockPos(pos);
		while (worldIn.getBlockState(bpos).getMaterial() == Material.WATER)
		{
			bpos.move(EnumFacing.UP);
		}
		return worldIn.isAirBlock(bpos) && worldIn.isAirBlock(bpos.up());
	}

	public void generate(World worldIn, Random rand, BlockPos pos)
	{
		if (searchForAirAbove(worldIn, pos))
		{
			IBlockState startLeaf = ModBlocks.END_LOTUS_STEM.getDefaultState().withProperty(EndLotusStemBlock.LEAF, true);
			IBlockState roots = ModBlocks.END_LOTUS_STEM.getDefaultState().withProperty(EndLotusStemBlock.SHAPE, TripleShape.BOTTOM).withProperty(BlockLiquid.LEVEL, 0);
			IBlockState stem = ModBlocks.END_LOTUS_STEM.getDefaultState();
			IBlockState flower = ModBlocks.END_LOTUS_FLOWER.getDefaultState();

			worldIn.setBlockState(pos, roots, 3);
			MutableBlockPos bpos = new MutableBlockPos(pos);
			bpos.move(EnumFacing.UP);
			while (worldIn.getBlockState(bpos).getMaterial() == Material.WATER)
			{
				worldIn.setBlockState(bpos, stem.withProperty(BlockLiquid.LEVEL, 0), 3);
				bpos.move(EnumFacing.UP);
			}

			int height = rand.nextBoolean() ? 0 : rand.nextBoolean() ? 1 : rand.nextBoolean() ? 1 : -1;
			TripleShape shape = (height == 0) ? TripleShape.TOP : TripleShape.MIDDLE;
			EnumFacing dir = BlockHelper.getRandomHorizontalDirection(rand);
			BlockPos leafCenter = bpos.toImmutable().offset(dir);
			if (canGenerateLeaf(worldIn, leafCenter))
			{
				generateLeaf(worldIn, leafCenter);
				worldIn.setBlockState(bpos, startLeaf.withProperty(EndLotusStemBlock.SHAPE, shape).withProperty(EndLotusStemBlock.FACING, dir), 3);
			}
			else
			{
				worldIn.setBlockState(bpos, stem.withProperty(EndLotusStemBlock.SHAPE, shape), 3);
			}

			bpos.move(EnumFacing.UP);
			for (int i = 1; i <= height; i++)
			{
				if (!worldIn.isAirBlock(bpos))
				{
					bpos.move(EnumFacing.DOWN);
					worldIn.setBlockState(bpos, flower, 3);
					bpos.move(EnumFacing.DOWN);
					stem = worldIn.getBlockState(bpos);
					worldIn.setBlockState(bpos, stem.withProperty(EndLotusStemBlock.SHAPE, TripleShape.TOP), 3);
					return;
				}
				worldIn.setBlockState(bpos, stem, 3);
				bpos.move(EnumFacing.UP);
			}

			if (!worldIn.isAirBlock(bpos) || height < 0)
			{
				bpos.move(EnumFacing.DOWN);
			}

			worldIn.setBlockState(bpos, flower, 3);
			bpos.move(EnumFacing.DOWN);
			stem = worldIn.getBlockState(bpos);
			if (stem.getBlock()!=(ModBlocks.END_LOTUS_STEM))
			{
				stem = ModBlocks.END_LOTUS_STEM.getDefaultState();
				if (!worldIn.getBlockState(bpos.north()).getMaterial().isReplaceable())
				{
					stem = stem.withProperty(BlockLiquid.LEVEL, 0);
				}
			}

			if (worldIn.getBlockState(bpos.offset(dir)).getBlock()==(ModBlocks.END_LOTUS_LEAF))
			{
				stem = stem.withProperty(EndLotusStemBlock.LEAF, true).withProperty(EndLotusStemBlock.FACING, dir);
			}

			worldIn.setBlockState(bpos, stem.withProperty(EndLotusStemBlock.SHAPE, TripleShape.TOP), 3);
		}
	}

	private boolean canGenerateLeaf(World world, BlockPos pos)
	{
		MutableBlockPos p = new MutableBlockPos();
		p.setY(pos.getY());
		int count = 0;
		for (int x = -1; x < 2; x++)
		{
			p.setPos(pos.getX() + x, p.getY(), p.getZ());
			for (int z = -1; z < 2; z++)
			{
				p.setPos(p.getX(), p.getY(), pos.getZ() + z);
				if (world.isAirBlock(p) && !world.getBlockState(p.down()).getMaterial().isReplaceable())
					count++;
			}
		}
		return count == 9;
	}

	private void generateLeaf(World worldIn, BlockPos pos)
	{
		MutableBlockPos p = new MutableBlockPos();
		IBlockState leaf = ModBlocks.END_LOTUS_LEAF.getDefaultState();
		worldIn.setBlockState(pos, leaf.withProperty(EndLotusLeafBlock.SHAPE, TripleShape.BOTTOM), 3);

		for (EnumFacing move : EnumFacing.Plane.HORIZONTAL)
		{
			worldIn.setBlockState(p.setPos(pos).move(move), leaf.withProperty(EndLotusLeafBlock.HORIZONTAL_FACING, move).withProperty(EndLotusLeafBlock.SHAPE, TripleShape.MIDDLE), 3);
		}

		for (int i = 0; i < 4; i++)
		{
			EnumFacing d1 = BlockHelper.HORIZONTAL_DIRECTIONS[i];
			EnumFacing d2 = BlockHelper.HORIZONTAL_DIRECTIONS[(i + 1) & 3];
			worldIn.setBlockState(p.setPos(pos).move(d1).move(d2), leaf.withProperty(EndLotusLeafBlock.HORIZONTAL_FACING, d1).withProperty(EndLotusLeafBlock.SHAPE, TripleShape.TOP), 3);
		}
	}


	@Override public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) { return true; }
	@Override
	public java.util.List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return com.google.common.collect.Lists.newArrayList(new ItemStack(Item.getItemFromBlock(this)));
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

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}// TODO META
}