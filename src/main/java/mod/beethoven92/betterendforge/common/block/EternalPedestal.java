package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.rituals.EternalRitual;
import mod.beethoven92.betterendforge.common.teleporter.EndPortals;
import mod.beethoven92.betterendforge.common.tileentity.EternalPedestalTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneLight;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nullable;

public class EternalPedestal extends PedestalBlock
{
	public static final PropertyBool ACTIVATED = PropertyBool.create("activated");

	public EternalPedestal()
	{
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVATED, false));
	}


	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		boolean result = super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		if (result)
		{
			TileEntity blockEntity = worldIn.getTileEntity(pos);
			if (blockEntity instanceof EternalPedestalTileEntity)
			{
				EternalPedestalTileEntity pedestal = (EternalPedestalTileEntity) blockEntity;
				IBlockState updatedState = worldIn.getBlockState(pos);
				if (pedestal.isEmpty() && updatedState.getValue(ACTIVATED))
				{
					if (pedestal.hasRitual())
					{
						EternalRitual ritual = pedestal.getRitual();
						Item item = pedestal.getStack().getItem();
						int dim = EndPortals.getPortalState(ForgeRegistries.ITEMS.getKey(item));
						ritual.removePortal(dim);
					}
					worldIn.setBlockState(pos, updatedState.withProperty(ACTIVATED, false));
				}
				else
				{
					ItemStack itemStack = pedestal.getStack();
					ResourceLocation id = ForgeRegistries.ITEMS.getKey(itemStack.getItem());
					if (EndPortals.isAvailableItem(id))
					{
						worldIn.setBlockState(pos, updatedState.withProperty(ACTIVATED, true));
						if (pedestal.hasRitual())
						{
							pedestal.getRitual().checkStructure();
						}
						else
						{
							EternalRitual ritual = new EternalRitual(worldIn, pos);
							ritual.checkStructure();
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos){
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
		IBlockState updated = worldIn.getBlockState(pos);
		if (!updated.getBlock().equals(this)){
			worldIn.setBlockState(pos, updated);
		}
		if (!this.canPlaceBlockAt(worldIn, pos))
		{
			worldIn.setBlockState(pos,updated.withProperty(ACTIVATED, false));
		}
		worldIn.setBlockState(pos, updated);
	}


	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this,STATE, HAS_ITEM, HAS_LIGHT, ACTIVATED);
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new EternalPedestalTileEntity();
	}

}
