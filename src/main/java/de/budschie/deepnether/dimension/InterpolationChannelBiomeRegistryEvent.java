package de.budschie.deepnether.dimension;

import net.minecraftforge.eventbus.api.Event;

public class InterpolationChannelBiomeRegistryEvent extends Event
{
	DeepnetherChunkGenerator chunkGenerator;
	
	/** This event is being fired after {@link InterpolationChannelRegistryEvent}. You should add your biome values and getters here. **/
	public InterpolationChannelBiomeRegistryEvent(DeepnetherChunkGenerator chunkGenerator)
	{
		this.chunkGenerator = chunkGenerator;
	}
	
	public DeepnetherChunkGenerator getChunkGenerator()
	{
		return chunkGenerator;
	}
	
	@Override
	public boolean isCancelable()
	{
		return false;
	}
}
