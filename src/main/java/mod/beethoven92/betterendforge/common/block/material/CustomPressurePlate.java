package mod.beethoven92.betterendforge.common.block.material;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class CustomPressurePlate extends BlockPressurePlate {
    public CustomPressurePlate(Material materialIn, Sensitivity sensitivityIn) {
        super(materialIn, sensitivityIn);
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }
}
