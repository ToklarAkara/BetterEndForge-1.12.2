package mod.beethoven92.betterendforge.common.init;

import java.util.ArrayList;
import java.util.function.Supplier;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.item.CrystaliteArmor;
import mod.beethoven92.betterendforge.common.item.EnchantedPetalItem;
import mod.beethoven92.betterendforge.common.item.EndAnvilItem;
import mod.beethoven92.betterendforge.common.item.GuideBookItem;
import mod.beethoven92.betterendforge.common.item.HammerItem;
import mod.beethoven92.betterendforge.common.item.ModArmorMaterial;
import mod.beethoven92.betterendforge.common.item.ModItemTier;
import mod.beethoven92.betterendforge.common.item.UmbrellaClusterJuiceItem;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class ModItems 
{
	public static final ArrayList<Item> ITEMS = new ArrayList<>();//DeferredRegister.create(ForgeRegistries.ITEMS, BetterEnd.MOD_ID);
	
	// MATERIAL ITEMS
	public final static Item AETERNIUM_INGOT = registerItem("aeternium_ingot",() -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item ENDER_DUST = registerItem("ender_dust",() -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item END_LILY_LEAF = registerItem("end_lily_leaf",() -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item END_LILY_LEAF_DRIED = registerItem("end_lily_leaf_dried",() -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item CRYSTAL_SHARDS = registerItem("crystal_shards",() -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item ETERNAL_CRYSTAL = registerItem("eternal_crystal",() -> new Item(){
		@Override
		public IRarity getForgeRarity(ItemStack stack) {
			return EnumRarity.EPIC;
		}
	}.setMaxStackSize(16).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item ENDER_SHARD = registerItem("ender_shard", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item RAW_AMBER = registerItem("raw_amber", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item AMBER_GEM = registerItem("amber_gem", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item GLOWING_BULB = registerItem("glowing_bulb", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item GELATINE = registerItem("gelatine", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item CRYSTALLINE_SULPHUR = registerItem("crystalline_sulphur", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item HYDRALUX_PETAL = registerItem("hydralux_petal", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item ENCHANTED_PETAL = registerItem("enchanted_petal", () -> new EnchantedPetalItem().setMaxStackSize(16).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item LEATHER_STRIPE = registerItem("leather_stripe", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item LEATHER_WRAPPED_STICK = registerItem("leather_wrapped_stick", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item SILK_FIBER = registerItem("silk_fiber", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item LUMECORN_ROD = registerItem("lumecorn_rod", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	
	// ARMOR ITEMS
	public static final Item AETERNIUM_HELMET = registerItem("aeternium_helmet", () -> new ItemArmor(ModArmorMaterial.AETERNIUM, 0, EntityEquipmentSlot.HEAD).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public static final Item AETERNIUM_CHESTPLATE = registerItem("aeternium_chestplate", () -> new ItemArmor(ModArmorMaterial.AETERNIUM, 0, EntityEquipmentSlot.CHEST).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public static final Item AETERNIUM_LEGGINGS = registerItem("aeternium_leggings", () -> new ItemArmor(ModArmorMaterial.AETERNIUM, 0, EntityEquipmentSlot.LEGS).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public static final Item AETERNIUM_BOOTS = registerItem("aeternium_boots", () -> new ItemArmor(ModArmorMaterial.AETERNIUM, 0, EntityEquipmentSlot.FEET).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public static final Item CRYSTALITE_HELMET = registerItem("crystalite_helmet", () -> new CrystaliteArmor(ModArmorMaterial.CRYSTALITE,0, EntityEquipmentSlot.HEAD).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public static final Item CRYSTALITE_CHESTPLATE = registerItem("crystalite_chestplate", () -> new CrystaliteArmor(ModArmorMaterial.CRYSTALITE,0, EntityEquipmentSlot.CHEST).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public static final Item CRYSTALITE_LEGGINGS = registerItem("crystalite_leggings", () -> new CrystaliteArmor(ModArmorMaterial.CRYSTALITE,0, EntityEquipmentSlot.LEGS).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public static final Item CRYSTALITE_BOOTS = registerItem("crystalite_boots", () -> new CrystaliteArmor(ModArmorMaterial.CRYSTALITE,0, EntityEquipmentSlot.FEET).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));

	// TOOL ITEMS
	public static final Item AETERNIUM_SWORD = registerItem("aeternium_sword", () -> new ItemSword(ModItemTier.AETERNIUM).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public static final Item AETERNIUM_SHOVEL = registerItem("aeternium_shovel", () -> new ItemSpade(ModItemTier.AETERNIUM).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public static final Item AETERNIUM_PICKAXE = registerItem("aeternium_pickaxe", () -> new ItemPickaxe(ModItemTier.AETERNIUM){

	}.setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public static final Item AETERNIUM_AXE = registerItem("aeternium_axe", () -> new ItemAxe(ModItemTier.AETERNIUM, ModItemTier.AETERNIUM.getAttackDamage(), -3){

	}.setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public static final Item AETERNIUM_HOE = registerItem("aeternium_hoe", () -> new ItemHoe(ModItemTier.AETERNIUM).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public static final Item AETERNIUM_HAMMER = registerItem("aeternium_hammer", () -> new HammerItem(ModItemTier.AETERNIUM, 6.0F, -3.0F, 0.3D).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public static final Item IRON_HAMMER = registerItem("iron_hammer", () -> new HammerItem(Item.ToolMaterial.IRON, 5.0F, -3.2F, 0.2D).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public static final Item GOLDEN_HAMMER = registerItem("golden_hammer", () -> new HammerItem(Item.ToolMaterial.GOLD, 4.5F, -3.4F, 0.3D).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public static final Item DIAMOND_HAMMER = registerItem("diamond_hammer", () -> new HammerItem(Item.ToolMaterial.DIAMOND, 5.5F, -3.1F, 0.2D).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	//public static final HammerItem NETHERITE_HAMMER = registerItem("netherite_hammer", () -> new HammerItem(Item.ToolMaterial.NETHERITE, 5.0F, -3.0F, 0.2D, new Item.Properties().group(ModCreativeTabs.CREATIVE_TAB)));
	
	// TOOLPARTS
	public final static Item AETERNIUM_SHOVEL_HEAD = registerItem("aeternium_shovel_head", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item AETERNIUM_PICKAXE_HEAD = registerItem("aeternium_pickaxe_head", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item AETERNIUM_AXE_HEAD = registerItem("aeternium_axe_head", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item AETERNIUM_HOE_HEAD = registerItem("aeternium_hoe_head", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item AETERNIUM_HAMMER_HEAD = registerItem("aeternium_hammer_head", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item AETERNIUM_SWORD_BLADE = registerItem("aeternium_sword_blade", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item AETERNIUM_SWORD_HANDLE = registerItem("aeternium_sword_handle", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB));

	// FOOD ITEMS
    public final static Item SHADOW_BERRY_RAW = registerItem("shadow_berry_raw", () -> new ItemFood(4, 0.5f, false).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item SHADOW_BERRY_COOKED = registerItem("shadow_berry_cooked", () -> new ItemFood(6, 0.7f, false).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item END_FISH_RAW = registerItem("end_fish_raw", () -> new ItemFood(2, 0.1f, false).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item END_FISH_COOKED = registerItem("end_fish_cooked", () -> new ItemFood(6, 0.8f, false).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item SWEET_BERRY_JELLY = registerItem("sweet_berry_jelly", () -> new ItemFood(3, 0.75f, false).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item SHADOW_BERRY_JELLY = registerItem("shadow_berry_jelly", () -> new ItemFood(4, 0.75f, false).setPotionEffect(new PotionEffect(Potion.getPotionById(16), 400),1).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item BLOSSOM_BERRY = registerItem("blossom_berry", () -> new ItemFood(4, 0.3f, false).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item AMBER_ROOT_RAW = registerItem("amber_root_raw", () -> new ItemFood(2, 0.8f, false).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item CHORUS_MUSHROOM_RAW = registerItem("chorus_mushroom_raw", () -> new ItemFood(3, 0.5f, false).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	public final static Item CHORUS_MUSHROOM_COOKED = registerItem("chorus_mushroom_cooked", () -> new ItemFood(6, 0.6f, false).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
	
	// DRINK ITEMS
	public final static Item UMBRELLA_CLUSTER_JUICE = registerItem("umbrella_cluster_juice", () -> new UmbrellaClusterJuiceItem().setCreativeTab(ModCreativeTabs.CREATIVE_TAB).setMaxStackSize(1));
	
	// MISC ITEMS
	public final static Item BUCKET_END_FISH = registerItem("bucket_end_fish", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB).setMaxStackSize(1));
	public final static Item BUCKET_CUBOZOA = registerItem("bucket_cubozoa", () -> new Item().setCreativeTab(ModCreativeTabs.CREATIVE_TAB).setMaxStackSize(1));
	//public final static Item GUIDE_BOOK = registerItem("guidebook", () -> new GuideBookItem().setCreativeTab(ModCreativeTabs.CREATIVE_TAB).setMaxStackSize(1));

	
	//public final static Item AETERNIUM_ANVIL = registerItem("aeternium_anvil", () -> new EndAnvilItem(ModBlocks.AETERNIUM_ANVIL).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));

	
	// MUSIC DISCS
	public final static Item MUSIC_DISC_STRANGE_AND_ALIEN = registerItem("music_disc_strange_and_alien", () -> new ItemRecord("strange_and_alien", ModSoundEvents.RECORD_STRANGE_AND_ALIEN){}.setCreativeTab(ModCreativeTabs.CREATIVE_TAB).setMaxStackSize(1));
	public final static Item MUSIC_DISC_GRASPING_AT_STARS = registerItem("music_disc_grasping_at_stars", () -> new ItemRecord("grasping_at_stars", ModSoundEvents.RECORD_GRASPING_AT_STARS){}.setCreativeTab(ModCreativeTabs.CREATIVE_TAB).setMaxStackSize(1));
	public final static Item MUSIC_DISC_ENDSEEKER = registerItem("music_disc_endseeker", () -> new ItemRecord("endseeker", ModSoundEvents.RECORD_ENDSEEKER){}.setCreativeTab(ModCreativeTabs.CREATIVE_TAB).setMaxStackSize(1));
	public final static Item MUSIC_DISC_EO_DRACONA = registerItem("music_disc_eo_dracona", () -> new ItemRecord("eo_dracona", ModSoundEvents.RECORD_EO_DRACONA){}.setCreativeTab(ModCreativeTabs.CREATIVE_TAB).setMaxStackSize(1));




	//////////////////////////////////////////////////////
	//
	// Item registration helpers
	//
	/////////////////////////////////////////////////////

	public static Item registerItem(Item item, String name){
		return registerItem(name, ()->item);
	}

	public static <T extends Item> T registerItem(String name, Supplier<? extends T> itemSupplier)
	{
		T item = itemSupplier.get();
		item.setRegistryName(name);
		item.setTranslationKey(BetterEnd.MOD_ID+"."+name);
		ITEMS.add(item);
		return item;
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> evt)
	{
		evt.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent event) {
		for(Item item : ITEMS) {
			ModelResourceLocation loc = new ModelResourceLocation(item.getRegistryName(), "inventory");
			ModelLoader.setCustomModelResourceLocation(item, 0, loc);
		}
	}
}
