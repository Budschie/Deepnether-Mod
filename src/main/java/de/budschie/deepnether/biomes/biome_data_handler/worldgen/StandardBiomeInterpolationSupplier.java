package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.function.Function;

import de.budschie.deepnether.dimension.IMappingValueSupplier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

public class StandardBiomeInterpolationSupplier implements IMappingValueSupplier<WeightedBiomeData>
{

	@Override
	public WeightedBiomeData get(BiomeProvider biomeProvider, Function<BlockPos, Biome> biomeSupplier, int x, int z, long seed)
	{
		return null;
	}

}
