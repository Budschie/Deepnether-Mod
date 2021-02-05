package de.budschie.deepnether.item;

import de.budschie.deepnether.main.References;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class ToolGroup 
{
	RegistryObject<SwordItem> swordItem;
	RegistryObject<PickaxeItem> pickaxeItem;
	RegistryObject<ShovelItem> shovelItem;
	RegistryObject<AxeItem> axeItem;
	
	String toolSuffix;
	
	/** Creates a new tool group the the given {@code toolSuffix} and the properties. **/
	public ToolGroup(DeferredRegister<Item> deferredRegister, String toolSuffix, IItemTier tierAll, int attackDmg, float attackSpeed, Properties propertiesSword, Properties propertiesPickaxe, Properties propertiesShovel, Properties propertiesAxe)
	{
		this(deferredRegister, toolSuffix, tierAll, new int[] {attackDmg, attackDmg, attackDmg, attackDmg}, new float[] {attackSpeed, attackSpeed, attackSpeed, attackSpeed}, propertiesSword, propertiesPickaxe, propertiesShovel, propertiesAxe);
	}
	
	/** Creates a new tool group the the given {@code toolSuffix} and the properties. Indices for {@code attackDmg} and {@code attackSpeed}:<br><b>Sword</b>: 0<br><b>Pickaxe</b>: 1<br><b>Shovel</b>: 2<br><b>Axe</b>: 3**/
	public ToolGroup(DeferredRegister<Item> deferredRegister, String toolSuffix, IItemTier tierAll, int[] attackDmg, float[] attackSpeed, Properties propertiesSword, Properties propertiesPickaxe, Properties propertiesShovel, Properties propertiesAxe)
	{
		this.toolSuffix = toolSuffix;
		
		this.swordItem = deferredRegister.register("sword_" + toolSuffix, () -> new SwordItem(tierAll, attackDmg[0], attackSpeed[0], propertiesSword));
		this.pickaxeItem = deferredRegister.register("pickaxe_" + toolSuffix, () -> new PickaxeItem(tierAll, attackDmg[1], attackSpeed[1], propertiesPickaxe));
		this.shovelItem = deferredRegister.register("axe_" + toolSuffix, () -> new ShovelItem(tierAll, attackDmg[2], attackSpeed[2], propertiesPickaxe));
		this.axeItem = deferredRegister.register("shovel_" + toolSuffix, () -> new AxeItem(tierAll, attackDmg[3], attackSpeed[3], propertiesPickaxe));
	}
	
	public RegistryObject<AxeItem> getAxeItem()
	{
		return axeItem;
	}
	
	public RegistryObject<PickaxeItem> getPickaxeItem()
	{
		return pickaxeItem;
	}
	
	public RegistryObject<ShovelItem> getShovelItem()
	{
		return shovelItem;
	}
	
	public RegistryObject<SwordItem> getSwordItem()
	{
		return swordItem;
	}
	
	public String getToolSuffix()
	{
		return toolSuffix;
	}
}
