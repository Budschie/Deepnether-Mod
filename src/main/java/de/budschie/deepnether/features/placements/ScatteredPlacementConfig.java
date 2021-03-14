package de.budschie.deepnether.features.placements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.gen.placement.IPlacementConfig;

public class ScatteredPlacementConfig implements IPlacementConfig
{
	public static final Codec<ScatteredPlacementConfig> CODEC = RecordCodecBuilder.create((builder) ->
	{
		return builder.group(Codec.STRING.fieldOf("channel").forGetter(config -> config.getChannel()), Codec.INT.fieldOf("amountPerChunk").forGetter(config -> config.getAmountPerChunk())).apply(builder, (channel, amountPerChunk) -> new ScatteredPlacementConfig(channel, amountPerChunk));
	});
	
	private String channel;
	private int amountPerChunk;
	
	public ScatteredPlacementConfig(String channel, int amountPerChunk)
	{
		this.channel = channel;
		this.amountPerChunk = amountPerChunk;
	}
	
	public String getChannel()
	{
		return channel;
	}
	
	public int getAmountPerChunk()
	{
		return amountPerChunk;
	}
}
