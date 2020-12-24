package de.budschie.deepnether.item.toolModifiers;

import java.util.ArrayList;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;

public class Stats
{
	private float attackDamageBase;
	private int durabilityBase;
	private int harvestLevelBase;
	private float destroySpeedBase;
	
	private float attackDamage;
	private int durability;
	private int harvestLevel;
	private float destroySpeed;
	
	ArrayList<AttributeTulpel> list = new ArrayList<>();
	
	public ArrayList<AttributeTulpel> getAttributesToApply()
	{
		return list;
	}
	
	public void addAttribute(AttributeTulpel tulpel)
	{
		list.add(tulpel);
	}
	
	public void setAttackDamageBase(float attackDamage)
	{
		this.attackDamageBase = attackDamage;
		this.attackDamage = attackDamage;
	}
	
	public void setHarvestLevelBase(int harvestLevel)
	{
		this.harvestLevelBase = harvestLevel;
		this.harvestLevel = harvestLevel;
	}
	
	public void setDurabilityBase(int durability)
	{
		this.durabilityBase = durability;
		this.durability = durability;
	}
	
	public void setDestroySpeedBase(float destroySpeed)
	{
		this.destroySpeed = destroySpeed;
		this.destroySpeedBase = destroySpeed;
	}
	
	public void addAttackDamage(float value)
	{
		this.attackDamage += value;
	}
	
	public void addDestroySpeed(float value)
	{
		this.destroySpeed += value;
	}
	
	public void addDurability(int value)
	{
		this.durability += value;
	}
	
	public void addHarvestLevel(int value)
	{
		this.harvestLevel += value;
	}
	
	public void multiplyAttackDamageBase(float value)
	{
		this.attackDamage += ((this.attackDamageBase * value) - this.attackDamageBase);
	}
	
	public void multiplyDestroySpeedBase(float value)
	{
		this.destroySpeed += ((this.destroySpeedBase * value) - this.destroySpeedBase);
	}
	
	public void multiplyDurabilityBase(float value)
	{
		this.durability += ((this.durabilityBase * value) - this.durabilityBase);
	}
	
	public void multiplyHarvestLevelBase(int value)
	{
		this.harvestLevel += ((this.harvestLevelBase * value) - this.harvestLevelBase);
	}
	
	public void multiplyAttackDamageTotal(float value)
	{
		this.attackDamage *= value;
	}
	
	public void multiplyDestroySpeedTotal(float value)
	{
		this.destroySpeed *= value;
	}
	
	public void multiplyDurabilityTotal(float value)
	{
		this.durability *= value;
	}
	
	public void multiplyHarvestLevelTotal(int value)
	{
		this.harvestLevel *= value;
	}
	
	public float getAttackDamage()
	{
		return attackDamage;
	}
	
	public int getDurability()
	{
		return durability;
	}
	
	public int getHarvestLevel()
	{
		return harvestLevel;
	}
	
	public float getDestroySpeed()
	{
		return destroySpeed;
	}
	
	public static class AttributeTulpel
	{
		public Operation op;
		public Attribute name;
		public AttributeModifier modifier;
	}
}