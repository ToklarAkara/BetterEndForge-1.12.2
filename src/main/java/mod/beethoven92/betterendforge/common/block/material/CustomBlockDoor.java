package mod.beethoven92.betterendforge.common.block.material;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;

import java.util.Random;

public class CustomBlockDoor extends BlockDoor {
    private ItemDoor doorItem;

    protected CustomBlockDoor(Material materialIn) {
        super(materialIn);
        setHardness(3.0F);
    }

    public void setDoorItem(ItemDoor doorItem){
        this.doorItem = doorItem;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : doorItem;
    }
}
