package de.budschie.deepnether.dimension;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.function.Function;

import de.budschie.deepnether.main.DeepnetherMain;
import de.budschie.deepnether.util.BiomeUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

public class InterpolationChannel<I, O>
{
	String name;
	I defaultValue;
	Class<I> iClazz;
	Class<O> oClazz;
	HashMap<ResourceLocation, IMappingValueSupplier<I>> mappingFunctionTable;
	IInterpolationApplier<I, O> interpolationApplier;
	int spacing;
	
	public InterpolationChannel(String name, Class<O> oClazz, I defaultValue, IInterpolationApplier<I, O> interpolationApplier, int spacing)
	{
		this.name = name;
		this.defaultValue = defaultValue;
		this.iClazz = (Class<I>) defaultValue.getClass();
		this.oClazz = oClazz;
		this.mappingFunctionTable = new HashMap<>();
		this.interpolationApplier = interpolationApplier;
		this.spacing = spacing;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void addMappingFunction(ResourceLocation biomeName, IMappingValueSupplier<I> mappingFunction)
	{
		mappingFunctionTable.put(biomeName, mappingFunction);
	}

	public O[][] getArea(long seed, Function<BlockPos, Biome> biomeSupplier, BiomeProvider biomeProvider, int startX, int startZ, Biome[][] cachedBiomes)
	{
		@SuppressWarnings("unchecked")
		I[][] sampleArea = (I[][]) Array.newInstance(iClazz, cachedBiomes.length + 2 * spacing, cachedBiomes[0].length + 2 * spacing);
		
		for(int x = -spacing; x < cachedBiomes.length + spacing; x++)
		{
			for(int z = -spacing; z < cachedBiomes[0].length + spacing; z++)
			{
				int xCoord = startX + x;
				int zCoord = startZ + z;
				
				boolean inReach = (x >= 0 && z >= 0 && x < cachedBiomes.length && z < cachedBiomes[0].length);
				Biome biome = inReach ? cachedBiomes[x][z] : biomeSupplier.apply(new BlockPos(xCoord, 0, zCoord));
				ResourceLocation rs = BiomeUtil.getBiomeRS(biome, DeepnetherMain.server);
				
				IMappingValueSupplier<I> eSupplier = mappingFunctionTable.get(rs);
				
				sampleArea[x + spacing][z + spacing] = eSupplier == null ? defaultValue : eSupplier.get(biomeProvider, biomeSupplier, xCoord, zCoord, seed);
			}
		}
		
		@SuppressWarnings("unchecked")
		O[][] outputArea = (O[][]) Array.newInstance(oClazz, cachedBiomes.length, cachedBiomes[0].length);
		
		/*
		for(int x = 0; x < cachedBiomes.length; x++)
		{
			for(int z = 0; z < cachedBiomes[0].length; z++)
			{
				//System.out.println("Interpolating " + String.join(", ", String.valueOf(sampleArea[x][z + spacing*2]), String.valueOf(sampleArea[x + spacing*2][z + spacing*2]), String.valueOf(sampleArea[x][z]), String.valueOf(sampleArea[x + spacing*2][z])) + "to: ");
				//outputArea[x][z] = sampleArea[x][z];
				// sampleArea[x][z + spacing*2], sampleArea[x + spacing*2][z + spacing*2], sampleArea[x][z], sampleArea[x + spacing*2][z]
				outputArea[x][z] = interpolationApplier.apply(sampleArea, x, z);
				//System.out.println(outputArea[x][z]);
			}
		}
		*/
		
		interpolationApplier.apply(sampleArea, outputArea);
		
		return outputArea;
	}
}
