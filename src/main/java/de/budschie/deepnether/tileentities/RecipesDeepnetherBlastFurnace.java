package de.budschie.deepnether.tileentities;

import java.util.HashMap;

import de.budschie.deepnether.item.IDeepnetherFuel;
import de.budschie.deepnether.main.References;
import de.budschie.deepnether.tileentities.RecipeEntry.FuelType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.registries.ObjectHolder;

public class RecipesDeepnetherBlastFurnace
{
	static HashMap<FuelType, HashMap<String, RecipeEntry>> recipes = new HashMap<>();
	
	@ObjectHolder(value = References.MODID + ":dylithite_ore")
	private static Item dylithite_ore = null;
	
	@ObjectHolder(value = References.MODID + ":dylithite_ingot")
	private static Item dylithite_ingot = null;
	
	public static void registerModRecipes()
	{
		register("get_dylithite_ingot", new RecipeEntry(FuelType.HEAT, 40, 1, dylithite_ore, new ItemStack(new IItemProvider()
		{
			@Override
			public Item asItem()
			{
				return dylithite_ingot;
			}
		}, 1)));
	}
	
	public static void register(String id, RecipeEntry entry)
	{
		if(!recipes.containsKey(entry.fuelType))
		{
			recipes.put(entry.fuelType, new HashMap<String, RecipeEntry>());
		}
		else if(recipes.get(entry.fuelType).containsKey(id))
			throw new IllegalArgumentException("ID for recipe already present: " + id + ". This is an programming error, please contact the developer.");
		
		recipes.get(entry.fuelType).put(id, entry);
	}
	
	public static RecipeEntry getRecipeByIDAndFuel(String id, FuelType fuelType)
	{
		return recipes.get(fuelType).get(id);
	}
	
	public static String getIDFromRecipe(RecipeEntry entry)
	{
		HashMap<String, RecipeEntry> map = recipes.get(entry.fuelType);
		
		for(String str : map.keySet())
		{
			RecipeEntry itRecipe = map.get(str);
			
			if(itRecipe == entry)
			{
				return str;
			}
		}
		
		return null;
	}
	
	public static RecipeEntry getRecipeByItems(Item itemIn, IDeepnetherFuel fuel)
	{
		HashMap<String, RecipeEntry> hashMap = recipes.get(fuel.getFuelType());
		
		if(hashMap != null)
		{
			for(RecipeEntry entry : hashMap.values())
			{
				if(entry.itemIn == itemIn)
				{
					return entry;
				}
			}
		}
		
		return null;
	}
	
	public static RecipeEntry getRecipeByItems(Item itemIn, IDeepnetherFuel fuel, ItemStack itemStackOutputSlot)
	{
		HashMap<String, RecipeEntry> hashMap = recipes.get(fuel.getFuelType());
		
		if(hashMap != null)
		{
			for(RecipeEntry entry : hashMap.values())
			{
				if(entry.itemIn == itemIn)
				{
					if((itemStackOutputSlot.getItem() == entry.itemOut.getItem()) && ((itemStackOutputSlot.getCount()+entry.itemOut.getCount()) <= entry.itemOut.getMaxStackSize()))
					{
						return entry;
					}
				}
			}
		}
		
		return null;
	}
}
