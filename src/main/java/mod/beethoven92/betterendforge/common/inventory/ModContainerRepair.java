package mod.beethoven92.betterendforge.common.inventory;

import mod.beethoven92.betterendforge.common.block.template.EndAnvilBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModContainerRepair extends ContainerRepair {
    private final World world;
    private final BlockPos pos;
    public ModContainerRepair(InventoryPlayer p_i45806_1_, World p_i45806_2_, EntityPlayer p_i45806_3_) {
        super(p_i45806_1_, p_i45806_2_, p_i45806_3_);
        this.world = p_i45806_2_;
        this.pos = BlockPos.ORIGIN;
    }

    public ModContainerRepair(InventoryPlayer p_i45807_1_, World p_i45807_2_, BlockPos p_i45807_3_, EntityPlayer p_i45807_4_) {
        super(p_i45807_1_, p_i45807_2_, p_i45807_3_, p_i45807_4_);
        this.world = p_i45807_2_;
        this.pos = p_i45807_3_;
    }

    public boolean canInteractWith(EntityPlayer playerIn)
    {
        if (!(this.world.getBlockState(this.pos).getBlock() instanceof EndAnvilBlock))
        {
            return false;
        }
        else
        {
            return playerIn.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }
}
