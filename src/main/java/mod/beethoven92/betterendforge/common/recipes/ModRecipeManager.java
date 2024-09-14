package mod.beethoven92.betterendforge.common.recipes;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class ModRecipeManager 
{
	public static final Map<Class, Map<ResourceLocation, IRecipe>> RECIPES = Maps.newHashMap();

	public static void addRecipe(Class type, IRecipe recipe)
	{
		Map<ResourceLocation, IRecipe> list = RECIPES.get(type);
		if (list == null) 
		{
			list = Maps.newHashMap();
			RECIPES.put(type, list);
		}
		list.put(recipe.getRegistryName(), recipe);
	}
	
	public static Map<Class, Map<ResourceLocation, IRecipe>> getMap(Map<Class, Map<ResourceLocation, IRecipe>> recipes)
	{
		Map<Class, Map<ResourceLocation, IRecipe>> result = Maps.newHashMap();

		for (Class type : recipes.keySet())
		{
			Map<ResourceLocation, IRecipe> typeList = Maps.newHashMap();
			typeList.putAll(recipes.get(type));
			result.put(type, typeList);
		}

		for (Class type : RECIPES.keySet())
		{
			Map<ResourceLocation, IRecipe> list = RECIPES.get(type);
			if (list != null)
			{
				Map<ResourceLocation, IRecipe> typeList = result.get(type);
				if (typeList == null) 
				{
					typeList = Maps.newHashMap();
					result.put(type, typeList);
				}
				for (Entry<ResourceLocation, IRecipe> entry : list.entrySet()) {
					ResourceLocation id = entry.getKey();
					if (!typeList.containsKey(id))
						typeList.put(id, entry.getValue());
				}
			}
		}

		return result;
	}
}
