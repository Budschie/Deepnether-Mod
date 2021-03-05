package de.budschie.deepnether.item;

import java.util.ArrayList;

import net.minecraft.item.IArmorMaterial;

public abstract class ModArmorMaterial implements IArmorMaterial
{
	ArrayList<ToolMaterialDescriptionElement> toolMaterialDescription = new ArrayList<ToolMaterialDescriptionElement>();
	
	public void addDescriptionElement(ToolMaterialDescriptionElement element)
	{
		toolMaterialDescription.add(element);
	}
	
	public ArrayList<ToolMaterialDescriptionElement> getDescription()
	{
		return toolMaterialDescription;
	}
}
