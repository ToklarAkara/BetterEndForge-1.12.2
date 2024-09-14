package mod.beethoven92.betterendforge.common.block.material;

import net.minecraft.block.BlockPurpurSlab;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class CustomBlockSlab extends BlockSlab {
    public CustomBlockSlab(Material p_i45714_1_) {
        super(p_i45714_1_);
    }

    @Override
    public boolean isDouble() {
        return false;
    }


    public String getTranslationKey(int p_150002_1_) {
        return super.getTranslationKey();
    }

    public IProperty<?> getVariantProperty() {
        return null;
    }

    public Comparable<?> getTypeForItem(ItemStack p_185674_1_) {
        return null;
    }

    public IBlockState getStateFromMeta(int p_176203_1_) {
        IBlockState lvt_2_1_ = this.getDefaultState();
        if (!this.isDouble()) {
            lvt_2_1_ = lvt_2_1_.withProperty(HALF, (p_176203_1_ & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        }

        return lvt_2_1_;
    }

    public int getMetaFromState(IBlockState p_176201_1_) {
        int lvt_2_1_ = 0;
        if (!this.isDouble() && p_176201_1_.getValue(HALF) == EnumBlockHalf.TOP) {
            lvt_2_1_ |= 8;
        }

        return lvt_2_1_;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF);
    }
}
