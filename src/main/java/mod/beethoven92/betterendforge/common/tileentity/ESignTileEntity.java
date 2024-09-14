package mod.beethoven92.betterendforge.common.tileentity;

import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.UUID;

public class ESignTileEntity extends TileEntity {
	public final ITextComponent[] text = new ITextComponent[] { new TextComponentString(""), new TextComponentString(""),
			new TextComponentString(""), new TextComponentString("") };
	private boolean editable = true;
	private EntityPlayer editor;
	private EnumDyeColor textColor = EnumDyeColor.BLACK;
	private final CommandResultStats stats = new CommandResultStats();

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		for (int i = 0; i < 4; ++i) {
			String string = ITextComponent.Serializer.componentToJson(this.text[i]);
			tag.setString("Text" + (i + 1), string);
		}

		tag.setInteger("Color", this.textColor.getMetadata());
		this.stats.writeStatsToNBT(tag);
		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		this.editable = false;
		super.readFromNBT(tag);
		this.textColor = (tag.hasKey("Color")? EnumDyeColor.byMetadata(tag.getInteger("Color")) : EnumDyeColor.BLACK);
		ICommandSender icommandsender = new ICommandSender()
		{
			public String getName()
			{
				return "Sign";
			}
			public boolean canUseCommand(int permLevel, String commandName)
			{
				return permLevel <= 2; //Forge: Fixes  MC-75630 - Exploit with signs and command blocks
			}
			public BlockPos getPosition()
			{
				return ESignTileEntity.this.pos;
			}
			public Vec3d getPositionVector()
			{
				return new Vec3d((double)ESignTileEntity.this.pos.getX() + 0.5D, (double)ESignTileEntity.this.pos.getY() + 0.5D, (double)ESignTileEntity.this.pos.getZ() + 0.5D);
			}
			public World getEntityWorld()
			{
				return ESignTileEntity.this.world;
			}
			public MinecraftServer getServer()
			{
				return ESignTileEntity.this.world.getMinecraftServer();
			}
		};
		for (int i = 0; i < 4; ++i) {
			String string = tag.getString("Text" + (i + 1));
			ITextComponent text = ITextComponent.Serializer.jsonToComponent(string.isEmpty() ? "\"\"" : string);
			if (this.world instanceof WorldServer) {
				try {
					this.text[i] = TextComponentUtils.processComponent(icommandsender,
							text, (Entity) null);
				} catch (Exception var7) {
					this.text[i] = text;
				}
			} else {
				this.text[i] = text;
			}
		}
	}

	public void setTextOnRow(int row, ITextComponent text) {
		this.text[row] = text;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 9, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public boolean onlyOpsCanSetNbt() {
		return true;
	}

	public boolean isEditable() {
		return this.editable;
	}

	public void setEditable(boolean bl) {
		this.editable = bl;
		if (!bl) {
			this.editor = null;
		}
	}

	public void setEditor(EntityPlayer player) {
		this.editor = player;
	}

	public EntityPlayer getEditor() {
		return this.editor;
	}

	public boolean onActivate(EntityPlayer player) {
		ITextComponent[] var2 = this.text;
		int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			ITextComponent text = var2[var4];
			Style style = text == null ? null : text.getStyle();
			if (style != null && style.getClickEvent() != null) {
				ClickEvent clickEvent = style.getClickEvent();
				if (clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
					player.getServer().getCommandManager()
							.executeCommand(this.getCommandSource((EntityPlayerMP) player), clickEvent.getValue());
				}
			}
		}

		return true;
	}

	public ICommandSender getCommandSource(EntityPlayerMP playerIn) {
		return new ICommandSender()
		{
			public String getName()
			{
				return playerIn.getName();
			}
			public ITextComponent getDisplayName()
			{
				return playerIn.getDisplayName();
			}
			public void sendMessage(ITextComponent component)
			{
			}
			public boolean canUseCommand(int permLevel, String commandName)
			{
				return permLevel <= 2;
			}
			public BlockPos getPosition()
			{
				return ESignTileEntity.this.pos;
			}
			public Vec3d getPositionVector()
			{
				return new Vec3d((double)ESignTileEntity.this.pos.getX() + 0.5D, (double)ESignTileEntity.this.pos.getY() + 0.5D, (double)ESignTileEntity.this.pos.getZ() + 0.5D);
			}
			public World getEntityWorld()
			{
				return playerIn.getEntityWorld();
			}
			public Entity getCommandSenderEntity()
			{
				return playerIn;
			}
			public boolean sendCommandFeedback()
			{
				return false;
			}
			public void setCommandStat(CommandResultStats.Type type, int amount)
			{
				if (ESignTileEntity.this.world != null && !ESignTileEntity.this.world.isRemote)
				{
					ESignTileEntity.this.stats.setCommandStatForSender(ESignTileEntity.this.world.getMinecraftServer(), this, type, amount);
				}
			}
			public MinecraftServer getServer()
			{
				return playerIn.getServer();
			}
		};
	}

	public EnumDyeColor getTextColor() {
		return this.textColor;
	}

	public boolean setTextColor(EnumDyeColor value) {
		if (value != this.getTextColor()) {
			this.textColor = value;
			this.markDirty();
			this.world.notifyBlockUpdate(this.pos, this.getWorld().getBlockState(pos), this.getWorld().getBlockState(pos), 3);
			return true;
		} else {
			return false;
		}
	}
}
