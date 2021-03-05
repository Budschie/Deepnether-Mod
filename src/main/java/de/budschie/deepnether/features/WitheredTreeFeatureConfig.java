package de.budschie.deepnether.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class WitheredTreeFeatureConfig implements IFeatureConfig
{
	public static final Codec<WitheredTreeFeatureConfig> CODEC = RecordCodecBuilder.create((builder) ->
	{
		return builder.group(BlockStateProvider.CODEC.fieldOf("logUp").forGetter((instance) -> instance.getLogUp()), 
				BlockStateProvider.CODEC.fieldOf("rotatedX").forGetter((instance) -> instance.getRotatedX()), 
						BlockStateProvider.CODEC.fieldOf("rotatedZ").forGetter((instance) -> instance.getRotatedZ())).apply(builder, WitheredTreeFeatureConfig::new);
	});
	
	private BlockStateProvider logUp, rotatedX, rotatedZ;
	
	public WitheredTreeFeatureConfig(BlockStateProvider logUp, BlockStateProvider rotatedX, BlockStateProvider rotatedZ)
	{
		this.logUp = logUp;
		this.rotatedX = rotatedX;
		this.rotatedZ = rotatedZ;
	}
	
	public BlockStateProvider getLogUp()
	{
		return logUp;
	}
	
	public BlockStateProvider getRotatedX()
	{
		return rotatedX;
	}
	
	public BlockStateProvider getRotatedZ()
	{
		return rotatedZ;
	}
}
