package de.budschie.deepnether.block;

import de.budschie.deepnether.item.ItemInit;
import de.budschie.deepnether.main.References;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Block.Properties;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class ModLeavesBlock extends LeavesBlock
{

	public ModLeavesBlock(Properties props, String name, ItemGroup group)
	{
		super(props);
		this.setRegistryName(new ResourceLocation(References.MODID, name));
		
		BlockInit.MOD_BLOCKS.add(this);
		ItemInit.MOD_ITEMS.add(new BlockItem(this, new Item.Properties().group(group)).setRegistryName(getRegistryName()));
	}
	
	public ModLeavesBlock(Properties props, String name, ItemGroup group, boolean hasItem)
	{
		super(props);
		this.setRegistryName(new ResourceLocation(References.MODID, name));
		
		BlockInit.MOD_BLOCKS.add(this);
		if(hasItem)
		ItemInit.MOD_ITEMS.add(new BlockItem(this, new Item.Properties().group(group)).setRegistryName(getRegistryName()));
	}

}
