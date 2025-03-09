package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.rituals.InfusionRitual;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InfusionPedestalTileEntity extends PedestalTileEntity implements ITickable {
	private InfusionRitual linkedRitual;

	public InfusionPedestalTileEntity()
	{
		super();
	}

	@Override
	public void setWorld(World world)
	{
		super.setWorld(world);
		if (hasRitual())
		{
			this.linkedRitual.setLocation(world, this.pos);
		}
	}

	@Override
	public void setPos(BlockPos pos)
	{
		super.setPos(pos);
		if (hasRitual())
		{
			this.linkedRitual.setLocation(this.world, pos);
		}
	}

	public void linkRitual(InfusionRitual ritual)
	{
		this.linkedRitual = ritual;
	}

	public InfusionRitual getRitual()
	{
		return this.linkedRitual;
	}

	public boolean hasRitual()
	{
		return this.linkedRitual != null;
	}

	@Override
	public void update()
	{
		if (hasRitual())
		{
			this.linkedRitual.tick();
		}
		super.update();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		if (hasRitual())
		{
			compound.setTag("ritual", linkedRitual.writeToNBT(new NBTTagCompound()));
		}
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("ritual"))
		{
			this.linkedRitual = new InfusionRitual(this.world, this.pos);
			this.linkedRitual.readFromNBT(compound.getCompoundTag("ritual"));
		}
	}
}
