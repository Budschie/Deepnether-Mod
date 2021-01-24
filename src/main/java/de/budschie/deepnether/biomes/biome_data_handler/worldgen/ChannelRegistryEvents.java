package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.HashMap;

import de.budschie.deepnether.dimension.IInterpolationApplier;
import de.budschie.deepnether.dimension.InterpolationChannel;
import de.budschie.deepnether.dimension.InterpolationChannelRegistryEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class ChannelRegistryEvents
{
	@SubscribeEvent
	public static void onRegisterDefaultChannels(InterpolationChannelRegistryEvent event)
	{
		// Dude this is gonna hurt my poor CPU a lot...
		
		event.getChunkGenerator().addInterpolationEntry(new InterpolationChannel<Double, Double>("heightmap", Double.class, 0.0, IInterpolationApplier.getSimpleApplier((inputArea, x, z) -> inputArea[x][z]), 0));
		event.getChunkGenerator().addInterpolationEntry(new InterpolationChannel<Integer, Integer>("terrainHeight", Integer.class, 0, IInterpolationApplier.getSimpleApplier((inputArea, x, z) -> 
		{
			//return (int) MathUtil.bilinearInterpolation(sample2x2(sampledArea, 0, 15), sample2x2(sampledArea, 15, 15), sample2x2(sampledArea, 0, 0), sample2x2(sampledArea, 15, 0), x / 16f, z / 16f);
			int added = 0;
			
			// sexy performance
			for(int xIn = 0; xIn < 20; xIn++)
			{
				for(int zIn = 0; zIn < 20; zIn++)
				{
					added += inputArea[x + xIn][z + zIn];
				}
			}
			
			return added / (20*20);
		}), 10));
		event.getChunkGenerator().addInterpolationEntry(new InterpolationChannel<Integer, Integer>("minTerrainHeight", Integer.class, 0, IInterpolationApplier.getSimpleApplier((inputArea, x, z) -> 
		{
			//return (int) MathUtil.bilinearInterpolation(sample2x2(sampledArea, 0, 15), sample2x2(sampledArea, 15, 15), sample2x2(sampledArea, 0, 0), sample2x2(sampledArea, 15, 0), x / 16f, z / 16f);
			int added = 0;
			
			// sexy performance
			for(int xIn = 0; xIn < 20; xIn++)
			{
				for(int zIn = 0; zIn < 20; zIn++)
				{
					added += inputArea[x + xIn][z + zIn];
				}
			}
			
			return added / (20*20);
		}), 10));
		event.getChunkGenerator().addInterpolationEntry(new InterpolationChannel<ResourceLocation, WeightedBiomeData>("nearbyBiomes", WeightedBiomeData.class, new ResourceLocation("yeetus"), IInterpolationApplier.getSimpleApplier((inputArea, x, z) -> 
		{
			HashMap<ResourceLocation, Integer> occurences = new HashMap<>();
			
			// sexy performance
			for(int xIn = 0; xIn < 20; xIn++)
			{
				for(int zIn = 0; zIn < 20; zIn++)
				{
					ResourceLocation currentRs = inputArea[x + xIn][z + zIn];
					occurences.computeIfPresent(currentRs, (rs, value) -> value + 1);
					occurences.computeIfAbsent(currentRs, (rs) -> 1);
				}
			}
			
			return new WeightedBiomeData(occurences);
		}), 10));
	}
}
