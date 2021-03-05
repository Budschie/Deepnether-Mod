package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.Optional;

import net.minecraft.block.BlockState;

public interface ILavaGenerationInterface
{
	/** If this method returns {@link Optional#empty()}, this means that there is no transition between different biomes and their lava behaviours. If this, however, isn't empty, 
	 * we are trying to reduce the lave so that we eventually don't have lava at the end, to allow a seamless transition between lava and none-lava biomes. **/
	public Optional<BlockState> getFillerBlock();
}
