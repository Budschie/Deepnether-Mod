package de.budschie.deepnether.features.placements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.gen.placement.IPlacementConfig;

public class ScatteredPlacementConfig implements IPlacementConfig
{
	public static final Codec<ScatteredPlacementConfig> CODEC = RecordCodecBuilder.create((builder) ->
	{
		return builder.group(Codec.STRING.fieldOf("channel").forGetter(config -> config.getChannel()), 
				Codec.INT.fieldOf("amountPerChunk").forGetter(config -> config.getAmountPerChunk()), 
				Codec.INT.optionalFieldOf("randomDelta", 0).forGetter(config -> config.getRandomDelta()),
				Codec.FLOAT.optionalFieldOf("chunkChance", 1f).forGetter(config -> config.getChunkChance()),
				Codec.INT.fieldOf("minHeight").forGetter(config -> config.getMinHeight()),
				Codec.INT.fieldOf("maxHeight").forGetter(config -> config.getMaxHeight()))
				.apply(builder, ScatteredPlacementConfig::new);
	});
	
	private String channel;
	private int amountPerChunk;
	private int randomDelta;
	private int minHeight;
	private int maxHeight;
	private float chunkChance;
	
	public ScatteredPlacementConfig(String channel, int amountPerChunk, int randomDelta, float chunkChance, int minHeight, int maxHeight)
	{
		this.channel = channel;
		this.amountPerChunk = amountPerChunk;
		this.randomDelta = randomDelta;
		this.chunkChance = chunkChance;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
	}
	
	public ScatteredPlacementConfig(String channel, int amountPerChunk, int randomDelta, float chunkChance)
	{
		this(channel, amountPerChunk, randomDelta, chunkChance, 0, 255);
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
	
	public int getMaxHeight()
	{
		return maxHeight;
	}
	
	public int getMinHeight()
	{
		return minHeight;
	}
}
