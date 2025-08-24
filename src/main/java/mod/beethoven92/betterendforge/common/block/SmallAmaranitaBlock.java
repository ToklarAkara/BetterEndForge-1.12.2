package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nullable;

public class SmallAmaranitaBlock extends BlockBush implements IGrowable {

	private static final AxisAlignedBB SHAPE = new AxisAlignedBB(4.0 / 16.0, 0.0, 4.0 / 16.0, 12.0 / 16.0, 10.0 / 16.0, 12.0 / 16.0);

	public SmallAmaranitaBlock() {
		super(Material.PLANTS);
		this.setSoundType(SoundType.PLANT);
		this.setHardness(0.0F);
		this.setResistance(0.0F);
		this.setTickRandomly(true);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SHAPE;
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == ModBlocks.SANGNUM || state.getBlock() == ModBlocks.MOSSY_OBSIDIAN || state.getBlock() == ModBlocks.MOSSY_DRAGON_BONE;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			if (rand.nextInt(8) == 0) {
				this.grow(worldIn, rand, pos, state);
			}
		}
	}

	@Override
	public boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean b) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
		return true;
	}

	public void grow(World world, Random random, BlockPos pos, IBlockState state) {
		BlockPos bigPos = growBig(world, pos);
		if (bigPos != null) {
			if (ModFeatures.GIGANTIC_AMARANITA.generate(world, random, bigPos)) {
				replaceMushroom(world, bigPos);
				replaceMushroom(world, bigPos.south());
				replaceMushroom(world, bigPos.east());
				replaceMushroom(world, bigPos.south().east());
			}
			return;
		}
		ModFeatures.LARGE_AMARANITA.generate(world, random, pos);
	}

	private BlockPos growBig(World world, BlockPos pos) {
		for (int x = -1; x < 2; x++) {
			for (int z = -1; z < 2; z++) {
				BlockPos p = pos.add(x, 0, z);
				if (checkFrame(world, p)) {
					return p;
				}
			}
		}
		return null;
	}

	private boolean checkFrame(World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() == this && world.getBlockState(pos.south()).getBlock() == this
				&& world.getBlockState(pos.east()).getBlock() == this && world.getBlockState(pos.south().east()).getBlock() == this;
	}

	private void replaceMushroom(World world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() == this) {
			BlockHelper.setWithUpdate(world, pos, Blocks.AIR.getDefaultState());
		}
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
