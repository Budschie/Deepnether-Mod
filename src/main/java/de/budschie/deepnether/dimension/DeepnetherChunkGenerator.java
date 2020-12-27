package de.budschie.deepnether.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import de.budschie.deepnether.biomes.biome_data_handler.BiomeDataHandler;
import de.budschie.deepnether.biomes.biome_data_handler.IDeepnetherBiomeData;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.BiomeGeneratorBase;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraftforge.registries.ForgeRegistries;

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
	
	public DeepnetherChunkGenerator(BiomeProvider firstBiomeProvider, long seed)
	{
		super(firstBiomeProvider, firstBiomeProvider, new DimensionStructuresSettings(true), seed);
		
		this.seed = seed;
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
		
		double[][] heightmap = new double[16][16];
		
		for(int x = 0; x < 16; x++)
		{
			for(int z = 0; z < 16; z++)
			{
				/*
				DeepnetherBiomeBase deepnetherBiomeBase = this.biomeProvider.getNoiseBiome(posXStart + x, 0, posZStart + z);
				BiomeGeneratorBase biomeGenerator = deepnetherBiomeBase.getBiomeGenerator();
				*/
				Biome biome = worldGenRegion.getBiome(new BlockPos(posXStart + x, 0, posZStart + z));
				BiomeGeneratorBase biomeGenerator = BiomeDataHandler.getBiomeData(biome.getRegistryName()).getBiomeGenerator();
				heightmap[x][z] = biomeGenerator.getGroundHeight(seed, posXStart + x, posZStart + z);
			}
		}
		
		for(int x = 0; x < 16; x++)
		{
			for(int z = 0; z < 16; z++)
			{
				Biome biome = worldGenRegion.getBiome(new BlockPos(posXStart + x, 0, posZStart + z));
				BiomeGeneratorBase biomeGenerator = BiomeDataHandler.getBiomeData(biome.getRegistryName()).getBiomeGenerator();
				
				biomeGenerator.preprocess(posXStart + x, posZStart + z, heightmap[x][z], seed, (DeepnetherBiomeProvider)biomeProvider);
			
				for(int y = 0; y < biomeGenerator.getGenerationHeight(); y++)
				{
					chunk.setBlockState(new BlockPos(posXStart + x, 0, posZStart + z), biomeGenerator.pickBlock(posXStart + x, y, posZStart + z, heightmap[x][z], seed, (DeepnetherBiomeProvider)biomeProvider), false);
				}
			}
		}
	}

	@Override
	public void func_230352_b_(IWorld world, StructureManager structureManager, IChunk chunk)
	{
		
	}

	@Override
	public IBlockReader func_230348_a_(int p_230348_1_, int p_230348_2_)
	{
		return null;
	}
	
	@Override
	public BiomeProvider getBiomeProvider()
	{
		return biomeProvider;
	}

	@Override
	public int getHeight(int x, int z, Type heightmapType)
	{
		return 0;
	}
}
