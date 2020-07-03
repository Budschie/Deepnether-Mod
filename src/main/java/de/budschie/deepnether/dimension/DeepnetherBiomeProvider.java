package de.budschie.deepnether.dimension;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;

import de.budschie.deepnether.biomes.BiomeRegistry;
import de.budschie.deepnether.biomes.DeepnetherBiomeBase;
import de.budschie.deepnether.biomes.FloatingIslandsBiome;
import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.NetherBiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.IPixelTransformer;

public class DeepnetherBiomeProvider extends BiomeProvider
{

	protected DeepnetherBiomeProvider(List<Biome> p_i231634_1_)
	{
		super(p_i231634_1_);
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z)
	{
		return null;
	}

	@Override
	protected Codec<? extends BiomeProvider> func_230319_a_()
	{
		return null;
	}

	@Override
	public BiomeProvider func_230320_a_(long p_230320_1_)
	{
		return null;
	}
}
