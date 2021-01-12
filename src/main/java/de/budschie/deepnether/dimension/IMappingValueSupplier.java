package de.budschie.deepnether.dimension;

import net.minecraft.world.biome.provider.BiomeProvider;

public interface IMappingValueSupplier<E>
{
	E get(BiomeProvider biomeProvider, int x, int z, long seed);
}
