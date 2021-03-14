package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.budschie.deepnether.biomes.biome_data_handler.BiomeDataHandler;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.GreenForestBiomeGenerator.WeightedBlockState;
import de.budschie.deepnether.dimension.DeepnetherChunkGenerator;
import de.budschie.deepnether.dimension.InterpolationChannel;
import de.budschie.deepnether.dimension.InterpolationChannelBuffer;
import de.budschie.deepnether.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.IChunk;

public interface IBiomeGenerator
{
	public void generate(int chunkStartX, int chunkStartZ, int localX, int localZ, IChunk chunk, DeepnetherChunkGenerator chunkGenerator, InterpolationChannelBuffer interpolationChannelBuffer);
	
	/** This method is used to determine which block should be interpolated. **/
	public BlockState getFillerBlock();
	
	/** This returns the height of the sea level to interpolate between the sea levels. If this is empty, we are trying to interpolate to the standard height of the current terrain. **/
	public Optional<Integer> getSeaLevelHeight();
	
	/** This method is used to retrieve custom values. **/
	public default <T> T getValue(Class<T> clazz, String name) { return null; }
	
	/** This method is used to retrive custom values. It also accepts paramters, like, for example, block positions. **/
	public default <T, A> T getParamValue(Class<T> clazz, String name, A args) { return null; }
	
	public default Pair<Float, List<WeightedBlockState>> getLaveInterpolationData(WeightedBiomeData data, int currentTerrainHeight)
	{
		ArrayList<WeightedBlockState> entries = new ArrayList<>();
		float allHeights = 0;
		
		for(ResourceLocation resourceLocation : data.getWeights().keySet())
		{
			Double weight = data.getWeights().get(resourceLocation);
			
			IBiomeGenerator biomeGenerator = BiomeDataHandler.getBiomeData(resourceLocation).getBiomeGenerator();
			allHeights += (biomeGenerator.getSeaLevelHeight().orElse(currentTerrainHeight)) * weight;
			entries.add(new WeightedBlockState((int) (weight * 1000), biomeGenerator.getFillerBlock()));
		}
		
		return new Pair<Float, List<WeightedBlockState>>(allHeights, entries);
	}
}
