package mod.beethoven92.betterendforge.common.block;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;

public interface IDyedBlock
{
	Block createFromColor(EnumDyeColor color);
}
