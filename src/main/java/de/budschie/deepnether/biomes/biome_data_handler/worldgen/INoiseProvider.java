package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.function.Function;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

public interface INoiseProvider
{
	public Double getNoise(BiomeProvider biomeProvider, Function<BlockPos, Biome> biomeSupplier, int x, int z);
}
