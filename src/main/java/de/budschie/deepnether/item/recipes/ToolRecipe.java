package de.budschie.deepnether.item.recipes;

import de.budschie.deepnether.capabilities.IToolDefinition;
import de.budschie.deepnether.capabilities.ToolDefinitionCapability;
import de.budschie.deepnether.item.ItemInit;
import de.budschie.deepnether.item.toolModifiers.IToolUsableItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.capabilities.Capability;

public class ToolRecipe implements ICraftingRecipe
{
	private Matcher matcher;
	private ToolType toolType;
	private ResourceLocation id;
	
	private static Item[][] to3x3(CraftingInventory inv)
	{
		Item[][] item = new Item[3][3];
		int pos = 0;
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				item[x][y] = inv.getStackInSlot(pos).getItem();
				pos++;
			}
		}
		
		return item;
	}
	
	public ToolType getToolType()
	{
		return toolType;
	}
	
	public Matcher getMatcher()
	{
		return matcher;
	}
	
	public ToolRecipe(Matcher matcher, ToolType toolType, ResourceLocation id)
	{
		this.matcher = matcher;
		this.toolType = toolType;
		this.id = id;
	}
	
	@Override
	public boolean matches(CraftingInventory inv, World worldIn)
	{
		Item[][] item = to3x3(inv);
		boolean b = matcher.matches(item);
		System.out.println("CURRENT CRAFTING:");
		matcher.getCurrentCrafting(item);
		System.out.println();
		System.out.println("WHERE AS EXPECTED:");
		matcher.getExpectedCrafting();
		if(b)
			System.out.println("MATCHING FOR TYPE: " + toolType.getName());
		return b;
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		Item[][] grid3x3 = to3x3(inv);
		
		IToolUsableItem head = matcher.getHead(grid3x3);
		IToolUsableItem stick = matcher.getStick(grid3x3);
		
		ItemStack itemStack = new ItemStack(new IItemProvider()
		{
			
			@Override
			public Item asItem()
			{
				return ItemInit.COMMON_TOOL;
			}
		});
		
		if(head == null || stick == null)
		{
			System.out.println("NULL -> EMPTY");
			return ItemStack.EMPTY;
		}
		
		IToolDefinition definition = itemStack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP).orElse(null);
		definition.setToolType(toolType);
		definition.setHead(head);
		definition.setStick(stick);
		
		return itemStack;
	}

	@Override
	public boolean canFit(int width, int height)
	{
		return width == 3 && height == 3;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return ItemStack.EMPTY;
	}

	@Override
	public ResourceLocation getId()
	{
		return id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return ToolRecipeSerializerRegistry.TOOL_RECIPE.get();
	}
	
	@Override
	public boolean isDynamic()
	{
		return true;
	}

}
