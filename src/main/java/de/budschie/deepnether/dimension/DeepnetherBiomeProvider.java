package de.budschie.deepnether.dimension;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import de.budschie.deepnether.biomes.BiomeRegistry;
import de.budschie.deepnether.noise.VoronoiNoise;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.feature.structure.Structure;

public class DeepnetherBiomeProvider extends BiomeProvider
{	
	//				RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).fieldOf("biomes").forGetter((obj) -> obj.lookupRegistry))

	public static final Codec<DeepnetherBiomeProvider> CODEC = RecordCodecBuilder.create((builder) ->
	{
		return builder.group(Codec.LONG.fieldOf("seed").stable().forGetter((obj) -> obj.seed))
				.apply(builder, builder.stable(DeepnetherBiomeProvider::new));
	});
	
	private long seed;
	private VoronoiNoise noise;
	
	public DeepnetherBiomeProvider(long seed)
	{
		super(Arrays.asList(BiomeRegistry.GREEN_FOREST_BIOME.get()));
		noise = new VoronoiNoise(seed);
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z)
	{
		double value = noise.voronoiNoise((double)x, (double)z, getBiomeScale(), true);
		//int index = (int) Math.ceil(value * (biomes.size() - 1));
		
		Random rand = new Random((long)value*1234);
		return biomes.get(rand.nextInt(biomes.size()));
	}
	
	public int getBiomeId(int x, int y, int z)
	{
		double value = noise.voronoiNoise((double)x * getBiomeScale(), 0, (double)z * getBiomeScale(), true);
		return (int) (value * Integer.MAX_VALUE);
	}

	@Override
	protected Codec<? extends BiomeProvider> getBiomeProviderCodec()
	{
		return CODEC;
	}

	@Override
	public BiomeProvider getBiomeProvider(long seed)
	{
		return new DeepnetherBiomeProvider(seed);
	}
	
	public double getBiomeScale()
	{
		return 0.125;
	}
	
	@Override
	public boolean hasStructure(Structure<?> structureIn)
	{
		return false;
	}
}
