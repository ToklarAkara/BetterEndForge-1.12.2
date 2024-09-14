package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.block.EndBarrelBlock;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class EndBarrelTileEntity extends TileEntityLockableLoot {
	private NonNullList<ItemStack> inventory;
	private int viewerCount;

	public EndBarrelTileEntity() {
		this.inventory = NonNullList.withSize(27, ItemStack.EMPTY);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (!this.checkLootAndWrite(compound)) {
			ItemStackHelper.saveAllItems(compound, this.inventory);
		}
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		if (!this.checkLootAndRead(compound)) {
			ItemStackHelper.loadAllItems(compound, this.inventory);
		}
	}

	@Override
	public int getSizeInventory() {
		return 27;
	}

	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.inventory)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.barrel";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.inventory;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("container.barrel");
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerChest(playerInventory, this, playerIn);
	}

	@Override
	public String getGuiID() {
		return "container.barrel";
	}

	@Override
	public void openInventory(EntityPlayer player) {
		if (!player.isSpectator()) {
			if (this.viewerCount < 0) {
				this.viewerCount = 0;
			}

			++this.viewerCount;
			IBlockState blockState = this.world.getBlockState(pos);
			boolean isOpen = blockState.getValue(EndBarrelBlock.OPEN);
			if (!isOpen) {
				this.playSound(blockState, SoundEvents.BLOCK_CHEST_OPEN);
				this.setOpen(blockState, true);
			}

			this.scheduleTick();
		}
	}

	private void scheduleTick() {
		this.world.scheduleBlockUpdate(this.pos, this.getBlockType(), 5, 1);
	}

	public void update() {
		int i = this.pos.getX();
		int j = this.pos.getY();
		int k = this.pos.getZ();
		this.viewerCount = 0;//TileEntityChest.getPlayersUsing(this.world, this, i, j, k);
		if (this.viewerCount > 0) {
			this.scheduleTick();
		} else {
			IBlockState blockState = this.world.getBlockState(pos);
			if (!(blockState.getBlock() instanceof EndBarrelBlock)) {
				this.invalidate();
				return;
			}

			boolean isOpen = blockState.getValue(EndBarrelBlock.OPEN);
			if (isOpen) {
				this.playSound(blockState, SoundEvents.BLOCK_CHEST_CLOSE);
				this.setOpen(blockState, false);
			}
		}
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		if (!player.isSpectator()) {
			--this.viewerCount;
		}
	}

	private void setOpen(IBlockState state, boolean open) {
		this.world.setBlockState(this.pos, state.withProperty(EndBarrelBlock.OPEN, open), 3);
	}

	private void playSound(IBlockState blockState, SoundEvent soundEvent) {
		Vec3i vec3i = blockState.getValue(EndBarrelBlock.FACING).getDirectionVec();
		double d = (double) this.pos.getX() + 0.5D + (double) vec3i.getX() / 2.0D;
		double e = (double) this.pos.getY() + 0.5D + (double) vec3i.getY() / 2.0D;
		double f = (double) this.pos.getZ() + 0.5D + (double) vec3i.getZ() / 2.0D;
		this.world.playSound((EntityPlayer) null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F,
				this.world.rand.nextFloat() * 0.1F + 0.9F);
	}
}
