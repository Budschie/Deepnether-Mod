package de.budschie.deepnether.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ToolDefinitionCapability implements ICapabilitySerializable<CompoundNBT>
{
	@CapabilityInject(IToolDefinition.class)
	public static final Capability<IToolDefinition> TOOL_DEF_CAP = null;
	
	LazyOptional<IToolDefinition> toolDefinitionProvider = LazyOptional.of(TOOL_DEF_CAP::getDefaultInstance);
	
	public static void register()
	{
		CapabilityManager.INSTANCE.register(IToolDefinition.class, new ToolDefinitionStorage(), () -> new ToolDefinition(null, null, null)); 
	}
	
	public ToolDefinitionCapability()
	{
		if(TOOL_DEF_CAP == null)
			throw new IllegalStateException();
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		return TOOL_DEF_CAP.orEmpty(cap, toolDefinitionProvider);
	}

	@Override
	public CompoundNBT serializeNBT()
	{
		return (CompoundNBT) TOOL_DEF_CAP.getStorage().writeNBT(TOOL_DEF_CAP, toolDefinitionProvider.orElseGet(null), null);
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		TOOL_DEF_CAP.getStorage().readNBT(TOOL_DEF_CAP, toolDefinitionProvider.orElseGet(null), null, nbt);
	}
}
