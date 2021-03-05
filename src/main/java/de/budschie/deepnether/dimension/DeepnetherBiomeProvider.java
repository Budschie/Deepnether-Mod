package de.budschie.deepnether.dimension;

import java.util.Random;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import de.budschie.deepnether.main.References;
import de.budschie.deepnether.noise.VoronoiNoise;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.feature.structure.Structure;

public class DeepnetherBiomeProvider extends BiomeProvider
{	
	//				RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).fieldOf("biomes").forGetter((obj) -> obj.lookupRegistry))
	public static final Codec<DeepnetherBiomeProvider> CODEC = RecordCodecBuilder.create((builder) ->
	{
		return builder.group(Codec.LONG.fieldOf("seed").stable().forGetter((obj) -> obj.seed), 
				RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter((biome) -> biome.biomeRegistry)
)
				.apply(builder, builder.stable(DeepnetherBiomeProvider::new));
	});
	
	private long seed;
	private VoronoiNoise noise;
	private Registry<Biome> biomeRegistry;
	
	public DeepnetherBiomeProvider(long seed, Registry<Biome> biomeRegistry)
	{
		super(biomeRegistry.getEntries().stream()
        .filter(entry -> entry.getKey().getLocation().getNamespace().equals(References.MODID))
        .filter((entry) -> 
        {
        	String name = entry.getKey().getLocation().getPath();
        	return name.equals("green_forest_biome") || name.equals("deepnether_biome") || name.equals("soul_desert_biome");
        })
        .map((entry) -> entry.getValue())
        .collect(Collectors.toList()));
		
		noise = new VoronoiNoise(seed);
		this.seed = seed;
		this.biomeRegistry = biomeRegistry;
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z)
	{
		double value = noise.voronoiNoise((double)x, (double)z, getBiomeScale(), true);
		return getNoiseBiomeByNoise(value);
	}
	
	public Biome getNoiseBiomeByNoise(double noise)
	{
		Random rand = new Random((long)(noise * Integer.MAX_VALUE));
		return biomes.get(rand.nextInt(biomes.size()));
	}
	
	public int getBiomeId(int x, int y, int z)
	{
		double value = noise.voronoiNoise(x, z, getBiomeScale(), true);
		return (int) (value * Integer.MAX_VALUE);
	}
	
	public double getVoronoi(int x, int y, int z)
	{
		return noise.voronoiNoise((double)x, (double)z, getBiomeScale(), false);
	}

	@Override
	protected Codec<? extends BiomeProvider> getBiomeProviderCodec()
	{
		return CODEC;
	}

	@Override
	public BiomeProvider getBiomeProvider(long seed)
	{
		return new DeepnetherBiomeProvider(seed, biomeRegistry);
	}
	
	public double getBiomeScale()
	{
		return .0125;
	}
	
	@Override
	public boolean hasStructure(Structure<?> structureIn)
	{
		return false;
	}
}
