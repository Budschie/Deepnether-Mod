package de.budschie.deepnether.capabilities;

import java.util.ArrayList;

import de.budschie.deepnether.item.CommonTool;
import de.budschie.deepnether.item.toolModifiers.IModifier;
import de.budschie.deepnether.item.toolModifiers.IToolUsableItem;
import de.budschie.deepnether.item.toolModifiers.Stats;
import de.budschie.deepnether.item.toolModifiers.IToolUsableItem.Part;
import net.minecraft.command.impl.TellRawCommand;
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
	
	void setHead(IToolUsableItem head);
	
	void setStick(IToolUsableItem stick);
	
	void setToolType(ToolType toolType);
	
	public default Stats constructStats(CommonTool commonTool, ItemStack itemStack)
	{
		Stats stats = new Stats();
		
		stats.setDurabilityBase(getHead().getDurability(Part.HEAD, getToolType()) + getStick().getDurability(Part.STICK, getToolType()));
		stats.setAttackDamageBase(getHead().getAttackDamage(Part.HEAD, getToolType()));
		stats.setHarvestLevelBase(getHead().getHarvestLevel(getToolType()));
		stats.setDestroySpeedBase(getHead().getDestroySpeed(Part.HEAD, getToolType()));
		
		ArrayList<IModifier> modifiers = getModifiers();
		
		for(IModifier modifier : modifiers)
		{
			modifier.apply(commonTool, stats, itemStack);
		}
		
		return stats;
	}
}
