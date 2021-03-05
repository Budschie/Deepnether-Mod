package de.budschie.deepnether.commands;

import de.budschie.deepnether.main.References;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.MODID, bus = Bus.FORGE)
public class CommandRegistry
{
	@SubscribeEvent
	public static void onRegisterCommands(RegisterCommandsEvent event)
	{
    	CommandToggleSel.register(event.getDispatcher());
    	CommandSaveSelection.registerCommandTpDim(event.getDispatcher());
    	CommandPlaceStructure.registerCommandTpDim(event.getDispatcher());
    	CommandPlaceTree.register(event.getDispatcher());
    	CommandPlayIntro.register(event.getDispatcher());
	}
}
