package mod.beethoven92.betterendforge.client.gui;

import mod.beethoven92.betterendforge.common.inventory.EndStoneSmelterContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0)
            return new EndStoneSmelterScreen(player.inventory, new EndStoneSmelterContainer(player.inventory, (IInventory) world.getTileEntity(new BlockPos(x,y,z))));
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0)
            return new EndStoneSmelterContainer(player.inventory, (IInventory) world.getTileEntity(new BlockPos(x,y,z)));
        return null;
    }
}
