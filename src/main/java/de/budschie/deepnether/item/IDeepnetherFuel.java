package de.budschie.deepnether.item;

import de.budschie.deepnether.tileentities.RecipeEntry.FuelType;

public interface IDeepnetherFuel
{
	public FuelType getFuelType();
	public int getFuelTime();
	public int getFuelSpeed();
}