package de.budschie.deepnether.item;

import de.budschie.deepnether.main.References;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class ArmorSet
{
	String materialSuffix;
	RegistryObject<ArmorItem> helmet;
	RegistryObject<ArmorItem> chest;
	RegistryObject<ArmorItem> leggings;
	RegistryObject<ArmorItem> boots;
	
	public ArmorSet(DeferredRegister<Item> deferredRegister, String materialSuffix, IArmorMaterial material, Properties armorProps)
	{
		this.materialSuffix = materialSuffix;
		this.helmet = deferredRegister.register("helmet_"+materialSuffix, () -> new ArmorItem(material, EquipmentSlotType.HEAD, armorProps));
		this.chest = deferredRegister.register("chest_"+materialSuffix, () -> new ArmorItem(material, EquipmentSlotType.CHEST, armorProps));
		this.leggings = deferredRegister.register("leggings_"+materialSuffix, () -> new ArmorItem(material, EquipmentSlotType.LEGS, armorProps));
		this.boots = deferredRegister.register("boots_"+materialSuffix, () -> new ArmorItem(material, EquipmentSlotType.FEET, armorProps));
	}
	
	public String getMaterialSuffix()
	{
		return materialSuffix;
	}
	
	public RegistryObject<ArmorItem> getHelmet()
	{
		return helmet;
	}
	
	public RegistryObject<ArmorItem> getBoots()
	{
		return boots;
	}
	
	public RegistryObject<ArmorItem> getChest()
	{
		return chest;
	}
	
	public RegistryObject<ArmorItem> getLeggings()
	{
		return leggings;
	}
}
