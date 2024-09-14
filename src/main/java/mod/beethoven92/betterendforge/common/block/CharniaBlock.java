package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class CharniaBlock extends UnderwaterPlantBlock {

	public CharniaBlock() {
		super(Material.WATER);
		this.setTickRandomly(true);
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		return this.canSustainBush(worldIn.getBlockState(pos.down()))
				&& worldIn.getBlockState(pos).getMaterial() == Material.WATER;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return this.canBlockStay((World) worldIn, pos, worldIn.getBlockState(pos));
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
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
		return SoundType.PLANT;
	}
}
