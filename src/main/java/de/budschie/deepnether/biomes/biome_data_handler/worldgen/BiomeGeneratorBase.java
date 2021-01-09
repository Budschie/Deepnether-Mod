package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import de.budschie.deepnether.dimension.DeepnetherBiomeProvider;
import net.minecraft.block.BlockState;

/** TODO: Make thread-safe (with using a provider) **/
public abstract class BiomeGeneratorBase
{
	/** What block should be placed? **/
	public abstract BlockState pickBlock(int x, int y, int z, double heightmap, long seed, DeepnetherBiomeProvider biomeProvider);
	
	/** Is used for interpolation and heightmap **/
	public abstract double getGroundHeight(long seed, int x, int z);
	
	/** Should the heightmap of this object take count on ground interpolations?**/
	public boolean shouldInterpolate()
	{
		return true;
	}
	
	public int getGenerationHeight()
	{
		return 256;
	}
	
	/** Calculate things that are y-axis-independent! **/
	public void preprocess(int x, int z, double heightmap, long seed, DeepnetherBiomeProvider biomeProvider)
	{
		
	}
}
