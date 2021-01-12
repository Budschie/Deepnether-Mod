package de.budschie.deepnether.dimension;

import net.minecraftforge.eventbus.api.Event;

public class InterpolationChannelRegistryEvent extends Event
{
	DeepnetherChunkGenerator chunkGenerator;
	
	/** This event is fired when the deepnether chunk generator is being built. You should register InterpolationChannels here. **/
	public InterpolationChannelRegistryEvent(DeepnetherChunkGenerator chunkGenerator)
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
