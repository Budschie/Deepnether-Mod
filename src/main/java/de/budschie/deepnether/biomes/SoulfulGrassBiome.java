package de.budschie.deepnether.biomes;

import de.budschie.deepnether.block.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class SoulfulGrassBiome extends FloatingIslandsBiome
{
	public static class SoulfulGrassBuilder extends Builder
	{
		public SoulfulGrassBuilder()
		{
			this.precipitation(RainType.NONE).category(Category.NETHER).temperature(100);
		}
	}
	
	public SoulfulGrassBiome(Builder builder)
	{
		super(builder);
	}
	
	@Override
	public BlockState getTopBlock()
	{
		return BlockInit.NETHER_DUST_GRASS_BLOCK.getDefaultState();
	}
}
