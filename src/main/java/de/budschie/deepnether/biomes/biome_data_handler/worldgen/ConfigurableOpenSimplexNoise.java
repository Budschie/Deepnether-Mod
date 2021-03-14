package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.function.Function;

import de.budschie.deepnether.util.NoiseUtils;
import net.kdotjpg.opensimplexnoise.OpenSimplexNoise;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

public class ConfigurableOpenSimplexNoise implements INoiseProvider
{	
	OpenSimplexNoise osn;
	long seed;
	double size;
	double attenuation;
	int octaves;
	double initialTransparency;
	
	public ConfigurableOpenSimplexNoise(long seed, double size, int octaves, double initialTransparency, double attenuation)
	{
		this.size = size;
		this.octaves = octaves;
		this.initialTransparency = initialTransparency;
		this.attenuation = attenuation;
		this.seed = seed;
		this.osn = new OpenSimplexNoise(seed);
	}
	
	public ConfigurableOpenSimplexNoise(long seed, double size, int octaves, double attenuation)
	{
		this(seed, size, octaves, .5, attenuation);
	}
	
	@Override
	public Double getNoise(BiomeProvider biomeProvider, Function<BlockPos, Biome> biomeSupplier, int x, int z)
	{
		return (NoiseUtils.sampleNoiseAdvanced((sampleX, sampleY) -> osn.eval(sampleX, sampleY), x, z, size, octaves, attenuation, initialTransparency) + 1) * 0.5;
	}
}
