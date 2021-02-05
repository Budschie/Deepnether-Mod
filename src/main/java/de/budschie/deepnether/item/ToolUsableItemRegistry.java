package de.budschie.deepnether.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import com.google.common.collect.Lists;

import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.item.toolModifiers.AttackSpeedModifier;
import de.budschie.deepnether.item.toolModifiers.DurabilityModifier;
import de.budschie.deepnether.item.toolModifiers.IModifier;
import de.budschie.deepnether.item.toolModifiers.IToolUsableItem;
import de.budschie.deepnether.item.toolModifiers.IToolUsableItem.Part;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

public class ToolUsableItemRegistry
{
	private static HashMap<String, IToolUsableItem> map = new HashMap<String, IToolUsableItem>();
	
	public static void attach(Item item, IToolUsableItem usable)
	{
		map.put(item.getRegistryName().toString(), usable);
		usable.setBoundItem(item.getRegistryName().toString());
	}
	
	public static Optional<IToolUsableItem> get(String item)
	{
		return item == null ? Optional.empty() : Optional.ofNullable(map.get(item));
	}
	
	public static Optional<IToolUsableItem> get(Item item)
	{
		return item == null ? Optional.empty() : Optional.ofNullable(map.get(item.getRegistryName().toString()));
	}
	
	public static void init()
	{
		attach(ItemInit.DYLITHITE_INGOT.get(), new IToolUsableItem()
		{
			String bound = "";
			@Override
			public void setBoundItem(String string)
			{
				bound = string;
			}
			
			@Override
			public ArrayList<IModifier> getModifiers(Part part, ToolType type)
			{
				return part == Part.HEAD ? Lists.newArrayList(new DurabilityModifier(Operation.ADDITION, -100), new AttackSpeedModifier(Operation.ADDITION, 1.0f)) : Lists.newArrayList(new DurabilityModifier(Operation.ADDITION, -100), new AttackSpeedModifier(Operation.ADDITION, 1.0f));
			}
			
			@Override
			public int getHarvestLevel(ToolType toolType)
			{
				return toolType.getName().equals(ToolType.PICKAXE.getName()) ? 1 : -1;
			}
			
			@Override
			public int getDurability(Part part, ToolType type)
			{
				return 1000;
			}
			
			@Override
			public String getBoundItem()
			{
				return bound;
			}
			
			@Override
			public float getAttackDamage(Part part, ToolType toolType)
			{
				return 2 * ToolHelper.getDmgMultiplierForTool(toolType);
			}

			@Override
			public String getImage(Part part, ToolType toolType)
			{
				return "dylithite_" + (part == Part.HEAD ? toolType.getName() : "stick");
			}

			@Override
			public float getDestroySpeed(Part part, ToolType toolType)
			{
				return 1.25f;
			}
		});
		
		attach(BlockInit.COMPRESSED_NETHERRACK.asItem(), new IToolUsableItem()
		{
			String bound = "";
			
			@Override
			public void setBoundItem(String string)
			{
				bound = string;
			}
			
			@Override
			public ArrayList<IModifier> getModifiers(Part part, ToolType type)
			{
				return part == Part.HEAD ? Lists.newArrayList(new AttackSpeedModifier(Operation.ADDITION, 5.0f), new DurabilityModifier(Operation.MULTIPLY_BASE, 0.75f)) : Lists.newArrayList(new DurabilityModifier(Operation.ADDITION, 250));
			}
			
			@Override
			public int getHarvestLevel(ToolType toolType)
			{
				return toolType.getName().equals(ToolType.PICKAXE.getName()) ? 3 : -1;
			}
			
			@Override
			public int getDurability(Part part, ToolType type)
			{
				return part == Part.HEAD ? 500 : 1000;
			}
			
			@Override
			public String getBoundItem()
			{
				return bound;
			}
			
			@Override
			public float getAttackDamage(Part part, ToolType toolType)
			{
				return 4 * ToolHelper.getDmgMultiplierForTool(toolType);
			}

			@Override
			public String getImage(Part part, ToolType toolType)
			{
				return "cnr_" + (part == Part.HEAD ? toolType.getName() : "stick");
			}

			@Override
			public float getDestroySpeed(Part part, ToolType toolType)
			{
				return 2.5f;
			}
		});
	}
}
