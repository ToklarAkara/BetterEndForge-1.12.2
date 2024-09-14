package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.rituals.EternalRitual;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EternalPedestalTileEntity extends PedestalTileEntity {
	private EternalRitual linkedRitual;
	public boolean hasRitual() {
		return this.linkedRitual != null;
	}

	public void linkRitual(EternalRitual ritual) {
		this.linkedRitual = ritual;
	}

	public EternalRitual getRitual() {
		return this.linkedRitual;
	}

	@Override
	public void setWorldCreate(World worldIn) {
		super.setWorldCreate(worldIn);
		if (hasRitual()) {
			this.linkedRitual.setWorld(worldIn);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("ritual")) {
			this.linkedRitual = new EternalRitual(world);
			this.linkedRitual.readFromNBT(compound.getCompoundTag("ritual"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (this.hasRitual()) {
			compound.setTag("ritual", linkedRitual.writeToNBT(new NBTTagCompound()));
		}
		return compound;
	}
}
