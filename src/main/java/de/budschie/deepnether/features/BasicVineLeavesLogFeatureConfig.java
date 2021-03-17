package de.budschie.deepnether.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;

public class BasicVineLeavesLogFeatureConfig extends BasicLeavesLogFeatureConfig
{
	public static final Codec<BasicVineLeavesLogFeatureConfig> CODEC = RecordCodecBuilder.create((builder) ->
	{
		return builder.group(BlockStateProvider.CODEC.fieldOf("logUp").forGetter((instance) -> instance.getLogUp()), 
				BlockStateProvider.CODEC.fieldOf("rotatedX").forGetter((instance) -> instance.getRotatedX()), 
				BlockStateProvider.CODEC.fieldOf("rotatedZ").forGetter((instance) -> instance.getRotatedZ()),
				BlockStateProvider.CODEC.fieldOf("leaves").forGetter((instance) -> instance.getLeavesBlockStateProvider()),
				BlockStateProvider.CODEC.fieldOf("vines").forGetter(instance -> instance.getVineBlockStateProvider())).apply(builder, BasicVineLeavesLogFeatureConfig::new);
	});
	
	private BlockStateProvider vineBlockStateProvider;
	
	public BasicVineLeavesLogFeatureConfig(BlockStateProvider logUp, BlockStateProvider rotatedX, BlockStateProvider rotatedZ, BlockStateProvider leavesBlockStateProvider, BlockStateProvider vineBlockStateProvider)
	{
		super(logUp, rotatedX, rotatedZ, leavesBlockStateProvider);
		this.vineBlockStateProvider = vineBlockStateProvider;
	}

	public BlockStateProvider getVineBlockStateProvider()
	{
		return vineBlockStateProvider;
	}
}
