package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.EndVineBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class FilaluxBlock extends EndVineBlock {
	public FilaluxBlock() {
		super(Material.VINE);
		setHardness(0.0F);
		setLightLevel(1);
		setSoundType(SoundType.PLANT);
	}

	@Override
	public Block.EnumOffsetType getOffsetType() {
		return Block.EnumOffsetType.NONE;
	}
}
