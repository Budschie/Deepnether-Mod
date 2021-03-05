package de.budschie.deepnether.block;

import de.budschie.deepnether.item.IDeepnetherFuel;
import de.budschie.deepnether.tileentities.RecipeEntry.FuelType;
import de.budschie.deepnether.util.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;

public class CompressedNetherrackBlockItem extends BlockItem implements IDeepnetherFuel
{
	public CompressedNetherrackBlockItem(Block blockIn)
	{
		super(blockIn, new Properties().group(ModItemGroups.MOD_BLOCKS));
	}

	@Override
	public FuelType getFuelType()
	{
		return FuelType.HEAT;
	}

	@Override
	public int getFuelTime()
	{
		return 225;
	}

	@Override
	public int getFuelSpeed()
	{
		return 1;
	}
}
