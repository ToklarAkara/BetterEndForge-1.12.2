package mod.beethoven92.betterendforge.common.block.material;

import mod.beethoven92.betterendforge.common.block.BulbVineLanternBlock;
import mod.beethoven92.betterendforge.common.block.template.ChainBlock;
import mod.beethoven92.betterendforge.common.block.template.ChandelierBlock;
import mod.beethoven92.betterendforge.common.block.template.EndAnvilBlock;
import mod.beethoven92.betterendforge.common.block.template.MetalPaneBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModCreativeTabs;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.item.EndAnvilItem;
import mod.beethoven92.betterendforge.common.item.HammerItem;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MapColor;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MetalMaterial
{
	public final Block ore;
	public final Block block;
	public final Block tile;
	public final Block bars;
	public final Block pressure_plate;
	//public final Block door;
	public final Block trapdoor;
	public final Block anvil;
	public final Block chain;
	public final Block stairs;
	//public final Block slab;

	public final Block chandelier;
	public final Block bulb_lantern;
	public final ColoredMaterial bulb_lantern_colored;

	public final Item nugget;
	public final Item ingot;

	public final Item shovelHead;
	public final Item pickaxeHead;
	public final Item axeHead;
	public final Item hoeHead;
	public final Item swordBlade;
	public final Item swordHandle;

	public final Item shovel;
	public final Item sword;
	public final Item pickaxe;
	public final Item axe;
	public final Item hoe;
	public final Item hammer;

	public final Item helmet;
	public final Item chestplate;
	public final Item leggings;
	public final Item boots;

	public final boolean hasOre;

	public final String name;

	public static MetalMaterial makeNormal(String name, Item.ToolMaterial material, ItemArmor.ArmorMaterial armor)
	{
		return new MetalMaterial(name, true, Material.IRON, material, armor);
	}

	public static MetalMaterial makeOreless(String name, Item.ToolMaterial material, ItemArmor.ArmorMaterial armor)
	{
		return new MetalMaterial(name, false, Material.IRON, material, armor);
	}

	public MetalMaterial(String name, boolean hasOre, Material blockMaterial, Item.ToolMaterial material, ItemArmor.ArmorMaterial armor)
	{
		this.hasOre = hasOre;
		this.name = name;

		ore = hasOre ? new Block(Material.ROCK).setHardness(3.0F).setResistance(5.0F) : new Block(Material.ROCK, MapColor.SAND).setHardness(3.0F).setResistance(15.0F);
		block = new Block(blockMaterial).setHardness(5.0F).setResistance(10.0F);
		tile = new Block(blockMaterial).setHardness(5.0F).setResistance(10.0F);
		stairs = new CustomBlockStairs(block.getDefaultState());
//		slab = new BlockSlab(blockMaterial);
//		door = new BlockDoor(blockMaterial);
		trapdoor = new CustomBlockTrapDoor(blockMaterial);
		anvil = new EndAnvilBlock(0);
		bars = new MetalPaneBlock(blockMaterial, true);
		chain = new ChainBlock().setHardness(5.0F).setResistance(10.0F);
		pressure_plate = new CustomPressurePlate(Material.IRON, BlockPressurePlate.Sensitivity.EVERYTHING);

		chandelier = new ChandelierBlock(blockMaterial);
		bulb_lantern = new BulbVineLanternBlock(EnumDyeColor.WHITE);//blockMaterial);
		bulb_lantern_colored = new ColoredMaterial(name + "_bulb_lantern", ()->new BulbVineLanternBlock(), bulb_lantern, false);

		nugget = new Item();
		ingot = new Item();

		shovelHead = new Item();
		pickaxeHead = new Item();
		axeHead = new Item();
		hoeHead = new Item();
		swordHandle = new Item();
		swordBlade = new Item();

		shovel = new ItemSpade(material);
		sword = new ItemSword(material);
		pickaxe = new ItemPickaxe(material){};
		axe = new ItemAxe(material, material.getAttackDamage(), material.getEfficiency()){};
		hoe = new ItemHoe(material);
		hammer = new HammerItem(material,  material.getAttackDamage(), -3.2F, 0.2D);

		helmet = new ItemArmor(armor, 0, EntityEquipmentSlot.HEAD);
		chestplate = new ItemArmor(armor, 0, EntityEquipmentSlot.CHEST);
		leggings = new ItemArmor(armor, 0, EntityEquipmentSlot.LEGS);
		boots = new ItemArmor(armor, 0, EntityEquipmentSlot.FEET);

		ModBlocks.registerBlockWithDefaultItem (ore, name + "_ore");
		ModBlocks.registerBlockWithDefaultItem(block, name + "_block");
		ModBlocks.registerBlockWithDefaultItem(tile, name + "_tile");
		ModBlocks.registerBlockWithDefaultItem(stairs, name + "_stairs");
		//ModBlocks.registerBlockWithDefaultItem(slab, name + "_slab");
//		ModBlocks.registerBlockWithDefaultItem(door, name + "_door");
		ModBlocks.registerBlockWithDefaultItem(trapdoor, name + "_trapdoor");
//		ModBlocks.registerBlockWithDefaultItem(anvil, name + "_anvil");
		ModBlocks.registerBlockWithDefaultItem(bars, name + "_bars");
		ModBlocks.registerBlockWithDefaultItem(chain, name + "_chain");
		ModBlocks.registerBlockWithDefaultItem(pressure_plate, name + "_pressure_plate");

		ModBlocks.registerBlockWithDefaultItem(chandelier, name + "_chandelier");
		ModBlocks.registerBlockWithDefaultItem(bulb_lantern, name + "_bulb_lantern");

		ModItems.registerItem(nugget, name + "_nugget");
		ModItems.registerItem(ingot, name + "_ingot");

		ModItems.registerItem(shovelHead, name + "_shovel_head");
		ModItems.registerItem(pickaxeHead, name + "_pickaxe_head");
		ModItems.registerItem(axeHead, name + "_axe_head");
		ModItems.registerItem(hoeHead, name + "_hoe_head");
		ModItems.registerItem(swordHandle, name + "_sword_handle");
		ModItems.registerItem(swordBlade, name + "_sword_blade");

		ModItems.registerItem(shovel, name + "_shovel");
		ModItems.registerItem(sword, name + "_sword");
		ModItems.registerItem(pickaxe, name + "_pickaxe");
		ModItems.registerItem(axe, name + "_axe");
		ModItems.registerItem(hoe, name + "_hoe");
		ModItems.registerItem(hammer, name + "_hammer");

		ModItems.registerItem(helmet, name + "_helmet");
		ModItems.registerItem(chestplate, name + "_chestplate");
		ModItems.registerItem(leggings, name + "_leggings");
		ModItems.registerItem(boots, name + "_boots");
	}
}
