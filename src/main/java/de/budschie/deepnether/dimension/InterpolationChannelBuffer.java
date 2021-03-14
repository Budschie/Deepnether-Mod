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
	Function<BlockPos, Biome> biomeFunction;
	
	HashMap<String, Object> cache = new HashMap<>();
	
	public InterpolationChannelBuffer(DeepnetherChunkGenerator deepnetherChunkGenerator, Biome[][] currentBiome, int startX, int startZ, Function<BlockPos, Biome> biomeFunction)
	{
		this.deepnetherChunkGenerator = deepnetherChunkGenerator;
		this.currentBiome = currentBiome;
		this.startX = startX;
		this.startZ = startZ;
		this.seed = deepnetherChunkGenerator.seed;
		this.biomeFunction = biomeFunction;
	}
	
	@SuppressWarnings("unchecked")
	public <E> E[][] getValue(String name)
	{
		return (E[][]) cache.computeIfAbsent(name, (absentName) -> deepnetherChunkGenerator.getInterpolationChannel(absentName).getArea(seed, (blockPos) -> biomeFunction.apply(blockPos), deepnetherChunkGenerator.getBiomeProvider(), startX, startZ, currentBiome));
	}
	
	public long getSeed()
	{
		return seed;
	}
	
	public Function<BlockPos, Biome> getBiomeFunction()
	{
		return biomeFunction;
	}
}
