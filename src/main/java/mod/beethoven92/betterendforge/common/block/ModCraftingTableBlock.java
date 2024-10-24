package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.inventory.ModContainerWorkbench;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ModCraftingTableBlock extends Block {
	private static final ITextComponent CONTAINER_NAME = new TextComponentTranslation("container.crafting");

	public ModCraftingTableBlock(Material materialIn) {
		super(materialIn);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            player.displayGui(new InterfaceCraftingTable(worldIn, pos));
            player.addStat(StatList.CRAFTING_TABLE_INTERACTION);
        }
        return true;
    }

	public static class InterfaceCraftingTable implements IInteractionObject {
		private final World world;
		private final BlockPos pos;

		public InterfaceCraftingTable(World world, BlockPos pos) {
			this.world = world;
			this.pos = pos;
		}

		@Override
		public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
			return new ModContainerWorkbench(playerInventory, this.world, this.pos);
		}

		@Override
		public String getGuiID() {
			return "minecraft:crafting_table";
		}

		@Override
		public String getName() {
			return CONTAINER_NAME.getFormattedText();
		}

		@Override
		public boolean hasCustomName() {
			return false;
		}

		@Override
		public ITextComponent getDisplayName() {
			return CONTAINER_NAME;
		}
	}
}
