package de.budschie.deepnether.dimension;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.function.Function;

import de.budschie.deepnether.main.DeepnetherMain;
import de.budschie.deepnether.util.BiomeUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

public class InterpolationChannel<E>
{
	String name;
	E defaultValue;
	Class<E> clazz;
	HashMap<ResourceLocation, IMappingValueSupplier<E>> mappingFunctionTable;
	IInterpolationApplier<E> interpolationApplier;
	int spacing;
	
	public InterpolationChannel(String name, E defaultValue, IInterpolationApplier<E> interpolationApplier, int spacing)
	{
		this.name = name;
		this.defaultValue = defaultValue;
		this.clazz = (Class<E>) defaultValue.getClass();
		this.mappingFunctionTable = new HashMap<>();
		this.interpolationApplier = interpolationApplier;
		this.spacing = spacing;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void addMappingFunction(ResourceLocation biomeName, IMappingValueSupplier<E> mappingFunction)
	{
		mappingFunctionTable.put(biomeName, mappingFunction);
	}

	public E[][] getArea(long seed, BiomeProvider currentBiomeProvider, int startX, int startZ, Biome[][] cachedBiomes)
	{
		@SuppressWarnings("unchecked")
		E[][] sampleArea = (E[][]) Array.newInstance(clazz, cachedBiomes.length + 2 * spacing, cachedBiomes[0].length + 2 * spacing);
		
		for(int x = -spacing; x < cachedBiomes.length + spacing; x++)
		{
			for(int z = -spacing; z < cachedBiomes[0].length + spacing; z++)
			{
				boolean inReach = (x > 0 && z > 0 && x < cachedBiomes.length && z < cachedBiomes[0].length);
				Biome biome = inReach ? cachedBiomes[x][z] : currentBiomeProvider.getNoiseBiome(startX + x, 0, startZ + z);
				ResourceLocation rs = BiomeUtil.getBiomeRS(biome, DeepnetherMain.server);
				
				IMappingValueSupplier<E> eSupplier = mappingFunctionTable.get(rs);
				
				sampleArea[x + spacing][z + spacing] = eSupplier == null ? defaultValue : eSupplier.get(currentBiomeProvider, startX, startZ, seed);
			}
		}
		
		@SuppressWarnings("unchecked")
		E[][] outputArea = (E[][]) Array.newInstance(clazz, cachedBiomes.length, cachedBiomes[0].length);
		for(int x = 0; x < cachedBiomes.length; x++)
		{
			for(int z = 0; z < cachedBiomes[0].length; z++)
			{
				outputArea[x][z] = interpolationApplier.apply(sampleArea[x-spacing][z + spacing], sampleArea[x + spacing][z + spacing], sampleArea[x - spacing][z - spacing], sampleArea[x + spacing][z - spacing]);
			}
		}
		
		return outputArea;
	}
}
