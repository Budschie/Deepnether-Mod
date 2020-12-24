package de.budschie.deepnether.dimension;

import java.util.Arrays;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import de.budschie.deepnether.main.References;
import de.budschie.deepnether.noise.VoronoiNoise;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraftforge.registries.ForgeRegistries;

public class DeepnetherBiomeProvider extends BiomeProvider
{	
	public static final Codec<DeepnetherBiomeProvider> CODEC = RecordCodecBuilder.create((builder) ->
	{
		return builder.group(Codec.LONG.fieldOf("seed").forGetter((obj) -> obj.seed)).apply(builder, DeepnetherBiomeProvider::new);	
	});
	
	private long seed;
	private VoronoiNoise noise;
	
	public DeepnetherBiomeProvider(long seed)
	{
		super(Arrays.asList(ForgeRegistries.BIOMES.getValue(new ResourceLocation(References.MODID, "deepnether"))));
		this.seed = seed;
		noise = new VoronoiNoise(seed);
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z)
	{
		double value = noise.voronoiNoise((double)x, (double)z, getBiomeScale(), false);
		int index = (int) Math.floor(value * biomes.size());
		
		return biomes.get(index);
	}
	
	public int getBiomeId(int x, int y, int z)
	{
		double value = noise.voronoiNoise((double)x * getBiomeScale(), 0, (double)z * getBiomeScale(), false);
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
}
