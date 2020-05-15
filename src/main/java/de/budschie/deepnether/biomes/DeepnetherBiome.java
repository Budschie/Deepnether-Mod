package de.budschie.deepnether.biomes;

import de.budschie.deepnether.block.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.biome.Biome.Category;

public class DeepnetherBiome extends DeepnetherBiomeBase
{
	public static class DeepnetherBiomeBuilder extends Builder
	{
		public DeepnetherBiomeBuilder()
		{
			this.precipitation(RainType.NONE).category(Category.NETHER).temperature(100);
		}
	}
	
	public DeepnetherBiome(Builder builder)
	{
		super(builder);
	}
	
	@Override
	public int getBaseHeightMap() 
	{
		return 30;
	}
	
	@Override
	public boolean hasParticles() 
	{
		return true;
	}
	
	@Override
	public BlockState getTopBlock()
	{
		return BlockInit.COMPRESSED_NETHERRACK.getDefaultState();
	}
}
