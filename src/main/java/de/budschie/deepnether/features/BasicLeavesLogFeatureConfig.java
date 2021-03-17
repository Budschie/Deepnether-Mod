package de.budschie.deepnether.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BasicLeavesLogFeatureConfig extends BasicLogFeatureConfig
{
	public static final Codec<BasicLeavesLogFeatureConfig> CODEC = RecordCodecBuilder.create((builder) ->
	{
		return builder.group(BlockStateProvider.CODEC.fieldOf("logUp").forGetter((instance) -> instance.getLogUp()), 
				BlockStateProvider.CODEC.fieldOf("rotatedX").forGetter((instance) -> instance.getRotatedX()), 
				BlockStateProvider.CODEC.fieldOf("rotatedZ").forGetter((instance) -> instance.getRotatedZ()),
				BlockStateProvider.CODEC.fieldOf("leaves").forGetter((instance) -> instance.getLeavesBlockStateProvider())).apply(builder, BasicLeavesLogFeatureConfig::new);
	});
	
	private BlockStateProvider leavesBlockStateProvider;
	
	public BasicLeavesLogFeatureConfig(BlockStateProvider logUp, BlockStateProvider rotatedX, BlockStateProvider rotatedZ, BlockStateProvider leavesBlockStateProvider)
	{
		super(logUp, rotatedX, rotatedZ);
		this.leavesBlockStateProvider = leavesBlockStateProvider;
	}
	
	public BlockStateProvider getLeavesBlockStateProvider()
	{
		return leavesBlockStateProvider;
	}
}