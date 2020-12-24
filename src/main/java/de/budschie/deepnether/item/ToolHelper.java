package de.budschie.deepnether.item;

import net.minecraftforge.common.ToolType;

public class ToolHelper
{
	public static float getDmgMultiplierForTool(ToolType toolType)
	{
		if(toolType == null)
		{
			System.out.println("TOOLTYPE WAS NULL!");
			return 1f;
		}
		if(toolType.getName().equals(ToolType.AXE.getName()))
		{
			return 1.3f;
		}
		else if(toolType.getName().equals(ToolType.SHOVEL.getName()))
		{
			return 0.7f;
		}
		else if(toolType.getName().equals(ToolType.PICKAXE.getName()))
		{
			return 0.65f;
		}
		else
			return 1f;
	}
}
