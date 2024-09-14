package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class UmbrellaTreeClusterBlock extends Block {
	public static final PropertyBool NATURAL = PropertyBool.create("natural");

	public UmbrellaTreeClusterBlock() {
		super(Material.PLANTS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NATURAL, false));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, NATURAL);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(NATURAL, (meta & 1) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(NATURAL) ? 1 : 0;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.getHeldItemMainhand();
		if (stack.getItem() == Items.GLASS_BOTTLE) {
			if (!playerIn.capabilities.isCreativeMode) {
				stack.shrink(1);
			}
			ItemStack newStack = new ItemStack(ModItems.UMBRELLA_CLUSTER_JUICE);
			if (!playerIn.inventory.addItemStackToInventory(newStack)) {
				playerIn.dropItem(newStack, false);
			}
			worldIn.playSound(playerIn, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
			BlockHelper.setWithUpdate(worldIn, pos, ModBlocks.UMBRELLA_TREE_CLUSTER_EMPTY.getDefaultState().withProperty(NATURAL, state.getValue(NATURAL)));
			return true;
		}
		return false;
	}
}
