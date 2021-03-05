package de.budschie.deepnether.tileentities;

import de.budschie.deepnether.item.IDeepnetherFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipeEntry
{
	public enum FuelType { HEAT, SOUL };
	int time, fuelConsume;
	Item itemIn;
	ItemStack itemOut;
	FuelType fuelType;
	
	/** Checks if the item matches the fuel **/
	public boolean isFuelValid(Item item)
	{
		if(item instanceof IDeepnetherFuel)
		{
			return ((IDeepnetherFuel)(item)).getFuelType() == this.fuelType;
		}
		return false;
	}
	
	public RecipeEntry(FuelType fuelType, int time, int fuelConsume, Item itemIn, ItemStack itemOut)
	{
		this.fuelType = fuelType;
		this.time = time;
		this.fuelConsume = fuelConsume;
		this.itemIn = itemIn;
		this.itemOut = itemOut;
	}
}
