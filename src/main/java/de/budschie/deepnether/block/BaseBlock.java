package de.budschie.deepnether.block;

import de.budschie.deepnether.item.ItemInit;
import de.budschie.deepnether.main.References;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

/** An abstract base block for creating decoration blocks **/
public class BaseBlock extends Block
{
	public BaseBlock(Properties props, String name, ItemGroup group)
	{
		super(props);
		this.setRegistryName(new ResourceLocation(References.MODID, name));
		
		BlockInit.MOD_BLOCKS.add(this);
		ItemInit.MOD_ITEMS.add(new BlockItem(this, new Item.Properties().group(group)).setRegistryName(getRegistryName()));
	}
	
	public BaseBlock(Properties props, String name, ItemGroup group, boolean hasItem)
	{
		super(props);
		this.setRegistryName(new ResourceLocation(References.MODID, name));
		
		BlockInit.MOD_BLOCKS.add(this);
		if(hasItem)
		ItemInit.MOD_ITEMS.add(new BlockItem(this, new Item.Properties().group(group)).setRegistryName(getRegistryName()));
	}
}
