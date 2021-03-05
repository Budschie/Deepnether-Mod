package de.budschie.deepnether.dimension;

import net.minecraftforge.eventbus.api.Event;

public class DeepnetherChunkGenerationInitEvent extends Event
{
	DeepnetherChunkGenerator chunkGenerator;
	long seed;
	
	/** This event is fired when the deepnether chunk generator is being built. You should register InterpolationChannels here. **/
	public DeepnetherChunkGenerationInitEvent(DeepnetherChunkGenerator chunkGenerator)
	{
		this.chunkGenerator = chunkGenerator;
		this.seed = chunkGenerator.seed;
	}
	
	public DeepnetherChunkGenerator getChunkGenerator()
	{
		return chunkGenerator;
	}
	
	public long getSeed()
	{
		return seed;
	}
	
	@Override
	public boolean isCancelable()
	{
		return false;
	}
}
