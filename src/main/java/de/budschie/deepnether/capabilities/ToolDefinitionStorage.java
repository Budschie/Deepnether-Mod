package de.budschie.deepnether.capabilities;

import de.budschie.deepnether.item.ToolUsableItemRegistry;
import de.budschie.deepnether.item.toolModifiers.IToolUsableItem;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ToolDefinitionStorage implements IStorage<IToolDefinition>
{

	@Override
	public INBT writeNBT(Capability<IToolDefinition> capability, IToolDefinition instance, Direction side)
	{
		CompoundNBT compound = new CompoundNBT();
		if(instance.isUsed())
			compound.putString("toolType", instance.getToolType().getName());
		if(instance.isUsed())
			compound.putString("head", instance.getHead().getBoundItem());
		if(instance.isUsed())
			compound.putString("stick", instance.getStick().getBoundItem());
		return compound;
	}

	@Override
	public void readNBT(Capability<IToolDefinition> capability, IToolDefinition instance, Direction side, INBT nbt)
	{
		CompoundNBT compound = (CompoundNBT) nbt;
		if(compound.contains("toolType"))
			instance.setToolType(ToolType.get(compound.getString("toolType")));
		ToolUsableItemRegistry.get(compound.getString("head")).ifPresent((usable) -> instance.setHead(usable));
		ToolUsableItemRegistry.get(compound.getString("stick")).ifPresent((usable) -> instance.setStick(usable));
	}
}
