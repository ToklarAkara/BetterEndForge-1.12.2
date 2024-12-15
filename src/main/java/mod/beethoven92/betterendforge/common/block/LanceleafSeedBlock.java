package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.PentaShape;
import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

public class LanceleafSeedBlock extends PlantBlockWithAge {
	public LanceleafSeedBlock(Material materialIn) {
		super(materialIn);
	}

	@Override
	public void growAdult(World world, Random random, BlockPos pos) {
		int height = ModMathHelper.randRange(4, 6, random);
		int h = BlockHelper.upRay(world, pos, height + 2);
		if (h < height + 1) {
			return;
		}
		int rotation = random.nextInt(3);
		MutableBlockPos mut = new MutableBlockPos(pos);
		IBlockState plant = ModBlocks.LANCELEAF.getDefaultState().withProperty(LanceleafBlock.ROTATION, rotation);
		BlockHelper.setWithUpdate(world, mut, plant.withProperty(BlockProperties.PENTA_SHAPE, PentaShape.BOTTOM));
		BlockHelper.setWithUpdate(world, mut.move(EnumFacing.UP), plant.withProperty(BlockProperties.PENTA_SHAPE, PentaShape.PRE_BOTTOM));
		for (int i = 2; i < height - 2; i++) {
			BlockHelper.setWithUpdate(world, mut.move(EnumFacing.UP), plant.withProperty(BlockProperties.PENTA_SHAPE, PentaShape.MIDDLE));
		}
		BlockHelper.setWithUpdate(world, mut.move(EnumFacing.UP), plant.withProperty(BlockProperties.PENTA_SHAPE, PentaShape.PRE_TOP));
		BlockHelper.setWithUpdate(world, mut.move(EnumFacing.UP), plant.withProperty(BlockProperties.PENTA_SHAPE, PentaShape.TOP));
	}

	@Override
	protected boolean isTerrain(IBlockState state) {
		return state.getBlock() == ModBlocks.AMBER_MOSS;
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
