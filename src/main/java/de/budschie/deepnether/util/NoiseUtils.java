package de.budschie.deepnether.util;

import java.util.function.BiFunction;

public class NoiseUtils
{
	public static double sampleNoiseAdvanced(BiFunction<Double, Double, Double> noiseFunction, double x, double y, double size, int octaves, double attenuation, double initialTransparency)
	{
		double currentSize = size;
		double transparency = 1;

		double currentValue = 0;

		for (int o = 0; o < octaves; o++)
		{
			if(o == 1)
				transparency = initialTransparency;
			
			currentValue += noiseFunction.apply(x * currentSize, y * currentSize) * transparency;

			transparency -= transparency / attenuation;
			currentSize *= 2;
		}
		
		return currentValue;
	}
	
	public static double sampleNormNoiseAdvanced(BiFunction<Double, Double, Double> noiseFunction, double x, double y, double size, int octaves, double attenuation, double initialTransparency)
	{
		return normalizeNoise(sampleNoiseAdvanced(noiseFunction, x, y, size, octaves, attenuation, initialTransparency));
	}
	
	public static double normalizeNoise(double input)
	{
		return (input + 1) / 2;
	}
}
