package de.budschie.deepnether.dimension;

import java.util.HashMap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import de.budschie.deepnether.biomes.biome_data_handler.BiomeDataHandler;
import de.budschie.deepnether.biomes.biome_data_handler.IDeepnetherBiomeData;
import de.budschie.deepnether.main.DeepnetherMain;
import de.budschie.deepnether.util.BiomeUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeMagnifier;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.Features;
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

	@Override
	public void generateSurface(WorldGenRegion worldGenRegion, IChunk chunk)
	{
		int posXStart = chunk.getPos().x * 16;
		int posZStart = chunk.getPos().z * 16;
		
		// Fill heightmap
		HashMap<ResourceLocation, IDeepnetherBiomeData> cache = new HashMap<>();
		
		Biome[][] currentBiomes = new Biome[16][16];
		
		for(int x = 0; x < 16; x++)
		{
			for(int z = 0; z < 16; z++)
			{
				Biome currentBiome = worldGenRegion.getBiome(new BlockPos(posXStart + x, 0, posZStart + z));
				currentBiomes[x][z] = currentBiome;
				cache.computeIfAbsent(BiomeUtil.getBiomeRS(currentBiome, DeepnetherMain.server), (biome) -> BiomeDataHandler.getBiomeData(biome));
			}
		}
		
		InterpolationChannelBuffer buffer = new InterpolationChannelBuffer(this, currentBiomes, posXStart, posZStart, worldGenRegion);
		
		for(int x = 0; x < 16; x++)
		{
			for(int z = 0; z < 16; z++)
			{
				cache.get(BiomeUtil.getBiomeRS(currentBiomes[x][z], DeepnetherMain.server)).getBiomeGenerator().generate(posXStart, posZStart, x, z, chunk, this, buffer);
			}
		}
	}
	
	@Override
	public DeepnetherBiomeProvider getBiomeProvider()
	{
		return (DeepnetherBiomeProvider) biomeProvider;
	}

	@Override
	public int getHeight(int x, int z, Type heightmapType)
	{
		return 0;
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
