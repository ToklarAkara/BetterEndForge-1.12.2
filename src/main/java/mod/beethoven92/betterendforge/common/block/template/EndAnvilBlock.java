package mod.beethoven92.betterendforge.common.block.template;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.ModCraftingTableBlock;
import mod.beethoven92.betterendforge.common.inventory.ModContainerRepair;
import mod.beethoven92.betterendforge.common.inventory.ModContainerWorkbench;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

public class EndAnvilBlock extends BlockAnvil {

	protected final int level;

	public EndAnvilBlock(int level) {
		super();
		this.level = level;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            player.displayGui(new Anvil(worldIn, pos));
        }
        return true;
    }

	public static class Anvil implements IInteractionObject {
		private final World world;
		private final BlockPos position;

		public Anvil(World p_i45741_1_, BlockPos p_i45741_2_) {
			this.world = p_i45741_1_;
			this.position = p_i45741_2_;
		}

		public String getName() {
			return "anvil";
		}

		public boolean hasCustomName() {
			return false;
		}

		public ITextComponent getDisplayName() {
			return new TextComponentTranslation(Blocks.ANVIL.getTranslationKey() + ".name", new Object[0]);
		}

		public Container createContainer(InventoryPlayer p_174876_1_, EntityPlayer p_174876_2_) {
			return new ModContainerRepair(p_174876_1_, this.world, this.position, p_174876_2_);
		}

		public String getGuiID() {
			return "minecraft:anvil";
		}
	}
}
