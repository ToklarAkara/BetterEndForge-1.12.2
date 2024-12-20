package mod.beethoven92.betterendforge.common.block.material;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Iterables;

import mod.beethoven92.betterendforge.common.block.ModLanternBlock;
import mod.beethoven92.betterendforge.common.block.template.EndFurnaceBlock;
import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.block.template.PillarBlockTemplate;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class StoneMaterial
{
	private static List<StoneMaterial> MATERIALS = new ArrayList<>();
	
	public String name;
	
	public Block stone;
	
	public Block polished;
	public Block tiles;
	public Block pillar;
	public Block stairs;
	public Block slab;
	public Block wall;
	public Block button;
	public Block pressure_plate;
	public Block pedestal;
	public Block lantern;
	
	public Block bricks;
	public Block brick_stairs;
	public Block brick_slab;
	public Block brick_wall;
	
	public Block furnace;
	
	public StoneMaterial(String name, MapColor color)
	{
		this.name = name;

		stone = ModBlocks.registerBlockWithDefaultItem(name, 
				() -> new Block(Material.ROCK).setHardness(3.0F).setResistance(9.0F));
		polished = ModBlocks.registerBlockWithDefaultItem(name + "_polished", 
				() -> new Block(Material.ROCK).setHardness(3.0F).setResistance(9.0F));
		tiles = ModBlocks.registerBlockWithDefaultItem(name + "_tiles", 
				() -> new Block(Material.ROCK).setHardness(3.0F).setResistance(9.0F));
		pillar = ModBlocks.registerBlockWithDefaultItem(name + "_pillar", 
				() -> new PillarBlockTemplate(Material.ROCK).setHardness(3.0F).setResistance(9.0F));
		stairs = ModBlocks.registerBlockWithDefaultItem(name + "_stairs", 
				() -> new CustomBlockStairs(stone.getDefaultState()).setHardness(3.0F).setResistance(9.0F));
		slab = ModBlocks.registerBlockWithDefaultItem(name + "_slab",
				() -> new CustomBlockSlab(Material.ROCK).setHardness(3.0F).setResistance(9.0F));
		wall = ModBlocks.registerBlockWithDefaultItem(name + "_wall", 
				() -> new BlockWall(stone){
					public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
					{
						items.add(new ItemStack(this));
					}
				}.setHardness(3.0F).setResistance(9.0F));
//		button = ModBlocks.registerBlockWithDefaultItem(name + "_button",
//				() -> new BlockButton(Material.ROCK).setHardness(3.0F).setResistance(9.0F));

		pressure_plate = ModBlocks.registerBlockWithDefaultItem(name + "_pressure_plate", 
				() -> new CustomPressurePlate(Material.ROCK, BlockPressurePlate.Sensitivity.MOBS).setHardness(3.0F).setResistance(9.0F));
		pedestal = ModBlocks.registerBlockWithDefaultItem(name + "_pedestal", 
				() -> new PedestalBlock().setHardness(3.0F).setResistance(9.0F));
		lantern = ModBlocks.registerBlockWithDefaultItem(name + "_lantern", 
				() -> new ModLanternBlock().setHardness(3.0F).setResistance(9.0F).setLightLevel(1));
		
		bricks = ModBlocks.registerBlockWithDefaultItem(name + "_bricks", 
				() -> new Block(Material.ROCK).setHardness(3.0F).setResistance(9.0F));
		brick_stairs = ModBlocks.registerBlockWithDefaultItem(name + "_bricks_stairs", 
				() -> new CustomBlockStairs(bricks.getDefaultState()).setHardness(3.0F).setResistance(9.0F));
		brick_slab = ModBlocks.registerBlockWithDefaultItem(name + "_bricks_slab",
				() -> new CustomBlockSlab(Material.ROCK).setHardness(3.0F).setResistance(9.0F));
		brick_wall = ModBlocks.registerBlockWithDefaultItem(name + "_bricks_wall", 
				() -> new BlockWall(bricks).setHardness(3.0F).setResistance(9.0F));
		
//		furnace = ModBlocks.registerBlockWithDefaultItem(name + "_furnace",
//				() -> new EndFurnaceBlock(false).setLightLevel(0));
		
		MATERIALS.add(this);
	}
	
	public static Iterable<StoneMaterial> getMaterials() {
		return Iterables.unmodifiableIterable(MATERIALS);
	}
}
