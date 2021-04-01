package de.budschie.deepnether.item.recipes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Optional;

import de.budschie.deepnether.item.ToolUsableItemRegistry;
import de.budschie.deepnether.item.toolModifiers.IToolUsableItem;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class Matcher
{
	public static Matcher fromString(String[] string)
	{
		ArrayList<Point> headIndices = new ArrayList<Point>();
		ArrayList<Point> stickIndices = new ArrayList<Point>();
		
		for(int y = 0; y < string.length; y++)
		{
			String line = string[y];
			for(int x = 0; x < line.length(); x++)
			{
				char currentChar = line.charAt(x);
				if(currentChar == 'I')
				{
					stickIndices.add(new Point(x, y));
				}
				else if(currentChar == 'X')
				{
					headIndices.add(new Point(x, y));
				}
			}
		}
		
		return new Matcher(headIndices, stickIndices);
	}
	
	ArrayList<Point> headIndices;
	ArrayList<Point> stickIndices;
	
	public Matcher(ArrayList<Point> headIndices, ArrayList<Point> stickIndices)
	{
		this.headIndices = headIndices;
		this.stickIndices = stickIndices;
	}
	
	public void getCurrentCrafting(Item[][] itemArray)
	{
		System.out.println();
		for(int x = 0; x < itemArray.length; x++)
		{
			for(int y = 0; y < itemArray[0].length; y++)
			{
				Item item = itemArray[x][y];
				if(item != Items.AIR)
				{
					System.out.print('X');
				}
				else
					System.out.print(' ');
			}
			System.out.println();
		}
	}
	
	public void getExpectedCrafting()
	{
		System.out.println();
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 3; x++)
			{
				if(headIndices.contains(new Point(x,y)) || stickIndices.contains(new Point(x,y)))
					System.out.print('X');
				else
					System.out.print(' ');
			}
			
			System.out.println();
		}
	}
	
	public String[] toStringForNetworking()
	{
		String[] str = new String[] {"", "", ""};
		
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 3; x++)
			{
				if(headIndices.contains(new Point(x, y)))
				{
					str[y] += 'X';
				}
				else if(stickIndices.contains(new Point(x, y)))
				{
					str[y] += 'I';
				}
				else
					str[y] += " ";
			}
		}
		
		/*
		for(String string : str)
			if(!string.contains("X"))
				throw new IllegalStateException("Illegal JSON file. Program will exit.");
				*/
		
		return str;
	}
	
	public boolean matches(Item[][] itemArray)
	{
		int c = 0;
		
		for(int x = 0; x < itemArray.length; x++)
		{
			for(int y = 0; y < itemArray[0].length; y++)
			{
				Item item = itemArray[x][y];
				if(item != Items.AIR)
				{
					c++;
				}
			}
		}
		
		if(c > (headIndices.size() + stickIndices.size()))
			return false;
		
		Item firstItemHead = null, firstItemStick = null;
		
		for(Point point : headIndices)
		{
			if(isAIOB(point, itemArray.length, itemArray[0].length))
				return false;
			else 
			{
				Item currentItem = itemArray[point.y][point.x];
				
				if(firstItemHead == null)
					firstItemHead = currentItem;
				
				if(!ToolUsableItemRegistry.get(currentItem.getRegistryName().toString()).isPresent() || firstItemHead != currentItem)
					return false;
			}
		}
		
		for(Point point : stickIndices)
		{
			if(isAIOB(point, itemArray.length, itemArray[0].length))
				return false;
			else 
			{
				Item currentItem = itemArray[point.y][point.x];
				
				if(firstItemStick == null)
					firstItemStick = currentItem;
				
				if(!ToolUsableItemRegistry.get(currentItem.getRegistryName().toString()).isPresent() || firstItemStick != currentItem)
					return false;
			}
		}
		
		return true;
	}
	
	public IToolUsableItem getHead(Item[][] itemArray)
	{
		Optional<IToolUsableItem> item = ToolUsableItemRegistry.get(itemArray[headIndices.get(0).y][headIndices.get(0).x].getRegistryName().toString());
		return item.isPresent() ? item.get() : null;
	}
	
	public IToolUsableItem getStick(Item[][] itemArray)
	{
		Optional<IToolUsableItem> item = ToolUsableItemRegistry.get(itemArray[stickIndices.get(0).y][stickIndices.get(0).x].getRegistryName().toString());
		return item.isPresent() ? item.get() : null;
	}
	
	private static boolean isAIOB(Point l, int s1, int s2)
	{
		return l.x >= s1 || l.y >= s2;
	}
}
