package de.budschie.deepnether.item.toolModifiers;

import de.budschie.deepnether.item.CommonTool;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public interface IModifier
{
	public enum BalanceType { BUFF, NERF, UNDEFINED }
	
	public default void apply(CommonTool commonTool, Stats stats, ItemStack itemStack)
	{
		
	}
	
	public String getEffectDescription();
	
	public BalanceType getBalanceType();
	
	public default String getColor()
	{
		return getBalanceType() == BalanceType.BUFF ? TextFormatting.GREEN.toString() : (getBalanceType() == BalanceType.NERF ? TextFormatting.RED.toString() : TextFormatting.YELLOW.toString());
	}
}
