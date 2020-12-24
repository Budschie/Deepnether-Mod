package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.Optional;

import de.budschie.deepnether.dimension.DeepnetherBiomeProvider;
import net.kdotjpg.opensimplexnoise.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class ClassicBiomeGenerator extends BiomeGeneratorBase
{
	double size;
	int octaves;
	int terrainHeightMax;
	int terrainHeightMin;
	BlockState lavaBlock, groundBlock;
	
	Optional<BlockState> lavaSoil = Optional.empty();
	int lavaSoilThickness;
	
	public ClassicBiomeGenerator(double size, int octaves, int terrainHeightMin, int terrainHeightMax, BlockState lavaBlock, BlockState groundBlock)
	{
		this.size = size;
		this.octaves = octaves;
		
		this.lavaBlock = lavaBlock;
		this.groundBlock = groundBlock;
	}
	
	@Override
	public BlockState pickBlock(int x, int y, int z, double heightmap, long seed, DeepnetherBiomeProvider biomeProvider)
	{		
		int terrainHeight = (int) (heightmap * (terrainHeightMax-terrainHeightMin));
		
		if(y < terrainHeightMin)
		{
			return lavaBlock;
		}
		else if(!lavaSoil.isEmpty() && (y - lavaSoilThickness) < terrainHeightMin)
		{
			return lavaSoil.get();
		}
		else if(y <= (terrainHeight+terrainHeightMin))
		{
			return groundBlock;
		}
		else
			return Blocks.AIR.getDefaultState();
	}

	@Override
	public double getGroundHeight(long seed, int x, int z)
	{
		OpenSimplexNoise osn = new OpenSimplexNoise(seed);
		
		double currentSize = size;
		double transparency = 1;
		
		double currentValue = 0;
		
		for(int octaves = 0; octaves < this.octaves; octaves++)
		{
			currentValue += osn.eval(x * currentSize, z * currentSize) * transparency;
			
			transparency /= 2;
			currentSize /= 2;
		}

		return currentValue;
	}
	
	@Override
	public int getGenerationHeight()
	{
		return terrainHeightMax;
	}
	
	public void setLavaSoil(BlockState lavaSoil)
	{
		this.lavaSoil = Optional.of(lavaSoil);
	}
	
	public void setLavaSoilThickness(int lavaSoilThickness)
	{
		this.lavaSoilThickness = lavaSoilThickness;
	}
}
