package mod.beethoven92.betterendforge.common.block.template;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

public class EndAnvilBlock extends BlockAnvil {

	protected final int level;

	public EndAnvilBlock(int level) {
		super();
		this.level = level;
	}
}
