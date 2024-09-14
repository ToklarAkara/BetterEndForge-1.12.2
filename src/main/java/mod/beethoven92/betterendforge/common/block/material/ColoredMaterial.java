package mod.beethoven92.betterendforge.common.block.material;

import com.google.common.collect.Maps;
import mod.beethoven92.betterendforge.common.block.IDyedBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;

import java.util.Map;
import java.util.function.Supplier;

public class ColoredMaterial
{
	public final Map<EnumDyeColor, Block> dyedBlocks = Maps.newEnumMap(EnumDyeColor.class);

	public final boolean craftEight;

	public final Block craftMaterial;

	public final String name;

	public ColoredMaterial(String name, Supplier<? extends IDyedBlock> source, Block craftMaterial, boolean craftEight)
	{
		this.craftEight = craftEight;

		this.craftMaterial = craftMaterial;

		this.name = name;

		for (EnumDyeColor color: EnumDyeColor.values())
		{
			String coloredName = name + "_" + color.getName();

			Block block = source.get().createFromColor(color);
			ModBlocks.registerBlockWithDefaultItem(coloredName, ()->block);
			dyedBlocks.put(color, block);
		}
	}

	public Block[] getBlocks()
	{
		return dyedBlocks.values().toArray(new Block[0]);
	}

	public Block getByColor(EnumDyeColor color)
	{
		return dyedBlocks.get(color);
	}
}
