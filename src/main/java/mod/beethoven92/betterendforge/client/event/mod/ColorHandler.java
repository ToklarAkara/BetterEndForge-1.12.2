package mod.beethoven92.betterendforge.client.event.mod;

import java.util.ArrayList;
import java.util.List;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.AuroraCrystalBlock;
import mod.beethoven92.betterendforge.common.block.EndPortalBlock;
import mod.beethoven92.betterendforge.common.block.HelixTreeLeavesBlock;
import mod.beethoven92.betterendforge.common.block.JellyshroomCapBlock;
import mod.beethoven92.betterendforge.common.block.ModLanternBlock;
import mod.beethoven92.betterendforge.common.block.RespawnObeliskBlock;
import mod.beethoven92.betterendforge.common.block.TenaneaFlowersBlock;
import mod.beethoven92.betterendforge.common.block.material.ColoredMaterial;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.teleporter.EndPortals;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class ColorHandler
{
	public static void init() {
		MinecraftForge.EVENT_BUS.register(new ColorHandler());
	}

	@SubscribeEvent
	public void onBlockColorHandler(ColorHandlerEvent.Block event)
	{
		event.getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) ->
				AuroraCrystalBlock.getBlockColor(pos), ModBlocks.AURORA_CRYSTAL);

		event.getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) ->
				TenaneaFlowersBlock.getBlockColor(pos), ModBlocks.TENANEA_FLOWERS);

//		event.getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) ->
//				RespawnObeliskBlock.getBlockColor(pos), ModBlocks.RESPAWN_OBELISK);

		registerColoredMaterialBlocks(event, ModBlocks.HYDRALUX_PETAL_BLOCK_COLORED);
		registerColoredMaterialBlocks(event, ModBlocks.IRON_BULB_LANTERN_COLORED);
		registerColoredMaterialBlocks(event, ModBlocks.THALLASIUM.bulb_lantern_colored);
		registerColoredMaterialBlocks(event, ModBlocks.TERMINITE.bulb_lantern_colored);

		event.getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) ->
				HelixTreeLeavesBlock.getBlockColor(state), ModBlocks.HELIX_TREE_LEAVES);

		event.getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) ->
				((JellyshroomCapBlock)(state.getBlock())).getBlockColor(state), ModBlocks.JELLYSHROOM_CAP_PURPLE);

		event.getBlockColors().registerBlockColorHandler(ModLanternBlock::getBlockColor, getLanterns());

		event.getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) ->
				EndPortals.getColor(state.getValue(EndPortalBlock.PORTAL)), ModBlocks.END_PORTAL_BLOCK);
	}

	@SubscribeEvent
	public void onItemColorHandler(ColorHandlerEvent.Item event)
	{
		event.getItemColors().registerItemColorHandler((stack, tintIndex) ->
				AuroraCrystalBlock.getItemColor(), ModBlocks.AURORA_CRYSTAL);

		event.getItemColors().registerItemColorHandler((stack, tintIndex) ->
				TenaneaFlowersBlock.getItemColor(), ModBlocks.TENANEA_FLOWERS);

		event.getItemColors().registerItemColorHandler((stack, tintIndex) ->
				HelixTreeLeavesBlock.getItemColor(), ModBlocks.HELIX_TREE_LEAVES);

//		event.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
//					return ItemMonsterPlacer.getColorFromItemStack(stack, tintIndex);
//				}, ModItems.DRAGONFLY_SPAWN_EGG, ModItems.END_FISH_SPAWN_EGG,
//				ModItems.SHADOW_WALKER_SPAWN_EGG, ModItems.END_SLIME_SPAWN_EGG,
//				ModItems.CUBOZOA_SPAWN_EGG, ModItems.SILK_MOTH_SPAWN_EGG);

		registerColoredMaterialItems(event, ModBlocks.HYDRALUX_PETAL_BLOCK_COLORED);
		registerColoredMaterialItems(event, ModBlocks.IRON_BULB_LANTERN_COLORED);
		registerColoredMaterialItems(event, ModBlocks.THALLASIUM.bulb_lantern_colored);
		registerColoredMaterialItems(event, ModBlocks.TERMINITE.bulb_lantern_colored);

		event.getItemColors().registerItemColorHandler((stack, tintIndex) ->
				JellyshroomCapBlock.getItemColor(stack), ModBlocks.JELLYSHROOM_CAP_PURPLE);

		event.getItemColors().registerItemColorHandler((stack, tintIndex) ->
				ModLanternBlock.getItemColor(stack, tintIndex), getLanterns());

		event.getItemColors().registerItemColorHandler((stack, tintIndex) ->
				EndPortals.getColor(0), ModBlocks.END_PORTAL_BLOCK);
	}

	private void registerColoredMaterialBlocks(ColorHandlerEvent.Block event, ColoredMaterial material)
	{
		event.getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) ->
				state.getMaterial().getMaterialMapColor().colorValue, material.getBlocks());
	}

	private void registerColoredMaterialItems(ColorHandlerEvent.Item event, ColoredMaterial material)
	{
		event.getItemColors().registerItemColorHandler((stack, tintIndex) ->
				((ItemBlock)stack.getItem()).getBlock().getMaterial(null).getMaterialMapColor().colorValue, material.getBlocks());
	}

	private Block[] getLanterns() {
		List<Block> result = new ArrayList<>();
		for (StoneMaterial m : StoneMaterial.getMaterials())
			result.add(m.lantern);
		result.add(ModBlocks.ANDESITE_LANTERN);
		result.add(ModBlocks.BLACKSTONE_LANTERN);
		result.add(ModBlocks.DIORITE_LANTERN);
		result.add(ModBlocks.END_STONE_LANTERN);
		result.add(ModBlocks.GRANITE_LANTERN);
		result.add(ModBlocks.PURPUR_LANTERN);
		result.add(ModBlocks.QUARTZ_LANTERN);
		return result.toArray(new Block[0]);
	}
}
