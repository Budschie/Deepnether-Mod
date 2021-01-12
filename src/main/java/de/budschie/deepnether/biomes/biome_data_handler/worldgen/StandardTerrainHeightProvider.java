package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import de.budschie.deepnether.dimension.IMappingValueSupplier;
import net.minecraft.world.biome.provider.BiomeProvider;

public class StandardTerrainHeightProvider implements IMappingValueSupplier<Integer>
{
	int terrainHeight;
	
	public StandardTerrainHeightProvider(int terrainHeight)
	{
		this.terrainHeight = terrainHeight;
	}
	
	@Override
	public Integer get(BiomeProvider biomeProvider, int x, int z, long seed)
	{
		return terrainHeight;
	}
}
