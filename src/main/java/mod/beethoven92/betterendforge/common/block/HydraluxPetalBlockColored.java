package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.material.IColoredBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;

public class HydraluxPetalBlockColored extends HydraluxPetalBlock implements IDyedBlock, IColoredBlock {
	public EnumDyeColor color;

	@Override
	public Block createFromColor(EnumDyeColor color) {
		HydraluxPetalBlockColored block = (HydraluxPetalBlockColored) new HydraluxPetalBlockColored()
				.setSoundType(SoundType.PLANT)
				.setHardness(1.0F);
		block.setHarvestLevel("axe", 0);
		block.color = color;
		return block;
	}

	@Override
	public EnumDyeColor getColor() {
		return color;
	}
}
