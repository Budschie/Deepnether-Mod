package de.budschie.deepnether.dimension;

import com.google.common.collect.ImmutableSet;

import de.budschie.deepnether.biomes.BiomeRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.LayerUtil;

public class DeepnetherBiomeProvider extends BiomeProvider
{
	private final Layer genBiomes;
	
	public DeepnetherBiomeProvider()
	{
		//OverworldBiomeProvider
		super(ImmutableSet.<Biome>of(BiomeRegistry.DEEPNETHER_BIOME, BiomeRegistry.CRYSTAL_CAVE_BIOME, BiomeRegistry.FLOATING_ISLANDS_BIOME, BiomeRegistry.LAVA_BIOME, BiomeRegistry.SOULFUL_GRASS_BIOME));
		this.genBiomes = new Layer(new IAreaFactory<LazyArea>()
		{
			
			@Override
			public LazyArea make()
			{
				IAreaFactory<LazyArea> factory = new IAreaFactory<LazyArea>()
				{
					
					@Override
					public LazyArea make()
					{
						LazyArea lazyArea = new LazyArea(p_i51286_1_, p_i51286_2_, p_i51286_3_);
						return null;
					}
				};
				
				return factory.make();
			}
		});
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z)
	{
		return null;
	}
}
