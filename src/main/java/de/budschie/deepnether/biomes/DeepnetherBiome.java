package de.budschie.deepnether.biomes;

import java.util.List;

import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.entity.EntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityClassification;
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
		//this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityInit.HELL_CREEPER, 2, 2, 4));
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
