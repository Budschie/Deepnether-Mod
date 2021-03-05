package de.budschie.deepnether.capabilities;

import java.util.ArrayList;

import de.budschie.deepnether.item.toolModifiers.IModifier;
import de.budschie.deepnether.item.toolModifiers.IToolUsableItem;
import net.minecraftforge.common.ToolType;

public class ToolDefinition implements IToolDefinition
{
	IToolUsableItem head;
	IToolUsableItem stick;
	ToolType toolType;
	
	public ToolDefinition(IToolUsableItem head, IToolUsableItem stick, ToolType toolType)
	{
		this.head = head;
		this.stick = stick;
		this.toolType = toolType;
	}
	
	@Override
	public IToolUsableItem getHead()
	{
		return head;
	}

	@Override
	public IToolUsableItem getStick()
	{
		return stick;
	}

	@Override
	public ToolType getToolType()
	{
		return toolType;
	}

	@Override
	public void setHead(IToolUsableItem head)
	{
		this.head = head;
	}

	@Override
	public void setStick(IToolUsableItem stick)
	{
		this.stick = stick;
	}

	@Override
	public void setToolType(ToolType toolType)
	{
		this.toolType = toolType;
	}
}
