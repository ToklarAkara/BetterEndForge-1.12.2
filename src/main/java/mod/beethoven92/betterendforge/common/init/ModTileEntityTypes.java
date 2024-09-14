package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.tileentity.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntityTypes 
{

	public static void registerTileEntities(){
		GameRegistry.registerTileEntity(PedestalTileEntity.class, new ResourceLocation(BetterEnd.MOD_ID, "pedestal_tile_entity"));
		GameRegistry.registerTileEntity(EternalPedestalTileEntity.class, new ResourceLocation(BetterEnd.MOD_ID, "eternal_pedestal_tile_entity"));
		GameRegistry.registerTileEntity(InfusionPedestalTileEntity.class, new ResourceLocation(BetterEnd.MOD_ID, "infusion_pedestal_tile_entity"));
		GameRegistry.registerTileEntity(EndStoneSmelterTileEntity.class, new ResourceLocation(BetterEnd.MOD_ID, "end_stone_smelter_tile_entity"));
		GameRegistry.registerTileEntity(HydrothermalVentTileEntity.class, new ResourceLocation(BetterEnd.MOD_ID, "hydrothermal_vent_tile_entity"));
		GameRegistry.registerTileEntity(EChestTileEntity.class, new ResourceLocation(BetterEnd.MOD_ID, "chest"));
		GameRegistry.registerTileEntity(ESignTileEntity.class, new ResourceLocation(BetterEnd.MOD_ID, "sign"));
		GameRegistry.registerTileEntity(EndBarrelTileEntity.class, new ResourceLocation(BetterEnd.MOD_ID, "barrel"));
		GameRegistry.registerTileEntity(EndFurnaceTileEntity.class, new ResourceLocation(BetterEnd.MOD_ID, "furnace"));
	}
}
