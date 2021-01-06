package de.budschie.deepnether.biomes.biome_data_handler;

import de.budschie.deepnether.biomes.biome_data_handler.worldgen.BiomeGeneratorBase;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.ClassicBiomeGenerator;
import de.budschie.deepnether.block.BlockInit;
import net.minecraft.block.Blocks;

public class DeepnetherBiomeData implements IDeepnetherBiomeData
{
	ClassicBiomeGenerator generator;
	
	public DeepnetherBiomeData()
	{
		generator = new ClassicBiomeGenerator(10, 4, 5, 70, Blocks.LAVA.getDefaultState(), BlockInit.COMPRESSED_NETHERRACK.getDefaultState());
	}

	@Override
	public BiomeGeneratorBase getBiomeGenerator()
	{
		return generator;
	}
}
