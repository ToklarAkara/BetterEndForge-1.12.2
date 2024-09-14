package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class MurkweedBlock extends BlockBush
{
	public MurkweedBlock(Material mat)
	{
		super(mat);
		this.setTickRandomly(true);
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		double x = pos.getX() + rand.nextDouble();
		double y = pos.getY() + rand.nextDouble() * 0.5 + 0.5;
		double z = pos.getZ() + rand.nextDouble();
		double v = rand.nextDouble() * 0.1;
		worldIn.spawnParticle(EnumParticleTypes.SPELL_MOB, x, y, z, v, v, v);
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if (entityIn instanceof EntityLivingBase && !((EntityLivingBase) entityIn).isPotionActive(MobEffects.BLINDNESS))
		{
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 50));
		}
	}

	@Override
	protected boolean canSustainBush(IBlockState state)
	{
		return state.getBlock() == ModBlocks.SHADOW_GRASS;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
	{
		return false;
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
