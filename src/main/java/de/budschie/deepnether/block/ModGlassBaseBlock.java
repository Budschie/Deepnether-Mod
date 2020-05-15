package de.budschie.deepnether.block;

import de.budschie.deepnether.item.ItemInit;
import de.budschie.deepnether.main.DeepnetherMain;
import de.budschie.deepnether.main.References;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.GlassBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class ModGlassBaseBlock extends GlassBlock
{
	public ModGlassBaseBlock(Properties properties, String name, ItemGroup group) 
	{
		super(properties);
		
		this.setRegistryName(new ResourceLocation(References.MODID, name));
		
		BlockInit.MOD_BLOCKS.add(this);
		ItemInit.MOD_ITEMS.add(new BlockItem(this, new Item.Properties().group(group)).setRegistryName(getRegistryName()));
	}
	
	@Override
	public boolean isTransparent(BlockState state) 
	{
		return true;
	}
	
	@Override
	public boolean isVariableOpacity() 
	{
		return true;
	}
}
