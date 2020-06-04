package de.budschie.deepnether.dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import de.budschie.deepnether.biomes.DeepnetherBiome;
import de.budschie.deepnether.biomes.DeepnetherBiomeBase;
import de.budschie.deepnether.biomes.FloatingIslandsBiome;
import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.worldgen.structureSaving.IHasSpawnList;
import de.budschie.deepnether.worldgen.structureSaving.StructureData;
import de.budschie.deepnether.worldgen.structureSaving.StructureDataHandler;
import net.kdotjpg.opensimplexnoise.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.audio.SoundEngine;
import net.minecraft.client.audio.SoundSystem;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.dimension.NetherDimension;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.GenerationStage.Carving;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public class DeepnetherChunkGenerator extends ChunkGenerator<GenerationSettings>
{
	OpenSimplexNoise noise;
	
	public static final int cloudMinHeigth = 50;
	public static final int stretch = 30;
	public static final float clipping = 0.75f;
	public static final int yMax = 127;
	public static final int yMaxTerrain = 50;
	public static final double yStalactitesStretch = 150;
	public static final int islandRadius = 10;
	public static final float FEATURE_SIZE = 75;
	
	public DeepnetherChunkGenerator(IWorld worldIn, BiomeProvider biomeProviderIn)
	{
		super(worldIn, biomeProviderIn, new GenerationSettings());
		Random rand = new Random(seed);
		noise = new OpenSimplexNoise(world.getSeed());
	}
	
	@Override
	public void func_225550_a_(BiomeManager p_225550_1_, IChunk p_225550_2_, Carving p_225550_3_)
	{
		super.func_225550_a_(p_225550_1_, p_225550_2_, p_225550_3_);
	}
	
	
	
	@Override
	public DeepnetherBiomeProvider getBiomeProvider()
	{
		return (DeepnetherBiomeProvider) super.getBiomeProvider();
	}
	
	@Override
	public boolean hasStructure(Biome biomeIn, Structure<? extends IFeatureConfig> structureIn)
	{
		return false;
	}
	
	@Override
	public BlockPos findNearestStructure(World worldIn, String name, BlockPos pos, int radius, boolean p_211403_5_)
	{
		return super.findNearestStructure(worldIn, name, pos, radius, p_211403_5_);
	}
	
	@Override
	protected Biome getBiome(BiomeManager biomeManagerIn, BlockPos posIn)
	{
		return this.getBiomeProvider().getBiome(posIn.getX(), posIn.getZ());
	}
	
	@Override
	public int getGroundHeight()
	{
		return 60;
	}
	
	@Override
	public void generateBiomes(IChunk chunkIn)
	{
		((ChunkPrimer)chunkIn).func_225548_a_(new BiomeContainer(((DeepnetherBiomeProvider)biomeProvider).getBiomesAsArray(chunkIn.getPos().getXStart(), chunkIn.getPos().getZStart(), 16, 16)));
	}

	@Override
	public void makeBase(IWorld worldIn, IChunk chunkIn)
	{
		ChunkPrimer primer = (ChunkPrimer) chunkIn;
		DeepnetherBiomeBase[][] biomes = this.getBiomeProvider().getBiomesDeepnether(chunkIn.getPos().x*16, chunkIn.getPos().z*16, 16, 16);
		buildSurface(primer, chunkIn.getPos().x, chunkIn.getPos().z, this.getBiomeProvider().getTopBlocks(chunkIn.getPos().x*16, chunkIn.getPos().z*16, 16, 16), this.getBiomeProvider().getLavaBlocks(chunkIn.getPos().x*16, chunkIn.getPos().z*16, 16, 16), biomes);
		buildCaves(primer, chunkIn.getPos().x, chunkIn.getPos().z);
		buildStalactites(primer, chunkIn.getPos().x, chunkIn.getPos().z);
		buildIslands(primer, chunkIn.getPos().x, chunkIn.getPos().z, this.getBiomeProvider().getBiomesAsArray(chunkIn.getPos().x*16, chunkIn.getPos().z*16, 16, 16));
	}

	@Override
	public int func_222529_a(int p_222529_1_, int p_222529_2_, Type heightmapType)
	{
		return 0;
	}
	
	@Override
	public int getSeaLevel()
	{
		return 10;
	}
	
	public void buildSurface(ChunkPrimer primer, int xIn, int zIn, BlockState[][] topBlocks, BlockState[][] lavaBlocks, DeepnetherBiomeBase[][] biomes)
	{
		int maxTerr[][] = ((DeepnetherBiomeProvider)this.getBiomeProvider()).getInterpolatedHeightMap((xIn * 16), (zIn * 16), 16, 16, 20);
		float fSizeClouds = FEATURE_SIZE * 2;
		float mixAmount = 1.0f;
		float pValClouds[][] = new float[16][16];
		
		for(int x = 0; x < 16; x++)
		{
			for(int z = 0; z < 16; z++)
			{
				pValClouds[x][z] = 0;
			}
		}
		
		for(int octaves = 0; octaves < 2; octaves++)
		{
			fSizeClouds -= fSizeClouds / 2.0D;
		for(int x = 0; x < 16; x++)
		{
				//double yOperation = (double)y / (double)yMax;

				for(int z = 0; z < 16; z++)
				{
					pValClouds[x][z] = ((float)noise.eval((x) / fSizeClouds  + (xIn * 16) / fSizeClouds, (z) / fSizeClouds   + (zIn * 16) / fSizeClouds, 0) * mixAmount) + ((pValClouds[x][z]) * (1 - mixAmount));
					
					//double value = noise.eval(x / FEATURE_SIZE, z / FEATURE_SIZE, 0.0);
					//int rgb = 0x010101 * (int)((pVal + 1) * 127.5);
					//image.setRGB(x, z, rgb);
				}
		}
		mixAmount -= (mixAmount / 2.0f);

		}
		for(int x = 0; x < 16; x++)
		{
			for(int z = 0; z < 16; z++)
			{
				float pVal = ((pValClouds[x][z] + 1) / 2);
				pVal = pVal * maxTerr[x][z];
				
				for(int y = 0; y < maxTerr[x][z]; y++)
				{
					if(y <= getSeaLevel())
					{
						primer.setBlockState(new BlockPos(x, y, z), lavaBlocks[x][z], false);
					}
					
					if(y <= pVal && y > (getSeaLevel() + 4))
					{
						primer.setBlockState(new BlockPos(x, y, z), topBlocks[x][z], false);
					}
					else if(y <= pVal && y <= (getSeaLevel() + 4) && y > getSeaLevel())
					{
						if(biomes[x][z].hasNearLavaSoil())
							primer.setBlockState(new BlockPos(x, y, z), biomes[x][z].getLavaSoil(), false);
						else
							primer.setBlockState(new BlockPos(x, y, z), topBlocks[x][z], false);
					}
					else if(y <= pVal)
					{
						primer.setBlockState(new BlockPos(x, y, z), topBlocks[x][z], false);
					}
				}
			}
		}
		
		
		
	}
	
	public void buildStalactites(ChunkPrimer primer, int xIn, int zIn)
	{
		float pValClouds[][] = new float[16][16];
		float mixAmount = 1.0f;
		
		float fSizeClouds = DeepnetherChunkGenerator.FEATURE_SIZE * 2;
		

		for(int x = 0; x < 16; x++)
		{
			
				//double yOperation = (double)y / (double)yMax;

				for(int z = 0; z < 16; z++)
				{
					pValClouds[x][z] = 0;
					//double value = noise.eval(x / FEATURE_SIZE, z / FEATURE_SIZE, 0.0);
					//int rgb = 0x010101 * (int)((pVal + 1) * 127.5);
					//image.setRGB(x, z, rgb);
				}
		}
		
		for(int octaves = 0; octaves < 3; octaves++)
		{
			fSizeClouds -= fSizeClouds / 2.0D;
		for(int x = 0; x < 16; x++)
		{
			
				//double yOperation = (double)y / (double)yMax;

				for(int z = 0; z < 16; z++)
				{
					pValClouds[x][z] = ((float)noise.eval((x) / fSizeClouds  + (xIn * 16) / fSizeClouds, (z) / fSizeClouds   + (zIn * 16) / fSizeClouds, 0) * mixAmount) + ((pValClouds[x][z]) * (1 - mixAmount));;
					
					//double value = noise.eval(x / FEATURE_SIZE, z / FEATURE_SIZE, 0.0);
					//int rgb = 0x010101 * (int)((pVal + 1) * 127.5);
					//image.setRGB(x, z, rgb);
				}
		}
		mixAmount -= (mixAmount / 2.0f);

		}
		
		for(int x = 0; x < 16; x++)
		{
			for(int z = 0; z < 16; z++)
			{
				double pVal = pValClouds[x][z];
				//pVal = pVal * 90;
				//pVal = Math.sin(pVal);
				pVal = (pVal * yStalactitesStretch);
				for(int y = 0; y < yMax; y++)
				{
					if(((y - yMax) * -1) <= pVal)
					{
						primer.setBlockState(new BlockPos(x, y, z), BlockInit.COMPRESSED_NETHERRACK.getDefaultState(), false);
					}
					

				}
			}
		}
	}
	
	public void buildCaves(ChunkPrimer primer, int xIn, int zIn)
	{
		float pValClouds[][] = new float[16][16];
		float mixAmount = 1.0f;
		
		float fSizeClouds = DeepnetherChunkGenerator.FEATURE_SIZE;
		
		for(int x = 0; x < 16; x++)
		{
			
				//double yOperation = (double)y / (double)yMax;

				for(int z = 0; z < 16; z++)
				{
					pValClouds[x][z] = (float)noise.eval((x) / DeepnetherChunkGenerator.FEATURE_SIZE, (z) / DeepnetherChunkGenerator.FEATURE_SIZE, 0);
					//double value = noise.eval(x / FEATURE_SIZE, z / FEATURE_SIZE, 0.0);
					//int rgb = 0x010101 * (int)((pVal + 1) * 127.5);
					//image.setRGB(x, z, rgb);
				}
		}
		
		for(int octaves = 0; octaves < 3; octaves++)
		{
			fSizeClouds -= fSizeClouds / 2.0f;
		for(int x = 0; x < 16; x++)
		{
			
				//double yOperation = (double)y / (double)yMax;

				for(int z = 0; z < 16; z++)
				{
					pValClouds[x][z] = ((float)noise.eval((x) / fSizeClouds  + (xIn * 16) / fSizeClouds, (z) / fSizeClouds   + (zIn * 16) / fSizeClouds, 0) * mixAmount) + ((pValClouds[x][z]) * (1 - mixAmount));
					
					//double value = noise.eval(x / FEATURE_SIZE, z / FEATURE_SIZE, 0.0);
					//int rgb = 0x010101 * (int)((pVal + 1) * 127.5);
					//image.setRGB(x, z, rgb);
				}
		}
		mixAmount -= (mixAmount / 2.0D);

		}
		
		for(int x = 0; x < 16; x++)
		{
			
				//double yOperation = (double)y / (double)yMax;

				for(int z = 0; z < 16; z++)
				{
					//double pVal = noise.eval((x) / FEATURE_SIZE + (xIn * 16) / FEATURE_SIZE, (z) / FEATURE_SIZE + (zIn * 16) / FEATURE_SIZE, 0);
					
					float pVal = (pValClouds[x][z] * yMaxTerrain) - 1;
					
					if(pVal < 0)
					{
						pVal = pVal * -1;
					}
					
					//System.out.println(pVal);
					for(int y = world.getSeaLevel(); y < yMax; y++)
					{
						if(pVal > y && y > world.getSeaLevel())
						{
							primer.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), false);
						}
					}
				}
					
			}
	}
	
	public boolean contains(Biome[] objects, Class doesContain)
	{
		//doesContain.
		for(Biome b : objects)
		{
			if(b.getClass().equals(doesContain))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void buildIslands(ChunkPrimer primer, int xIn, int zIn, Biome[] biomes)
	{
		if(!contains(biomes, FloatingIslandsBiome.class))
			return;
		
		float fSizeClouds = FEATURE_SIZE * 2;
		float mixAmount = 1.0f;
		
		// Creating extended map
		float pValCloudsF[][] = new float[16+islandRadius*2][16+islandRadius*2];
		//int pValClouds[][] = new int[16][16];

		
		for(int x = 0; x < 16; x++)
		{
			for(int z = 0; z < 16; z++)
			{
				pValCloudsF[x][z] = 0;
			}
		}
		
		float[][] stretchInterpolate = new float[16+islandRadius*2][16+islandRadius*2];
		
		//Generates the basic noisemap with octaves
		for(int octaves = 0; octaves < 2; octaves++)
		{
			fSizeClouds -= fSizeClouds / 2.0D;
			
			for(int x = 0; x < stretchInterpolate.length; x++)
			{
					for(int z = 0; z < stretchInterpolate[0].length; z++)
					{
						//pValClouds[x][z] = (int) (((float)noise.eval((x) / fSizeClouds  + (xIn * 16) / fSizeClouds, (z) / fSizeClouds   + (zIn * 16) / fSizeClouds, 0) * mixAmount) + ((pValClouds[x][z]) * (1 - mixAmount)) * stretch);
					//	float temp = ()
						pValCloudsF[x][z] = ((float)noise.eval((x - islandRadius) / fSizeClouds  + (xIn * 16) / fSizeClouds, (z - islandRadius) / fSizeClouds  + (zIn * 16) / fSizeClouds, 0) * mixAmount) + ((pValCloudsF[x][z]) * (1 - mixAmount));
						
						//Takes the noise input from -1 to 1 and changes it to an range from 0 to 1
						pValCloudsF[x][z] = ((pValCloudsF[x][z] + 1.0f) / 2.0f);
						
						
						//int rgb = 0x010101 * (int)((pVal + 1) * 127.5);
						//image.setRGB(x, z, rgb);
					}
					
				//pos++;
			}
			
			mixAmount = (mixAmount / 2.0f);
		}
		//
		Biome[] biomeTemp = this.getBiomeProvider().getBiomesAsArray(xIn*16-islandRadius, zIn*16-islandRadius, 16+(islandRadius*2), 16+(islandRadius*2));
		int pos = 0;
		
		for(int x = 0; x < stretchInterpolate.length; x++)
		{
				for(int z = 0; z < stretchInterpolate[0].length; z++)
				{
					//
					if((biomeTemp[pos] instanceof FloatingIslandsBiome) && (pValCloudsF[x][z] > clipping))
					{
						//System.out.println("Set to one");
						stretchInterpolate[x][z] = 1.0f * stretch;
					}
					else
					{
						//System.out.println("Set to 00000000000");
						stretchInterpolate[x][z] = 0.0f;
					}
					pos++;
				}
				
			
		}
		
		
		float[][] interpolatedMap = DeepnetherBiomeProvider.interpolate(stretchInterpolate, islandRadius);
		
		
		pos = 0;
		for(int x = 0; x < 16; x++)
		{
			for(int z = 0; z < 16; z++)
			{
				/*
				if(biomes[pos] instanceof BiomeFloatingIsland && pValClouds[x][z] > clipping)
				{
					int pVal = ((pValCloudsInterpolated[x][z]) + cloudMinHeigth);
					pVal = (pVal + cloudMinHeigth);
					for(int y = 0; y < pVal; y++)
					{
						if(y < pVal && y > cloudMinHeigth)
						{
							primer.setBlockState(x, y, z, BLOCK_MAGMA);
						}
					}
				}
				*/
				
				
				
				if(biomes[pos] instanceof FloatingIslandsBiome)
				{
					for(int y = (cloudMinHeigth-2); y < (cloudMinHeigth + stretch + 1); y++)
					{
						int pVal = (int) (((pValCloudsF[x+islandRadius][z+islandRadius]) * interpolatedMap[x][z]) + cloudMinHeigth);
						//System.out.println("pVal: " + pVal);
						if(y > cloudMinHeigth && y <= pVal)
						{
							if(y == pVal)
							{
								primer.setBlockState(new BlockPos(x, y, z), BlockInit.NETHER_DUST_GRASS_BLOCK.getDefaultState(), false);
								
								//Check if tall grass should be placed
								if(noise.eval(x/3.5, z/3.5) > 0.25)
								{
									if(primer.getBlockState(new BlockPos(x, y+1, z)) == Blocks.AIR.getDefaultState())
									{
										primer.setBlockState(new BlockPos(x, y+1, z), BlockInit.NETHER_DUST_GRASS.getDefaultState(), false);
									}
								}
							}
							else if(y > (pVal-5))
							{
								primer.setBlockState(new BlockPos(x, y, z), BlockInit.SOUL_DUST.getDefaultState(), false);

							}
							else
							{
								primer.setBlockState(new BlockPos(x, y, z), BlockInit.COMPRESSED_NETHERRACK.getDefaultState(), false);

							}
						}
					}
				}
				
				pos++;
			}
		}
		
	}
	
	public static final Predicate<StructureData> PREDICATE_SPAWNABLE = new Predicate<StructureData>()
	{
		@Override
		public boolean test(StructureData t)
		{
			return t instanceof IHasSpawnList;
		}
	};
	
	@Override
	public List<SpawnListEntry> getPossibleCreatures(EntityClassification creatureType, BlockPos pos)
	{
		List<SpawnListEntry> entries = ((DeepnetherBiomeBase)this.getBiomeProvider().getBiome(pos.getX(), pos.getZ())).getSpawnablesPositioned(pos, creatureType);
		
		IHasSpawnList match = (IHasSpawnList) StructureDataHandler.getStructureAtPosition(pos, world, PREDICATE_SPAWNABLE);
		
		if(match == null)
			return entries;
		else
		{
			System.out.println("HAS ENTRIES!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			if(match.replaceOldList())
				return match.getSpawnables();
			else
			{
				entries.addAll(match.getSpawnables());
				return entries;
			}
		}
	}

	@Override
	public void generateSurface(WorldGenRegion p_225551_1_, IChunk p_225551_2_)
	{
		
	}
}
