package mod.beethoven92.betterendforge.common.block.material;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class CustomBlockTrapDoor extends BlockTrapDoor {
    protected CustomBlockTrapDoor(Material materialIn) {
        super(materialIn);
        setHardness(3.0F);
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }
}
