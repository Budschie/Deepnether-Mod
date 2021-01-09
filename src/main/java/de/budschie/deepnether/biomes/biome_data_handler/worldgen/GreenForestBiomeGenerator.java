package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.Optional;

import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.dimension.DeepnetherBiomeProvider;
import de.budschie.deepnether.main.DeepnetherMain;
import de.budschie.deepnether.main.References;
import de.budschie.deepnether.noise.VoronoiNoise;
import de.budschie.deepnether.util.BiomeUtil;
import de.budschie.deepnether.util.Util;
import net.kdotjpg.opensimplexnoise.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

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
		this.terrainHeightMin = terrainHeightMin;
		this.terrainHeightMax = terrainHeightMax;
		this.lavaBlock = lavaBlock;
	}
	
	BlockState underground = null;
	BlockState grass = null;
	
	double hillValue;
	
	@Override
	public void preprocess(int x, int z, double heightmap, long seed, DeepnetherBiomeProvider biomeProvider)
	{
		VoronoiNoise voronoiNoise = new VoronoiNoise(seed);
		double edgeDistance = voronoiNoise.voronoiNoise(x, z, biomeProvider.getBiomeScale(), false);
		
		Biome nearbyBiome = biomeProvider.getNoiseBiomeByNoise(voronoiNoise.voronoiNoise(x, seed, biomeProvider.getBiomeScale(), 1, true));
		
		System.out.println(nearbyBiome);
		
		System.out.println(BiomeUtil.getBiomeRS(nearbyBiome, DeepnetherMain.server));
		
		if(BiomeUtil.getBiomeRS(nearbyBiome, DeepnetherMain.server).equals(new ResourceLocation(References.MODID, "deepnether_biome")))
		{
			if(edgeDistance > .3)
			{
				underground = BlockInit.FERTILIUM.getDefaultState();
				grass = BlockInit.GREEN_FOREST_FERTILIUM_GRASS_BLOCK.getDefaultState();
			}
			else if(edgeDistance > .2)
			{
				underground = BlockInit.COMPRESSED_NETHERRACK.getDefaultState();
				grass = BlockInit.GREEN_FOREST_CNR_GRASS_BLOCK.getDefaultState();
			}
			else
			{
				underground = BlockInit.COMPRESSED_NETHERRACK.getDefaultState();
				grass = BlockInit.COMPRESSED_NETHERRACK.getDefaultState();
			}
		}
		else
		{
			underground = BlockInit.FERTILIUM.getDefaultState();
			grass = BlockInit.GREEN_FOREST_FERTILIUM_GRASS_BLOCK.getDefaultState();
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
		
		for(int octaves = 0; octaves < 8; octaves++)
		{
			currentValue += osn.eval(x * currentSize, z * currentSize) * transparency;
			
			transparency *= 0.5;
			currentSize *= 2;
		}
		
		if(currentValue < -1)
			currentValue = -1;
		else if(currentValue > 1)
			currentValue = 1;
		
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
