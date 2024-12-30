package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.DoublePlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class UmbrellaMossTallBlock extends DoublePlantBlock {

	public UmbrellaMossTallBlock() {
		super(Material.PLANTS);
		this.setSoundType(SoundType.PLANT);
		this.setHardness(0.0F);
		this.setResistance(0.0F);
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == ModBlocks.END_MOSS || state.getBlock() == ModBlocks.END_MYCELIUM;
	}

	public int quantityDropped(Random random)
	{
		return 0;
	}

	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
	{
		if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
		{
			spawnAsEntity(worldIn, pos, new ItemStack(ModBlocks.UMBRELLA_MOSS_TALL, 1, 0));
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
