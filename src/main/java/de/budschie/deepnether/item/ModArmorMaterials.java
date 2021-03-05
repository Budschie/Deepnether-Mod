package de.budschie.deepnether.item;

import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.main.References;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class ModArmorMaterials
{
	public static final ModArmorMaterial CNR_ARMOR_MATERIAL = new ModArmorMaterial()
	{
		@Override
		public float getToughness()
		{
			return 0;
		}
		
		@Override
		public SoundEvent getSoundEvent()
		{
			return SoundEvents.ITEM_ARMOR_EQUIP_CHAIN;
		}
		
		@Override
		public Ingredient getRepairMaterial()
		{
			return Ingredient.fromStacks(new ItemStack(BlockInit.COMPRESSED_NETHERRACK.asItem(), 1));
		}
		
		@Override
		public String getName()
		{
			return References.MODID+":armor_material_cnr";
		}
		
		@Override
		public int getEnchantability()
		{
			return 0;
		}
		
		@Override
		public int getDurability(EquipmentSlotType slotIn)
		{
			return 225;
		}
		
		@Override
		public int getDamageReductionAmount(EquipmentSlotType slotIn)
		{
			switch (slotIn)
			{
			case CHEST:
				return 5;
			case FEET:
				return 1;
			case LEGS:
				return 4;
			case HEAD:
				return 2;
			default:
				return 0;
			}
		}
		
		@Override
		public float getKnockbackResistance()
		{
			return 0;
		}
	};
	
	public static final ModArmorMaterial DYL_ARMOR_MATERIAL = new ModArmorMaterial()
	{
		@Override
		public float getToughness()
		{
			return 2;
		}
		
		@Override
		public SoundEvent getSoundEvent()
		{
			return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
		}
		
		@Override
		public Ingredient getRepairMaterial()
		{
			return Ingredient.EMPTY;
		}
		
		@Override
		public String getName()
		{
			return References.MODID+":armor_material_dyl";
		}
		
		@Override
		public int getEnchantability()
		{
			return 50;
		}
		
		@Override
		public int getDurability(EquipmentSlotType slotIn)
		{
			return 1000;
		}
		
		@Override
		public int getDamageReductionAmount(EquipmentSlotType slotIn)
		{
			switch (slotIn)
			{
			case CHEST:
				return 3;
			case FEET:
				return 2;
			case LEGS:
				return 3;
			case HEAD:
				return 2;
			default:
				return 0;
			}
		}

		public float getKnockbackResistance() 
		{
			return 0f;
		}
	};
}
