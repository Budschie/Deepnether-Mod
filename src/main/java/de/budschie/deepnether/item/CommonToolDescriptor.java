package de.budschie.deepnether.item;

import de.budschie.deepnether.item.toolModifiers.IToolUsableItem;

public class CommonToolDescriptor
{
	private IToolUsableItem head, stick;
	
	public CommonToolDescriptor(IToolUsableItem head, IToolUsableItem stick)
	{
		this.head = head;
		this.stick = stick;
	}
	
	public IToolUsableItem getHead()
	{
		return head;
	}
	
	public IToolUsableItem getStick()
	{
		return stick;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof CommonToolDescriptor)
		{
			CommonToolDescriptor casted = (CommonToolDescriptor) obj;
			return casted.head == head && casted.stick == stick;
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return stick.hashCode() ^ head.hashCode();
	}
}
