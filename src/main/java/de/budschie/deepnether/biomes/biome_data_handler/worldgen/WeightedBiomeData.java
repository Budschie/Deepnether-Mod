package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import net.minecraft.util.ResourceLocation;

public class WeightedBiomeData
{
	private HashMap<ResourceLocation, Double> weights;
	
	public WeightedBiomeData(HashMap<ResourceLocation, Integer> occurences)
	{
		weights = new HashMap<>();
		
		AtomicInteger allWeightsAdded = new AtomicInteger();
		
		occurences.values().forEach((value) -> allWeightsAdded.addAndGet(value));
		
		for(ResourceLocation rs : occurences.keySet())
		{
			int currentWeight = occurences.get(rs);
			
			weights.put(rs, (double)currentWeight / allWeightsAdded.get());
		}
	}
		
	public HashMap<ResourceLocation, Double> getWeights()
	{
		return weights;
	}
}
