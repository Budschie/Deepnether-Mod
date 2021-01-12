package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import de.budschie.deepnether.dimension.DeepnetherChunkGenerator;
import de.budschie.deepnether.dimension.InterpolationChannel;
import de.budschie.deepnether.dimension.InterpolationChannelBuffer;
import net.minecraft.world.chunk.IChunk;

public interface IBiomeGenerator
{
	public void generate(int chunkStartX, int chunkStartZ, int localX, int localZ, IChunk chunk, DeepnetherChunkGenerator chunkGenerator, InterpolationChannelBuffer interpolationChannelBuffer);
}
