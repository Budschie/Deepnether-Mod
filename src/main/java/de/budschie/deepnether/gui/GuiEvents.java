package de.budschie.deepnether.gui;

import de.budschie.deepnether.networking.StructureIDPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

//@EventBusSubscriber(value = Dist.CLIENT)
public class GuiEvents
{
	//@SubscribeEvent
	public static void onDrawGui(RenderGameOverlayEvent.Post event)
	{
		//Minecraft.getInstance().fontRenderer.func_238411_a_(Integer.valueOf(StructureIDPacket.currentId).toString(), 500, 250, 15218744);
	}
}
