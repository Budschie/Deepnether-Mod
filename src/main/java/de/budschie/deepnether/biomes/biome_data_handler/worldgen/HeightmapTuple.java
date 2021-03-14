package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.function.Function;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

public class HeightmapTuple
{
	BiomeProvider biomeProvider;
	Function<BlockPos, Biome> biomeSupplier; 
	int x;
	int z;
	
	public HeightmapTuple(BiomeProvider biomeProvider, Function<BlockPos, Biome> biomeSupplier, int x, int z)
	{
		this.biomeProvider = biomeProvider;
		this.biomeSupplier = biomeSupplier;
		this.x = x;
		this.z = z;
	}
	
	public BiomeProvider getBiomeProvider()
	{
		return biomeProvider;
	}
	
	public Function<BlockPos, Biome> getBiomeSupplier()
	{
		return biomeSupplier;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getZ()
	{
		return z;
	}	
}
