package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.SoundType;

public class PallidiumBlock extends Block
{
	private final Block nextLevel;

	public PallidiumBlock(String thickness, Block nextLevel)
	{
		super(Material.ROCK);
		this.setHardness(3.0F);
		this.setResistance(9.0F);
		this.setSoundType(SoundType.STONE);
		this.nextLevel = nextLevel;
	}

	public boolean canBePotted()
	{
		return this == ModBlocks.PALLIDIUM_FULL;
	}
}
