package de.budschie.deepnether.item;

import de.budschie.deepnether.main.References;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.ResourceLocation;

public class ArmorSet
{
	String materialSuffix;
	ArmorItem helmet;
	ArmorItem chest;
	ArmorItem leggings;
	ArmorItem boots;
	
	public ArmorSet(String materialSuffix, IArmorMaterial material, Properties armorProps)
	{
		this.materialSuffix = materialSuffix;
		
		helmet = new ArmorItem(material, EquipmentSlotType.HEAD, armorProps);
		chest = new ArmorItem(material, EquipmentSlotType.CHEST, armorProps);
		leggings = new ArmorItem(material, EquipmentSlotType.LEGS, armorProps);
		boots = new ArmorItem(material, EquipmentSlotType.FEET, armorProps);
		
		helmet.setRegistryName(new ResourceLocation(References.MODID, "helmet_"+materialSuffix));
		chest.setRegistryName(new ResourceLocation(References.MODID, "chest_"+materialSuffix));
		leggings.setRegistryName(new ResourceLocation(References.MODID, "leggings_"+materialSuffix));
		boots.setRegistryName(new ResourceLocation(References.MODID, "boots_"+materialSuffix));
		
		ItemInit.MOD_ITEMS.add(helmet);
		ItemInit.MOD_ITEMS.add(chest);
		ItemInit.MOD_ITEMS.add(leggings);
		ItemInit.MOD_ITEMS.add(boots);
	}
}
