package de.budschie.deepnether.dimension;

import java.util.ArrayList;
import java.util.Set;

import com.google.common.collect.Sets;

import de.budschie.deepnether.biomes.DeepnetherBiomeBase;
import de.budschie.deepnether.biomes.FloatingIslandsBiome;
import net.kdotjpg.opensimplexnoise.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.feature.structure.Structure;

public class DeepnetherBiomeProviderOld extends BiomeProvider
{


	float biomeSize;
	OpenSimplexNoise noise;
	
	public DeepnetherBiomeProviderOld(long seed, float biomeSize, DeepnetherBiomeBase... biomes) 
	{
		super(null);
		System.out.println("Creating new instance...");
		noise = new OpenSimplexNoise(seed);
		this.biomeSize = biomeSize;
		for(DeepnetherBiomeBase biome : biomes)
		{
			System.out.println("Added biome " + biome.getRegistryName().toString());
			this.biomes.add(biome);
		}
		
		if(this.biomes.size() <= 0)
		{
			throw new IllegalArgumentException("You have to give at least one biome to this constructor!");
		}
	}
	
	
	ArrayList<DeepnetherBiomeBase> biomes = new ArrayList<DeepnetherBiomeBase>();
	
	public Biome getBiome(int x, int z) 
	{
		//System.out.println(pos.getX() + " " + pos.getZ());
		int processedNoiseOut = getNoise(x, z);
		//System.out.println(biomes.get(processedNoiseOut).getRegistryName().toString());
		//System.out.println("Will get: " + (int)Math.floor(noiseOut));
		return ((Biome)this.biomes.get(processedNoiseOut));
	}
	
	
	static int ID = 0;
	
	public Set<Biome> getBiomes(int x, int z, int width, int depth) 
	{
		//ID++;
		//ArrayList<Biome> biomes_ = new ArrayList<Biome>();
		Set<Biome> set = Sets.newHashSet();
		//double[][] testNoiseArr = new double[width][depth];
		
		int pos = 0;
		
		for(int xLoop = 0; xLoop < width; xLoop++)
		{
			for(int zLoop = 0; zLoop < depth; zLoop++)
			{
				int processedNoiseOut = getNoise(x+xLoop, z+zLoop);
				
				
			//	testNoiseArr[i][j] = processedNoiseOut;
				
				//System.out.println(noiseOut);
				//System.out.println(this.biomes.size());
				//System.out.println("Will get: " + (int)Math.round(noiseOut));
				
				
				//biomes_.add(this.biomes.get(processedNoiseOut));
				set.add(this.biomes.get(processedNoiseOut));
				
				pos++;
			}
		}
		

		
		return set;
	}
	
	public Biome[] getBiomesAsArray(int x, int z, int width, int depth) 
	{
		//ID++;
		//ArrayList<Biome> biomes_ = new ArrayList<Biome>();
		Biome[] set = new Biome[width*depth];
		//double[][] testNoiseArr = new double[width][depth];
		
		int pos = 0;
		
		for(int xLoop = 0; xLoop < width; xLoop++)
		{
			for(int zLoop = 0; zLoop < depth; zLoop++)
			{
				int processedNoiseOut = getNoise(x+xLoop, z+zLoop);
				
				
			//	testNoiseArr[i][j] = processedNoiseOut;
				
				//System.out.println(noiseOut);
				//System.out.println(this.biomes.size());
				//System.out.println("Will get: " + (int)Math.round(noiseOut));
				
				
				//biomes_.add(this.biomes.get(processedNoiseOut));
				set[pos] = (this.biomes.get(processedNoiseOut));
				
				pos++;
			}
		}
		

		
		return set;
	}
	
	
	public DeepnetherBiomeBase[][] getBiomesDeepnether(int x, int z, int width, int depth) 
	{
		//ID++;
		DeepnetherBiomeBase[][] biomesToOutput = new DeepnetherBiomeBase[width][depth];

		//double[][] testNoiseArr = new double[width][depth];
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < depth; j++)
			{
				int processedNoiseOut = getNoise(x+i, z+j);

				
			//	testNoiseArr[i][j] = processedNoiseOut;
				
				//System.out.println(noiseOut);
				//System.out.println(this.biomes.size());
				//System.out.println("Will get: " + (int)Math.round(noiseOut));
				
				
				biomesToOutput[i][j] = this.biomes.get(processedNoiseOut);
			}
		}
		

		
		return biomesToOutput;
	}
	
	private int getNoise(int x, int y)
	{
		float noiseprocessed = (float) noise.eval(x / biomeSize, y / biomeSize);

		noiseprocessed = ((noiseprocessed + 1) / 2);
		noiseprocessed = (noiseprocessed * biomes.size());
		
		if(noiseprocessed >= biomes.size())
		{
			return(biomes.size()-1);
		}
		else if(noiseprocessed <= 0)
		{
			return 0;
		}
		else
		{
			return (int) Math.floor(noiseprocessed);
		}
	}
	
	
	
	/** 1 wird mit 2 gemischt => 1.0 => voll a **/
	public static int blend(int a, int b, float amount)
	{
		return Math.round(((a * amount) + b * ((amount-1)*-1)));
	}
	
	
	public static boolean areSame(Biome... biomes)
	{
		for(Biome biome : biomes)
		{
			if(!biome.getRegistryName().equals(biomes[0].getRegistryName()))
			{
				return false;
			}
		}
		
		return true;
	}
	
	public Vec3d getFogColor(int x, int z, int radius)
	{
		// ((BiomeDeepNetherBase[]) getBiomes(new Biome[0], x - radius, z - radius,  radius * 2, radius * 2));
		DeepnetherBiomeBase[][] biomes = getBiomesDeepnether(x - radius, z - radius, radius * 2, radius * 2);
		float rMixed = 0;
		float gMixed = 0;
		float bMixed = 0;
		
		for(int xLoop = 0; xLoop < radius*2; xLoop++)
		{
			for(int zLoop = 0; zLoop < radius*2; zLoop++)
			{
				Vec3d temp = biomes[xLoop][zLoop].getFogColor();
				rMixed += temp.x;
				gMixed += temp.y;
				bMixed += temp.z;
			}
		}
		
		int div = ((radius*2)*(radius*2));
		rMixed = (rMixed / div);
		gMixed = (gMixed / div);
		bMixed = (bMixed / div);
		
		return new Vec3d(rMixed, gMixed, bMixed);
	}
	
	public int[][] getInterpolatedHeightMap(int x, int z, int width, int length, int radius)
	{
		/** Only at edges **/
		//if((getBiome(new BlockPos(x,0,z)) != null || getBiome(new BlockPos(x,0,z+length)) != null || getBiome(new BlockPos(x+width,0,z)) != null || getBiome(new BlockPos(x+width,0,z+length)) != null || )
		/*
		if(areSame(getBiome(new BlockPos(x,0,z)), getBiome(new BlockPos(x,0,z+length)), getBiome(new BlockPos(x+width,0,z)), getBiome(new BlockPos(x+width,0,z+length))))
		{
			int biomes[][] = new int[width][length];
			for(int i = 0; i < biomes.length; i++)
			{
				for(int j = 0; j < biomes[0].length; j++)
				{
					biomes[i][j] = ((BiomeDeepNetherBase)getBiome(new BlockPos((x+i), 1, (z+j)))).getBaseHeightMap();
				}
			}
			
			return biomes;
		}
		*/
		Biome[][] biomes = new Biome[width+radius*2][length+radius*2];
		int[][] heightmaps = new int[width][length];
		
		for(int i = 0; i < biomes.length; i++)
		{
			for(int j = 0; j < biomes[0].length; j++)
			{
				biomes[i][j] = getBiome((x+i), (z+j));
			}
		}
		
		for(int xIn = 0; xIn < width; xIn++)
		{
			for(int zIn = 0; zIn < length; zIn++)
			{
				int valEnd = 0;
				for(int radiusLoop = 0; radiusLoop < (radius * 2); radiusLoop++)
				{
					for(int radiusLoop2 = 0; radiusLoop2 < (radius * 2); radiusLoop2++)
					{
						//System.out.println(valEnd);
						valEnd += (((DeepnetherBiomeBase)biomes[xIn+radiusLoop][zIn+radiusLoop2]).getBaseHeightMap());
						//System.out.println();
						//System.out.println("Ran through");
					}
					//System.out.println(valEnd);
					//valEnd += (((BiomeDeepNetherBase)biomes[xIn+radiusLoop][zIn+radiusLoop]).getBaseHeightMap());
					//System.out.println();
					//System.out.println("Ran through");
				}
				
				valEnd = (valEnd / ((radius*2)*(radius*2)));
				//System.out.println(valEnd);
				//System.out.println("Value was: " + valEnd);
				
				heightmaps[xIn][zIn] = valEnd;
				//heightmaps[xIn][zIn] = ((BiomeDeepNetherBase)biomes[radius+xIn][radius+zIn]).getBaseHeightMap();
			}
		}
		
		return heightmaps;
	}
	
	public int[][] getInterpolatedHeightMapIslands(int x, int z, int width, int length, int radius)
	{
		/** Only at edges **/
		//if((getBiome(new BlockPos(x,0,z)) != null || getBiome(new BlockPos(x,0,z+length)) != null || getBiome(new BlockPos(x+width,0,z)) != null || getBiome(new BlockPos(x+width,0,z+length)) != null || )
		/*
		if(areSame(getBiome(new BlockPos(x,0,z)), getBiome(new BlockPos(x,0,z+length)), getBiome(new BlockPos(x+width,0,z)), getBiome(new BlockPos(x+width,0,z+length))))
		{
			int biomes[][] = new int[width][length];
			for(int i = 0; i < biomes.length; i++)
			{
				for(int j = 0; j < biomes[0].length; j++)
				{
					biomes[i][j] = ((BiomeDeepNetherBase)getBiome(new BlockPos((x+i), 1, (z+j)))).getBaseHeightMap();
				}
			}
			
			return biomes;
		}
		*/
		Biome[][] biomes = new Biome[width+radius*2][length+radius*2];
		int[][] heightmaps = new int[width][length];
		
		for(int i = 0; i < biomes.length; i++)
		{
			for(int j = 0; j < biomes[0].length; j++)
			{
				biomes[i][j] = getBiome((x+i), (z+j));
			}
		}
		
		for(int xIn = 0; xIn < width; xIn++)
		{
			for(int zIn = 0; zIn < length; zIn++)
			{
				int valEnd = 0;
				for(int radiusLoop = 0; radiusLoop < (radius * 2); radiusLoop++)
				{
					for(int radiusLoop2 = 0; radiusLoop2 < (radius * 2); radiusLoop2++)
					{
						//System.out.println(valEnd);
						if(biomes[xIn+radiusLoop][zIn+radiusLoop2] instanceof FloatingIslandsBiome)
						{
							valEnd += DeepnetherChunkGenerator.stretch;
						}
						//System.out.println();
						//System.out.println("Ran through");
					}
					//System.out.println(valEnd);
					//valEnd += (((BiomeDeepNetherBase)biomes[xIn+radiusLoop][zIn+radiusLoop]).getBaseHeightMap());
					//System.out.println();
					//System.out.println("Ran through");
				}
				
				valEnd = (valEnd / ((radius*2)*(radius*2)));
				//System.out.println(valEnd);
				//System.out.println("Value was: " + valEnd);
				
				heightmaps[xIn][zIn] = valEnd;
				//heightmaps[xIn][zIn] = ((BiomeDeepNetherBase)biomes[radius+xIn][radius+zIn]).getBaseHeightMap();
			}
		}
		
		return heightmaps;
	}
	
	public static float[][] interpolate(float[][] toInterpolateExtended, int radius)
	{
		float[][] heightMapInterpolated = new float[toInterpolateExtended.length-radius*2][toInterpolateExtended[0].length-radius*2];
	
		for(int xIn = 0; xIn < heightMapInterpolated.length; xIn++)
		{
			for(int zIn = 0; zIn < heightMapInterpolated[0].length; zIn++)
			{
				float valEnd = 0;
				for(int radiusLoop = 0; radiusLoop < (radius * 2); radiusLoop++)
				{
					for(int radiusLoop2 = 0; radiusLoop2 < (radius * 2); radiusLoop2++)
					{
						valEnd += toInterpolateExtended[xIn + radiusLoop][zIn+radiusLoop2];
					}
				}
				
				valEnd = (valEnd / ((radius*2)*(radius*2)));
				//System.out.println(valEnd);
				//System.out.println("Value was: " + valEnd);
				
				heightMapInterpolated[xIn][zIn] = valEnd;
				//heightmaps[xIn][zIn] = ((BiomeDeepNetherBase)biomes[radius+xIn][radius+zIn]).getBaseHeightMap();
			}
		}
		
		return heightMapInterpolated;
	}
	
	public static float[][] interpolateFromInt(int[][] toInterpolateExtended, int radius)
	{
		float[][] heightMapInterpolated = new float[toInterpolateExtended.length-radius*2][toInterpolateExtended[0].length-radius*2];
	
		for(int xIn = 0; xIn < heightMapInterpolated.length; xIn++)
		{
			for(int zIn = 0; zIn < heightMapInterpolated[0].length; zIn++)
			{
				float valEnd = 0;
				for(int radiusLoop = 0; radiusLoop < (radius * 2); radiusLoop++)
				{
					for(int radiusLoop2 = 0; radiusLoop2 < (radius * 2); radiusLoop2++)
					{
						valEnd += toInterpolateExtended[xIn+radiusLoop][zIn+radiusLoop2];
					}
					//System.out.println(valEnd);
					//valEnd += (((BiomeDeepNetherBase)biomes[xIn+radiusLoop][zIn+radiusLoop]).getBaseHeightMap());
					//System.out.println();
					//System.out.println("Ran through");
				}
				
				System.out.println(valEnd);
				valEnd = (valEnd / ((radius*2)*(radius*2)));
				System.out.println(valEnd);
				//System.out.println(valEnd);
				//System.out.println("Value was: " + valEnd);
				
				heightMapInterpolated[xIn][zIn] = valEnd;
				//heightmaps[xIn][zIn] = ((BiomeDeepNetherBase)biomes[radius+xIn][radius+zIn]).getBaseHeightMap();
			}
		}
		
		return heightMapInterpolated;
	}
	
	public BlockState[][] getTopBlocks(int x, int z, int width, int length)
	{
		BlockState[][] states = new BlockState[width][length];
		
		for(int xIn = 0; xIn < width; xIn++)
		{
			for(int zIn = 0; zIn < width; zIn++)
			{
				states[xIn][zIn] = (((DeepnetherBiomeBase) (getBiome(x+xIn, z+zIn))).getTopBlock());
			}
		}
		
		return states;
	}
	
	public BlockState[][] getLavaBlocks(int x, int z, int width, int length)
	{
		BlockState[][] states = new BlockState[width][length];
		
		for(int xIn = 0; xIn < width; xIn++)
		{
			for(int zIn = 0; zIn < width; zIn++)
			{
				states[xIn][zIn] = (((DeepnetherBiomeBase) (getBiome(x+xIn, z+zIn))).getLavaBlock());
			}
		}
		
		return states;
	}
	
	private static float getDist(int x, int y, int xTarget, int yTarget)
	{
		int xPr = x-xTarget;
		if(xPr < 0)
		{
			xPr *= -1;
		}
		
		int yPr = y-yTarget;
		if(yPr < 0)
		{
			yPr *= -1;
		}
		
		return (float) Math.sqrt((xPr*xPr+yPr*yPr));
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z)
	{
		return getBiome(x, z);
	}
	
	@Override
	public boolean hasStructure(Structure<?> structureIn)
	{
		return false;
	}
}
