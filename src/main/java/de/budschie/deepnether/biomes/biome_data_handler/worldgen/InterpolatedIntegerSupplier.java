package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.function.Function;

import de.budschie.deepnether.dimension.DeepnetherBiomeProvider;
import de.budschie.deepnether.dimension.IMappingValueSupplier;
import de.budschie.deepnether.util.MathUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

public class InterpolatedIntegerSupplier implements IMappingValueSupplier<Integer>
{
	int edgeValue, middleValue;
	
	public InterpolatedIntegerSupplier(int edgeValue, int middleValue)
	{
		this.edgeValue = edgeValue;
		this.middleValue = middleValue;
	}
	
	@Override
	public Integer get(BiomeProvider biomeProvider, Function<BlockPos, Biome> biomeSupplier, int x, int z, long seed)
	{
		double voronoi = ((DeepnetherBiomeProvider)biomeProvider).getVoronoi(x >> 2, 0, z >> 2);
		
		return (int) MathUtil.linearInterpolation(middleValue, edgeValue, voronoi);
	}
}
