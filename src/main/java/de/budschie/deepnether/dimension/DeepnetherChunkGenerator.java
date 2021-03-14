package de.budschie.deepnether.dimension;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import de.budschie.deepnether.biomes.biome_data_handler.BiomeDataHandler;
import de.budschie.deepnether.biomes.biome_data_handler.IDeepnetherBiomeData;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.IBiomeGenerator;
import de.budschie.deepnether.main.DeepnetherMain;
import de.budschie.deepnether.util.BiomeUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeMagnifier;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraftforge.common.MinecraftForge;

public class DeepnetherChunkGenerator extends ChunkGenerator
{
	public static final Codec<DeepnetherChunkGenerator> CODEC = RecordCodecBuilder.create((builder) -> {
	      return builder.group(BiomeProvider.CODEC.fieldOf("biome_source").forGetter((obj) -> {
	         return obj.biomeProvider;
	      }), Codec.LONG.fieldOf("seed").stable().forGetter((obj) -> {
	         return obj.seed;
	      })).apply(builder, builder.stable(DeepnetherChunkGenerator::new));
	   });
	
	long seed;
	OctavesNoiseGenerator noiseGenerator;
	private HashMap<String, InterpolationChannel<?, ?>> interpolationMap = new HashMap<>();
	
	public DeepnetherChunkGenerator(BiomeProvider firstBiomeProvider, long seed)
	{
		super(firstBiomeProvider, firstBiomeProvider, new DimensionStructuresSettings(true), seed);
		this.seed = seed;
		MinecraftForge.EVENT_BUS.post(new InterpolationChannelRegistryEvent(this));
		MinecraftForge.EVENT_BUS.post(new InterpolationChannelBiomeRegistryEvent(this));
		MinecraftForge.EVENT_BUS.post(new DeepnetherChunkGenerationInitEvent(this));
	}
	
	public void addInterpolationEntry(InterpolationChannel<?, ?> interpolationChannel)
	{
		interpolationMap.put(interpolationChannel.getName(), interpolationChannel);
	}
	
	@SuppressWarnings("unchecked")
	public <I, O> InterpolationChannel<I, O> getInterpolationChannel(String name)
	{
		return (InterpolationChannel<I, O>) interpolationMap.get(name);
	}

	@Override
	protected Codec<? extends ChunkGenerator> func_230347_a_()
	{
		return CODEC;
	}

	@Override
	public ChunkGenerator func_230349_a_(long seed)
	{
		this.seed = seed;
		return this;
	}
	
	private HashMap<String, WeakHashMap<ChunkPos, int[][]>> cachedHeightmaps = new HashMap<>();
	
	private static final int MAX_CACHED_AMOUNT = 10;
	
	private synchronized Optional<int[][]> returnCachedValue(String channel, ChunkPos chunkPos)
	{
		// Definetly not over-engineered...
		
		if(!cachedHeightmaps.containsKey(channel))
			return Optional.empty();
		
		WeakHashMap<ChunkPos, int[][]> channelCache = cachedHeightmaps.get(channel);
		
		Optional<int[][]> returnValue = channelCache.containsKey(chunkPos) ? Optional.of(channelCache.get(chunkPos)) : Optional.empty();
		
		if(cachedHeightmaps.keySet().size() > MAX_CACHED_AMOUNT)
		{
			int i = cachedHeightmaps.size() - MAX_CACHED_AMOUNT;
			Iterator<ChunkPos> chunkPosIterator = channelCache.keySet().iterator();
			
			while(i > 0)
			{
				ChunkPos currentKey = chunkPosIterator.next();
				
				if(!currentKey.equals(chunkPos))
				{
					channelCache.remove(currentKey);
					i--;
				}
			}
		}
		
		return returnValue;
	}
	
	private synchronized void setCachedValue(String channel, ChunkPos chunkPos, int[][] value)
	{
		cachedHeightmaps.computeIfAbsent(channel, name -> new WeakHashMap<>()).put(chunkPos, value);
	}
		
	private static Biome[][] getCurrentBiomes(Function<BlockPos, Biome> biomeSupplier, ChunkPos chunk, Optional<Consumer<Biome>> biomeConsumer)
	{
		Biome[][] currentBiomes = new Biome[16][16];
		
		for(int x = 0; x < 16; x++)
		{
			for(int z = 0; z < 16; z++)
			{
				Biome currentBiome = biomeSupplier.apply(new BlockPos(chunk.x * 16 + x, 0, chunk.z * 16 + z));
				currentBiomes[x][z] = currentBiome;
				biomeConsumer.ifPresent((consumer) -> consumer.accept(currentBiome));
			}
		}
		
		return currentBiomes;
	}
	
	@Override
	public void generateSurface(WorldGenRegion worldGenRegion, IChunk chunk)
	{
		int posXStart = chunk.getPos().x * 16;
		int posZStart = chunk.getPos().z * 16;
		
		// Fill heightmap
		HashMap<ResourceLocation, IDeepnetherBiomeData> cache = new HashMap<>();
		
		Biome[][] biomes = getCurrentBiomes((blockPos) -> worldGenRegion.getBiomeManager().getBiome(blockPos), chunk.getPos(), Optional.of(currentBiome -> cache.computeIfAbsent(BiomeUtil.getBiomeRS(currentBiome, DeepnetherMain.server), (biome) -> BiomeDataHandler.getBiomeData(biome))));
		
		InterpolationChannelBuffer buffer = new InterpolationChannelBuffer(this, biomes, posXStart, posZStart, (blockPos) -> worldGenRegion.getBiomeManager().getBiome(blockPos));
		
		for(int x = 0; x < 16; x++)
		{
			for(int z = 0; z < 16; z++)
			{
				IBiomeGenerator generator = cache.get(BiomeUtil.getBiomeRS(biomes[x][z], DeepnetherMain.server)).getBiomeGenerator();
				generator.generate(posXStart, posZStart, x, z, chunk, this, buffer);
			}
		}
	}
	
	@Override
	public DeepnetherBiomeProvider getBiomeProvider()
	{
		return (DeepnetherBiomeProvider) biomeProvider;
	}
	
	public int[][] getDeepnetherHeightmap(String channel, ChunkPos pos)
	{
		Optional<int[][]> cachedValue = returnCachedValue(channel, pos);
		
		if(cachedValue.isPresent())
			return cachedValue.get();
		else
		{
			// We have to calculate... );
			int posXStart = pos.x * 16;
			int posZStart = pos.z * 16;
			
			int heights[][] = new int[16][16];
			
			HashMap<ResourceLocation, IDeepnetherBiomeData> cache = new HashMap<>();
			
			Biome[][] biomes = getCurrentBiomes((blockPos) -> getBiomeProvider().getNoiseBiome(blockPos.getX() >> 2, blockPos.getY() >> 2, blockPos.getZ() >> 2), pos, Optional.of(currentBiome -> cache.computeIfAbsent(BiomeUtil.getBiomeRS(currentBiome, DeepnetherMain.server), (biome) -> BiomeDataHandler.getBiomeData(biome))));
			
			InterpolationChannelBuffer buffer = new InterpolationChannelBuffer(this, biomes, posXStart, posZStart, (blockPos) -> getBiomeProvider().getNoiseBiome(blockPos.getX() >> 2, blockPos.getY() >> 2, blockPos.getZ() >> 2));
			
			for(int x = 0; x < 16; x++)
			{
				for(int z = 0; z < 16; z++)
				{
					IBiomeGenerator generator = cache.get(BiomeUtil.getBiomeRS(biomes[x][z], DeepnetherMain.server)).getBiomeGenerator();
					
					if(generator instanceof IHeightmapProvider)
					{
						IHeightmapProvider provider = (IHeightmapProvider) generator;
						heights[x][z] = provider.getHeight(channel, posXStart, posZStart, x, z, this, buffer);
					}
					else
					{
						heights[x][z] = IHeightmapProvider.NOOP_INT;
					}
				}
			}
			
			setCachedValue(channel, pos, heights);
			return heights;
		}
	}

	@Override
	public int getHeight(int x, int z, Type heightmapType)
	{
		return -4269420;
	}

	@Override
	public void func_230352_b_(IWorld p_230352_1_, StructureManager p_230352_2_, IChunk p_230352_3_)
	{
		
	}

	@Override
	public IBlockReader func_230348_a_(int p_230348_1_, int p_230348_2_)
	{
		return null;
	}
}
