package de.budschie.deepnether.dimension;

import net.minecraft.world.chunk.IChunk;

public interface IHeightmapProvider
{
	public static final int NOOP_INT = -65;
	
	int getHeight(String channel, int chunkStartX, int chunkStartZ, int localX, int localZ, DeepnetherChunkGenerator chunkGenerator, InterpolationChannelBuffer interpolationChannelBuffer);
}
