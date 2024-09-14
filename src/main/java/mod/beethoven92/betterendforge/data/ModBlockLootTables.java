//package mod.beethoven92.betterendforge.data;
//
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//import mod.beethoven92.betterendforge.BetterEnd;
//import mod.beethoven92.betterendforge.common.block.BlockProperties;
//import mod.beethoven92.betterendforge.common.block.BlockProperties.HydraluxShape;
//import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
//import mod.beethoven92.betterendforge.common.block.BulbVineBlock;
//import mod.beethoven92.betterendforge.common.block.EndLilyBlock;
//import mod.beethoven92.betterendforge.common.block.HydraluxBlock;
//import mod.beethoven92.betterendforge.common.block.LanceleafBlock;
//import mod.beethoven92.betterendforge.common.block.LumecornBlock;
//import mod.beethoven92.betterendforge.common.block.RespawnObeliskBlock;
//import mod.beethoven92.betterendforge.common.block.ShadowBerryBlock;
//import mod.beethoven92.betterendforge.common.block.SilkMothNestBlock;
//import mod.beethoven92.betterendforge.common.block.SulphurCrystalBlock;
//import mod.beethoven92.betterendforge.common.block.UmbrellaTreeMembraneBlock;
//import mod.beethoven92.betterendforge.common.block.material.ColoredMaterial;
//import mod.beethoven92.betterendforge.common.block.material.MetalMaterial;
//import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
//import mod.beethoven92.betterendforge.common.block.material.WoodenMaterial;
//import mod.beethoven92.betterendforge.common.block.template.EndAnvilBlock;
//import mod.beethoven92.betterendforge.common.block.template.EndCropBlock;
//import mod.beethoven92.betterendforge.common.init.ModBlocks;
//import mod.beethoven92.betterendforge.common.init.ModItems;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockGrass;
//import net.minecraft.init.Blocks;
//import net.minecraft.init.Enchantments;
//import net.minecraft.init.Items;
//import net.minecraft.item.Item;
//import net.minecraft.world.storage.loot.LootTableManager;
//import net.minecraftforge.fml.common.registry.ForgeRegistries;
//
//public class ModBlockLootTables {
//	@Override
//	protected Iterable<Block> getKnownBlocks() {
//		return StreamSupport.stream(ForgeRegistries.BLOCKS.spliterator(), false)
//				.filter(entry -> entry.getRegistryName() != null
//						&& entry.getRegistryName().getNamespace().equals(BetterEnd.MOD_ID))
//				.collect(Collectors.toSet());
//	}
//
//	@Override
//	protected void addTables() {
//		// BLOCKS
//		anvilLootTable((EndAnvilBlock) ModBlocks.AETERNIUM_ANVIL);
//
////		registerLootTable(ModBlocks.BLUE_VINE_FUR, (block) -> {
////			return droppingWithSilkTouchOrShears(block,
////					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.BLUE_VINE_SEED)).acceptCondition(
////							TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F, 0.083333336F, 0.1F)));
////		});
//
//		registerLootTable(ModBlocks.NEEDLEGRASS, (block) -> {
//			return droppingWithShears(block, withExplosionDecay(block, ItemLootEntry.builder(Items.STICK)
//					.acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F)))));
//		});
//
//
//		this.registerLootTable(ModBlocks.HYDRALUX, (block) -> {
//			return hydraluxDrop();
//		});
//
//
//		lanceleaf();
//
//		lumecorn();
//
//		registerLootTable(ModBlocks.GLOWING_PILLAR_LEAVES, (block) -> {
//			return droppingWithSilkTouchOrShears(block,
//					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.GLOWING_PILLAR_SEED))
//							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
//									0.083333336F, 0.1F)));
//		});
//
//
//		registerLootTable(ModBlocks.SILK_MOTH_NEST, (block) -> {
//			return LootTable.builder()
//					.addLootPool(withSurvivesExplosion(block, LootPool.builder().rolls(ConstantRange.of(1))
//							.addEntry(ItemLootEntry.builder(block).acceptCondition(
//									BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder
//											.newBuilder().withBoolProp(SilkMothNestBlock.ACTIVE, true))))));
//		});
//		// CROPS
//		ILootCondition.IBuilder ilootcondition$ibuilder = BlockStateProperty.builder(ModBlocks.SHADOW_BERRY)
//				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(ShadowBerryBlock.AGE, 3));
//		registerLootTable(ModBlocks.SHADOW_BERRY, droppingAndBonusWhen(ModBlocks.SHADOW_BERRY,
//				ModItems.SHADOW_BERRY_RAW, ModBlocks.SHADOW_BERRY.asItem(), ilootcondition$ibuilder));
//
//		ilootcondition$ibuilder = BlockStateProperty.builder(ModBlocks.BLOSSOM_BERRY)
//				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(EndCropBlock.AGE, 3));
//		registerLootTable(ModBlocks.BLOSSOM_BERRY, droppingAndBonusWhen(ModBlocks.BLOSSOM_BERRY,
//				ModItems.BLOSSOM_BERRY, ModBlocks.BLOSSOM_BERRY.asItem(), ilootcondition$ibuilder));
//
//		ilootcondition$ibuilder = BlockStateProperty.builder(ModBlocks.AMBER_ROOT)
//				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(EndCropBlock.AGE, 3));
//		registerLootTable(ModBlocks.AMBER_ROOT, droppingAndBonusWhen(ModBlocks.AMBER_ROOT,
//				ModItems.AMBER_ROOT_RAW, ModBlocks.AMBER_ROOT.asItem(), ilootcondition$ibuilder));
//
//		ilootcondition$ibuilder = BlockStateProperty.builder(ModBlocks.CHORUS_MUSHROOM)
//				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(EndCropBlock.AGE, 3));
//		registerLootTable(ModBlocks.CHORUS_MUSHROOM, droppingAndBonusWhen(ModBlocks.CHORUS_MUSHROOM,
//				ModItems.CHORUS_MUSHROOM_RAW, ModBlocks.CHORUS_MUSHROOM.asItem(), ilootcondition$ibuilder));
//
////	    ilootcondition$ibuilder = BlockStateProperty.builder(ModBlocks.PEARLBERRY).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(EndCropBlock.AGE, 3));
////	    registerLootTable(ModBlocks.PEARLBERRY, droppingAndBonusWhen(ModBlocks.PEARLBERRY, ModItems.BLOSSOM_BERRY, ModBlocks.PEARLBERRY.asItem(), ilootcondition$ibuilder));
//
//
//		registerLootTable(ModBlocks.BULB_VINE, (block) -> {
//			return bulbVineDrop();
//		});
//		registerLootTable(ModBlocks.MOSSY_GLOWSHROOM_FUR, (block) -> {
//			return droppingWithSilkTouchOrShears(block,
//					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.MOSSY_GLOWSHROOM_SAPLING))
//							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
//									0.083333336F, 0.1F)));
//		});
//
//		registerLootTable(ModBlocks.LACUGROVE_LEAVES, (block) -> {
//			return droppingWithSilkTouchOrShears(block,
//					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.LACUGROVE_SAPLING))
//							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
//									0.083333336F, 0.1F)));
//		});
//
//		registerLootTable(ModBlocks.PYTHADENDRON_LEAVES, (block) -> {
//			return droppingWithSilkTouchOrShears(block,
//					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.PYTHADENDRON_SAPLING))
//							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
//									0.083333336F, 0.1F)));
//		});
//
//		registerLootTable(ModBlocks.DRAGON_TREE_LEAVES, (block) -> {
//			return droppingWithSilkTouchOrShears(block,
//					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.DRAGON_TREE_SAPLING))
//							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
//									0.083333336F, 0.1F)));
//		});
//
//		registerLootTable(ModBlocks.TENANEA_LEAVES, (block) -> {
//			return droppingWithSilkTouchOrShears(block,
//					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.TENANEA_SAPLING))
//							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
//									0.083333336F, 0.1F)));
//		});
//
//		registerLootTable(ModBlocks.TENANEA_FLOWERS, BlockLootTables::onlyWithShears);
//		registerLootTable(ModBlocks.TENANEA_OUTER_LEAVES, (block) -> {
//			return droppingWithSilkTouchOrShears(block,
//					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.TENANEA_SAPLING))
//							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
//									0.083333336F, 0.1F)));
//		});
//
//		registerLootTable(ModBlocks.HELIX_TREE_LEAVES, (block) -> {
//			return droppingWithSilkTouchOrShears(block,
//					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.HELIX_TREE_SAPLING))
//							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
//									0.083333336F, 0.1F)));
//		});
//
//		registerLootTable(ModBlocks.UMBRELLA_TREE_MEMBRANE, (block) -> {
//			return umbrellaTreeMembraneDrop();
//		});
//		registerLootTable(ModBlocks.AMARANITA_FUR, (block) -> {
//			return droppingWithSilkTouchOrShears(block,
//					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.MOSSY_GLOWSHROOM_SAPLING))
//							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
//									0.083333336F, 0.1F)));
//		});
//
//		registerLootTable(ModBlocks.MOSSY_DRAGON_BONE, (block) -> {
//			return droppingWithSilkTouch(block, ModBlocks.DRAGON_BONE_BLOCK);
//		});
//
//		registerLootTable(ModBlocks.LUCERNIA_LEAVES, (block) -> {
//			return droppingWithSilkTouchOrShears(block,
//					withSurvivesExplosion(block, ItemLootEntry.builder(ModBlocks.LUCERNIA_SAPLING))
//							.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.025F,
//									0.083333336F, 0.1F)));
//		});
//		registerLootTable(ModBlocks.LUCERNIA_OUTER_LEAVES, (block) -> {
//			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(Items.AIR));
//		});
//		registerLootTable(ModBlocks.FILALUX, (block) -> {
//			return droppingWithSilkTouchOrShears(block, ItemLootEntry.builder(Items.AIR));
//		});
//
//		// FLOWER POT BLOCKS
//		registerFlowerPotLootTables();
//
//		// COLORED MATERIALS
//		registerColoredMaterialLootTables(ModBlocks.HYDRALUX_PETAL_BLOCK_COLORED);
//		registerColoredMaterialLootTables(ModBlocks.IRON_BULB_LANTERN_COLORED);
//	}
//
//	private void lanceleaf() {
//		ILootCondition.IBuilder lanceleafSeedCond1 = BlockStateProperty.builder((ModBlocks.LANCELEAF))
//				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LanceleafBlock.SHAPE,
//						BlockProperties.PentaShape.BOTTOM));
//		ILootCondition.IBuilder lanceleafSeedCond2 = BlockStateProperty.builder((ModBlocks.LANCELEAF))
//				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LanceleafBlock.SHAPE,
//						BlockProperties.PentaShape.PRE_BOTTOM));
//		ILootCondition.IBuilder lanceleafSeedCond3 = BlockStateProperty.builder((ModBlocks.LANCELEAF))
//				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LanceleafBlock.SHAPE,
//						BlockProperties.PentaShape.MIDDLE));
//		ILootCondition.IBuilder lanceleafSeedCond4 = BlockStateProperty.builder((ModBlocks.LANCELEAF))
//				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LanceleafBlock.SHAPE,
//						BlockProperties.PentaShape.PRE_TOP));
//		ILootCondition.IBuilder lanceleafSeedCond5 = BlockStateProperty.builder((ModBlocks.LANCELEAF))
//				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LanceleafBlock.SHAPE,
//						BlockProperties.PentaShape.TOP));
//		LootEntry.Builder<?> lanceleafSeedDrop = ItemLootEntry.builder(ModBlocks.LANCELEAF_SEED)
//				.acceptCondition(RandomChance.builder(0.5F));
//		registerLootTable(ModBlocks.LANCELEAF, LootTable.builder()
//				.addLootPool(LootPool.builder().addEntry(lanceleafSeedDrop).acceptCondition(lanceleafSeedCond1))
//				.addLootPool(LootPool.builder().addEntry(lanceleafSeedDrop).acceptCondition(lanceleafSeedCond2))
//				.addLootPool(LootPool.builder().addEntry(lanceleafSeedDrop).acceptCondition(lanceleafSeedCond3))
//				.addLootPool(LootPool.builder().addEntry(lanceleafSeedDrop).acceptCondition(lanceleafSeedCond4))
//				.addLootPool(LootPool.builder().addEntry(lanceleafSeedDrop).acceptCondition(lanceleafSeedCond5)));
//
//	}
//
//	private void lumecorn() {
//		registerLootTable(ModBlocks.LUMECORN_SEED, BlockLootTables::onlyWithShears);
//		ILootCondition.IBuilder lumecornSeedCond1 = BlockStateProperty.builder(ModBlocks.LUMECORN)
//				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LumecornBlock.SHAPE,
//						BlockProperties.LumecornShape.BOTTOM_BIG));
//		ILootCondition.IBuilder lumecornSeedCond2 = BlockStateProperty.builder(ModBlocks.LUMECORN)
//				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LumecornBlock.SHAPE,
//						BlockProperties.LumecornShape.BOTTOM_SMALL));
//		ILootCondition.IBuilder lumecornSeedCond3 = BlockStateProperty.builder(ModBlocks.LUMECORN)
//				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LumecornBlock.SHAPE,
//						BlockProperties.LumecornShape.MIDDLE));
//		ILootCondition.IBuilder lumecornRodCond1 = BlockStateProperty.builder(ModBlocks.LUMECORN)
//				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LumecornBlock.SHAPE,
//						BlockProperties.LumecornShape.LIGHT_TOP));
//		ILootCondition.IBuilder lumecornRodCond2 = BlockStateProperty.builder(ModBlocks.LUMECORN)
//				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LumecornBlock.SHAPE,
//						BlockProperties.LumecornShape.LIGHT_TOP_MIDDLE));
//		ILootCondition.IBuilder lumecornRodCond3 = BlockStateProperty.builder(ModBlocks.LUMECORN)
//				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LumecornBlock.SHAPE,
//						BlockProperties.LumecornShape.LIGHT_MIDDLE));
//		ILootCondition.IBuilder lumecornRodCond4 = BlockStateProperty.builder(ModBlocks.LUMECORN)
//				.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(LumecornBlock.SHAPE,
//						BlockProperties.LumecornShape.LIGHT_BOTTOM));
//		LootEntry.Builder<?> lumecornSeedDrop = ItemLootEntry.builder(ModBlocks.LUMECORN_SEED)
//				.acceptCondition(RandomChance.builder(0.5F));
//		LootEntry.Builder<?> lumecornRodDrop = ItemLootEntry.builder(ModItems.LUMECORN_ROD)
//				.acceptCondition(RandomChance.builder(0.5F));
//		registerLootTable(ModBlocks.LUMECORN,
//				LootTable.builder()
//						.addLootPool(LootPool.builder().addEntry(lumecornSeedDrop).acceptCondition(lumecornSeedCond1))
//						.addLootPool(LootPool.builder().addEntry(lumecornSeedDrop).acceptCondition(lumecornSeedCond2))
//						.addLootPool(LootPool.builder().addEntry(lumecornSeedDrop).acceptCondition(lumecornSeedCond3))
//						.addLootPool(LootPool.builder().addEntry(lumecornRodDrop).acceptCondition(lumecornRodCond1))
//						.addLootPool(LootPool.builder().addEntry(lumecornRodDrop).acceptCondition(lumecornRodCond2))
//						.addLootPool(LootPool.builder().addEntry(lumecornRodDrop).acceptCondition(lumecornRodCond3))
//						.addLootPool(LootPool.builder().addEntry(lumecornRodDrop).acceptCondition(lumecornRodCond4)));
//	}
//
//
//	private void anvilLootTable(EndAnvilBlock block) {
//		ILootCondition.IBuilder builder0 = BlockStateProperty.builder(block).fromProperties(
//				StatePropertiesPredicate.Builder.newBuilder().withIntProp(EndAnvilBlock.DESTRUCTION, 0));
//		ILootCondition.IBuilder builder1 = BlockStateProperty.builder(block).fromProperties(
//				StatePropertiesPredicate.Builder.newBuilder().withIntProp(EndAnvilBlock.DESTRUCTION, 1));
//		ILootCondition.IBuilder builder2 = BlockStateProperty.builder(block).fromProperties(
//				StatePropertiesPredicate.Builder.newBuilder().withIntProp(EndAnvilBlock.DESTRUCTION, 2));
//		CompoundNBT level0 = new CompoundNBT();
//		level0.putInt("level", 0);
//		CompoundNBT level1 = new CompoundNBT();
//		level1.putInt("level", 1);
//		CompoundNBT level2 = new CompoundNBT();
//		level2.putInt("level", 2);
//
//		registerLootTable(block, LootTable.builder()
//				.addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(block).acceptFunction(SetNBT.builder(level0)).acceptCondition(builder0)))
//				.addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(block).acceptFunction(SetNBT.builder(level1)).acceptCondition(builder1)))
//				.addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(block).acceptFunction(SetNBT.builder(level2)).acceptCondition(builder2))));
//
//	}
//
//	private void registerFlowerPotLootTables() {
//		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach((block) -> {
//			if (block instanceof FlowerPotBlock) {
//				registerFlowerPot(block);
//			}
//		});
//	}
//
//	private static LootTable.Builder bulbVineDrop() {
//		LootEntry.Builder<?> bulb_drop = ItemLootEntry.builder(ModItems.GLOWING_BULB);
//		LootEntry.Builder<?> seed_drop = ItemLootEntry.builder(ModBlocks.BULB_VINE_SEED)
//				.acceptCondition(RandomChance.builder(0.125F));
//
//		LootPool.Builder bottom_loot = LootPool.builder().addEntry(bulb_drop).acceptCondition(
//				BlockStateProperty.builder(ModBlocks.BULB_VINE).fromProperties(StatePropertiesPredicate.Builder
//						.newBuilder().withProp(BulbVineBlock.SHAPE, TripleShape.BOTTOM)));
//		LootPool.Builder middle_loot = LootPool.builder().addEntry(seed_drop).acceptCondition(
//				BlockStateProperty.builder(ModBlocks.BULB_VINE).fromProperties(StatePropertiesPredicate.Builder
//						.newBuilder().withProp(BulbVineBlock.SHAPE, TripleShape.MIDDLE)));
//		LootPool.Builder top_loot = LootPool.builder().addEntry(bulb_drop)
//				.acceptCondition(BlockStateProperty.builder(ModBlocks.BULB_VINE).fromProperties(
//						StatePropertiesPredicate.Builder.newBuilder().withProp(BulbVineBlock.SHAPE, TripleShape.TOP)));
//
//		return LootTable.builder().addLootPool(bottom_loot).addLootPool(middle_loot).addLootPool(top_loot);
//	}
//
//	private static LootTable.Builder hydraluxDrop() {
//		LootEntry.Builder<?> petal_drop = ItemLootEntry.builder(ModItems.HYDRALUX_PETAL)
//				.acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 4.0F)));
//		LootEntry.Builder<?> roots_drop = ItemLootEntry.builder(ModBlocks.HYDRALUX_SAPLING)
//				.acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)));
//
//		LootPool.Builder small_flower_loot = LootPool.builder().addEntry(petal_drop).acceptCondition(
//				BlockStateProperty.builder(ModBlocks.HYDRALUX).fromProperties(StatePropertiesPredicate.Builder
//						.newBuilder().withProp(HydraluxBlock.SHAPE, HydraluxShape.FLOWER_SMALL_BOTTOM)));
//		LootPool.Builder big_flower_loot = LootPool.builder().addEntry(petal_drop).acceptCondition(
//				BlockStateProperty.builder(ModBlocks.HYDRALUX).fromProperties(StatePropertiesPredicate.Builder
//						.newBuilder().withProp(HydraluxBlock.SHAPE, HydraluxShape.FLOWER_BIG_BOTTOM)));
//		LootPool.Builder roots_loot = LootPool.builder().addEntry(roots_drop).acceptCondition(
//				BlockStateProperty.builder(ModBlocks.HYDRALUX).fromProperties(StatePropertiesPredicate.Builder
//						.newBuilder().withProp(HydraluxBlock.SHAPE, HydraluxShape.ROOTS)));
//
//		return LootTable.builder().addLootPool(small_flower_loot).addLootPool(big_flower_loot).addLootPool(roots_loot);
//	}
//
//	private static LootTable.Builder umbrellaTreeMembraneDrop() {
//		LootEntry.Builder<?> block_drop = ItemLootEntry.builder(ModBlocks.UMBRELLA_TREE_MEMBRANE);
//		LootEntry.Builder<?> sapling_drop = ItemLootEntry.builder(ModBlocks.UMBRELLA_TREE_SAPLING)
//				.acceptCondition(RandomChance.builder(0.25F));
//
//		LootPool.Builder color_0_loot = LootPool.builder().addEntry(sapling_drop)
//				.acceptCondition(BlockStateProperty.builder(ModBlocks.UMBRELLA_TREE_MEMBRANE).fromProperties(
//						StatePropertiesPredicate.Builder.newBuilder().withIntProp(UmbrellaTreeMembraneBlock.COLOR, 0)));
//		LootPool.Builder color_1_loot = LootPool.builder().addEntry(block_drop)
//				.acceptCondition(BlockStateProperty.builder(ModBlocks.UMBRELLA_TREE_MEMBRANE).fromProperties(
//						StatePropertiesPredicate.Builder.newBuilder().withIntProp(UmbrellaTreeMembraneBlock.COLOR, 1)));
//		LootPool.Builder color_2_loot = LootPool.builder().addEntry(block_drop)
//				.acceptCondition(BlockStateProperty.builder(ModBlocks.UMBRELLA_TREE_MEMBRANE).fromProperties(
//						StatePropertiesPredicate.Builder.newBuilder().withIntProp(UmbrellaTreeMembraneBlock.COLOR, 2)));
//		LootPool.Builder color_3_loot = LootPool.builder().addEntry(block_drop)
//				.acceptCondition(BlockStateProperty.builder(ModBlocks.UMBRELLA_TREE_MEMBRANE).fromProperties(
//						StatePropertiesPredicate.Builder.newBuilder().withIntProp(UmbrellaTreeMembraneBlock.COLOR, 3)));
//		LootPool.Builder color_4_loot = LootPool.builder().addEntry(block_drop)
//				.acceptCondition(BlockStateProperty.builder(ModBlocks.UMBRELLA_TREE_MEMBRANE).fromProperties(
//						StatePropertiesPredicate.Builder.newBuilder().withIntProp(UmbrellaTreeMembraneBlock.COLOR, 4)));
//		LootPool.Builder color_5_loot = LootPool.builder().addEntry(block_drop)
//				.acceptCondition(BlockStateProperty.builder(ModBlocks.UMBRELLA_TREE_MEMBRANE).fromProperties(
//						StatePropertiesPredicate.Builder.newBuilder().withIntProp(UmbrellaTreeMembraneBlock.COLOR, 5)));
//		LootPool.Builder color_6_loot = LootPool.builder().addEntry(block_drop)
//				.acceptCondition(BlockStateProperty.builder(ModBlocks.UMBRELLA_TREE_MEMBRANE).fromProperties(
//						StatePropertiesPredicate.Builder.newBuilder().withIntProp(UmbrellaTreeMembraneBlock.COLOR, 6)));
//
//		return LootTable.builder().addLootPool(color_0_loot).addLootPool(color_1_loot).addLootPool(color_2_loot)
//				.addLootPool(color_3_loot).addLootPool(color_4_loot).addLootPool(color_5_loot)
//				.addLootPool(color_6_loot);
//	}
//
//}
