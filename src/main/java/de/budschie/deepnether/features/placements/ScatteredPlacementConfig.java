package de.budschie.deepnether.features.placements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.gen.placement.IPlacementConfig;

public class ScatteredPlacementConfig implements IPlacementConfig
{
	public static final Codec<ScatteredPlacementConfig> CODEC = RecordCodecBuilder.create((builder) ->
	{
		return builder.group(Codec.STRING.fieldOf("channel").forGetter(config -> config.getChannel()), 
				Codec.INT.fieldOf("amountPerChunk").forGetter(config -> config.getAmountPerChunk()), Codec.INT.optionalFieldOf("randomDelta", 0).forGetter(config -> config.getRandomDelta()),
				Codec.FLOAT.optionalFieldOf("chunkChance", 1f).forGetter(config -> config.getChunkChance()))
				.apply(builder, (channel, amountPerChunk, randomDelta, chunkChance) -> new ScatteredPlacementConfig(channel, amountPerChunk, randomDelta, chunkChance));
	});
	
	private String channel;
	private int amountPerChunk;
	private int randomDelta;
	private float chunkChance;
	
	public ScatteredPlacementConfig(String channel, int amountPerChunk, int randomDelta, float chunkChance)
	{
		this.channel = channel;
		this.amountPerChunk = amountPerChunk;
		this.randomDelta = randomDelta;
		this.chunkChance = chunkChance;
	}
	
	public String getChannel()
	{
		return channel;
	}
	
	public int getAmountPerChunk()
	{
		return amountPerChunk;
	}
	
	public int getRandomDelta()
	{
		return randomDelta;
	}
	
	public float getChunkChance()
	{
		return chunkChance;
	}
}
