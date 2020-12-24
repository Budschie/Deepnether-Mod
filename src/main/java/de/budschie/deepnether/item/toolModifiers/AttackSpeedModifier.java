package de.budschie.deepnether.item.toolModifiers;

import de.budschie.deepnether.item.CommonTool;
import de.budschie.deepnether.item.toolModifiers.Stats.AttributeTulpel;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class AttackSpeedModifier implements IModifier
{
	private float amount;
	private Operation operation;
	
	public AttackSpeedModifier(Operation operation, float amount)
	{
		this.amount = amount;
		this.operation = operation;
	}
	
	@Override
	public void apply(CommonTool commonTool, Stats stats, ItemStack itemStack)
	{
		//itemStack.addAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier("name", amount, operation), EquipmentSlotType.MAINHAND);
		AttributeTulpel tulpel = new AttributeTulpel();
		tulpel.modifier = new AttributeModifier("name", amount, operation);
		
		//Attack Speed
		tulpel.name = Attributes.ATTACK_SPEED;
		
		stats.addAttribute(tulpel);
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
		
		return getColor() + "Attack speed gets " + verb + " " + preposition + " " + TextFormatting.BOLD + value;
	}

	@Override
	public BalanceType getBalanceType()
	{
		return BalanceType.UNDEFINED;
	}
}
