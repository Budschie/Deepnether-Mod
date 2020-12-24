package de.budschie.deepnether.item;

import de.budschie.deepnether.main.References;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;

public class ToolGroup 
{
	SwordItem swordItem;
	PickaxeItem pickaxeItem;
	ShovelItem shovelItem;
	AxeItem axeItem;
	
	String toolSuffix;
	
	/** Creates a new tool group the the given {@code toolSuffix} and the properties. **/
	public ToolGroup(String toolSuffix, IItemTier tierAll, int attackDmg, float attackSpeed, Properties propertiesSword, Properties propertiesPickaxe, Properties propertiesShovel, Properties propertiesAxe)
	{
		this.toolSuffix = toolSuffix;
		
		swordItem = new SwordItem(tierAll, attackDmg, attackSpeed, propertiesSword);
		pickaxeItem = new PickaxeItem(tierAll, attackDmg, attackSpeed, propertiesPickaxe);
		shovelItem = new ShovelItem(tierAll, attackDmg, attackSpeed, propertiesPickaxe);
		axeItem = new AxeItem(tierAll, attackDmg, attackSpeed, propertiesPickaxe);
		
		swordItem.setRegistryName(new ResourceLocation(References.MODID, "sword_"+toolSuffix));
		pickaxeItem.setRegistryName(new ResourceLocation(References.MODID, "pickaxe_"+toolSuffix));
		axeItem.setRegistryName(new ResourceLocation(References.MODID, "axe_"+toolSuffix));
		shovelItem.setRegistryName(new ResourceLocation(References.MODID, "shovel_"+toolSuffix));
		
		ItemInit.MOD_ITEMS.add(swordItem);
		ItemInit.MOD_ITEMS.add(pickaxeItem);
		ItemInit.MOD_ITEMS.add(axeItem);
		ItemInit.MOD_ITEMS.add(shovelItem);
	}
	
	/** Creates a new tool group the the given {@code toolSuffix} and the properties. Indices for {@code attackDmg} and {@code attackSpeed}:<br><b>Sword</b>: 0<br><b>Pickaxe</b>: 1<br><b>Shovel</b>: 2<br><b>Axe</b>: 3**/
	public ToolGroup(String toolSuffix, IItemTier tierAll, int[] attackDmg, float[] attackSpeed, Properties propertiesSword, Properties propertiesPickaxe, Properties propertiesShovel, Properties propertiesAxe)
	{
		this.toolSuffix = toolSuffix;
		
		swordItem = new SwordItem(tierAll, attackDmg[0], attackSpeed[0], propertiesSword);
		pickaxeItem = new PickaxeItem(tierAll, attackDmg[1], attackSpeed[1], propertiesPickaxe);
		shovelItem = new ShovelItem(tierAll, attackDmg[2], attackSpeed[2], propertiesPickaxe);
		axeItem = new AxeItem(tierAll, attackDmg[3], attackSpeed[3], propertiesPickaxe);
		
		swordItem.setRegistryName(new ResourceLocation(References.MODID, "sword_"+toolSuffix));
		pickaxeItem.setRegistryName(new ResourceLocation(References.MODID, "pickaxe_"+toolSuffix));
		axeItem.setRegistryName(new ResourceLocation(References.MODID, "axe_"+toolSuffix));
		shovelItem.setRegistryName(new ResourceLocation(References.MODID, "shovel_"+toolSuffix));
		
		ItemInit.MOD_ITEMS.add(swordItem);
		ItemInit.MOD_ITEMS.add(pickaxeItem);
		ItemInit.MOD_ITEMS.add(axeItem);
		ItemInit.MOD_ITEMS.add(shovelItem);
	}
	
	public AxeItem getAxeItem() 
	{
		return axeItem;
	}
	
	public ShovelItem getShovelItem() 
	{
		return shovelItem;
	}
	
	public SwordItem getSwordItem() 
	{
		return swordItem;
	}
	
	public PickaxeItem getPickaxeItem() 
	{
		return pickaxeItem;
	}
}
