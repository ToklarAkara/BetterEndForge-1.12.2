package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class CustomBlock extends Block {
    public CustomBlock(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    public CustomBlock(Material materialIn) {
        super(materialIn);
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }
}
