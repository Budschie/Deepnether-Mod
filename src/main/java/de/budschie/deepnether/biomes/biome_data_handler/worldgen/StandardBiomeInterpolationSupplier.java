package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import de.budschie.deepnether.dimension.IMappingValueSupplier;
import net.minecraft.world.biome.provider.BiomeProvider;

public class StandardBiomeInterpolationSupplier implements IMappingValueSupplier<WeightedBiomeData>
{

	@Override
	public WeightedBiomeData get(BiomeProvider biomeProvider, int x, int z, long seed)
	{
		return null;
	}

}
