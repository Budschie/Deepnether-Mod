package de.budschie.deepnether.item;

import de.budschie.deepnether.main.References;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class BaseItem extends Item
{
	String name = "";
	
	public BaseItem(String name, Properties properties)
	{
		super(properties);
		
		this.name = name;
		this.setRegistryName(new ResourceLocation(References.MODID, name));
		
		ItemInit.MOD_ITEMS.add(this);
	}
}
