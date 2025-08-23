package mod.beethoven92.betterendforge.common.block.material;

import java.util.ArrayList;
import java.util.function.Supplier;

import mod.beethoven92.betterendforge.common.block.template.BarkBlockTemplate;
import mod.beethoven92.betterendforge.common.block.template.StripableBarkBlockTemplate;
import mod.beethoven92.betterendforge.common.block.template.StripableLogBlockTemplate;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModCreativeTabs;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.item.EndSignItem;
import mod.beethoven92.betterendforge.common.tileentity.EChestTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.EndSignTileEntity;
import mod.beethoven92.betterendforge.mixin.minecraft.BlockAccessor;
import mod.beethoven92.betterendforge.mixin.minecraft.BlockLadderAccessor;
import mod.beethoven92.betterendforge.mixin.minecraft.BlockWorkbenchAccessor;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// TO DO? Make all wooden blocks flammable so they can take and spread fire
public class WoodenMaterial
{
	public final String name;

	public final Block log;
	public final Block bark;

	public final Block log_stripped;
	public final Block bark_stripped;

	public final Block planks;

	public final Block stairs;
	public final Block slab;
	public final Block fence;
	//public final Block gate;
	//public final Block button;
	public final Block pressurePlate;
	public final Block trapdoor;
	public final Block door;

//	public final Block craftingTable;
	public final Block ladder;
	public final Block sign_standing;
	public final Block sign_wall;
	public final Item sign;
//
	public final Block chest;
//	public final Block barrel;
	public final Block shelf;
	//public final Block composter;
	public final Block craftingTable;

	public final ArrayList<Block> logBlockTag;
	public final ArrayList<Item> logItemTag;

	public WoodenMaterial(String name, MapColor woodColor, MapColor planksColor)
	{
		this.name = name;

		logBlockTag = ModTags.makeModBlockTag(name + "_logs");
		logItemTag = ModTags.makeModItemTag(name + "_logs");
		Material materialPlanks = Material.WOOD;
		Material materialPlanksNotSolid = Material.WOOD;

		log_stripped = ModBlocks.registerBlockWithDefaultItem(name + "_stripped_log",
				() -> ((BlockAccessor)new BlockLog(){
					@Override
					protected BlockStateContainer createBlockState() {
						return new BlockStateContainer(this, LOG_AXIS);
					}

					@Override
					public IBlockState getStateFromMeta(int meta) {
						return this.getDefaultState().withProperty(LOG_AXIS, EnumAxis.values()[meta]);
					}

					@Override
					public int getMetaFromState(IBlockState state) {
						return state.getValue(LOG_AXIS).ordinal();
					}

				}.setHardness(3.0F)).invokeSetSoundType(SoundType.WOOD));
		bark_stripped = ModBlocks.registerBlockWithDefaultItem(name + "_stripped_bark",
				() -> ((BlockAccessor)new BarkBlockTemplate().setHardness(3.0F)).invokeSetSoundType(SoundType.WOOD));

		log = ModBlocks.registerBlockWithDefaultItem(name + "_log",
				() -> ((BlockAccessor)new StripableLogBlockTemplate(woodColor, log_stripped).setHardness(3.0F)).invokeSetSoundType(SoundType.WOOD));
		bark = ModBlocks.registerBlockWithDefaultItem(name + "_bark",
				() -> ((BlockAccessor)new StripableBarkBlockTemplate(woodColor, bark_stripped).setHardness(3.0F)).invokeSetSoundType(SoundType.WOOD));

		planks = ModBlocks.registerBlockWithDefaultItem(name + "_planks",
				() -> ((BlockAccessor)new Block(materialPlanks).setHardness(3.0F)).invokeSetSoundType(SoundType.WOOD));
		stairs = ModBlocks.registerBlockWithDefaultItem(name + "_stairs",
				() -> ((BlockAccessor)new CustomBlockStairs(planks.getDefaultState()).setHardness(3.0F)).invokeSetSoundType(SoundType.WOOD));
		slab = ModBlocks.registerBlockWithDefaultItem(name + "_slab",
				() -> ((BlockAccessor)new CustomBlockSlab(Material.WOOD).setHardness(3.0F)).invokeSetSoundType(SoundType.WOOD));
		fence = ModBlocks.registerBlockWithDefaultItem(name + "_fence",
				() -> ((BlockAccessor)new BlockFence(materialPlanks, planksColor).setHardness(3.0F)).invokeSetSoundType(SoundType.WOOD));
//		gate = registerBlockWithDefaultItem(name + "_gate",
//				() -> new BlockFenceGate(materialPlanks, planksColor));
//		button = registerBlockWithDefaultItem(name + "_button",
//				() -> new BlockButtonWood());
		pressurePlate = ModBlocks.registerBlockWithDefaultItem(name + "_pressure_plate",
				() -> ((BlockAccessor)new CustomPressurePlate(materialPlanks, BlockPressurePlate.Sensitivity.EVERYTHING).setHardness(3.0F)).invokeSetSoundType(SoundType.WOOD));
		trapdoor = ModBlocks.registerBlockWithDefaultItem(name + "_trapdoor",
				() -> ((BlockAccessor)new CustomBlockTrapDoor(materialPlanksNotSolid).setHardness(3.0F)).invokeSetSoundType(SoundType.WOOD));
		door = ModBlocks.registerBlockWithDoorItem(name + "_door",
				() -> (CustomBlockDoor) ((BlockAccessor)new CustomBlockDoor(materialPlanksNotSolid)).invokeSetSoundType(SoundType.WOOD));

//		composter = registerBlockWithBurnItem(name + "_composter",
//				() -> new BlockComposter(materialPlanksNotSolid), 300);
		craftingTable = registerBlockWithBurnItem(name + "_crafting_table",
				() -> ((BlockAccessor)BlockWorkbenchAccessor.constructNew().setHardness(3.0F)).invokeSetSoundType(SoundType.WOOD), 300);
		ladder = registerBlockWithBurnItem(name + "_ladder",
				() -> ((BlockAccessor)BlockLadderAccessor.constructNew().setHardness(3.0F)).invokeSetSoundType(SoundType.WOOD), 300);
		chest = ModBlocks.registerBlock(name + "_chest",
				() -> ((BlockAccessor)new BlockChest(BlockChest.Type.BASIC) {
					@Override
					public TileEntity createTileEntity(World world, IBlockState state) {
						return new EChestTileEntity();
					}
				}.setHardness(3.0F)).invokeSetSoundType(SoundType.WOOD));
		ModItems.ITEMS.add(new ItemBlock(chest).setRegistryName(chest.getRegistryName()));

		sign_standing = ModBlocks.registerBlock(name + "_sign_standing",
				() ->((BlockAccessor) new BlockStandingSign() {
					@Override
					public TileEntity createTileEntity(World world, IBlockState state) {
						return new EndSignTileEntity();
					}
				}.setHardness(3.0F)).invokeSetSoundType(SoundType.WOOD));

		sign_wall = ModBlocks.registerBlock(name + "_sign_wall",
				() -> ((BlockAccessor)new BlockWallSign() {
					@Override
					public TileEntity createTileEntity(World world, IBlockState state) {
						return new EndSignTileEntity();
					}
				}.setHardness(3.0F)).invokeSetSoundType(SoundType.WOOD));
		sign = new EndSignItem(sign_standing, sign_wall).setRegistryName(name + "_sign").setTranslationKey(name + "_sign").setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
		ModItems.ITEMS.add(sign);
//		barrel = registerBlockWithBurnItem(name + "_barrel",
//				() -> new EndBarrelBlock(materialPlanksNotSolid), 300);
		shelf = ((BlockAccessor)registerBlockWithBurnItem(name + "_bookshelf",
				() -> new Block(materialPlanks) {
					@Override
					public float getEnchantPowerBonus(World world, BlockPos pos) {
						return 1;
					}
				}, 300).setHardness(3.0F)).invokeSetSoundType(SoundType.WOOD);
	}

	public boolean isTreeLog(Block block) {
		return block == log || block == bark;
	}

	public boolean isTreeLog(IBlockState state) {
		return isTreeLog(state.getBlock());
	}

	private static <T extends Block> T registerBlockWithBurnItem(String name, Supplier<? extends T> blockSupplier, int burnTime) {
//		T block = blockSupplier.get();
//		block.setRegistryName(new ResourceLocation(BetterEnd.MOD_ID, name));
//		ForgeRegistries.BLOCKS.register(block);
//		ItemBlock itemBlock = new ItemBlock(block);
//		itemBlock.setRegistryName(block.getRegistryName());
//		ForgeRegistries.ITEMS.register(itemBlock);
//		return block;
		return ModBlocks.registerBlockWithDefaultItem(name, blockSupplier);
	}
}
