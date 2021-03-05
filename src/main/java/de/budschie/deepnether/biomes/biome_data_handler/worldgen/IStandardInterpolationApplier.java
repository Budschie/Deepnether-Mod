package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

public interface IStandardInterpolationApplier<I, O>
{
	O apply(I[][] sampledArea, int currentX, int currentZ);
}
