package de.budschie.deepnether.item.toolModifiers;

import java.util.ArrayList;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

public interface IToolUsableItem
{
	public enum Part {STICK, HEAD}
	
	/** The returned var must be final **/
	public ArrayList<IModifier> getModifiers(Part part, ToolType type);
	public int getDurability(Part part, ToolType type);
	public float getAttackDamage(Part part, ToolType toolType);
	
	public float getDestroySpeed(Part part, ToolType toolType);
	
	public int getHarvestLevel(ToolType toolType);
	
	public void setBoundItem(String string);
	public String getBoundItem();
	
	public String getImage(Part part, ToolType toolType);
}
