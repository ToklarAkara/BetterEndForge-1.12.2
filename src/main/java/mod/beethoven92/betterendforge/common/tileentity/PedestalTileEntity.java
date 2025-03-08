package mod.beethoven92.betterendforge.common.tileentity;

import javax.annotation.Nullable;

import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PedestalTileEntity extends TileEntity implements ITickable
{
	private ItemStack activeItem = ItemStack.EMPTY;

	private final int maxAge = 314;
	private int age;

	public PedestalTileEntity()
	{
		super();
	}

	public int getAge()
	{
		return this.age;
	}

	public int getMaxAge()
	{
		return this.maxAge;
	}

	public void clear()
	{
		activeItem = ItemStack.EMPTY;
	}

	public boolean isEmpty()
	{
		return activeItem.isEmpty();
	}

	public ItemStack getStack()
	{
		return activeItem;
	}

	public void setStack(ItemStack split)
	{
		this.activeItem = split;
		this.markDirty();
		this.world.notifyBlockUpdate(this.pos, this.world.getBlockState(this.pos), this.world.getBlockState(this.pos), 3);
	}

	public void removeStack(World world, IBlockState state)
	{
		world.setBlockState(pos, state.withProperty(PedestalBlock.HAS_ITEM, false).withProperty(PedestalBlock.HAS_LIGHT, false));
		this.activeItem = ItemStack.EMPTY;
		this.markDirty();
		this.world.notifyBlockUpdate(this.pos, this.world.getBlockState(this.pos), this.world.getBlockState(this.pos), 3);
	}

	public ItemStack removeStack()
	{
		ItemStack stored = this.activeItem;
		this.activeItem = ItemStack.EMPTY;
		this.markDirty();
		return stored;
	}

	@Override
	public void markDirty()
	{
		if (world != null && !world.isRemote)
		{
//			IBlockState state = world.getBlockState(pos);
//			if (state.getBlock() instanceof PedestalBlock)
//			{
//				state = state.withProperty(PedestalBlock.HAS_ITEM, !isEmpty());
//				if (activeItem.getItem() == ModItems.ETERNAL_CRYSTAL)
//				{
//					state = state.withProperty(PedestalBlock.HAS_LIGHT, true);
//				}
//				else
//				{
//					state = state.withProperty(PedestalBlock.HAS_LIGHT, false);
//				}
//				world.setBlockState(pos, state);
//			}
		}
		super.markDirty();
	}

	@Override
	public void update()
	{
		if (!isEmpty())
		{
			this.age++;
			if (age > maxAge)
			{
				this.age = 0;
			}
		}
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int tileEntityType = 42;
		return new SPacketUpdateTileEntity(this.pos, tileEntityType, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		IBlockState blockState = world.getBlockState(pos);
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return nbtTagCompound;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}


	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		NBTTagCompound itemStackNBT = new NBTTagCompound();
		if (!activeItem.isEmpty())
		{
			activeItem.writeToNBT(itemStackNBT);
			compound.setTag("activeItem", itemStackNBT);
		}
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if (nbt.hasKey("activeItem"))
		{
			activeItem = new ItemStack(nbt.getCompoundTag("activeItem"));
		}
		else
		{
			activeItem = ItemStack.EMPTY;
		}
	}
}
