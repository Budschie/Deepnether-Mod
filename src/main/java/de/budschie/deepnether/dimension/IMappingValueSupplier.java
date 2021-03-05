package de.budschie.deepnether.dimension;

import java.util.function.Function;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

public interface IMappingValueSupplier<E>
{
	E get(BiomeProvider biomeProvider, Function<BlockPos, Biome> biomeSupplier, int x, int z, long seed);
}
