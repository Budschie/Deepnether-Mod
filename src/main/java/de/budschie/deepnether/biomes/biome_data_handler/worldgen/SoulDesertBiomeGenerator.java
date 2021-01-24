package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.Random;

import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.dimension.DeepnetherChunkGenerator;
import de.budschie.deepnether.dimension.InterpolationChannelBuffer;
import de.budschie.deepnether.main.References;
import de.budschie.deepnether.noise.VoronoiNoise;
import de.budschie.deepnether.util.MathUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunk;

public class SoulDesertBiomeGenerator implements IBiomeGenerator
{
	public static final int MAX_ISLAND_HEIGHT = 120;
	public static final int MIN_ISLAND_HEIGHT = 90;
	public static final double ISLAND_SCALING = .05;
	public static final double ISLAND_SIZE = .05;
	public static final int ISLAND_CHANCE = 10;
	
	@Override
	public void generate(int chunkStartX, int chunkStartZ, int localX, int localZ, IChunk chunk,
			DeepnetherChunkGenerator chunkGenerator, InterpolationChannelBuffer interpolationChannelBuffer)
	{
		int currentTerrainHeight = interpolationChannelBuffer.<Integer>getValue("terrainHeight")[localX][localZ];
		int minTerrainHeight = interpolationChannelBuffer.<Integer>getValue("minTerrainHeight")[localX][localZ];
		double currentHeightValue = interpolationChannelBuffer.<Double>getValue("heightmap")[localX][localZ];
		
		WeightedBiomeData biomeData = interpolationChannelBuffer.<WeightedBiomeData>getValue("nearbyBiomes")[localX][localZ];
		
		// Modify so that the minTerrainHeight applies
		// Remapping from 0 to 1 to x to 1
		currentHeightValue = MathUtil.linearInterpolation(minTerrainHeight / (double)currentTerrainHeight, 1, currentHeightValue);
				
		int definedTerrainHeight = (int) (currentHeightValue * currentTerrainHeight);
		
		for(int y = 0; y <= definedTerrainHeight; y++)
		{
			if(y <= definedTerrainHeight)
				chunk.setBlockState(new BlockPos(localX, y, localZ), BlockInit.SOUL_DUST.getDefaultState(), false);
		}
		
		int biomeId = chunkGenerator.getBiomeProvider().getBiomeId(chunkStartX + localX, 0, chunkStartZ + localZ);
				
		VoronoiNoise noise = new VoronoiNoise(biomeId + 5);
		
		boolean hasIsland = (new Random((long) (noise.voronoiNoise(chunkStartX + localX, chunkStartZ + localZ, ISLAND_SIZE, true) * 57892)).nextInt(ISLAND_CHANCE) == 0);
		double sampledHeightmap = (hasIsland ? noise.voronoiNoise(chunkStartX + localX, chunkStartZ + localZ, ISLAND_SCALING, false) : 0);
		
		sampledHeightmap *= biomeData.getWeights().get(new ResourceLocation(References.MODID, "soul_desert_biome"));
		
		int totalValue = (int) ((sampledHeightmap * (MAX_ISLAND_HEIGHT - MIN_ISLAND_HEIGHT))) - 1;
		
		for(int y = 0; y <= totalValue; y++)
		{
			int currentY = y + MIN_ISLAND_HEIGHT;
			
			if(y == totalValue)
			{
				chunk.setBlockState(new BlockPos(localX, currentY, localZ), BlockInit.NETHER_DUST_GRASS_BLOCK.getDefaultState(), false);
				
				boolean placeGrass = new Random(Integer.hashCode(currentY * localX * localZ << 2)).nextInt(6) == 0;
				
				if(placeGrass)
					chunk.setBlockState(new BlockPos(localX, currentY + 1, localZ), BlockInit.NETHER_DUST_GRASS.getDefaultState(), false);
			}
			else if((y + 3) >= totalValue)
			{
				chunk.setBlockState(new BlockPos(localX, currentY, localZ), BlockInit.SOUL_DUST.getDefaultState(), false);
			}
			else
			{
				chunk.setBlockState(new BlockPos(localX, currentY, localZ), BlockInit.COMPRESSED_NETHERRACK.getDefaultState(), false);
			}
		}
	}
}
