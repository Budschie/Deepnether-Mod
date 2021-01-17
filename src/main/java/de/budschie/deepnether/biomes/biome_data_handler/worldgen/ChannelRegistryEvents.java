package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.stream.Stream;

import de.budschie.deepnether.dimension.IInterpolationApplier;
import de.budschie.deepnether.dimension.InterpolationChannel;
import de.budschie.deepnether.dimension.InterpolationChannelRegistryEvent;
import de.budschie.deepnether.util.MathUtil;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class ChannelRegistryEvents
{
	@SubscribeEvent
	public static void onRegisterDefaultChannels(InterpolationChannelRegistryEvent event)
	{
		event.getChunkGenerator().addInterpolationEntry(new InterpolationChannel<Double>("heightmap", 0.0, (sampledArea, x, z) -> sampledArea[x][z], 0));
		event.getChunkGenerator().addInterpolationEntry(new InterpolationChannel<Integer>("terrainHeight", 0, (sampledArea, x, z) -> 
		{
			return (int) MathUtil.bilinearInterpolation(sample2x2(sampledArea, 0, 15), sample2x2(sampledArea, 15, 15), sample2x2(sampledArea, 0, 0), sample2x2(sampledArea, 15, 0), x / 16f, z / 16f);
		}, 1));
	}
	
	private static int sample2x2(Integer[][] heights, int xCoordBottomLeft, int yCoordBottomLeft)
	{
		return (heights[xCoordBottomLeft][yCoordBottomLeft] + heights[xCoordBottomLeft + 1][yCoordBottomLeft] + heights[xCoordBottomLeft][yCoordBottomLeft + 1] + heights[xCoordBottomLeft + 1][yCoordBottomLeft + 1]) / 4;
	}
}
