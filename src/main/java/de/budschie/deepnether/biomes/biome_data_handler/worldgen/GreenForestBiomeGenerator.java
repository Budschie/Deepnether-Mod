package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.Optional;
import java.util.Random;

import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.dimension.DeepnetherBiomeProvider;
import de.budschie.deepnether.noise.VoronoiNoise;
import net.kdotjpg.opensimplexnoise.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class GreenForestBiomeGenerator extends BiomeGeneratorBase
{
	double size;
	int octaves;
	int terrainHeightMax;
	int terrainHeightMin;
	BlockState lavaBlock;
	
	Optional<BlockState> lavaSoil = Optional.empty();
	int lavaSoilThickness;
	
	public GreenForestBiomeGenerator(double size, int octaves, int terrainHeightMin, int terrainHeightMax, BlockState lavaBlock)
	{
		this.size = size;
		this.octaves = octaves;
		
		this.lavaBlock = lavaBlock;
	}
	
	BlockState underground = null;
	BlockState grass = null;
	
	double hillValue;
	
	@Override
	public void preprocess(int x, int z, double heightmap, long seed, DeepnetherBiomeProvider biomeProvider)
	{
		VoronoiNoise voronoiNoise = new VoronoiNoise(seed);
		double biomeVal = Math.min(0.0001, voronoiNoise.voronoiNoise(x, z, biomeProvider.getBiomeScale(), false));
		
		int biomeID = biomeProvider.getBiomeId(x, 0, z);
		Random rand = new Random(biomeID);
		if(rand.nextInt(2) == 1)
			hillValue = (rand.nextInt(100)/50d) * biomeVal;
		else
			hillValue = 0;
		
		double blendingRadiusCNRToFertile = rand.nextInt(10)/500.0d;
		
		double blendingProgress = Math.min(1.2d, blendingRadiusCNRToFertile/biomeVal);
		
		Random rand2 = new Random(x+z+seed);
		
		if((rand2.nextInt(500)/500d) < blendingProgress)
		{
			underground = BlockInit.FERTILIUM.getDefaultState();
			grass = BlockInit.GREEN_FOREST_FERTILIUM_GRASS_BLOCK.getDefaultState();
		}
		else
		{
			underground = BlockInit.COMPRESSED_NETHERRACK.getDefaultState();
			grass = BlockInit.GREEN_FOREST_CNR_GRASS_BLOCK.getDefaultState();
		}
	}
	
	@Override
	public BlockState pickBlock(int x, int y, int z, double heightmap, long seed, DeepnetherBiomeProvider biomeProvider)
	{	
		int terrainHeight = (int) ((heightmap + hillValue) * (terrainHeightMax-terrainHeightMin));
		
		if(y < terrainHeightMin)
		{
			return lavaBlock;
		}
		else if(!lavaSoil.isEmpty() && (y - lavaSoilThickness) < terrainHeightMin)
		{
			return lavaSoil.get();
		}
		else if(y < (terrainHeight+terrainHeightMin))
		{
			return underground;
		}
		else if(y == (terrainHeight+terrainHeightMin))
		{
			return grass;
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
