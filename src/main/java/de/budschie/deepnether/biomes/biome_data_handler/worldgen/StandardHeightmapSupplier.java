package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.Optional;

import de.budschie.deepnether.dimension.IMappingValueSupplier;
import net.kdotjpg.opensimplexnoise.OpenSimplexNoise;
import net.minecraft.world.biome.provider.BiomeProvider;

public class StandardHeightmapSupplier implements IMappingValueSupplier<Double>
{	
	OpenSimplexNoise osn;
	Optional<Long> currentSeed = Optional.empty();
	double size;
	int octaves;
	
	public StandardHeightmapSupplier(double size, int octaves)
	{
		this.size = size;
		this.octaves = octaves;
	}
	
	private void setOSN(long seed)
	{
		osn = new OpenSimplexNoise(seed);
		currentSeed = Optional.of(seed);
	}
	
	@Override
	public Double get(BiomeProvider biomeProvider, int x, int z, long seed)
	{
		if(currentSeed.isEmpty() || currentSeed.get() != seed)
		{
			setOSN(seed);
		}

		double currentSize = size;
		double transparency = 1;

		double currentValue = 0;

		for (int octaves = 0; octaves < this.octaves; octaves++)
		{
			currentValue += osn.eval(x * currentSize, z * currentSize) * transparency;

			transparency *= 0.5;
			currentSize *= 2;
		}

		if (currentValue < -1)
			currentValue = -1;
		else if (currentValue > 1)
			currentValue = 1;

		return (currentValue + 1) * 0.5;
	}
}
