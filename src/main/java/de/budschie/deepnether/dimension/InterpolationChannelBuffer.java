package de.budschie.deepnether.dimension;

import java.util.HashMap;
import java.util.function.Function;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.WorldGenRegion;

public class InterpolationChannelBuffer
{
	DeepnetherChunkGenerator deepnetherChunkGenerator;
	Biome[][] currentBiome;
	int startX, startZ;
	long seed;
	WorldGenRegion worldGenRegion;
	
	HashMap<String, Object> cache = new HashMap<>();
	
	public InterpolationChannelBuffer(DeepnetherChunkGenerator deepnetherChunkGenerator, Biome[][] currentBiome, int startX, int startZ, WorldGenRegion worldGenRegion)
	{
		this.deepnetherChunkGenerator = deepnetherChunkGenerator;
		this.currentBiome = currentBiome;
		this.startX = startX;
		this.startZ = startZ;
		this.seed = deepnetherChunkGenerator.seed;
		this.worldGenRegion = worldGenRegion;
	}
	
	@SuppressWarnings("unchecked")
	public <E> E[][] getValue(String name)
	{
		return (E[][]) cache.computeIfAbsent(name, (absentName) -> deepnetherChunkGenerator.getInterpolationChannel(absentName).getArea(seed, (blockPos) -> worldGenRegion.getBiomeManager().getBiome(blockPos), deepnetherChunkGenerator.getBiomeProvider(), startX, startZ, currentBiome));
	}
}
