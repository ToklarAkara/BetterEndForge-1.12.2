package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;

public class ModTags 
{
	// MOD BLOCK TAGS	
	public static final ArrayList<Block> END_GROUND = makeModBlockTag("end_ground");
	public static final ArrayList<Block> GEN_TERRAIN = makeModBlockTag("gen_terrain");
	
	// MOD ITEM TAGS	
	public static final ArrayList<Item> HAMMERS = makeModItemTag("hammers");
	public static final ArrayList<Item> FURNACES = makeModItemTag("furnaces");

	public static final String[] END_GROUND_STRS = new String[]{"minecraft:end_stone",
			"betterendforge:endstone_dust",
			"betterendforge:crystal_moss",
			"betterendforge:end_mycelium",
			"betterendforge:end_moss",
			"betterendforge:chorus_nylium",
			"betterendforge:cave_moss",
			"betterendforge:shadow_grass",
			"betterendforge:pink_moss",
			"betterendforge:sulphuric_rock",
			"betterendforge:brimstone",
			"betterendforge:amber_moss",
			"betterendforge:jungle_moss",
			"betterendforge:rutiscus",
			"betterendforge:sangnum",
			"betterendforge:pallidium_full",
			"betterendforge:pallidium_heavy",
			"betterendforge:pallidium_thin",
			"betterendforge:pallidium_tiny"};

	public static final String[] GEN_TERRAIN_STRS = new String[]{"minecraft:end_stone",
			"betterendforge:endstone_dust",
			"betterendforge:crystal_moss",
			"betterendforge:end_mycelium",
			"betterendforge:end_moss",
			"betterendforge:chorus_nylium",
			"betterendforge:cave_moss",
			"betterendforge:shadow_grass",
			"betterendforge:pink_moss",
			"betterendforge:ender_ore",
			"betterendforge:flavolite",
			"betterendforge:virid_jadestone",
			"betterendforge:violecite",
			"betterendforge:sulphuric_rock",
			"betterendforge:brimstone",
			"betterendforge:amber_moss",
			"betterendforge:amber_ore",
			"betterendforge:jungle_moss",
			"betterendforge:thallasium_ore",
			"betterendforge:rutiscus",
			"betterendforge:sangnum",
			"betterendforge:umbralith",
			"betterendforge:pallidium_full",
			"betterendforge:pallidium_heavy",
			"betterendforge:pallidium_thin",
			"betterendforge:pallidium_tiny"};
	
	
	public static ArrayList<Block> makeModBlockTag(final String name)
	{
		return new ArrayList<>();
	}
	
	public static ArrayList<Item> makeModItemTag(final String name)
	{
		return new ArrayList<>();
	}
	
	public static boolean validGenBlock(IBlockState block)
	{
		return END_GROUND.contains(block.getBlock()) || END_GROUND.contains(block.getBlock());
	}

	public static void initTags(){
		for(String s : ModTags.END_GROUND_STRS){
			ModTags.END_GROUND.add(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(s)));
		}

		for(String s : ModTags.GEN_TERRAIN_STRS){
			ModTags.GEN_TERRAIN.add(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(s)));
		}
	}
}
