package de.budschie.deepnether.biomes.biome_data_handler;

import java.util.HashMap;

import net.minecraft.util.ResourceLocation;

public class BiomeDataHandler
{
	private static HashMap<ResourceLocation, IDeepnetherBiomeData> map = new HashMap<>();
	
	public static void addBiomeData(ResourceLocation resourceLocation, IDeepnetherBiomeData data)
	{
		map.put(resourceLocation, data);
	}
	
	public static IDeepnetherBiomeData getBiomeData(ResourceLocation resourceLocation)
	{
		IDeepnetherBiomeData data = map.get(resourceLocation);
		
		if(data == null)
			System.err.println("There is no data for " + resourceLocation);
		
		return data;
	}
}
