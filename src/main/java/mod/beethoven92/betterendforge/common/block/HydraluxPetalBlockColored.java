package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;

public class HydraluxPetalBlockColored extends HydraluxPetalBlock implements IDyedBlock {


	@Override
	public Block createFromColor(EnumDyeColor color) {
		Block block = new HydraluxPetalBlockColored()
				.setSoundType(SoundType.PLANT)
				.setHardness(1.0F);
		block.setHarvestLevel("axe", 0);
		return block;
	}
}
