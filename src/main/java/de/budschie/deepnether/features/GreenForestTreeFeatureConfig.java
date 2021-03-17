package de.budschie.deepnether.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;

public class GreenForestTreeFeatureConfig extends BasicVineLeavesLogFeatureConfig
{
	public static final Codec<GreenForestTreeFeatureConfig> CODEC = RecordCodecBuilder.create((builder) ->
	{
		return builder.group(BlockStateProvider.CODEC.fieldOf("logUp").forGetter((instance) -> instance.getLogUp()), 
				BlockStateProvider.CODEC.fieldOf("rotatedX").forGetter((instance) -> instance.getRotatedX()), 
				BlockStateProvider.CODEC.fieldOf("rotatedZ").forGetter((instance) -> instance.getRotatedZ()),
				BlockStateProvider.CODEC.fieldOf("leaves").forGetter((instance) -> instance.getLeavesBlockStateProvider()),
				BlockStateProvider.CODEC.fieldOf("vines").forGetter(instance -> instance.getVineBlockStateProvider()),
				BlockStateProvider.CODEC.fieldOf("harvestableVines").forGetter(instance -> instance.getHarvestableVineBlockStateProvider()))
				.apply(builder, GreenForestTreeFeatureConfig::new);
	});
	
	private BlockStateProvider harvestableVineBlockStateProvider;
	
	public GreenForestTreeFeatureConfig(BlockStateProvider logUp, BlockStateProvider rotatedX, BlockStateProvider rotatedZ, BlockStateProvider leavesBlockStateProvider,
			BlockStateProvider vineBlockStateProvider, BlockStateProvider harvestableVineBlockStateProvider)
	{
		super(logUp, rotatedX, rotatedZ, leavesBlockStateProvider, vineBlockStateProvider);
		this.harvestableVineBlockStateProvider = harvestableVineBlockStateProvider;
	}
	
	public BlockStateProvider getHarvestableVineBlockStateProvider()
	{
		return harvestableVineBlockStateProvider;
	}
}
