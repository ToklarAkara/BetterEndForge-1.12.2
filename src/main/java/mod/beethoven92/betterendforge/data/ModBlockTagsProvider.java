//package mod.beethoven92.betterendforge.data;
//
//import mod.beethoven92.betterendforge.BetterEnd;
//import mod.beethoven92.betterendforge.common.block.TerrainBlock;
//import mod.beethoven92.betterendforge.common.block.material.MetalMaterial;
//import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
//import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
//import mod.beethoven92.betterendforge.common.block.template.EndSaplingBlock;
//import mod.beethoven92.betterendforge.common.block.template.EndVineBlock;
//import mod.beethoven92.betterendforge.common.init.ModBlocks;
//import mod.beethoven92.betterendforge.common.init.ModTags;
//import net.minecraft.block.FlowerPotBlock;
//import net.minecraft.block.LeavesBlock;
//import net.minecraft.data.BlockTagsProvider;
//import net.minecraft.data.DataGenerator;
//import net.minecraft.tags.BlockTags;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.common.Tags;
//import net.minecraftforge.common.data.ExistingFileHelper;
//import net.minecraftforge.fml.RegistryObject;
//import net.minecraftforge.versions.forge.ForgeVersion;
//
//public class ModBlockTagsProvider extends BlockTagsProvider
//{
//	public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper)
//	{
//		super(generatorIn, BetterEnd.MOD_ID, existingFileHelper);
//	}
//
//	@Override
//	protected void registerTags()
//	{
//		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach((block) -> {
//			if (block instanceof TerrainBlock)
//			{
//				getOrCreateBuilder(BlockTags.NYLIUM).add(block);
//				getOrCreateBuilder(Tags.Blocks.END_STONES).add(block);
//			}
//			if (block instanceof LeavesBlock)
//			{
//				getOrCreateBuilder(BlockTags.LEAVES).add(block);
//			}
//			if (block instanceof EndVineBlock)
//			{
//				getOrCreateBuilder(BlockTags.CLIMBABLE).add(block);
//			}
//			if (block instanceof EndSaplingBlock)
//			{
//				getOrCreateBuilder(BlockTags.SAPLINGS).add(block);
//			}
//			if (block instanceof FlowerPotBlock)
//			{
//				getOrCreateBuilder(BlockTags.FLOWER_POTS).add(block);
//			}
//		});
//
//		// Misc Forge tags
//		getOrCreateBuilder(Tags.Blocks.ORES).add(ModBlocks.ENDER_ORE);
//		getOrCreateBuilder(Tags.Blocks.ORES).add(ModBlocks.AMBER_ORE);
//
//		getOrCreateBuilder(Tags.Blocks.STORAGE_BLOCKS).add(ModBlocks.AETERNIUM_BLOCK);
//		getOrCreateBuilder(Tags.Blocks.STORAGE_BLOCKS).add(ModBlocks.AMBER_BLOCK);
//
//		// Misc Minecraft tags
//		getOrCreateBuilder(BlockTags.BEACON_BASE_BLOCKS).add(ModBlocks.AETERNIUM_BLOCK);
//
//		getOrCreateBuilder(BlockTags.ICE).add(ModBlocks.EMERALD_ICE);
//		getOrCreateBuilder(BlockTags.ICE).add(ModBlocks.DENSE_EMERALD_ICE);
//		getOrCreateBuilder(BlockTags.ICE).add(ModBlocks.ANCIENT_EMERALD_ICE);
//
//		getOrCreateBuilder(BlockTags.ANVIL).add(ModBlocks.AETERNIUM_ANVIL);
//
//		getOrCreateBuilder(BlockTags.SLABS).add(ModBlocks.DRAGON_BONE_SLAB);
//		getOrCreateBuilder(BlockTags.SLABS).add(ModBlocks.NEON_CACTUS_BLOCK_SLAB);
//		getOrCreateBuilder(BlockTags.WOODEN_SLABS).add(ModBlocks.NEON_CACTUS_BLOCK_SLAB);
//
//		getOrCreateBuilder(BlockTags.STAIRS).add(ModBlocks.DRAGON_BONE_STAIRS);
//		getOrCreateBuilder(BlockTags.STAIRS).add(ModBlocks.NEON_CACTUS_BLOCK_STAIRS);
//		getOrCreateBuilder(BlockTags.WOODEN_STAIRS).add(ModBlocks.NEON_CACTUS_BLOCK_STAIRS);
//
//		// WOODEN MATERIALS
//		registerWoodenMaterialTags(ModBlocks.MOSSY_GLOWSHROOM);
//		registerWoodenMaterialTags(ModBlocks.LACUGROVE);
//		registerWoodenMaterialTags(ModBlocks.END_LOTUS);
//		registerWoodenMaterialTags(ModBlocks.PYTHADENDRON);
//		registerWoodenMaterialTags(ModBlocks.DRAGON_TREE);
//		registerWoodenMaterialTags(ModBlocks.TENANEA);
//		registerWoodenMaterialTags(ModBlocks.HELIX_TREE);
//		registerWoodenMaterialTags(ModBlocks.UMBRELLA_TREE);
//		registerWoodenMaterialTags(ModBlocks.JELLYSHROOM);
//		registerWoodenMaterialTags(ModBlocks.LUCERNIA);
//
//		// STONE MATERIALS
//		registerStoneMaterialTags(ModBlocks.FLAVOLITE);
//		registerStoneMaterialTags(ModBlocks.VIOLECITE);
//		registerStoneMaterialTags(ModBlocks.SULPHURIC_ROCK);
//		registerStoneMaterialTags(ModBlocks.VIRID_JADESTONE);
//		registerStoneMaterialTags(ModBlocks.AZURE_JADESTONE);
//		registerStoneMaterialTags(ModBlocks.SANDY_JADESTONE);
//		registerStoneMaterialTags(ModBlocks.UMBRALITH);
//
//		// METAL MATERIALS
//		registerMetalMaterialTags(ModBlocks.THALLASIUM);
//		registerMetalMaterialTags(ModBlocks.TERMINITE);
//	}
//
//	private void registerWoodenMaterialTags(WoodenMaterial material)
//	{
//		getOrCreateBuilder(material.logBlockTag).add(material.log, material.bark, material.log_stripped, material.bark_stripped);
//
//		getOrCreateBuilder(BlockTags.PLANKS).add(material.planks);
//
//		getOrCreateBuilder(BlockTags.LOGS).add(material.log, material.bark, material.log_stripped, material.bark_stripped);
//
//		getOrCreateBuilder(BlockTags.LOGS_THAT_BURN).add(material.log, material.bark, material.log_stripped, material.bark_stripped);
//
//		getOrCreateBuilder(BlockTags.BUTTONS).add(material.button);
//		getOrCreateBuilder(BlockTags.WOODEN_BUTTONS).add(material.button);
//
//		getOrCreateBuilder(BlockTags.PRESSURE_PLATES).add(material.pressurePlate);
//		getOrCreateBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(material.pressurePlate);
//
//		getOrCreateBuilder(BlockTags.DOORS).add(material.door);
//		getOrCreateBuilder(BlockTags.WOODEN_DOORS).add(material.door);
//
//		getOrCreateBuilder(BlockTags.FENCES).add(material.fence);
//		getOrCreateBuilder(BlockTags.WOODEN_FENCES).add(material.fence);
//
//		getOrCreateBuilder(BlockTags.SLABS).add(material.slab);
//		getOrCreateBuilder(BlockTags.WOODEN_SLABS).add(material.slab);
//
//		getOrCreateBuilder(BlockTags.STAIRS).add(material.stairs);
//		getOrCreateBuilder(BlockTags.WOODEN_STAIRS).add(material.stairs);
//
//		getOrCreateBuilder(BlockTags.TRAPDOORS).add(material.trapdoor);
//		getOrCreateBuilder(BlockTags.WOODEN_TRAPDOORS).add(material.trapdoor);
//
//		getOrCreateBuilder(BlockTags.SIGNS).add(material.sign);
//
//		getOrCreateBuilder(BlockTags.CLIMBABLE).add(material.ladder);
//
//		getOrCreateBuilder(BlockTags.GUARDED_BY_PIGLINS).add(material.chest);
//		getOrCreateBuilder(BlockTags.GUARDED_BY_PIGLINS).add(material.barrel);
//
//		// Forge Tags
//		getOrCreateBuilder(Tags.Blocks.FENCES).add(material.fence);
//		getOrCreateBuilder(Tags.Blocks.FENCES_WOODEN).add(material.fence);
//
//		getOrCreateBuilder(Tags.Blocks.FENCE_GATES).add(material.gate);
//		getOrCreateBuilder(Tags.Blocks.FENCE_GATES_WOODEN).add(material.gate);
//
//		getOrCreateBuilder(Tags.Blocks.CHESTS).add(material.chest);
//		getOrCreateBuilder(Tags.Blocks.CHESTS_WOODEN).add(material.chest);
//
//		getOrCreateBuilder(Tags.Blocks.CHESTS_WOODEN).add(material.chest);
//
//		getOrCreateBuilder(BlockTags.createOptional(frl("workbench"))).add(material.craftingTable);
//
//	}
//
//	private ResourceLocation frl(String tag) {
//		return new ResourceLocation(ForgeVersion.MOD_ID, tag);
//	}
//
//	private void registerStoneMaterialTags(StoneMaterial material)
//	{
//		getOrCreateBuilder(BlockTags.STONE_BRICKS).add(material.bricks);
//
//		getOrCreateBuilder(BlockTags.WALLS).add(material.wall);
//		getOrCreateBuilder(BlockTags.WALLS).add(material.brick_wall);
//
//		getOrCreateBuilder(BlockTags.SLABS).add(material.slab);
//		getOrCreateBuilder(BlockTags.SLABS).add(material.brick_slab);
//
//		getOrCreateBuilder(BlockTags.STAIRS).add(material.stairs);
//		getOrCreateBuilder(BlockTags.STAIRS).add(material.brick_stairs);
//
//		getOrCreateBuilder(BlockTags.PRESSURE_PLATES).add(material.pressure_plate);
//		getOrCreateBuilder(BlockTags.STONE_PRESSURE_PLATES).add(material.pressure_plate);
//
//		// Forge Tags
//		getOrCreateBuilder(Tags.Blocks.STONE).add(material.stone);
//	}
//
//	private void registerMetalMaterialTags(MetalMaterial material)
//	{
//		getOrCreateBuilder(BlockTags.BEACON_BASE_BLOCKS).add(material.block);
//
//		getOrCreateBuilder(BlockTags.DOORS).add(material.door);
//
//		getOrCreateBuilder(BlockTags.STAIRS).add(material.stairs);
//
//		getOrCreateBuilder(BlockTags.TRAPDOORS).add(material.trapdoor);
//
//		getOrCreateBuilder(BlockTags.SLABS).add(material.slab);
//
//		getOrCreateBuilder(BlockTags.PRESSURE_PLATES).add(material.pressure_plate);
//
//		getOrCreateBuilder(BlockTags.ANVIL).add(material.anvil);
//
//		// Forge Tags
//		if (material.hasOre)
//		{
//			getOrCreateBuilder(Tags.Blocks.ORES).add(material.ore);
//		}
//
//		getOrCreateBuilder(Tags.Blocks.STORAGE_BLOCKS).add(material.block);
//	}
//}
