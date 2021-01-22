package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import de.budschie.deepnether.dimension.DeepnetherChunkGenerator;
import de.budschie.deepnether.dimension.InterpolationChannelBuffer;
import de.budschie.deepnether.util.MathUtil;
import net.minecraft.world.chunk.IChunk;

public class SoulDesertBiomeGenerator implements IBiomeGenerator
{
	@Override
	public void generate(int chunkStartX, int chunkStartZ, int localX, int localZ, IChunk chunk,
			DeepnetherChunkGenerator chunkGenerator, InterpolationChannelBuffer interpolationChannelBuffer)
	{
		int currentTerrainHeight = interpolationChannelBuffer.<Integer>getValue("terrainHeight")[localX][localZ];
		int minTerrainHeight = interpolationChannelBuffer.<Integer>getValue("minTerrainHeight")[localX][localZ];
		double currentHeightValue = interpolationChannelBuffer.<Double>getValue("heightmap")[localX][localZ];
		
		// Modify so that the minTerrainHeight applies
		// Remapping from 0 to 1 to x to 1
		currentHeightValue = MathUtil.linearInterpolation(minTerrainHeight / (double)currentTerrainHeight, 1, currentHeightValue);
				
		int definedTerrainHeight = (int) (currentHeightValue * currentTerrainHeight);
		
		for(int i = 0; i <= definedTerrainHeight; i++)
		{
			
		}
	}
}
