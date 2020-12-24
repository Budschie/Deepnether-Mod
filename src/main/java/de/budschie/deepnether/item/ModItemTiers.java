package de.budschie.deepnether.item;

import net.minecraft.item.crafting.Ingredient;

public class ModItemTiers
{
	public static final ModItemTier CNR_TIER = new ModItemTier()
	{
		@Override
		public Ingredient getRepairMaterial() 
		{
			return Ingredient.EMPTY;
		}
		
		@Override
		public int getMaxUses() 
		{
			return 500;
		}
		
		@Override
		public int getHarvestLevel() 
		{
			return 2;
		}
		
		@Override
		public int getEnchantability() 
		{
			return 15;
		}
		
		@Override
		public float getEfficiency() 
		{
			return 1.75F;
		}
		
		@Override
		public float getAttackDamage() 
		{
			return 5.5F;
		}
	};
	
	public static final ModItemTier DYL_TIER = new ModItemTier()
	{
		@Override
		public Ingredient getRepairMaterial() 
		{
			return Ingredient.EMPTY;
		}
		
		@Override
		public int getMaxUses() 
		{
			return 750;
		}
		
		@Override
		public int getHarvestLevel() 
		{
			return 1;
		}
		
		@Override
		public int getEnchantability() 
		{
			return 15;
		}
		
		@Override
		public float getEfficiency() 
		{
			return 1.5F;
		}
		
		@Override
		public float getAttackDamage() 
		{
			return 3.0F;
		}
	};
}
