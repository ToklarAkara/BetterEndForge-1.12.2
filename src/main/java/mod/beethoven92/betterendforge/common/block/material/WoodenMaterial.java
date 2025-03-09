package mod.beethoven92.betterendforge.common.block.material;

import java.util.ArrayList;
import java.util.function.Supplier;

import mod.beethoven92.betterendforge.common.block.EndSignBlock;
import mod.beethoven92.betterendforge.common.block.template.BarkBlockTemplate;
import mod.beethoven92.betterendforge.common.block.template.StripableBarkBlockTemplate;
import mod.beethoven92.betterendforge.common.block.template.StripableLogBlockTemplate;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModCreativeTabs;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.item.EndSignItem;
import mod.beethoven92.betterendforge.common.tileentity.EChestTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.ESignTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.EndSignTileEntity;
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
				() -> new BlockLog(){
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

				}.setHardness(3.0F));
		bark_stripped = ModBlocks.registerBlockWithDefaultItem(name + "_stripped_bark",
				() -> new BarkBlockTemplate().setHardness(3.0F));

		log = ModBlocks.registerBlockWithDefaultItem(name + "_log",
				() -> new StripableLogBlockTemplate(woodColor, log_stripped).setHardness(3.0F));
		bark = ModBlocks.registerBlockWithDefaultItem(name + "_bark",
				() -> new StripableBarkBlockTemplate(woodColor, bark_stripped).setHardness(3.0F));

		planks = ModBlocks.registerBlockWithDefaultItem(name + "_planks",
				() -> new Block(materialPlanks).setHardness(3.0F));
		stairs = ModBlocks.registerBlockWithDefaultItem(name + "_stairs",
				() -> new CustomBlockStairs(planks.getDefaultState()).setHardness(3.0F));
		slab = ModBlocks.registerBlockWithDefaultItem(name + "_slab",
				() -> new CustomBlockSlab(Material.WOOD).setHardness(3.0F));
		fence = ModBlocks.registerBlockWithDefaultItem(name + "_fence",
				() -> new BlockFence(materialPlanks, planksColor).setHardness(3.0F));
//		gate = registerBlockWithDefaultItem(name + "_gate",
//				() -> new BlockFenceGate(materialPlanks, planksColor));
//		button = registerBlockWithDefaultItem(name + "_button",
//				() -> new BlockButtonWood());
		pressurePlate = ModBlocks.registerBlockWithDefaultItem(name + "_pressure_plate",
				() -> new CustomPressurePlate(materialPlanks, BlockPressurePlate.Sensitivity.EVERYTHING).setHardness(3.0F));
		trapdoor = ModBlocks.registerBlockWithDefaultItem(name + "_trapdoor",
				() -> new CustomBlockTrapDoor(materialPlanksNotSolid).setHardness(3.0F));
		door = ModBlocks.registerBlockWithDoorItem(name + "_door",
				() -> new CustomBlockDoor(materialPlanksNotSolid));

//		composter = registerBlockWithBurnItem(name + "_composter",
//				() -> new BlockComposter(materialPlanksNotSolid), 300);
		craftingTable = registerBlockWithBurnItem(name + "_crafting_table",
				() -> new BlockWorkbench().setHardness(3.0F), 300);
		ladder = registerBlockWithBurnItem(name + "_ladder",
				() -> new BlockLadder().setHardness(3.0F), 300);
		chest = ModBlocks.registerBlock(name + "_chest",
				() -> new BlockChest(BlockChest.Type.BASIC) {
					@Override
					public TileEntity createTileEntity(World world, IBlockState state) {
						return new EChestTileEntity();
					}
				}.setHardness(3.0F));
		ModItems.ITEMS.add(new ItemBlock(chest).setRegistryName(chest.getRegistryName()));

		sign_standing = ModBlocks.registerBlock(name + "_sign_standing",
				() -> new BlockStandingSign() {
					@Override
					public TileEntity createTileEntity(World world, IBlockState state) {
						return new EndSignTileEntity();
					}
				}.setHardness(3.0F));

		sign_wall = ModBlocks.registerBlock(name + "_sign_wall",
				() -> new BlockWallSign() {
					@Override
					public TileEntity createTileEntity(World world, IBlockState state) {
						return new EndSignTileEntity();
					}
				}.setHardness(3.0F));
		sign = new EndSignItem(sign_standing, sign_wall).setRegistryName(name + "_sign").setTranslationKey(name + "_sign").setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
		ModItems.ITEMS.add(sign);
//		barrel = registerBlockWithBurnItem(name + "_barrel",
//				() -> new EndBarrelBlock(materialPlanksNotSolid), 300);
		shelf = registerBlockWithBurnItem(name + "_bookshelf",
				() -> new Block(materialPlanks) {
					@Override
					public float getEnchantPowerBonus(World world, BlockPos pos) {
						return 1;
					}
				}, 300).setHardness(3.0F);
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
