package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.DoublePlantBlock;
import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class UmbrellaMossBlock extends PlantBlock {
	public UmbrellaMossBlock() {
		super(Material.PLANTS);
		this.setSoundType(SoundType.PLANT);
		this.setHardness(0.0F);
		this.setResistance(0.0F);
		this.setTickRandomly(true);
	}

	@Override
	public float getAmbientOcclusionLightValue(IBlockState state) {
		return 1.0F;
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == ModBlocks.END_MOSS || state.getBlock() == ModBlocks.END_MYCELIUM;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return worldIn.isAirBlock(pos.up());
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		int rot = rand.nextInt(4);
		IBlockState bs = ModBlocks.UMBRELLA_MOSS_TALL.getDefaultState().withProperty(DoublePlantBlock.ROTATION, rot);
		BlockHelper.setWithoutUpdate(worldIn, pos, bs);
		BlockHelper.setWithoutUpdate(worldIn, pos.up(), bs.withProperty(DoublePlantBlock.TOP, true));
	}

	public int quantityDropped(Random random)
	{
		return 0;
	}

	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
	{
		if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
		{
			spawnAsEntity(worldIn, pos, new ItemStack(ModBlocks.UMBRELLA_MOSS, 1, 0));
		}
		else
		{
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
	}

	@Override
	protected boolean isTerrain(IBlockState state)
	{
		return state.getBlock() == ModBlocks.END_MOSS || state.getBlock() == ModBlocks.END_MYCELIUM;
	}
}
