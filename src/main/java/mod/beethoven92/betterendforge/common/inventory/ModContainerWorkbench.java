package mod.beethoven92.betterendforge.common.inventory;

import mod.beethoven92.betterendforge.common.block.ModCraftingTableBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModContainerWorkbench extends ContainerWorkbench {

    private final World world;
    private final BlockPos pos;
    public ModContainerWorkbench(InventoryPlayer p_i45800_1_, World p_i45800_2_, BlockPos p_i45800_3_) {
        super(p_i45800_1_, p_i45800_2_, p_i45800_3_);
        this.world = p_i45800_2_;
        this.pos = p_i45800_3_;
    }

    public boolean canInteractWith(EntityPlayer p_75145_1_) {
        if (!(this.world.getBlockState(this.pos).getBlock() instanceof ModCraftingTableBlock)) {
            return false;
        } else {
            return p_75145_1_.getDistanceSq((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) <= 64.0;
        }
    }
}
