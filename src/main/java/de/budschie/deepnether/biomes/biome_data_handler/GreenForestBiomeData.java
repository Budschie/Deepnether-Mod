package de.budschie.deepnether.biomes.biome_data_handler;

import de.budschie.deepnether.biomes.biome_data_handler.worldgen.BiomeGeneratorBase;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.ClassicBiomeGenerator;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.GreenForestBiomeGenerator;
import de.budschie.deepnether.block.BlockInit;
import net.minecraft.block.Blocks;

public class GreenForestBiomeData implements IDeepnetherBiomeData
{
	GreenForestBiomeGenerator generator;
	
	public GreenForestBiomeData()
	{
		generator = new GreenForestBiomeGenerator(10, 2, 0, 40, BlockInit.HOT_STONE.getDefaultState());
	}

	@Override
	public BiomeGeneratorBase getBiomeGenerator()
	{
		return generator;
	}
}
