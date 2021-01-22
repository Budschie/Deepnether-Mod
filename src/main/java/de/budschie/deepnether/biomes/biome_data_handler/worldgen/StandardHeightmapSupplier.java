package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.Optional;
import java.util.function.Function;

import de.budschie.deepnether.dimension.IMappingValueSupplier;
import net.kdotjpg.opensimplexnoise.OpenSimplexNoise;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

public class StandardHeightmapSupplier implements IMappingValueSupplier<Double>
{	
	OpenSimplexNoise osn;
	Optional<Long> currentSeed = Optional.empty();
	double size;
	double attenuation;
	int octaves;
	double initialTransparency;
	
	public StandardHeightmapSupplier(double size, int octaves, double initialTransparency, double attenuation)
	{
		this.size = size;
		this.octaves = octaves;
		this.initialTransparency = initialTransparency;
		this.attenuation = attenuation;
	}
	
	public StandardHeightmapSupplier(double size, int octaves, double attenuation)
	{
		this(size, octaves, .5, attenuation);
	}
	
	private void setOSN(long seed)
	{
		osn = new OpenSimplexNoise(seed);
		currentSeed = Optional.of(seed);
	}
	
	@Override
	public Double get(BiomeProvider biomeProvider, Function<BlockPos, Biome> biomeSupplier, int x, int z, long seed)
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
			if(octaves == 1)
				transparency = initialTransparency;
			
			currentValue += osn.eval(x * currentSize, z * currentSize) * transparency;

			transparency -= transparency / attenuation;
			currentSize *= 2;
		}

		if (currentValue < -1)
			currentValue = -1;
		else if (currentValue > 1)
			currentValue = 1;
		
		if(currentValue > 1)
			System.out.println("ALARM");

		return (currentValue + 1) * 0.5;
	}
}
