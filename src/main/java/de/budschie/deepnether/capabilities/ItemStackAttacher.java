package de.budschie.deepnether.capabilities;

import de.budschie.deepnether.item.CommonTool;
import de.budschie.deepnether.main.References;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class ItemStackAttacher
{
	@SubscribeEvent
	public static void onAttachCapability(AttachCapabilitiesEvent<ItemStack> event)
	{
		if(event.getObject().getItem() instanceof CommonTool)
			event.addCapability(new ResourceLocation(References.MODID, "tool_def_cap"), new ToolDefinitionCapability());
	}
}
