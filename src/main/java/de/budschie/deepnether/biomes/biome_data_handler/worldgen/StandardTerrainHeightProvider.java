package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.function.Function;

import de.budschie.deepnether.dimension.IMappingValueSupplier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

public class StandardTerrainHeightProvider implements IMappingValueSupplier<Integer>
{
	int terrainHeight;
	
	public StandardTerrainHeightProvider(int terrainHeight)
	{
		this.terrainHeight = terrainHeight;
	}
	
	@Override
	public Integer get(BiomeProvider biomeProvider, Function<BlockPos, Biome> biomeSupplier, int x, int z, long seed)
	{
		// System.err.println("BIOME WITH NAME " + BiomeUtil.getBiomeRS(biomeProvider.getNoiseBiome(x, 0, z), DeepnetherMain.server) + " got a height of " + terrainHeight);
		return terrainHeight;
	}
}
