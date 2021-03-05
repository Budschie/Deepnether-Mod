package de.budschie.deepnether.item;

import java.util.ArrayList;

import net.minecraft.item.IItemTier;

public abstract class ModItemTier implements IItemTier
{
	ArrayList<ToolMaterialDescriptionElement> toolMaterialDescription = new ArrayList<ToolMaterialDescriptionElement>();
	
	public ModItemTier addDescriptionElement(ToolMaterialDescriptionElement element)
	{
		toolMaterialDescription.add(element);
		return this;
	}
	
	public ArrayList<ToolMaterialDescriptionElement> getDescription()
	{
		return toolMaterialDescription;
	}
}
