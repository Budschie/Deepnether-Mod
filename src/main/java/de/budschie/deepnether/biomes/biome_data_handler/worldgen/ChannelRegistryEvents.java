package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

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
		event.getChunkGenerator().addInterpolationEntry(new InterpolationChannel<Double>("heightmap", 0.0, (upperLeft, upperRight, bottomLeft, bottomRight) -> MathUtil.bilinearInterpolation(0, 0, 0, 0, .5, .5), 10));
		event.getChunkGenerator().addInterpolationEntry(new InterpolationChannel<Integer>("terrainHeight", 0, (upperLeft, upperRight, bottomLeft, bottomRight) -> (int)MathUtil.bilinearInterpolation(upperLeft, upperRight, bottomLeft, bottomRight, .5f, .5f), 5));
	}
}
