package de.budschie.deepnether.capabilities;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import de.budschie.deepnether.item.CommonTool;
import de.budschie.deepnether.item.ToolHelper;
import de.budschie.deepnether.item.toolModifiers.AttackSpeedModifier;
import de.budschie.deepnether.item.toolModifiers.DurabilityModifier;
import de.budschie.deepnether.item.toolModifiers.IModifier;
import de.budschie.deepnether.item.toolModifiers.IToolUsableItem;
import de.budschie.deepnether.item.toolModifiers.Stats;
import de.budschie.deepnether.item.toolModifiers.IToolUsableItem.Part;
import net.minecraft.command.impl.TellRawCommand;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ForgeRegistries;

public interface IToolDefinition
{
	public IToolUsableItem getHead();
	
	public IToolUsableItem getStick();
	
	public ToolType getToolType();
	
	public default boolean isUsed()
	{
		return getHead() != null && getStick() != null;
	}
	
	public default ArrayList<ITextComponent> getToolTip()
	{
		ArrayList<ITextComponent> list = new ArrayList<>();
		for(IModifier modifier : this.getHead().getModifiers(Part.HEAD, getToolType()))
		{
			list.add(new StringTextComponent(TextFormatting.DARK_GREEN + "Effect from " + TextFormatting.AQUA + ForgeRegistries.ITEMS.getValue(new ResourceLocation(getHead().getBoundItem())).getName().getString() + TextFormatting.GREEN + " as head: " + TextFormatting.RESET + modifier.getEffectDescription()));
		}
		
		list.add(new StringTextComponent(""));
		
		for(IModifier modifier : this.getStick().getModifiers(Part.STICK, getToolType()))
		{
			list.add(new StringTextComponent(TextFormatting.DARK_GREEN + "Effect from " + TextFormatting.BLUE + ForgeRegistries.ITEMS.getValue(new ResourceLocation(getStick().getBoundItem())).getName().getString() + TextFormatting.GREEN + " as stick: " + TextFormatting.RESET + modifier.getEffectDescription()));
		}
		
		return list;
	}
	
	public default ArrayList<IModifier> getModifiers()
	{
		ArrayList<IModifier> list = getHead().getModifiers(Part.HEAD, getToolType());
		list.addAll(getStick().getModifiers(Part.STICK, getToolType()));
		return list;
	}
	
	public default ArrayList<IModifier> getModifiers(IToolUsableItem head, IToolUsableItem stick)
	{
		ArrayList<IModifier> list = head.getModifiers(Part.HEAD, getToolType());
		list.addAll(stick.getModifiers(Part.STICK, getToolType()));
		return list;
	}
	
	void setHead(IToolUsableItem head);
	
	void setStick(IToolUsableItem stick);
	
	void setToolType(ToolType toolType);
	
	public default Stats constructStats(CommonTool commonTool, ItemStack itemStack)
	{
		Stats stats = new Stats();
		
		IToolUsableItem head = getHead();
		IToolUsableItem stick = getStick();
		
		ToolType toolType = getToolType() == null ? ToolType.AXE : getToolType();
		
		if(!isUsed())
		{
			head = new IToolUsableItem()
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
			};
			
			stick = new IToolUsableItem()
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
			};
		}
		
		stats.setDurabilityBase(head.getDurability(Part.HEAD, toolType) + stick.getDurability(Part.STICK, toolType));
		stats.setAttackDamageBase(head.getAttackDamage(Part.HEAD, toolType));
		stats.setHarvestLevelBase(head.getHarvestLevel(toolType));
		stats.setDestroySpeedBase(head.getDestroySpeed(Part.HEAD, toolType));
		
		ArrayList<IModifier> modifiers = getModifiers(head, stick);
		
		for(IModifier modifier : modifiers)
		{
			modifier.apply(commonTool, stats, itemStack);
		}
		
		return stats;
	}
}
