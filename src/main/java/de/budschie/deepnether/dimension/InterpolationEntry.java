package de.budschie.deepnether.dimension;

import java.util.function.Function;

import net.minecraft.world.biome.Biome;

public class InterpolationEntry<E>
{
	Function<Biome, E> valueSupplier;
	IInterpolationApplier<E> interpolationFunction;
	
	public InterpolationEntry(Function<Biome, E> valueSupplier, IInterpolationApplier<E> interpolationFunction)
	{
		this.valueSupplier = valueSupplier;
		this.interpolationFunction = interpolationFunction;
	}

	public Function<Biome, E> getValueSupplier()
	{
		return valueSupplier;
	}
	
	public IInterpolationApplier<E> getInterpolationFunction()
	{
		return interpolationFunction;
	}
}
