package de.budschie.deepnether.item.toolModifiers;

import com.google.gson.JsonObject;

import de.budschie.deepnether.item.CommonTool;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TextFormatting;

public class AttackDamageModifier implements IModifier
{
	private float amount;
	private Operation operation;
	
	public AttackDamageModifier(Operation operation, float amount)
	{
		this.amount = amount;
		this.operation = operation;
	}
	
	@Override
	public void apply(CommonTool commonTool, Stats stats, ItemStack itemStack)
	{
		switch (operation)
		{
		case ADDITION:
			stats.addAttackDamage(amount);
			break;
		case MULTIPLY_BASE:
			stats.multiplyAttackDamageBase(amount);
		case MULTIPLY_TOTAL:
			stats.multiplyAttackDamageTotal(amount);
		default:
			break;
		}
	}

	@Override
	public String getEffectDescription()
	{
		String verb;
		String preposition;
		String value;
		
		if(operation == Operation.ADDITION)
		{
			if(amount < 0)
			{
				verb = "decreased";
				preposition = "by";
				value = Float.valueOf(amount*-1).toString();
			}
			else
			{
				verb = "increased";
				preposition = "by";
				value = Float.valueOf(amount).toString();
			}
		}
		else
		{
			if(amount < 1)
			{
				verb = "decreased";
				preposition = "by";
				value = Float.valueOf(100 - amount * 100).toString() + "%";
			}
			else
			{
				verb = "increased";
				preposition = "by";
				value = Float.valueOf(100 - amount * 100).toString() + "%";
			}
		}
		
		return getColor() + "Attack damage gets " + verb + " " + preposition + " " + TextFormatting.BOLD + value;
	}

	@Override
	public BalanceType getBalanceType()
	{
		if(operation == Operation.ADDITION)
			return amount < 0 ? BalanceType.NERF : BalanceType.BUFF;
		else
			return amount < 1 ? BalanceType.NERF : BalanceType.BUFF;
	}
}
