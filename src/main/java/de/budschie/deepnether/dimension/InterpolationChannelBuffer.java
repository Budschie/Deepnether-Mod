package de.budschie.deepnether.dimension;

import java.util.HashMap;

import net.minecraft.world.biome.Biome;

public class InterpolationChannelBuffer
{
	DeepnetherChunkGenerator deepnetherChunkGenerator;
	Biome[][] currentBiome;
	int startX, startZ;
	long seed;
	
	HashMap<String, Object> cache = new HashMap<>();
	
	public InterpolationChannelBuffer(DeepnetherChunkGenerator deepnetherChunkGenerator, Biome[][] currentBiome, int startX, int startZ)
	{
		this.deepnetherChunkGenerator = deepnetherChunkGenerator;
		this.currentBiome = currentBiome;
		this.startX = startX;
		this.startZ = startZ;
		this.seed = deepnetherChunkGenerator.seed;
	}
	
	@SuppressWarnings("unchecked")
	public <E> E[][] getValue(String name)
	{
		return (E[][]) cache.computeIfAbsent(name, (absentName) -> deepnetherChunkGenerator.getInterpolationChannel(absentName).getArea(seed, deepnetherChunkGenerator.getBiomeProvider(), startX, startZ, currentBiome));
	}
}
